package com.web.curation.controller;

import com.web.curation.dao.board.BoardDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.board.Board;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    	
    	long longbid;
    	if(bid == null) longbid = Long.MAX_VALUE;//없으면 최대값
    	else longbid = Long.parseLong(bid);//있으면 해당 bid 밑으로
    	
    	Pageable paging = PageRequest.of(0, 10);//최신부터 10개(0페이지에 10개)
    	
    	List<Board> board;
    	
    	if(followingDao.findFollowingByFrom(uid).size() > 0) {//내가 follow 하는 사람의 리스트
    		board = boardDao.findFollowFeedByUid(longbid, uid, paging);
    	}
    	else {
    		board = boardDao.findFollowFeed(longbid, paging);
    	}
    	
        return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @PostMapping("/board")
    @ApiOperation(value="추가하기")
    public Object addBoard(@Valid @RequestBody AddBoard newBoard, @RequestParam("img") MultipartFile multipartFile){
        Board board = new Board();
        board.setUid(newBoard.getUid());
        board.set
        //Map<String,Object> hmap = new HashMap<String,Object>();
        //hmap.put("img", multipartFile.getBytes());
        
        try {
			board.setImg(multipartFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //List<Board> arr = new ArrayList<Board>();
        
        boardDao.save(board);
        
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        //boardDao.save(board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}