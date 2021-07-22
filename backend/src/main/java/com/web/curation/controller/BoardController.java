package com.web.curation.controller;

import com.web.curation.dao.ImgFile.ImgFileDao;
import com.web.curation.dao.board.BoardDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.file.ImgFile;
import com.web.curation.service.BoardService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;
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
    ImgFileDao imgFileDao;
    
    @Autowired
    BoardService BoardService;
    
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
    public Object modifyBoard(@PathVariable("bid") long bid ,AddBoard newBoard){
    	final BasicResponse result = new BasicResponse();
    	
    	try {
    		BoardService.modifyBoard(bid, newBoard);
    		result.status = true;
            result.data = "success";
    		
		} catch (Exception e) {
			result.status = false;
	        result.data = "failed";
		}
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