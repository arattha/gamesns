
package com.web.curation.dao.user;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByUid(Long uid);

    Optional<User> findUserByNickname(String nickname);
    
    List<User> findUserByNicknameContaining(String nickname);
    
}
