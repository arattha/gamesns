package com.web.curation.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.member.ImgRequest;
import com.web.curation.model.member.SignupRequest;
import com.web.curation.model.member.Member;

import com.web.curation.service.AccountService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

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

//    @GetMapping("/kakaoLogin2")
//    @ApiOperation(value = "kakaoLogin")
//    public Object klogin(@RequestParam String access_token) {
//
//        Long uid = new Long(service.getMemberInfo(access_token));
//        Optional<Member> memberOpt = service.getMember(uid);
//
//        if(memberOpt.isPresent()) {
//            // 회원정보가 있으면 회원정보와 함께 OK
//            return new ResponseEntity<>(memberOpt.get(), HttpStatus.OK);
//        } else {
//            // 회원정보가 없으면 uid 와 함께 NOT_FOUND
//            return new ResponseEntity<>(uid, HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/kakaoLogin")
    @ApiOperation(value = "카카오 로그인")
    public Object kakaoLogin(@RequestParam("code") String code) {
        String uid = service.kakaoLogin(code);
        System.out.println(uid);

        return new ResponseEntity<>(uid, HttpStatus.OK);
    }

    @GetMapping("/account/dupcheck")
    @ApiOperation(value = "중복체크")
    public Object dupcheck(@RequestParam("nickname") String nickname) {
        System.out.println(nickname);
        if(service.getUserByNickname(nickname).isPresent()){
            // 중복 => 가입 실패
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PostMapping("/account/signup")
    @ApiOperation(value="회원가입")
    public Object signup(@RequestBody SignupRequest request) {

        if(service.addUser(request)) {
            // 가입 성공
            return new ResponseEntity<>(null, HttpStatus.OK);
        } else {
            // 에러
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

    }

    // myPage 에서 회원 정보 수정
    // 처음에 기본이미지로 저장된 이미지를 수정
    @PutMapping("/account/mypageUpdate")
    @ApiOperation(value="닉네임, 이미지 경로 수정")
    public Object imgPut(ImgRequest request,
                         MultipartFile multipartFile) throws IllegalStateException, IOException {

        Optional<Member> memberOpt = service.getMember(request.getUid());

        final BasicResponse result = new BasicResponse();

        if(memberOpt.isPresent()) {

            Member member = memberOpt.get();

            // img 파일 이름 설정 및 경로 지정
            String filePath = "C://upload//";
            String originFile = multipartFile.getOriginalFilename();
            String originalFileExtension = originFile.substring(originFile.lastIndexOf("."));
            String storedFileName = request.getUid() + originalFileExtension;
            File file = new File(filePath + storedFileName);

            multipartFile.transferTo(file);

            if(service.updateMember(member, request, filePath + storedFileName)) {
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
    
    @GetMapping("/account/file/{uid}")
    @ApiOperation(value = "내파일")
    public Object bFile(@PathVariable final String uid, HttpServletRequest request) throws MalformedURLException{
    	Optional<Member> member = service.getMember(uid);
    	
    	Resource resource =  new FileSystemResource(member.get().getPimg());
		
		if(!resource.exists()) {
			final BasicResponse result = new BasicResponse();
			result.status = true;
	        result.data = "success";
			return new ResponseEntity<>(result, HttpStatus.OK);   
		}
		
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);  
    }
}