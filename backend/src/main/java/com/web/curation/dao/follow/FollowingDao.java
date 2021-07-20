package com.web.curation.dao.follow;

import com.web.curation.model.follow.Following;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowingDao extends JpaRepository<Following, String> {

    List<Following> findFollowingByFrom(long uid);
}
