package com.web.curation.dao.reply;

import com.web.curation.model.reply.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyDao extends JpaRepository<Reply, String> {
}
