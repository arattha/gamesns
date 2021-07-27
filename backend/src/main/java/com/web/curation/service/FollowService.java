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

    // return 값
    // 0 : 오류
    // 1 : 해당 (fromId, toId) 쌍이 DB 목록에 있는 데이터이므로 삭제
    // 2 : 새로운 (fromId, toId) 쌍을 DB 에 추가
    public int AddOrDeleteFollow(String fromNickname, String toNickname, boolean type) {

        Optional<Member> memberOpt;
        Optional<Member> memberOpt2;

        if(type){
            // 팔로잉 리스트 update
            memberOpt = memberDao.findMemberByNickname(fromNickname);
            memberOpt2 = memberDao.findMemberByNickname(toNickname);

            if(memberOpt.isPresent() && memberOpt2.isPresent()) {

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
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {

                        followingDao.delete(delFollow);

                        return 1;
                    }

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

        } else {
            // 팔로워 리스트 update
            memberOpt2 = memberDao.findMemberByNickname(fromNickname);
            memberOpt = memberDao.findMemberByNickname(toNickname);

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
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        followerDao.delete(delFollower);
                        return 1;
                    }

                    Follower follower = new Follower();
                    follower.setToId(toId);
                    follower.setToNickname(toNickname);
                    follower.setFromId(fromId);
                    follower.setFromNickname(fromNickname);
                    followerDao.save(follower);

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

}
