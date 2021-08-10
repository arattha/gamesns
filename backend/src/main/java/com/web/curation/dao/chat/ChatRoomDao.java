package com.web.curation.dao.chat;

import com.web.curation.model.chat.ChatRoom;
import com.web.curation.model.chat.ChatRoomJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomDao extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findByRoomId(Long room_id);

    List<ChatRoomJoin> findByUserId(String user_id);

}
