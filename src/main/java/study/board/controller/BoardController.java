package study.board.controller;

import org.springframework.web.bind.annotation.*;
import study.board.dto.BoardCreateRequest;
import study.board.entity.Board;
import study.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping()
    public void createBoard(@RequestBody BoardCreateRequest req){
        boardService.create(req);
    }

    @GetMapping()
    public List<Board> getAllBoard(){
        return boardService.getAllBoard();
    }

    @GetMapping("/{id}")
    public Board getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody Board board){
        boardService.update(id, board);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable Long id){
        boardService.delete(id);
    }
}
