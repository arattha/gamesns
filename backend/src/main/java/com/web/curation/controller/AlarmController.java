package com.web.curation.controller;

import com.web.curation.model.alarm.SocketVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/alarm")
public class AlarmController {

    // member 별 팔로우 알람을 위한 Map
    Map<String, Set<String>> FollowAlarm = new HashMap<String, Set<String>>();
//        put("조용일", new HashSet<>(Arrays.asList("최광진", "조성표", "3")));
//
//    }};

    @MessageMapping("/first")
    @SendTo("/firstSend")
    public String[] first(){
        System.out.println("hihihi");
        Set<String> u = new HashSet<>();
        u.add("조성표");
        u.add("최광진");
        FollowAlarm.put("조용일", u);
        String[] result = new String[FollowAlarm.get("조용일").size()];
        return FollowAlarm.get("조용일").toArray(result);
    }

    // /receive를 메시지를 받을 endpoint로 설정합니다.
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")

    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
    public String[] SocketHandler(SocketVO socketVO) {

        System.out.println(socketVO);

        String fromMember = socketVO.getMemberName();
        String toMember = socketVO.getFollowingName();
        toMember = "조용일";
        System.out.println("from : " + fromMember + ", to : " + toMember);
        // FollowAlarm update
        updateFollow(fromMember, toMember);

        System.out.println(FollowAlarm);

        // 반환
        String[] result = new String[FollowAlarm.get(toMember).size()];

        return FollowAlarm.get(toMember).toArray(result);
    }

    private void updateFollow(String fromMember, String toMember) {
        // FollowAlarm 에 newMember 가 있으면 해당 리스트를 가져오고 없으면 새로운 리스트 생성
        Set<String> members = FollowAlarm.get(toMember) != null ? FollowAlarm.get(toMember) : new HashSet<>();

        // toMember 를 key로 갖고 있는 Map 이 있다면 팔로우 신청 목록에 fromMember가 있는지 확인한다.
        // (toMember, fromMember) 쌍이 똑같은게 있다면 flag 를 true 로 바꿔주고 Set 에서 삭제한다.
        boolean flag = false;
        if(members.size() != 0) {
            for (String u : members) {
                if(u.equals(fromMember)){
                    members.remove(fromMember);
                    flag = true;
                    break;
                }
            }
        }

        if(!flag) members.add(fromMember);

        FollowAlarm.put(toMember, members);
    }



}