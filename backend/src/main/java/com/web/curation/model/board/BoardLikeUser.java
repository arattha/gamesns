package com.web.curation.model.board;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardLikeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    // 좋아요를 누른 사람
    private Long uid;
    private Long bid;

}

