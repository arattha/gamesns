package com.web.curation.controller;

import com.web.curation.dao.reply.ReplyDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.reply.Reply;
import com.web.curation.model.reply.ReplyCreateRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}