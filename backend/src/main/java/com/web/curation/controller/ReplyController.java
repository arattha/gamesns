package com.web.curation.controller;

import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyCreateRequest;
import com.web.curation.model.reply.ReplyUpdateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class ReplyController {

    @Autowired
    ReplyDao replyDao;

    @PostMapping("/reply")
    @ApiOperation(value = "댓글등록")
    public Object create(@Valid @RequestBody ReplyCreateRequest request) {
        final BasicResponse result = new BasicResponse();

        Reply reply = new Reply();
        reply.setContent(request.getContent());
        reply.setBid(request.getBid());
        reply.setUid(request.getUid());
        reply.setNickname(request.getNickname());

        try {
            replyDao.save(reply);

            result.status = true;
            result.data = "success";
        } catch (Exception e) {
            System.out.println(e);

            result.status = false;
            result.data = "failed";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/reply")
    @ApiOperation(value = "댓글수정")
    public Object update(@Valid @RequestBody ReplyUpdateRequest request) {
        final BasicResponse result = new BasicResponse();

        // 만약 수정하고자하는 댓글이 db에 없거나
        // 수정하고자하는 닉네임과 기존 댓글을 작성한 닉네임이 다를 경우에
        // 비정상적으로 처리됬음을 알리기위함
        result.status = false;
        result.data = "failed";

        System.out.println(request.getRid());

        // rid로 댓글을 검색하여 결과를 반환
        Optional<Reply> tempReply = replyDao.findReplyByRid(request.getRid());

        // rid로 검색한 결과가 있을 경우에만 수행
        if (tempReply.isPresent()) {
            // rid로 검색한 댓글을 받아온다
            Reply reply = tempReply.get();

            // 만약 수정하고자 하는 닉네임과 기존 댓글을 작성한 닉네임이 같은 경우에만 수행
            if (reply.getNickname().equals(request.getNickname())) {
                // 바뀐 내용을 넣어준다.
                reply.setContent(request.getContent());

                // 업데이트
                replyDao.save(reply);

                // 처리가 정상적으로 처리됬음을 알려준다.
                result.status = true;
                result.data = "success";
            }
        }
//        try {
//
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}