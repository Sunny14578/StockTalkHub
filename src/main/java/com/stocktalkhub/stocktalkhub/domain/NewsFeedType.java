package com.stocktalkhub.stocktalkhub.domain;

import lombok.Getter;

@Getter
public enum NewsFeedType {
    COMMENT,
    POST,
    POSTLIKE,
    COMMENTLIKE,
    FOLLOW
}
