package com.stocktalkhub.stocktalkhub.dto.LikeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostLikesDTO {
    private Long id;
    private Long postId ;
}
