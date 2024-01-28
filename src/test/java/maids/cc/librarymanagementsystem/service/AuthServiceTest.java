package maids.cc.librarymanagementsystem.service;

import maids.cc.librarymanagementsystem.auth.entity.Librarian;
import maids.cc.librarymanagementsystem.auth.exception.LibrarianNotFoundException;
import maids.cc.librarymanagementsystem.auth.payload.reponse.JwtResponse;
import maids.cc.librarymanagementsystem.auth.payload.request.LoginRequest;
import maids.cc.librarymanagementsystem.auth.payload.request.SignupRequest;
import maids.cc.librarymanagementsystem.auth.repository.LibrarianRepository;
import maids.cc.librarymanagementsystem.auth.service.impl.AuthServiceImpl;
import maids.cc.librarymanagementsystem.config.security.JwtService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private LibrarianRepository librarianRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void signUpTest(){
        //arrange
        when(librarianRepository.save(any(Librarian.class))).thenReturn(any(Librarian.class));

        //act
        SignupRequest signupRequest=new SignupRequest("barbie","girl");
        authService.signUp(signupRequest);

    }

    @Test
    public void authenticateTest(){
        //Arrange
        Librarian librarian=new Librarian();
        librarian.setUsername("barbie");
        librarian.setPassword("girl");
        when(librarianRepository.findByUsername(any(String.class))).thenReturn(Optional.of(librarian));
        when(jwtService.generateToken(any(User.class))).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYXJiaWUiLCJpYXQiOjE2OTk2ODgzNjYsImV4cCI6MTY5OTc3NDc2Nn0.NpbbRoPuIgzknwh3enH5qF0tUm2Yh8HYFvtZk07H6HE");
        when(passwordEncoder.encode(any(String.class))).thenReturn("passwordEncrypted");

        //Act
        LoginRequest jwtRequest=new LoginRequest("barbie","girl");
        JwtResponse jwtResponse=authService.login(jwtRequest);

        //Assert
        Assertions.assertThat(jwtResponse).isNotNull();
        assertEquals(jwtResponse.jwtToken(),"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYXJiaWUiLCJpYXQiOjE2OTk2ODgzNjYsImV4cCI6MTY5OTc3NDc2Nn0.NpbbRoPuIgzknwh3enH5qF0tUm2Yh8HYFvtZk07H6HE");
    }

    @Test
    public void authenticateTest_UserNotFound(){
        when(librarianRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        //Act and assert
        LoginRequest jwtRequest=new LoginRequest("otherThanbarbie","girl");
        LibrarianNotFoundException exception = assertThrows(LibrarianNotFoundException.class, () -> authService.login(jwtRequest));

        assertEquals(exception.getMessage(), "Librarian with username otherThanbarbie not found.");
    }

}
