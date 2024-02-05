package com.stocktalkhub.stocktalkhub.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NewsFeedByCommentsDTO {
    private Long id;
    private Long receiverId;
}
