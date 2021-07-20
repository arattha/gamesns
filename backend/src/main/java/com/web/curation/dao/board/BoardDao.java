package com.web.curation.dao.board;

import java.util.List;
import java.util.Optional;

import com.web.curation.model.board.Board;
import com.web.curation.model.user.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardDao extends JpaRepository<Board, String> {

    List<Board> findBoardByUid(String uid);
    
    
  //최신 10개를 넘겨주는
	
	//select * 
	//from board b, following f
	//where b.bid < #{b.bid} and b.uid = f.from
	//order by createDate desc
	//limit 10;
    
    @Query( "from Board b " + 
    		"where b.bid < :bid and " + 
    		"b.uid in (select f.to from Following f where f.from = :uid) or " +
    		"b.uid = :uid " + 
    		"order by b.createDate desc")
    List<Board> findBoardByFollowFeed(@Param("bid") long bid,@Param("uid") long uid, Pageable limitTen);
}
