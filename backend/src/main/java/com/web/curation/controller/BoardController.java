package com.web.curation.controller;

import com.web.curation.dao.board.BoardDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.board.Board;
import com.web.curation.model.follow.Following;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class BoardController {

    @Autowired
    BoardDao boardDao;
    
    @Autowired
    FollowingDao followingDao;
    
    @GetMapping("/board")
    @ApiOperation(value = "내 피드")
    public Object bList(@RequestParam(required = true) final long uid,
    		@RequestParam(required = false) String bid){
    	
    	List<Following> following = followingDao.findFollowingByFrom(uid);
    	long longbid = 0;
    	
    	Pageable paging = PageRequest.of(0, 10);
    	
    	if(bid == null) longbid = Long.MAX_VALUE;
    	else longbid = Long.parseLong(bid);
    	
    	List<Board> board = boardDao.findBoardByFollowFeed(longbid, uid,paging);
    	
    	
        return new ResponseEntity<>(board, HttpStatus.OK);

    }

    @PostMapping("/board")
    @ApiOperation(value="추가하기")
    public Object addBoard(@Valid @RequestBody AddBoard newBoard){
        Board board = new Board();
        board.setUid(newBoard.getUid());
        board.setImg(newBoard.getImg());
        
        List<Board> arr = new ArrayList<Board>();
        
        long u1 = 0;
        long u2 = 1;
        long u3 = 2;
        for (int i = 0; i < 20; i++) {
        	Board temp1 = new Board();
        	temp1.setUid(u1);
        	temp1.setImg(newBoard.getImg());
        	temp1.setContents("contents "+i);
        	Board temp2 = new Board();
        	temp2.setUid(u2);
        	temp2.setImg(newBoard.getImg());
        	temp2.setContents("contents "+i);
        	Board temp3 = new Board();
        	temp3.setUid(u3);
        	temp3.setImg(newBoard.getImg());
        	temp3.setContents("contents "+i);
			arr.add(temp1);
			arr.add(temp2);
			arr.add(temp3);
		}
        boardDao.saveAll(arr);
        
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        //boardDao.save(board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}