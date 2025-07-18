package com.example.demo.shared.wrappers;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Result<T> implements IResult<T> {

    private List<Messages> messages = new ArrayList<>();
    private boolean Status;
    private T data;

    public Result() {
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setStatus(false);
        return result;
    }

    public static <T> Result<T> fail(Messages message) {
        Result<T> result = new Result<>();
        result.setStatus(false);
        result.setMessages(new ArrayList<>(List.of(message)));
        return result;
    }

    public static <T> Result<T> fail(List<Messages> messages) {
        Result<T> result = new Result<>();
        result.setStatus(false);
        result.setMessages(new ArrayList<>(messages));
        return result;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setStatus(true);
        return result;
    }

    public static <T> Result<T> success(Messages message) {
        Result<T> result = new Result<>();
        result.setStatus(true);
        result.setMessages(new ArrayList<>(List.of(message)));
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setStatus(true);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, Messages message) {
        Result<T> result = new Result<>();
        result.setStatus(true);
        result.setMessages(new ArrayList<>(List.of(message)));
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data, List<Messages> messages) {
        Result<T> result = new Result<>();
        result.setStatus(true);
        result.setMessages(new ArrayList<>(messages));
        result.setData(data);
        return result;
    }

}

