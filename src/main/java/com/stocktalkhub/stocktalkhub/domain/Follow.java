package com.stocktalkhub.stocktalkhub.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member following;

    @OneToMany(mappedBy = "follow")
    private List<NewsFeed> sentNewsFeeds = new ArrayList<>();

    @Builder
    public Follow(Member follower, Member following) {
        this.follower = follower;
        this.following = following;
    }
}
