package com.stocktalkhub.stocktalkhub.dto.PostDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostsDTO {
    private Long memberId;
    private String title;
    private String content;
    private String mediaFile;
    private Long stockId;
    private LocalDateTime createdAt;

    @Builder
    private PostsDTO(String title, String content, String mediaFile, Long stockId, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.mediaFile = mediaFile;
        this.stockId = stockId;
        this.createdAt = createdAt;
    }
}
