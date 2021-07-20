package com.web.curation.dao.reply;

import com.web.curation.model.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyDao extends JpaRepository<Reply, String> {
    Optional<Reply> findReplyByRid(Long rid);
}
