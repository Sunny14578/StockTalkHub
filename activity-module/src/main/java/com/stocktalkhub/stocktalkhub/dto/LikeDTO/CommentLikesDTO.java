package com.stocktalkhub.stocktalkhub.dto.LikeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikesDTO {
    private Long id;
    private Long commentId ;
}
