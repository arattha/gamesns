package com.web.curation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.web.curation.model.matching.MatchingMessage;
import com.web.curation.model.matching.MatchingRequest;
import com.web.curation.model.matching.MatchingResponse;
import com.web.curation.model.matching.MessageType;
import com.web.curation.service.MatchingService;
import com.web.curation.util.ServletUtil;

@RestController
@CrossOrigin("*")
@RequestMapping("/matching")
public class MatchingController {

    private static final Logger logger = LoggerFactory.getLogger(MatchingController.class);

    @Autowired
    private MatchingService matchingService;

    // tag :: async
    @GetMapping("/join")//채팅방 요청.
    @ResponseBody
    public DeferredResult<MatchingResponse> joinRequest(@RequestParam String gameName, @RequestParam String uid) {
        String sessionId = ServletUtil.getSession().getId();
        logger.info(">> Join request. session id : {}", sessionId);
        System.out.println("조인 들어올 때" + gameName+" " + sessionId);
        final MatchingRequest user = new MatchingRequest(sessionId,gameName,uid); //게임 이름을 합쳐서 새로운 생성
        final DeferredResult<MatchingResponse> deferredResult = new DeferredResult<>(null); //비동기 프로세스 생성.
        matchingService.joinChatRoom(user, deferredResult); //채팅룸 생성으로 보임. 정보확인 필요.

        deferredResult.onCompletion(() -> matchingService.cancelChatRoom(user));//에러 확인되면 채팅룸 닫기.
        deferredResult.onError((throwable) -> matchingService.cancelChatRoom(user));
        deferredResult.onTimeout(() -> matchingService.timeout(user));

        return deferredResult;
    }

    @GetMapping("/cancel")
    @ResponseBody
    public ResponseEntity<Void> cancelRequest(@RequestParam String gameName, @RequestParam String uid) {
        String sessionId = ServletUtil.getSession().getId();
        	
        logger.info(">> Cancel request. session id : {}", sessionId);
        //System.out.println("캔슬 들어올 때" + gameName+" " + sessionId);
        final MatchingRequest user = new MatchingRequest(sessionId,gameName,uid);
        matchingService.cancelChatRoom(user);

        return ResponseEntity.ok().build();
    }

    // -- tag :: async
    // tag :: websocket stomp
    @MessageMapping("/chat.message/{matchingRoomId}")// 채팅하는 곳.
    public void sendMessage(@DestinationVariable("matchingRoomId") String matchingRoomId, @Payload MatchingMessage matchingMessage) {
        //logger.info("Request message. roomd id : {} | chat message : {} | principal : {}", chatRoomId, chatMessage);
        if (!StringUtils.hasText(matchingRoomId) || matchingMessage == null) {
            return;
        }
        
        if (matchingMessage.getMessageType() == MessageType.CHAT) {
            matchingService.sendMessage(matchingRoomId, matchingMessage);
        }
    }
    // -- tag :: websocket stomp
}