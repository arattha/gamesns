package com.web.curation.controller.board;

import com.web.curation.dao.board.BoardDao;
import com.web.curation.model.BasicResponse;
import com.web.curation.model.board.AddBoard;
import com.web.curation.model.board.Board;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/board/list")
    @ApiOperation(value = "내 피드")
    public Object bList(@RequestParam(required = true) final String uid){

        List<Board> board = boardDao.findBoardByUid(uid);

        return new ResponseEntity<>(board, HttpStatus.OK);

    }

    @PostMapping("/board/add")
    @ApiOperation(value="추가하기")
    public Object addBoard(@Valid @RequestBody AddBoard newBoard){
        Board board = new Board();
        board.setUid(newBoard.getUid());
        board.setContent(newBoard.getContent());
        board.setImg(newBoard.getImg());

        final BasicResponse result = new BasicResponse();
        result.status = true;
        result.data = "success";

        boardDao.save(board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


//    @PostMapping("/account/signup")
//    @ApiOperation(value = "변경하기")
//
//    public Object signup(@Valid @RequestBody SignupRequest request) {
//        // 이메일, 닉네임 중복처리 필수
//        // 회원가입단을 생성해 보세요.
//
//        User user = new User();
//        user.setEmail(request.getEmail());
//        user.setPassword(request.getPassword());
////        System.out.println(user);
//
//        final BasicResponse result = new BasicResponse();
//
//        // 전체 사용자 목록 가져오기
//        List<User> list = userDao.findAll();
//        for(User u : list){
//            // 이메일 중복 확인
//            if(u.getEmail().equals(user.getEmail())){
//                result.status = true;
//                result.data = "fail";
//                result.object = u;
//
//                return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
//            }
//            System.out.println(u);
//        }
//
//        // 중복된 값이 없으므로 회원가입이 가능
//        result.status = true;
//        result.data = "success";
//
//        userDao.save(user);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @PutMapping("/account/chpwd")
//    @ApiOperation(value = "가입하기")
//
//    public Object chpwd(@Valid @RequestBody ChpwdRequest request) {
//
//        Optional<User> user = userDao.findUserByEmail(request.getEmail());
//        System.out.println(user);
//        final BasicResponse result = new BasicResponse();
//
//        if(user.isPresent()){
//            User tmpUser = user.get();
//            tmpUser.setPassword(request.getPassword());
//
//            userDao.save(tmpUser);
//            result.status = true;
//            result.data = "success";
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        }
//
//        result.status = true;
//        result.data = "fail";
//
//        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
//    }


}