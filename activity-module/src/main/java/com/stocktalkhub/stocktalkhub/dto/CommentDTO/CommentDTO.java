package com.stocktalkhub.stocktalkhub.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long post_id;
    private String content;
}
