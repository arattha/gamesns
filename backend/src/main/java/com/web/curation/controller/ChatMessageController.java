package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.chat.ChatMessage;
import com.web.curation.model.chat.ChatRoom;
import com.web.curation.model.member.Member;
import com.web.curation.service.ChatService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@Controller
@CrossOrigin("*")
@RequestMapping("/chat")
public class ChatMessageController {

    @Autowired
    ChatService chatService;

    @PostMapping("/chat/newChat")
    public String newChat(@RequestParam("receiver") String user1, @RequestParam("sender") String user2){
        Long chatRoomId = chatService.newRoom(user1,user2);
        return "redirect:/personalChat/" + chatRoomId;
    }

    @RequestMapping("/personalChat/{chatRoomId}")
    public Object goChat(@PathVariable("chatRoomId") Long chatRoomId) {

        final BasicResponse result = new BasicResponse();

        result.status = true;
//        result.object = member.get().getNickname();

        Optional<ChatRoom> opt = chatService.findByRoomId(chatRoomId);
        if(opt.isPresent()) {
            ChatRoom chatRoom = opt.get();

            List<ChatMessage> messages = chatRoom.getMessages();
            Collections.reverse(messages);

            result.data = "success";
            result.object = messages;

            return new ResponseEntity<>(result, HttpStatus.OK);

        } else {

            result.data = "fail";

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

}
