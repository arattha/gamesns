//package com.web.curation.controller;
//
//import com.web.curation.dao.follow.FollowerDao;
//import com.web.curation.dao.follow.FollowingDao;
//import com.web.curation.dao.user.UserDao;
//import com.web.curation.model.BasicResponse;
//import com.web.curation.model.follow.Follower;
//import com.web.curation.model.follow.Following;
//import com.web.curation.model.user.User;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
//        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
//        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
//        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })
//
//@CrossOrigin(origins = "*")
//@RestController
//public class FollowController {
//
//    @Autowired
//    UserDao userDao;
//
//    @Autowired
//    FollowerDao followerDao;
//
//    @Autowired
//    FollowingDao followingDao;
//
//    @GetMapping("/follow/follower")
//    @ApiOperation(value = "팔로워리스트")
//    public Object follower(@RequestParam(required = true) final String uid) {
//
//        Optional<User> user = userDao.findUserByUid(uid);
//
//        if(user.isPresent()) {
//            List<Follower> FList = followerDao.findFollowerByUid(uid);
//
//            return new ResponseEntity<>(FList, HttpStatus.OK);
//        } else {
//            final BasicResponse result = new BasicResponse();
//            result.status = true;
//            result.data = "fail";
//            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
//        }
//
//    }
//
//    @GetMapping("/follow/following")
//    @ApiOperation(value = "팔로잉리스트")
//    public Object following(@RequestParam(required = true) final String uid) {
//
//        Optional<User> user = userDao.findUserByUid(uid);
//
//        if(user.isPresent()) {
//            List<Following> FList = followingDao.findFollowingByUid(uid);
//
//            return new ResponseEntity<>(FList, HttpStatus.OK);
//        } else {
//            final BasicResponse result = new BasicResponse();
//            result.status = true;
//            result.data = "fail";
//            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
//        }
//
//    }
//}