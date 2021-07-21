package com.web.curation.controller;

import com.web.curation.dao.ImgFile.ImgFileDao;
import com.web.curation.dao.board.BoardDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.board.Board;
import com.web.curation.model.board.ResponseBoard;
import com.web.curation.model.file.ImgFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    @Autowired
    ImgFileDao imgFileDao;
    @Autowired
    ResourceLoader rsLoader;
    
    @GetMapping("/board")
    @ApiOperation(value = "내 피드")
    public Object bList(@RequestParam(required = true) final long uid,
    		@RequestParam(required = false) String bid){
    	
    	long longbid;
    	if(bid == null) longbid = Long.MAX_VALUE;//없으면 최대값
    	else longbid = Long.parseLong(bid);//있으면 해당 bid 밑으로
    	
    	Pageable paging = PageRequest.of(0, 10);//최신부터 10개(0페이지에 10개)
    	
    	List<Board> boardList;
    	
    	if(followingDao.findFollowingByFrom(uid).size() > 0) {//내가 follow 하는 사람의 리스트
    		boardList = boardDao.findFollowFeedByUid(longbid, uid, paging);
    	}
    	else {
    		boardList = boardDao.findFollowFeed(longbid, paging);
    	}
    	List<ResponseBoard> resboard = new ArrayList<>();
    	for (Board board : boardList) {
    		resboard.add(new ResponseBoard(board,imgFileDao.findImgFileByBid(board.getBid())));
		}
    	
        return new ResponseEntity<>(resboard, HttpStatus.OK);
    }
    
    @PostMapping(value="/board")
    @ApiOperation(value="추가하기")
    public Object addBoard(AddBoard newBoard) throws IllegalStateException, IOException{
    	
        Board board = new Board();
        board.setUid(newBoard.getUid());
        board.setContents(newBoard.getContent());
        board = boardDao.save(board);
        
        String fileName;
        
        MultipartFile[] multipartFiles = newBoard.getMultipartFiles();
        
        for (int i = 0; i < multipartFiles.length; i++) {
        	
        	MultipartFile multipartFile = multipartFiles[i];
        	UUID uuid = UUID.randomUUID();
        	
        	fileName = uuid.toString()+"_"+multipartFile.getOriginalFilename();
        	multipartFile.transferTo(new File("D:\\upload"+"\\"+fileName));
        	String base_url = "D:\\upload"+"\\"+fileName;
        	
        	ImgFile file = new ImgFile();//이미지 파일 세팅
        	file.setFile_name(fileName);
        	file.setFile_base_url(base_url);
        	file.setFile_size(Long.toString(multipartFile.getSize()));
        	file.setBid(board.getBid());
        	
        	imgFileDao.save(file);
		}
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}