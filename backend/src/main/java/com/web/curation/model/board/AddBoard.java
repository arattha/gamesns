package com.web.curation.model.board;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

@Valid
@Data
@ToString
public class AddBoard {
    @ApiModelProperty(required = true)
    @NotNull
    Long uid;
    @ApiModelProperty(required = true)
    @NotNull
    String content;
    
    @ApiModelProperty(required = false)
    MultipartFile[] multipartFiles;
    
}
