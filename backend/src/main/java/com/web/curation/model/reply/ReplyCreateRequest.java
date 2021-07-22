package com.web.curation.model.reply;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Valid
public class ReplyCreateRequest {
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
