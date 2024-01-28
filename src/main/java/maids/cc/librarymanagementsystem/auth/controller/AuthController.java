package maids.cc.librarymanagementsystem.auth.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import maids.cc.librarymanagementsystem.auth.payload.reponse.JwtResponse;
import maids.cc.librarymanagementsystem.auth.payload.request.LoginRequest;
import maids.cc.librarymanagementsystem.auth.payload.request.SignupRequest;
import maids.cc.librarymanagementsystem.auth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Configuration: Authentication APIs", description = "Librarian authentication endpoints")
@RequestMapping("/api/librarian/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> authenticateLibrarian(@RequestBody LoginRequest loginRequest) {
        log.info("request to login");

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerLibrarian(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info("request to sign-up");

        authService.signUp(signUpRequest);
        return ResponseEntity.ok("Librarian registered successfully!");
    }
}
