package com.java.demo_ttcscn.enitities.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private int id;
    private String content;
    @CreatedDate
    private LocalDateTime create_at;
    private String username;
    private int askId;
}
