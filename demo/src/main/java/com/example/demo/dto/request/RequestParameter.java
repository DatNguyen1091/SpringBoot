package com.example.demo.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RequestParameter {
    String Keyword;
    int PageNumber;
    int PageSize;
    boolean IsExport;
    String SortBy;
    @Getter
    private Sort Order;

    public void setOrder(String order) {
        if (order != null && order.equalsIgnoreCase("ASC")) {
            this.Order = Sort.by(Sort.Direction.ASC, "createdOn");
        } else {
            this.Order = Sort.by(Sort.Direction.DESC, "createdOn");
        }
    }


    public RequestParameter()
    {
        this.PageNumber = 1;
        this.PageSize = 10;
        this.IsExport = false;
        this.SortBy = "";
        this.Order = Sort.by(Sort.Direction.DESC, "createdOn");
    }

    public RequestParameter(int pageNumber, int pageSize)
    {
        this.PageNumber = Math.max(pageNumber, 1);
        this.PageSize = Math.min(pageSize, 10);
    }
}
