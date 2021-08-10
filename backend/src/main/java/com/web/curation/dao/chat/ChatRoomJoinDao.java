package com.web.curation.dao.chat;

import com.web.curation.model.chat.ChatRoomJoin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJoinDao extends JpaRepository<ChatRoomJoin, String> {

}
