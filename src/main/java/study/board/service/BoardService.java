package study.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.dto.BoardCreateRequest;
import study.board.entity.Board;
import study.board.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /** 게시글 생성 **/
    public void create(BoardCreateRequest req){
        Board board = new Board(req.title(), req.content());
        boardRepository.save(board);
    }

    /** 게시글 전체 조회 **/
    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    /** 특정 게시글 조회 **/
    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    /** 게시물 수정 **/
    @Transactional
    public void update(Long id, Board newBoard) {
        Board updatedBoard = boardRepository.findById(id).orElse(null);
        updatedBoard.setTitle(newBoard.getTitle());
        updatedBoard.setContent(newBoard.getContent());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
