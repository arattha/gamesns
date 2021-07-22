package com.web.curation.controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.ImgRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;

import com.web.curation.service.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;


@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class AccountController {

    @Autowired
    AccountService service;

    @GetMapping("/kakaoLogout")
    @ApiOperation(value = "kakaoLogout")
    public void klogout(@RequestParam String access_token) {

        service.kakaoLogout(access_token);

    }

    @GetMapping("/kakaoLogin")
    @ApiOperation(value = "kakaoLogin")
    public Object klogin(@RequestParam String access_token) {

        Long uid = new Long(service.getUserInfo(access_token));
        Optional<User> userOpt = service.getUser(uid);

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

        if(service.addUser(request)) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    // myPage 에서 회원 정보 수정
    // 처음에 기본이미지로 저장된 이미지를 수정
    @PutMapping("/account/mypageUpdate")
    @ApiOperation(value="닉네임, 이미지 경로 수정")
    public Object imgPut(ImgRequest request,
                         MultipartFile multipartFile) throws IllegalStateException, IOException {

        Optional<User> userOpt = service.getUser(request.getUid());

        final BasicResponse result = new BasicResponse();

        if(userOpt.isPresent()) {

            User user = userOpt.get();

            // img 파일 이름 설정 및 경로 지정
            String filePath = "C:\\Users\\multicampus\\S05P12C203\\backend\\src\\main\\resources\\profile\\";
            String originFile = multipartFile.getOriginalFilename();
            String originalFileExtension = originFile.substring(originFile.lastIndexOf("."));
            String storedFileName = request.getUid() + originalFileExtension;
            File file = new File(filePath + storedFileName);

            multipartFile.transferTo(file);

            if(service.updateUser(user, request, filePath + storedFileName)) {
                return new ResponseEntity<>(null, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
        } else{
            result.status = true;
            result.data = "fail";
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }

}