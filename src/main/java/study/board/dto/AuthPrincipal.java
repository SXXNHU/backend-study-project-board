package study.board.dto;

import study.board.entity.User;

public record AuthPrincipal(Long id, String username) {

    public static AuthPrincipal from(User user) {
        return new AuthPrincipal(user.getId(), user.getUsername());
    }
}