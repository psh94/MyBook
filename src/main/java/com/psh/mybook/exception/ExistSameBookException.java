package com.psh.mybook.exception;

public class ExistSameBookException extends RuntimeException{

    public ExistSameBookException(String message){
        super(message);
    }
}
