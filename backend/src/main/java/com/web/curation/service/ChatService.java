package com.web.curation.service;

import com.web.curation.dao.chat.ChatRoomDao;
import com.web.curation.dao.chat.ChatRoomJoinDao;
import com.web.curation.dao.member.MemberDao;
import com.web.curation.model.OAuthToken;
import com.web.curation.model.chat.ChatRoom;
import com.web.curation.model.chat.ChatRoomJoin;
import com.web.curation.model.member.ImgRequest;
import com.web.curation.model.member.Member;
import com.web.curation.model.member.SignupRequest;
import com.web.curation.util.SecurityUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ChatService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    ChatRoomDao chatRoomDao;

    @Autowired
    ChatRoomJoinDao chatRoomJoinDao;

    public Long newRoom(String user1, String user2) {
        Long ret = check(user1,user2);
        if(ret != 0){
            //이미 존재하는 방이면 해당 방 번호 리턴
            return ret;
        }
        ChatRoom chatRoom = new ChatRoom();
        chatRoomDao.save(chatRoom);
        if(user1.equals(user2)){
            //나 자신과의 채팅은 한명만 존재
            createRoom(user1, chatRoom);
        }
        else{
            //두명 다 입장
            createRoom(user1, chatRoom);
            createRoom(user2, chatRoom);
        }
        return chatRoom.getRoom_id();
    }

    public Long check(String user1,String user2){

        // user1 의 아이디를 갖고 있는 채팅방 Set 가져오기
        Member userFirst = memberDao.findMemberByNickname(user1).get();
        List<ChatRoomJoin> listFirst = chatRoomDao.findByUserId(userFirst.getUid());
        Set<ChatRoom> setFirst = new HashSet<>();
        for(ChatRoomJoin chatRoomJoin : listFirst){
            setFirst.add(chatRoomJoin.getChatRoom());
        }

        // user2 의 아이디를 갖고 있는 채팅방 Set 가져오기
        Member userSecond = memberDao.findMemberByNickname(user2).get();
        List<ChatRoomJoin> listSecond = chatRoomDao.findByUserId(userSecond.getUid());
        for(ChatRoomJoin chatRoomJoin : listSecond){

            // 두 Set 을 비교해서 같은 채팅방이 있으면 그 채팅방의 아이디를 반환
            if(setFirst.contains(chatRoomJoin.getChatRoom())){
                return chatRoomJoin.getChatRoom().getRoom_id();
            }
        }

        // 두 user 가 속해 있는 채팅방이 없으면 0 반환 => 신규 채팅방 개설
        return 0L;
    }

    public void createRoom(String user, ChatRoom chatRoom){
        ChatRoomJoin chatRoomJoin = new ChatRoomJoin();
        chatRoomJoin.setChatRoom(chatRoom);
        chatRoomJoin.setUser(memberDao.findMemberByNickname(user).get());
        chatRoomJoinDao.save(chatRoomJoin);
    }

    public Optional<ChatRoom> findByRoomId(Long chatRoomId) {

        return chatRoomDao.findByRoomId(chatRoomId);
    }
}
