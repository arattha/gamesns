package com.web.curation.service;

import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.reply.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReplyService {
    @Autowired
    ReplyDao replyDao;

    // 얻어오고자하는 댓글의 페이지를 얻어와 해당 페이지가 있는지를 검사
    public Slice<Reply> getReplyListByPages(Long bid, Long lastRid, int size) {
        Pageable pageRequest = PageRequest.of(0, size, Sort.Direction.ASC, "regDate");
        return replyDao.findByBidAndRidGreaterThan(bid, lastRid, pageRequest);
    }

    // bid에 해당하는 댓글들을 모아서 마지막 rid(lastRid) 이후의 숫자를 갖고있는
    // 즉, 나중에 등록된 댓글들을 등록된 날짜의 오름차순으로 가져온다.ㄱ
//    private Page<Reply> getReplyPages(Long bid, Long lastRid, int size) {
//        Pageable pageRequest = PageRequest.of(0, size, Sort.Direction.ASC, "regDate");
//        return replyDao.findByBidAndRidGreaterThan(bid, lastRid, pageRequest);
//    }

    public Reply insert(Long uid, Long bid, String nickname, String content) {
        Reply reply = new Reply();
        reply.setUid(uid);
        reply.setBid(bid);
        reply.setNickname(nickname);
        reply.setContent(content);

        Reply saveReply = replyDao.save(reply);

        return saveReply;
    }
}
