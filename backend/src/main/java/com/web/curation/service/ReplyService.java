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
    private ReplyDao replyDao;

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

    public boolean update(Long rid, String nickname, String content) {
        // rid로 댓글을 검색하여 결과를 반환
        Optional<Reply> tempReply = replyDao.findReplyByRid(rid);
        // update가 성공했는지 여부를 반환
        boolean flag = false;

        // rid로 검색한 결과가 있을 경우에만 수행
        if (tempReply.isPresent()) {
            // rid로 검색한 댓글을 받아온다
            Reply reply = tempReply.get();

            // 만약 수정하고자 하는 닉네임과 기존 댓글을 작성한 닉네임이 같은 경우에만 수행
            if (reply.getNickname().equals(nickname)) {
                // 바뀐 내용을 넣어준다.
                reply.setContent(content);

                // 업데이트
                try {
                    replyDao.save(reply);
                    flag = true;
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        return flag;
    }
}
