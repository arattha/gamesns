package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.jwt.TokenDto;
import com.web.curation.model.member.MemberRequest;
import com.web.curation.service.AccountService;
import com.web.curation.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

/**
 * 회원가입 / 로그인 / 재발급을 처리
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * 회원가입을 담당
     *
     * 회원가입 성공 시 status는 true, data는 success가 담기고
     * 실패 시 status는 false, data는 fail이 담긴다.
     *
     * @param memberRequest
     * @return
     */
    @PostMapping("/signup")
    @ApiOperation(value = "회원 가입")
    public Object signup(@RequestBody MemberRequest memberRequest) {
        BasicResponse result = new BasicResponse();

        result.status = false;
        result.data = "fail";

        try {
            if (authService.signup(memberRequest.getUid(), memberRequest.getNickname())) {
                result.status = true;
                result.data = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 로그인을 처리
     * 로그인을 성공하면 access token을 반환
     * 
     * @param uid
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public Object login(@RequestBody String uid, HttpServletResponse response) {
        BasicResponse result = new BasicResponse();

        result.status = false;
        result.data = "fail";

        System.out.println(uid);

        try {
            Optional<TokenDto> getToken = authService.login(uid);

            if (getToken.isPresent()) {
                result.status = true;
                result.data = "success";
                TokenDto token = getToken.get();

                Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
                cookie.setMaxAge(7 * 24 * 60 * 60);

                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");

                response.addCookie(cookie);

                result.object = token.getAccessToken();
            }
        } catch (Exception e) {
            System.out.println(e);
            result.status = false;
            result.data = "fail";
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "토큰 재발급")
    public Object reissuance(@RequestBody String accessToken, HttpServletRequest request, HttpServletResponse response) {
        BasicResponse result = new BasicResponse();

        result.status = false;
        result.data = "fail";

        String refreshToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken != null && refreshToken.length() > 0) {
            System.out.println("??");
            Optional<TokenDto> getToken = authService.reissuance(accessToken, refreshToken);

            if (getToken.isPresent()) {
                result.status = true;
                result.data = "success";

                TokenDto token = getToken.get();

                Cookie cookie = new Cookie("refreshToken", token.getRefreshToken());
                cookie.setMaxAge(7 * 24 * 60 * 60);

                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/");

                response.addCookie(cookie);

                result.object = token.getAccessToken();
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public Object logout(@RequestBody String accessToken, HttpServletRequest request) {
        BasicResponse result = new BasicResponse();

        result.data = "fail";
        result.status = false;

        String refreshToken = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        boolean flag = authService.logout(accessToken, refreshToken);

        if (flag) {
            result.data = "success";
            result.status = true;
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
