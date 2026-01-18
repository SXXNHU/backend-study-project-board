package study.board.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.dto.request.BoardCreateRequest;
import study.board.dto.response.BoardResponse;
import study.board.dto.request.BoardUpdateRequest;
import study.board.entity.Board;
import study.board.entity.User;
import study.board.repository.BoardRepository;
import study.board.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    /** 게시글 생성 **/
    @Transactional
    public Long createBoard(BoardCreateRequest req, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("유저가 존재하지 않습니다."));
        Board board = new Board(req.title(), req.content(), user);
        return boardRepository.save(board).getId();
    }

    /** 게시글 전체 조회 **/
    public List<BoardResponse> getAllBoard() {
        return boardRepository.findAll().stream()
                .map(BoardResponse::from)
                .toList();
    }

    /** 특정 게시글 조회 **/
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        return BoardResponse.from(board);
    }

    /** 게시글 수정 (작성자) **/
    @Transactional
    public void updateBoard(Long boardId, BoardUpdateRequest req, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
        }
        board.update(req.title(), req.content());
    }

    /** 게시글 삭제 (작성자) **/
    @Transactional
    public void deleteBoard(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));

        if (!board.getAuthor().getId().equals(userId)) {
            throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);
    }
}
