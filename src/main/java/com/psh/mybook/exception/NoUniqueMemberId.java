package com.psh.mybook.exception;

public class NoUniqueMemberId extends RuntimeException{

    public NoUniqueMemberId(String message){
        super(message);
    }
}
