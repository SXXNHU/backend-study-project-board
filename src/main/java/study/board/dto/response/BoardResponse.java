package study.board.dto.response;

import study.board.entity.Board;

public record BoardResponse(
        Long id,
        String title,
        String content,
        Long authorId,
        String authorUsername
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getAuthor().getId(),
                board.getAuthor().getUsername()
        );
    }
}
