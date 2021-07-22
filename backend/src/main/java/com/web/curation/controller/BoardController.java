package com.web.curation.controller;

import com.web.curation.dao.ImgFile.ImgFileDao;
import com.web.curation.dao.board.BoardDao;
import com.web.curation.dao.follow.FollowingDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.board.Board;
import com.web.curation.model.board.ResponseBoard;
import com.web.curation.model.file.ImgFile;
import com.web.curation.service.BoardService;


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
import java.util.Optional;
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
    BoardService BoardService;
    
    @Autowired
    ResourceLoader rsLoader;
    
    @GetMapping("/board")
    @ApiOperation(value = "내 피드")
    public Object bList(@RequestParam(required = true) final long uid, @RequestParam(required = false) String bid){
		final BasicResponse result = new BasicResponse();
		try {
			result.object = BoardService.bList(uid, bid);
			result.status = true;
	        result.data = "success";
		} catch (Exception e) {
			// TODO: handle exception
			result.status = false;
	        result.data = "failed";
		}
        return new ResponseEntity<>(result, HttpStatus.OK);
        
    }
    
    @PostMapping(value="/board")
    @ApiOperation(value="추가하기")
    public Object addBoard(AddBoard newBoard) {
    	final BasicResponse result = new BasicResponse();
    	
    	try {
    		BoardService.addBoard(newBoard);
    		
    		result.status = true;
            result.data = "success";
    		
		} catch (Exception e) {
			result.status = false;
	        result.data = "failed";
		}

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value="/board/{bid}")
    @ApiOperation(value="수정하기")
    public Object modifyBoard(@PathVariable("bid") long bid ,AddBoard newBoard) throws IllegalStateException, IOException{

        //Board> boardBid = boardDao.findByBid(bid);
        Board board = boardDao.findBoardByBid(bid);
        board.setUid(newBoard.getUid());
        board.setContents(newBoard.getContent());
        System.out.println(board);
        String fileName;
        
        
        List<ImgFile> imgList = imgFileDao.findImgFileByBid(bid);//연관된 파일 삭제
        imgFileDao.deleteAll(imgList);
        
        for (ImgFile imgFile : imgList) {
			File file = new File(imgFile.getFile_base_url());//연관된 파일 삭제
			file.delete();
		}
        
        
        MultipartFile[] multipartFiles = newBoard.getMultipartFiles();
        
        for (int i = 0; i < multipartFiles.length; i++) {//재등록
        	
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
        
        board = boardDao.save(board);
        
        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @DeleteMapping(value="/board/{bid}")
    @ApiOperation(value="삭제하기")
    public Object deleteBoard(@PathVariable("bid") long bid) throws IllegalStateException, IOException{
    	
    	final BasicResponse result = new BasicResponse();
    	
    	List<ImgFile> imgList = imgFileDao.findImgFileByBid(bid);//연관된 파일 삭제
        
        for (ImgFile imgFile : imgList) {
			File file = new File(imgFile.getFile_base_url());//연관된 파일 삭제
			if(file.exists()) file.delete();
		}
        boardDao.delete(boardDao.findBoardByBid(bid));
        
    	result.status = true;
        result.data = "success";
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}