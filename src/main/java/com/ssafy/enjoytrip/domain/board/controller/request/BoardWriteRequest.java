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
    private int contentId;

    @Builder
    public BoardWriteRequest(final String subject, final String content, final String userId, final int boardTypeId, final int contentId) {
        this.subject = subject;
        this.content = content;
        this.userId = userId;
        this.boardTypeId = boardTypeId;
        this.contentId = contentId;
    }
}
