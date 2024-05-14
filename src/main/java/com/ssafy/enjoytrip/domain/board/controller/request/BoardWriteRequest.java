package com.ssafy.enjoytrip.domain.board.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardWriteRequest {
    private String subject;
    private String content;
    private String userId;
    private int boardTypeId;

    @Builder
    public BoardWriteRequest(final String subject, final String content, final String userId, final int boardTypeId) {
        this.subject = subject;
        this.content = content;
        this.userId = userId;
        this.boardTypeId = boardTypeId;
    }
}
