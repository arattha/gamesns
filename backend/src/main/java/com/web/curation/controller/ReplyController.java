package com.web.curation.controller;

import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyCreateRequest;
import com.web.curation.model.reply.ReplyUpdateRequest;
import com.web.curation.service.ReplyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    ReplyService replyService;

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

        // 만약 조회하고자하는 댓글이 db에 없거나
        // 수정하고자하는 닉네임과 기존 댓글을 작성한 닉네임이 다를 경우에
        // 비정상적으로 처리됬음을 알리기위함
        result.status = false;
        result.data = "failed";

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

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/reply")
    @ApiOperation(value = "댓글조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name="bid", value="현재 피드의 번호", required = true),
            @ApiImplicitParam(name="lastRid", value="현재 페이지의 마지막 댓글의 번호" +
                    "(처음 댓글 보기로 들어갔을 경우 0을 값으로 설정해야 함)", required = true)
    })
    public Object read(@RequestParam final Long bid, @RequestParam Long lastRid) {
        final BasicResponse result = new BasicResponse();

        // 비정상적으로 처리됬을때를 위한 반환값
        result.status = false;
        result.data = "failed";

        try {
            // 현재 페이지의 댓글들의 정보를 얻어온다.
            Page<Reply> curPage = replyService.getReplyListByPages(bid, lastRid,2);

            // 만약 아무것도 얻어오지 못했다면 이는 잘못된 처리이므로
            // curPage를 반환값에 담지 않게한다.
            if (curPage != null) {
                result.status = true;
                result.data = "success";
                result.object = curPage;
            }
        } catch (Exception e) {
            // 예외처리
            System.out.println(e);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}