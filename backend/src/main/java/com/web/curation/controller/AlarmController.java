package com.web.curation.controller;

import com.web.curation.model.alarm.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class AlarmController {

    // user 별 팔로우 알람을 위한 Map
    Map<String, Set<String>> FollowAlarm = new TreeMap<>();

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")

    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public String[] SocketHandler(SocketVO socketVO) {

        System.out.println(socketVO);

        String fromUser = socketVO.getUserName();
        String toUser = socketVO.getFollowingName();

        // FollowAlarm update
        updateFollow(fromUser, toUser);

        System.out.println(FollowAlarm);

        // 반환
        String[] result = new String[FollowAlarm.get(toUser).size()];

        return FollowAlarm.get(toUser).toArray(result);
    }

    private void updateFollow(String fromUser, String toUser) {
        // FollowAlarm 에 newUser 가 있으면 해당 리스트를 가져오고 없으면 새로운 리스트 생성
        Set<String> users = FollowAlarm.get(toUser) != null ? FollowAlarm.get(toUser) : new HashSet<>();

        // toUser 를 key로 갖고 있는 Map 이 있다면 팔로우 신청 목록에 fromUser가 있는지 확인한다.
        // (toUser, fromUser) 쌍이 똑같은게 있다면 flag 를 true 로 바꿔주고 Set 에서 삭제한다.
        boolean flag = false;
        if(users.size() != 0) {
            for (String u : users) {
                if(u.equals(fromUser)){
                    users.remove(fromUser);
                    flag = true;
                    break;
                }
            }
        }

        if(!flag) users.add(fromUser);

        FollowAlarm.put(toUser, users);
    }



}