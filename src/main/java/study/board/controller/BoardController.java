package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.board.dto.AuthPrincipal;
import study.board.dto.request.BoardCreateRequest;
import study.board.dto.response.BoardResponse;
import study.board.dto.request.BoardUpdateRequest;
import study.board.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> createBoard(
            @RequestBody BoardCreateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        Long id = boardService.createBoard(request, principal.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoard() {
        return ResponseEntity.ok(boardService.getAllBoard());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoard(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBoard(
            @PathVariable Long id,
            @RequestBody BoardUpdateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        boardService.updateBoard(id, request, principal.id());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        boardService.deleteBoard(id, principal.id());
        return ResponseEntity.noContent().build();
    }
}
