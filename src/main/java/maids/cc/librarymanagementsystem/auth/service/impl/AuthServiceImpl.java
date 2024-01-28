package maids.cc.librarymanagementsystem.auth.service.impl;

import lombok.RequiredArgsConstructor;
import maids.cc.librarymanagementsystem.auth.entity.Librarian;
import maids.cc.librarymanagementsystem.auth.exception.LibrarianNotFoundException;
import maids.cc.librarymanagementsystem.auth.payload.reponse.JwtResponse;
import maids.cc.librarymanagementsystem.auth.payload.request.LoginRequest;
import maids.cc.librarymanagementsystem.auth.payload.request.SignupRequest;
import maids.cc.librarymanagementsystem.auth.repository.LibrarianRepository;
import maids.cc.librarymanagementsystem.auth.service.AuthService;
import maids.cc.librarymanagementsystem.config.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final LibrarianRepository librarianRepository;


    @Override
    public void signUp(SignupRequest request) {
        Librarian librarian = new Librarian();
        librarian.setUsername(request.username());
        librarian.setPassword(passwordEncoder.encode(request.password()));
        librarianRepository.save(librarian);
    }

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Librarian librarian = librarianRepository.findByUsername(request.username()).orElseThrow(() ->new LibrarianNotFoundException(request.username()));

        User user= new User(librarian.getUsername(),passwordEncoder.encode(librarian.getPassword()), Collections.singleton(new SimpleGrantedAuthority("Librarian")));

        var jwtToken = jwtService.generateToken(user);
        return new JwtResponse(jwtToken);
    }
}
