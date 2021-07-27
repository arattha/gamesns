package com.web.curation.controller;

import com.web.curation.model.BasicResponse;
import com.web.curation.model.user.User;
import com.web.curation.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = BasicResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = BasicResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = BasicResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = BasicResponse.class) })

@CrossOrigin(origins = "*")
@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/searchUser")
    @ApiOperation(value = "유저 찾기")
    public Object search(@RequestParam String nickname){

        Optional<User> userOpt = searchService.getUser(nickname);

        if(userOpt.isPresent()) {

            return new ResponseEntity<>(userOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    
    @GetMapping("/search")
    @ApiOperation(value = "유저 찾기")
    public Object searchUser(@RequestParam String nickname){
        List<User> userOpt = searchService.searchUser(nickname);
        for (User u : userOpt) {
			System.out.println(u);
		}
        
        if(userOpt.size() >= 0) {
            return new ResponseEntity<>(userOpt, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

}