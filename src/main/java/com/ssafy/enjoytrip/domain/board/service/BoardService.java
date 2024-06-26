package com.ssafy.enjoytrip.domain.board.service;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.enjoytrip.global.util.HtmlUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.enjoytrip.api.moderation.model.response.ModerationResponse;
import com.ssafy.enjoytrip.api.moderation.service.ModerationService;
import com.ssafy.enjoytrip.domain.board.controller.request.BoardWriteRequest;
import com.ssafy.enjoytrip.domain.board.controller.request.TripBoardSearchDto;
import com.ssafy.enjoytrip.domain.board.controller.request.UpdateBoardDto;
import com.ssafy.enjoytrip.domain.board.mapper.BoardMapper;
import com.ssafy.enjoytrip.domain.board.model.BannedBoardDto;
import com.ssafy.enjoytrip.domain.board.model.BoardDto;
import com.ssafy.enjoytrip.domain.member.model.MemberDto;
import com.ssafy.enjoytrip.domain.member.service.MemberService;
import com.ssafy.enjoytrip.global.exception.NotFoundArticleException;
import com.ssafy.enjoytrip.global.util.Kmp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardMapper boardMapper;
    private final ModerationService moderationService;
    private final MemberService memberService;

    /**
     * 글 작성 로직을 수행하는 메소드
     *
     * @param request 제목, 내용, 작성자 아이디, 게시판 타입 (1 : 공지사항, 2 : QnA, 3 : 여행지 게시판)
     * @return 작성된 게시물의 ID
     * @throws Exception 작성 실패 메시지
     */
    @Transactional
    public int writeArticle(BoardWriteRequest request) throws Exception {

        String content = request.getContent();
        String escapeContent = HtmlUtil.escapeHtml(content);

        BoardDto boardDto = BoardDto.builder()
                .subject(request.getSubject())
                .content(escapeContent)
                .createDate(LocalDateTime.now().toString())
                .viewCount(0)
                .userId(request.getUserId())  // 사용자 ID 설정
                .boardTypeId(request.getBoardTypeId())
                .contentId(request.getContentId())
                .build();
        try {
            List<ModerationResponse> subjectModeration = moderationService.calculateModeration(boardDto.getSubject());
            List<ModerationResponse> contentModeration = moderationService.calculateModeration(boardDto.getContent());

            if (subjectModeration.isEmpty() && contentModeration.isEmpty()) {
                boardMapper.writeArticle(boardDto);
            } else {
                BannedBoardDto bannedBoardDto = new BannedBoardDto(boardDto);

                for (ModerationResponse mr : subjectModeration) {
                    double max;
                    switch (mr.getAttributeName()) {
                        case "TOXICITY":
                            max = Math.max(bannedBoardDto.getToxicity(), mr.getScore());
                            bannedBoardDto.setToxicity(max);
                            break;
                        case "SEVERE_TOXICITY":
                            max = Math.max(bannedBoardDto.getSevereToxicity(), mr.getScore());
                            bannedBoardDto.setSevereToxicity(max);
                            break;
                        case "IDENTITY_ATTACK":
                            max = Math.max(bannedBoardDto.getIdentityAttack(), mr.getScore());
                            bannedBoardDto.setIdentityAttack(max);
                            break;
                        case "INSULT":
                            max = Math.max(bannedBoardDto.getInsult(), mr.getScore());
                            bannedBoardDto.setInsult(max);
                            break;
                        case "PROFANITY":
                            max = Math.max(bannedBoardDto.getProfanity(), mr.getScore());
                            bannedBoardDto.setProfanity(max);
                            break;
                        case "THREAT":
                            max = Math.max(bannedBoardDto.getThreat(), mr.getScore());
                            bannedBoardDto.setThreat(max);
                            break;
                        default:
                            break;
                    }
                }

                for (ModerationResponse mr : contentModeration) {
                    double max;
                    switch (mr.getAttributeName()) {
                        case "TOXICITY":
                            max = Math.max(bannedBoardDto.getToxicity(), mr.getScore());
                            bannedBoardDto.setToxicity(max);
                            break;
                        case "SEVERE_TOXICITY":
                            max = Math.max(bannedBoardDto.getSevereToxicity(), mr.getScore());
                            bannedBoardDto.setSevereToxicity(max);
                            break;
                        case "IDENTITY_ATTACK":
                            max = Math.max(bannedBoardDto.getIdentityAttack(), mr.getScore());
                            bannedBoardDto.setIdentityAttack(max);
                            break;
                        case "INSULT":
                            max = Math.max(bannedBoardDto.getInsult(), mr.getScore());
                            bannedBoardDto.setInsult(max);
                            break;
                        case "PROFANITY":
                            max = Math.max(bannedBoardDto.getProfanity(), mr.getScore());
                            bannedBoardDto.setProfanity(max);
                            break;
                        case "THREAT":
                            max = Math.max(bannedBoardDto.getThreat(), mr.getScore());
                            bannedBoardDto.setThreat(max);
                            break;
                        default:
                            break;
                    }
                }

                boardMapper.writeBannedArticle(bannedBoardDto);
                return -1;
            }
            return boardDto.getBoardId();
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 작성중에 오류가 발생했습니다.");
        }
    }

    /**
     * 글 전체 목록을 조회하는 메소드
     * 키워드를 입력받으면 검색 기능으로서 활용된다.
     *
     * @param boardType 조회할 글 카테고리
     * @param keyword   검색 기능으로 활용할 키워드
     * @return 글 전체 목록
     * @throws Exception 조회 실패 메시지
     */
    public List<BoardDto> listArticle(int boardType, String keyword) throws Exception {
        try {
            if (keyword == null) {
                return boardMapper.listArticle(boardType);
            }
            return search(boardType, keyword);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 리스트 조회중에 오류가 발생했습니다.");
        }
    }

    public List<BoardDto> listTripArticle(int boardType, int contentId, String keyword) throws Exception {
        try {
            if (keyword == null) {
                return boardMapper.listTripArticle(new TripBoardSearchDto(boardType, contentId));
            }
            return search(boardType, contentId, keyword);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 리스트 조회중에 오류가 발생했습니다.");
        }
    }

    /**
     * 차단된 글 전체 목록을 조회하는 메소드
     * 키워드를 입력받으면 검색 기능으로서 활용된다.
     *
     * @param boardType 조회할 글 카테고리
     * @param keyword   검색 기능으로 활용할 키워드
     * @return 글 전체 목록
     * @throws Exception 조회 실패 메시지
     */
    public List<BannedBoardDto> listBannedArticle(String keyword) throws Exception {
        try {
            return boardMapper.listBannedArticle(keyword);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 리스트 조회중에 오류가 발생했습니다.");
        }
    }

    public BannedBoardDto detailBanArticle(final int id) throws Exception {
        try {
            BannedBoardDto bannedBoardDto = boardMapper.findBanById(id);
            if (bannedBoardDto == null) {
                throw new NotFoundArticleException();
            }
            boardMapper.viewBanCount(id);
            return bannedBoardDto;
        } catch (NotFoundArticleException e) {
            e.fillInStackTrace();
            throw new NotFoundArticleException("해당 게시물이 없습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시물 상세 조회중에 오류가 발생했습니다.");
        }
    }

    /**
     * 글 제목을 기준으로 검색을 수행함
     * KMP 알고리즘을 사용
     *
     * @param boardType 카테고리
     * @param keyword   검색 키워드
     * @return 글 전체 목록
     * @throws Exception 전체 조회 실패메시지
     */
    public List<BoardDto> search(int boardType, String keyword) throws Exception {
        String[] keywords = keyword.split(" ");
        return Kmp.multiKmp(boardMapper.listArticle(boardType), keywords);
    }

    public List<BoardDto> search(int boardType, int contentId, String keyword) throws Exception {
        String[] keywords = keyword.split(" ");
        return Kmp.multiKmp(boardMapper.listTripArticle(new TripBoardSearchDto(boardType, contentId)), keywords);
    }

    /**
     * 글 ID를 기준으로 게시물 상세 조회를 수행함
     *
     * @param id 게시물 ID
     * @return 게시물
     * @throws Exception 상세조회 실패 메시지
     */
    public BoardDto detailArticle(final int id) throws Exception {
        try {
            BoardDto boardDto = boardMapper.findById(id);
            if (boardDto == null) {
                throw new NotFoundArticleException();
            }
            boardMapper.viewCount(id);
            return boardDto;
        } catch (NotFoundArticleException e) {
            e.fillInStackTrace();
            throw new NotFoundArticleException("해당 게시물이 없습니다.");
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 상세 조회중에 오류가 발생했습니다.");
        }
    }

    public BoardDto detailQna(final int id) throws Exception {
        try {
            BoardDto boardDto = boardMapper.findById(id);
            if (boardDto == null) {
                throw new NotFoundArticleException();
            }
            boardMapper.viewCount(id);
            return boardDto;
        } catch (NotFoundArticleException e) {
            e.fillInStackTrace();
            throw new NotFoundArticleException("해당 게시물이 없습니다.");
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 상세 조회중에 오류가 발생했습니다.");
        }
    }

    @Transactional
    public int updateArticle(UpdateBoardDto boardDto) throws Exception {
        try {
            List<ModerationResponse> subjectModeration = moderationService.calculateModeration(boardDto.getSubject());
            List<ModerationResponse> contentModeration = moderationService.calculateModeration(boardDto.getContent());

            if (subjectModeration.isEmpty() && contentModeration.isEmpty()) {

                String content = boardDto.getContent();
                String escapeContent = HtmlUtil.escapeHtml(content);
                boardDto.setContent(escapeContent);
                boardMapper.update(boardDto);
                return 1;
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("게시물 업데이트 중 오류가 발생했습니다.");
        }
    }

    @Transactional
    public void deleteArticle(int id) throws Exception {
        try {
            boardMapper.delete(id);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new Exception("게시물 삭제 중 오류가 발생했습니다.");
        }
    }

}
