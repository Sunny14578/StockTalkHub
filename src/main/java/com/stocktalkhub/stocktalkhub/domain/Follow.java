package com.stocktalkhub.stocktalkhub.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "follows")
@Getter
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



}
