package com.example.demo.shared.wrappers;

import java.util.List;

public interface IResult<T> {
    List<Messages> getMessages();
    boolean isStatus();
    T getData();
}

