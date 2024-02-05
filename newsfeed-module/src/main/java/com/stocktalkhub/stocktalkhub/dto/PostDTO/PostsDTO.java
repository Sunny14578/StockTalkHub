package com.stocktalkhub.stocktalkhub.dto.PostDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostsDTO {
    private Long member_id;
    private String content;
    private String media_file;
    private LocalDateTime created_at;

    @Builder
    private PostsDTO(Long member_id, String content, LocalDateTime created_at) {
        this.member_id = member_id;
        this.content = content;
        this.created_at = created_at;
    }

}
