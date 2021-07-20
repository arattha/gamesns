package com.web.curation.controller;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.ChpwdRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    UserDao userDao;

    @GetMapping("/follow/follower")
    @ApiOperation(value = "팔로워리스트")
    public Object follower(@RequestParam(required = true) final String email,
            @RequestParam(required = true) final String password) {

        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        ResponseEntity response = null;

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/follow/following")
    @ApiOperation(value = "팔로잉리스트")
    public Object following(@RequestParam(required = true) final String email,
                        @RequestParam(required = true) final String password) {

        Optional<User> userOpt = userDao.findUserByEmailAndPassword(email, password);
        ResponseEntity response = null;

        if (userOpt.isPresent()) {
            final BasicResponse result = new BasicResponse();
            result.status = true;
            result.data = "success";
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping("/account/signup")
    @ApiOperation(value = "변경하기")

    public Object signup(@Valid @RequestBody SignupRequest request) {
        // 이메일, 닉네임 중복처리 필수
        // 회원가입단을 생성해 보세요.

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
//        System.out.println(user);

        final BasicResponse result = new BasicResponse();

        // 전체 사용자 목록 가져오기
        List<User> list = userDao.findAll();
        for(User u : list){
            // 이메일 중복 확인
            if(u.getEmail().equals(user.getEmail())){
                result.status = true;
                result.data = "fail";
                result.object = u;

                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
            }
            System.out.println(u);
        }

        // 중복된 값이 없으므로 회원가입이 가능
        result.status = true;
        result.data = "success";

        userDao.save(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/account/chpwd")
    @ApiOperation(value = "가입하기")

    public Object chpwd(@Valid @RequestBody ChpwdRequest request) {

        Optional<User> user = userDao.findUserByEmail(request.getEmail());
        System.out.println(user);
        final BasicResponse result = new BasicResponse();

        if(user.isPresent()){
            User tmpUser = user.get();
            tmpUser.setPassword(request.getPassword());

            userDao.save(tmpUser);
            result.status = true;
            result.data = "success";
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        result.status = true;
        result.data = "fail";

        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }


}