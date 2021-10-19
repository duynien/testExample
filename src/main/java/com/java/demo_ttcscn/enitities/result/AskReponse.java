package com.java.demo_ttcscn.enitities.result;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class AskReponse {
    private int id;
    private String purpose;
    private String content;
    private LocalDateTime createAt;
    private String username;
    private int commentListSize;
}
