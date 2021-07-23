package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.service.BoardService;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class BoardController {
    
    @Autowired
    BoardService boardService;
    
    @GetMapping("/board")
    @ApiOperation(value = "내 피드")
    public Object bList(@RequestParam(required = true) final long uid, @RequestParam(required = false) String bid){
		final BasicResponse result = new BasicResponse();
		try {
			result.object = boardService.bList(uid, bid);
			result.status = true;
	        result.data = "success";
		} catch (Exception e) {
			// TODO: handle exception
			result.status = false;
	        result.data = "failed";
	        System.out.println(e);
		}
		
        return new ResponseEntity<>(result, HttpStatus.OK);
        
    }
    
    @PostMapping(value="/board")
    @ApiOperation(value="추가하기")
    public Object addBoard(AddBoard newBoard) {
    	final BasicResponse result = new BasicResponse();
    	
    	try {
    		boardService.addBoard(newBoard);
    		
    		result.status = true;
            result.data = "success";
    		
		} catch (Exception e) {
			result.status = false;
	        result.data = "failed";
	        System.out.println(e);
		}

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(value="/board/{bid}")
    @ApiOperation(value="수정하기")
    public Object modifyBoard(@PathVariable("bid") long bid ,AddBoard newBoard){
    	
    	final BasicResponse result = new BasicResponse();
    	
    	try {
    		boardService.modifyBoard(bid, newBoard);
    		result.status = true;
            result.data = "success";
    		
		} catch (Exception e) {
			result.status = false;
	        result.data = "failed";
	        System.out.println(e);
		}
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @DeleteMapping(value="/board/{bid}")
    @ApiOperation(value="삭제하기")
    public Object deleteBoard(@PathVariable("bid") long bid){
    	
    	final BasicResponse result = new BasicResponse();
    	try {
    		boardService.deleteBoard(bid);
    		result.status = true;
            result.data = "success";
    		
		} catch (Exception e) {
			result.status = false;
	        result.data = "failed";
	        System.out.println(e);
		}
    	
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}