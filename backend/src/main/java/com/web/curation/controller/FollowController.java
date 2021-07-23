package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.follow.Follower;
import com.web.curation.model.follow.Following;
import com.web.curation.model.user.User;
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
    public Object follower(@RequestParam(required = true) final Long from) {

        Optional<User> user = accountService.getUser(from);

        if(user.isPresent()) {
            List<Follower> FList = followService.getFollower(from);

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
    public Object following(@RequestParam(required = true) final Long to) {

        Optional<User> user = accountService.getUser(to);

        if(user.isPresent()) {
            List<Following> FList = followService.getFollowing(to);

            return new ResponseEntity<>(FList, HttpStatus.OK);
        } else {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/follow/addFollow")
    @ApiOperation(value = "팔로우 추가")
    public Object addFollow(@RequestParam Long fromId, @RequestParam Long toId) {

        final BasicResponse result = new BasicResponse();

        if(followService.addFollow(fromId, toId)) {
            result.status = true;
            result.data = "success";
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }
}