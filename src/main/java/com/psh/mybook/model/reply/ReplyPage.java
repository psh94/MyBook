package com.psh.mybook.model.reply;

import com.psh.mybook.model.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class ReplyPage {

    @NotNull
    List<Reply> list;

    @NotNull
    Page pageInfo;


}
