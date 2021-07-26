package com.web.curation.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Valid
@Data
@ToString
public class ImgRequest {
    @ApiModelProperty(required = true)
    @NotNull
    Long uid;

    @ApiModelProperty(required = true)
    @NotNull
    String nickname;

}
