package com.web.curation.dao.follow;

import com.web.curation.model.follow.Follower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerDao extends JpaRepository<Follower, String> {

    List<Follower> findFollowerByUid(String uid);
}
