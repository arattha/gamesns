package com.web.curation.model.board;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.curation.model.file.ImgFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.sql.Blob;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    
    private Long uid;

    private String contents;

    private int likes;
    private int share;
    
    @Column(insertable = false)
    private LocalDateTime createDate;
}
