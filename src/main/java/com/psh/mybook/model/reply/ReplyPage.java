package com.psh.mybook.model.reply;

import com.psh.mybook.model.Page;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ReplyPage {

    List<Reply> list;

    Page pageInfo;


}
