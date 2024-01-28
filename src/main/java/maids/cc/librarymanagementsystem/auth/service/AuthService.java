package maids.cc.librarymanagementsystem.auth.service;

import maids.cc.librarymanagementsystem.auth.payload.reponse.JwtResponse;
import maids.cc.librarymanagementsystem.auth.payload.request.LoginRequest;
import maids.cc.librarymanagementsystem.auth.payload.request.SignupRequest;

public interface AuthService {
    void signUp(SignupRequest request);
    JwtResponse login(LoginRequest request);
}
