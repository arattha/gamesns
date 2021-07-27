
package com.web.curation.dao.member;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByUid(Long uid);

    Optional<Member> findMemberByNickname(String nickname);
    
    List<Member> findMemberByNicknameContaining(String nickname);
    
}
