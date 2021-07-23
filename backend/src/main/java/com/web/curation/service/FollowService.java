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
    FollowerDao followerDao;

    @Autowired
    FollowingDao followingDao;

    public List<Follower> getFollower(Long toId) {

        return followerDao.findFollowerByToId(toId);

    }

    public List<Following> getFollowing(Long fromId) {

        return followingDao.findFollowingByFromId(fromId);

    }

    public boolean addFollow(Long fromId, Long toId) {

        try {
            Follower follower = new Follower();
            follower.setFromId(toId);
            follower.setToId(fromId);
            followerDao.save(follower);

            Following following = new Following();
            following.setToId(toId);
            following.setFromId(fromId);
            followingDao.save(following);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
