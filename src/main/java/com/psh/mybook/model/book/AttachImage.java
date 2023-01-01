package com.psh.mybook.model.book;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AttachImage {

    /* 상품 id */
    @NotNull
    private int bookId;

    /* 경로 */
    @NotNull
    private String uploadPath;

    /* uuid */
    @NotNull
    private String uuid;

    /* 파일 이름 */
    @NotNull
    private String fileName;


}
