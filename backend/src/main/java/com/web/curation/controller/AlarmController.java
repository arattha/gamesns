package com.web.curation.controller;

import com.web.curation.model.follow.Following;
import com.web.curation.model.alarm.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

@Controller
public class AlarmController {

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
        addFollow(fromUser, toUser);

        System.out.println(FollowAlarm);

        // follow 관련 db 에 넣어주기 위해 Long 타입으로 변환
//        Long fromid = Long.parseLong(fromUser);
//        Long toid = Long.parseLong(toUser);

        // 생성자로 반환값을 생성합니다.
//        Following result = new Following();
//        result.setFrom(fromid);
//        result.setTo(toid);

        // 반환
        String[] result = new String[FollowAlarm.get(toUser).size()];
        return FollowAlarm.get(toUser).toArray(result);
    }

    private void addFollow(String fromUser, String toUser) {
        // FollowAlarm 에 newUser 가 있으면 해당 리스트를 가져오고 없으면 새로운 리스트 생성
        Set<String> users = FollowAlarm.get(toUser) != null ? FollowAlarm.get(toUser) : new HashSet<>();
        System.out.print("set : ");
        System.out.println(users);
        users.add(fromUser);
        FollowAlarm.put(toUser, users);
    }

}