package com.web.curation.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.ImgRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    @Autowired
    UserDao userDao;

    @GetMapping("/kakaoLogout")
    @ApiOperation(value = "kakaoLogout")
    public Object klogout(@RequestParam String access_token) {
        kakaoLogout(access_token);

        return "index";

    }

    @GetMapping("/kakaoLogin")
    @ApiOperation(value = "kakaoLogin")
    public Object klogin(@RequestParam String access_token) {
        System.out.println("access_token : " + access_token);
        Long uid = new Long(getUserInfo(access_token));
        System.out.println("orgin : " + uid);

        Optional<User> userOpt = userDao.findUserByUid(uid);
        System.out.println("uid : " + uid);

        if(userOpt.isPresent()) {
            // 회원정보가 있으면 회원정보와 함께 OK
            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
        } else {
            // 회원정보가 없으면 uid 와 함께 NOT_FOUND
            return new ResponseEntity<>(uid, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/account/signup")
    @ApiOperation(value="회원가입")
    public Object signup(@RequestBody SignupRequest request) {

        User user = new User();
        user.setUid(request.getUid());
        user.setNickname(request.getNickname());

        userDao.save(user);

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // myPage 에서 회원 정보 수정

    @PutMapping("/account/imgPut")
    @ApiOperation(value="이미지 삽입")
    public Object imgPut(@RequestBody ImgRequest request) {

        Optional<User> userOpt = userDao.findUserByUid(request.getUid());

        final BasicResponse result = new BasicResponse();

        if(userOpt.isPresent()) {

            User user = userOpt.get();
            user.setPimg(request.getPImg());

            userDao.save(user);

            result.status = true;
            result.data = "success";
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else{
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
        }
    }

    public int getUserInfo(String access_Token){

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null ){
                result += line;
            }

            JSONObject parser = new JSONObject(result);
            System.out.println(parser.get("id"));

            return (int) parser.get("id");

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void kakaoLogout(String access_Token){
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String result = "";
            String line = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}