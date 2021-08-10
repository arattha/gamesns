package com.web.curation.model.chat;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.*;

@Entity
@Data
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long room_id;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();
}
