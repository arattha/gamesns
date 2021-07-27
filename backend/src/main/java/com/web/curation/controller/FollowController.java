package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.FollowRequest;
import com.web.curation.model.follow.Follower;
import com.web.curation.model.follow.Following;
import com.web.curation.model.member.Member;
import com.web.curation.service.AccountService;
import com.web.curation.service.FollowService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class FollowController {

    @Autowired
    AccountService accountService;

    @Autowired
    FollowService followService;

    @GetMapping("/follow/follower")
    @ApiOperation(value = "팔로워리스트")
    public Object follower(@RequestParam final String to) {

        Optional<Member> member = accountService.getMember(to);

        if(member.isPresent()) {
            List<Follower> FList = followService.getFollower(to);

            return new ResponseEntity<>(FList, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/follow/following")
    @ApiOperation(value = "팔로잉리스트")
    public Object following(@RequestParam final String from) {

        Optional<Member> member = accountService.getMember(from);

        if(member.isPresent()) {

            List<Following> fList = followService.getFollowing(from);

            return new ResponseEntity<>(fList, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/follow/AddOrDeleteFollow")
    @ApiOperation(value = "팔로우 추가/삭제")
    public Object AddOrDeleteFollow(@RequestBody FollowRequest followRequest) {
        System.out.println("fromNickname : " + followRequest.getFromNickname() + ", toNickname : " + followRequest.getToNickname()
        + " type : " + followRequest.isType());

        // 오류(0), 삭제(1), 추가(2) 인지 확인할 변수
        int x = followService.AddOrDeleteFollow(followRequest.getFromNickname(), followRequest.getToNickname(), followRequest.isType());

        if(x == 2) {
            return new ResponseEntity<>(2, HttpStatus.OK);
        } else if(x == 1){
            return new ResponseEntity<>(1, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(0, HttpStatus.NOT_FOUND);
        }
    }
}