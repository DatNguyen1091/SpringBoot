package com.example.demo.shared.wrappers;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PaginatedResult<T> extends Result<T> {

    private List<T> Data;

    public PaginatedResult(List<T> data) {
        this.Data = data;
    }

    public PaginatedResult(boolean status, List<T> data, List<String> messages, int count, int page, int pageSize)
    {
        Data = data;
        CurrentPage = page;
        setStatus(status);
        PageSize = pageSize;
        TotalPages = (int) Math.ceil((double) count / pageSize);
        TotalCount = count;
    }

    public static <T> PaginatedResult<T> Failure(List<String> messages)
    {
        return new PaginatedResult<T>(false, null, messages, 0, 0, 0);
    }

    public static <T> PaginatedResult<T> Success(List<T> data, int count, int page, int pageSize)
    {
        return new PaginatedResult<T>(true, data, null, count, page, pageSize);
    }

    private int CurrentPage;
    private int TotalPages;
    private int TotalCount;
    private int PageSize;
    private boolean hasPreviousPage() { return CurrentPage > 1;}
    private boolean hasNextPage() {return CurrentPage < TotalPages;}
}
