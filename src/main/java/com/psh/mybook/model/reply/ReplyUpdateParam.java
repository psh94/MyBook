package com.psh.mybook.model.reply;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
public class ReplyUpdateParam {

    private int replyId;

    private int bookId;

    @NotNull
    private String content;

    @NotNull
    private double rating;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private Date regDate;
}
