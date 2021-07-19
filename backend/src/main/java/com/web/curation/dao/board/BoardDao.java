package com.web.curation.dao.board;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.board.Board;
import com.web.curation.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardDao extends JpaRepository<Board, String> {

    List<Board> findBoardByUid(String uid);

}
