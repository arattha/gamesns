package com.web.curation.service;

import com.web.curation.dao.member.MemberDao;
import com.web.curation.model.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    MemberDao memberDao;

    public Optional<Member> getMember(String nickname) {
        return memberDao.findMemberByNickname(nickname);
    }
    
    public List<Member> searchMember(String nickname) {
        return memberDao.findMemberByNicknameContaining(nickname);
    }
}
