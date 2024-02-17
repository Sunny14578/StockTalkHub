package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "media_file")
    private String mediaFile;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "stock_id")
    private Long stockId;


//    private LocalDateTime deleted_at;
//    private LocalDateTime updated_at;

    @Builder
    public Post(Long memberId, String title, String content, Long stockId, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.stockId = stockId;
    }

}
