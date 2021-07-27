package com.web.curation.service;

import com.web.curation.dao.follow.FollowerDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.dao.user.UserDao;
import com.web.curation.model.follow.Follower;
import com.web.curation.model.follow.Following;
import com.web.curation.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    UserDao userDao;

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

    // return 값
    // 0 : 오류
    // 1 : 해당 (fromId, toId) 쌍이 DB 목록에 있는 데이터이므로 삭제
    // 2 : 새로운 (fromId, toId) 쌍을 DB 에 추가
    public int AddOrDeleteFollow(String fromNickname, String toNickname) {

        Optional<User> userOpt = userDao.findUserByNickname(fromNickname);
        Optional<User> userOpt2 = userDao.findUserByNickname(toNickname);

        if(userOpt.isPresent() && userOpt2.isPresent()) {
            System.out.println("herereererer!!");
            try {

                Long fromId = userOpt.get().getUid();
                Long toId = userOpt2.get().getUid();

                // follower DB 에 있는 (fromId, toId) 쌍인지 확인할 변수
                boolean flag = false;
                List<Follower> fList = followerDao.findFollowerByToId(toId);
                for (Follower f : fList) {
                    if (f.getFromId() == fromId) {
                        flag = true;
                        break;
                    }
                }

                if (flag) return 1;

                Follower follower = new Follower();
                follower.setFromId(toId);
                follower.setFromNickname(fromNickname);
                follower.setToId(fromId);
                follower.setToNickname(toNickname);
                followerDao.save(follower);

                Following following = new Following();
                following.setToId(toId);
                following.setToNickname(toNickname);
                following.setFromId(fromId);
                following.setFromNickname(fromNickname);
                followingDao.save(following);

                return 2;

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            return 0;
        }
    }

}
