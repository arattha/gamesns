package com.web.curation.model.reply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class ReplyUpdateRequest {
    @ApiModelProperty(required = true)
    @NotNull
    Long rid;
    @ApiModelProperty(required = true)
    @NotNull
    Long bid;
    @ApiModelProperty(required = true)
    @NotNull
    Long uid;
    @ApiModelProperty(required = true)
    @NotNull
    String nickname;
    @ApiModelProperty(required = true)
    @NotNull
    String content;
}
