package com.web.curation.service;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.user.ImgRequest;
import com.web.curation.model.user.SignupRequest;
import com.web.curation.model.user.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    UserDao userDao;

    // uid 로 회원정보 가져오기
    public Optional<User> getUser(Long uid) {

        return userDao.findUserByUid(uid);

    }

    // 닉네임 받아와서 회원정보 추가(user table 에 insert)
    public void addUser(SignupRequest request) {
        User user = new User();
        user.setUid(request.getUid());
        user.setNickname(request.getNickname());

        userDao.save(user);
    }

    // 닉네임과 프로필 사진을 받아와서 회원정보 수정
    public void updateUser(User user, ImgRequest request, String path) {

        user.setNickname(request.getNickname());
        user.setPimg(path);

        userDao.save(user);
    }


    // 카카오톡 access_token 으로 사용자 id 받기
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

    // 카카오톡 로그아웃
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