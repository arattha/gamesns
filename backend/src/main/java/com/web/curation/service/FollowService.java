package com.web.curation.service;

import com.web.curation.dao.follow.FollowerDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.follow.Follower;
import com.web.curation.model.follow.Following;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    @Autowired
    UserDao userDao;

    @Autowired
    FollowerDao followerDao;

    @Autowired
    FollowingDao followingDao;

    public List<Follower> getFollower(Long from) {

        return followerDao.findFollowerByFrom(from);

    }

    public List<Following> getFollowing(Long from) {

        return followingDao.findFollowingByFrom(from);

    }

}
