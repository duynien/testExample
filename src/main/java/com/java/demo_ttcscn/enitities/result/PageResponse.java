package com.java.demo_ttcscn.enitities.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse {
    private int currentPage;
    private int totalPage;
    private int totalRecord;
    private Object content;
}
