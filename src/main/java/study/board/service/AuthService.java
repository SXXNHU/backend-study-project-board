package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import study.board.dto.request.LoginRequest;
import study.board.dto.response.LoginResponse;
import study.board.dto.request.SignUpRequest;
import study.board.entity.User;
import study.board.global.security.JwtProvider;
import study.board.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(SignUpRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "이미 존재하는 username입니다."
            );
        }
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User(request.username(),encodedPassword);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "존재하지 않는 사용자입니다."
                ));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "비밀번호가 일치하지 않습니다."
            );
        }
        String token = jwtProvider.createAccessToken(user.getId(),user.getUsername());
        return new LoginResponse(token);
    }
}
