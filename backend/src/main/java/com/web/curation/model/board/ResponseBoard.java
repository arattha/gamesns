package com.web.curation.model.board;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.file.ImgFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBoard {
	
    private Long bid;
    private Long uid;

    private String contents;

    private int likes;
    private int share;
    
    private List<ImgFile> imgFiles;
    
    public ResponseBoard(Board board, List<ImgFile> imgFiles) {
		this.bid = board.getBid();
		this.uid = board.getUid();
		this.contents = board.getContents();
		this.likes = board.getLikes();
		this.share = board.getShare();
		this.imgFiles = imgFiles;
		this.createDate = board.getCreateDate();
	}

    private LocalDateTime createDate;
}