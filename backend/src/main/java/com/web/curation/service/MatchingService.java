package com.web.curation.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.web.curation.model.matching.MatchingMessage;
import com.web.curation.model.matching.MatchingRequest;
import com.web.curation.model.matching.MatchingResponse;
import com.web.curation.model.matching.MatchingResponse.ResponseResult;
import com.web.curation.model.matching.MessageType;

@Service
public class MatchingService {

    private static final Logger logger = LoggerFactory.getLogger(MatchingService.class);
    
    private Map< String ,Map<MatchingRequest, DeferredResult<MatchingResponse>>> waitingQueue;
    
    
    // hashTable 사용 쓰레드 세이프하게 
    // watingUser (게임이름, SET)
    
    
    // {key : websocket session id, value : chat room id}
    private Map<String, String> connectedUsers;
    private ReentrantReadWriteLock lock;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    private void setUp() {
        //this.waitingUsers = new LinkedHashMap<>();//순서가 지켜지는
        this.lock = new ReentrantReadWriteLock();
        this.connectedUsers = new ConcurrentHashMap<>();
        this.waitingQueue = new Hashtable<String, Map<MatchingRequest,DeferredResult<MatchingResponse>>>();
    }

    @Async("asyncThreadPool")
    public void joinChatRoom(MatchingRequest request, DeferredResult<MatchingResponse> deferredResult) {
        logger.info("## Join chat room request. {}[{}]", Thread.currentThread().getName(), Thread.currentThread().getId());
        if (request == null || deferredResult == null) {
            return;
        }
        try {
            lock.writeLock().lock();
            if(waitingQueue.containsKey(request.getKey())) {//이미 생성된 키가 있으면
            	waitingQueue.get(request.getKey()).put(request,deferredResult);
            } else {
            	Map<MatchingRequest, DeferredResult<MatchingResponse>> newUserPool = new LinkedHashMap<>();
            	newUserPool.put(request, deferredResult);
            	waitingQueue.put(request.getKey(),newUserPool);
            }
            //waitingUsers.put(request, deferredResult);
        } finally {
            lock.writeLock().unlock();
            establishMatchingRoom(request);
        }
    }

    public void cancelChatRoom(MatchingRequest matchingRequest) {
        try {
            lock.writeLock().lock();
            setJoinResult(waitingQueue.get(matchingRequest.getKey()).remove(matchingRequest), 
            		new MatchingResponse(ResponseResult.CANCEL, null, matchingRequest.getSessionId()));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void timeout(MatchingRequest matchingRequest) {
        try {
            lock.writeLock().lock();
            setJoinResult(waitingQueue.get(matchingRequest.getKey()).remove(matchingRequest), 
            		new MatchingResponse(ResponseResult.TIMEOUT, null, matchingRequest.getSessionId()));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void establishMatchingRoom(MatchingRequest request) {//웨이팅 큐
        try {
            logger.debug("Current waiting users : " + waitingQueue.get(request.getKey()).size());
            lock.readLock().lock();
            if (waitingQueue.get(request.getKey()).size() < Integer.parseInt(request.getPeopleLimit())) {//유저가 특정수 이하 면 컷
                return;
            }
            
            //Discord -- >이곳에서 형한테 유저 정보 던져야함.

            Iterator<MatchingRequest> itr = waitingQueue.get(request.getKey()).keySet().iterator();
            
            List<MatchingRequest> roomUserKey = new ArrayList<>();
            List<DeferredResult<MatchingResponse>> roomUserValue = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
            	roomUserKey.add(itr.next());
			}
            
            //MatchingRequest user1 = itr.next();
            //MatchingRequest user2 = itr.next();

            String uuid = UUID.randomUUID().toString(); //채팅방 이름 생성.
            
            for (int i = 0; i < 2; i++) {
            	roomUserValue.add(waitingQueue.get(request.getKey()).remove(roomUserKey.get(i)));
			}
            
            //DeferredResult<MatchingResponse> user1Result = waitingUsers.remove(user1);
            //DeferredResult<MatchingResponse> user2Result = waitingUsers.remove(user2);
            
            
            for (int i = 0; i < 2; i++) {
            	roomUserValue.get(i).setResult(new MatchingResponse(ResponseResult.SUCCESS, uuid, roomUserKey.get(i).getSessionId(), roomUserKey));
			}
            
            //user1Result.setResult(new MatchingResponse(ResponseResult.SUCCESS, uuid, user1.getSessionId()));
            //user2Result.setResult(new MatchingResponse(ResponseResult.SUCCESS, uuid, user2.getSessionId()));
            
        } catch (Exception e) {
            logger.warn("Exception occur while checking waiting users", e);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void sendMessage(String matchingRoomId, MatchingMessage matchingMessage) {
        String destination = getDestination(matchingRoomId);
        messagingTemplate.convertAndSend(destination, matchingMessage);
    }

    public void connectUser(String matchingRoomId, String websocketSessionId) {
        connectedUsers.put(websocketSessionId, matchingRoomId);
    }

    public void disconnectUser(String websocketSessionId) {
    	System.out.println("종료 시도:"+connectedUsers.get(websocketSessionId));
        String matchingRoomId = connectedUsers.get(websocketSessionId);
        MatchingMessage matchingMessage = new MatchingMessage();

        matchingMessage.setMessageType(MessageType.DISCONNECTED);
        sendMessage(matchingRoomId, matchingMessage);
    }

    private String getDestination(String matchingRoomId) {
        return "/topic/chat/" + matchingRoomId;
    }

    private void setJoinResult(DeferredResult<MatchingResponse> result, MatchingResponse response) {
        if (result != null) {
            result.setResult(response);
        }
    }
}