package com.web.curation.service;

import com.web.curation.dao.user.UserDao;
import com.web.curation.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    UserDao userDao;

    public Optional<User> getUser(String nickname) {
        return userDao.findUserByNickname(nickname);
    }
    
    public List<User> searchUser(String nickname) {
        return userDao.findUserByNicknameContaining(nickname);
    }
}
