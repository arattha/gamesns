package com.web.curation.service;

import com.web.curation.dao.follow.FollowerDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.dao.member.MemberDao;
import com.web.curation.model.follow.Follower;
import com.web.curation.model.follow.Following;
import com.web.curation.model.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    FollowerDao followerDao;

    @Autowired
    FollowingDao followingDao;

    public List<Follower> getFollower(String toId) {

        return followerDao.findFollowerByToId(toId);

    }

    public List<Following> getFollowing(String fromId) {

        return followingDao.findFollowingByFromId(fromId);

    }

    public List<Following> AddOrDeleteFollowing(String fromNickname, String toNickname) {

        Optional<Member> memberOpt;
        Optional<Member> memberOpt2;

        // 팔로잉 리스트 update
        memberOpt = memberDao.findMemberByNickname(fromNickname);
        memberOpt2 = memberDao.findMemberByNickname(toNickname);

        if (memberOpt.isPresent() && memberOpt2.isPresent()) {

            try {

                String fromId = memberOpt.get().getUid();
                String toId = memberOpt2.get().getUid();

                // follower DB 에 있는 (fromId, toId) 쌍인지 확인할 변수
                boolean flag = false;
                Following delFollow = new Following();
                List<Following> fList = followingDao.findFollowingByFromId(fromId);
                for (Following f : fList) {
                    if (f.getFromId().equals(fromId) && f.getToId().equals(toId)) {
                        delFollow = f;
                        fList.remove(f);
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    followingDao.delete(delFollow);
                } else {

                    Following following = new Following();
                    following.setToId(toId);
                    following.setToNickname(toNickname);
                    following.setFromId(fromId);
                    following.setFromNickname(fromNickname);
                    followingDao.save(following);
                    fList.add(following);
                }

                return fList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }


    public List<Follower> AddOrDeleteFollower(String fromNickname, String toNickname) {

        Optional<Member> memberOpt = memberDao.findMemberByNickname(toNickname);
        Optional<Member> memberOpt2 = memberDao.findMemberByNickname(fromNickname);

        if(memberOpt.isPresent() && memberOpt2.isPresent()) {

            try {

                String fromId = memberOpt2.get().getUid();
                String toId = memberOpt.get().getUid();

                // follower DB 에 있는 (fromId, toId) 쌍인지 확인할 변수
                boolean flag = false;
                Follower delFollower = new Follower();
                List<Follower> fList = followerDao.findFollowerByToId(toId);
                for (Follower f : fList) {
                    if (f.getFromId().equals(fromId) && f.getToId().equals(toId)) {
                        delFollower = f;
                        fList.remove(f);
                        flag = true;
                        break;
                    }
                }

                if (flag) {
                    followerDao.delete(delFollower);
                } else {

                    Follower follower = new Follower();
                    follower.setToId(toId);
                    follower.setToNickname(toNickname);
                    follower.setFromId(fromId);
                    follower.setFromNickname(fromNickname);
                    followerDao.save(follower);
                    fList.add(follower);
                }
                return fList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

}
