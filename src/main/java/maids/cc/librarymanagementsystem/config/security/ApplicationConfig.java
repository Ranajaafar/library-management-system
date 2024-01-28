package maids.cc.librarymanagementsystem.config.security;

import lombok.RequiredArgsConstructor;
import maids.cc.librarymanagementsystem.auth.entity.Librarian;
import maids.cc.librarymanagementsystem.auth.exception.LibrarianNotFoundException;
import maids.cc.librarymanagementsystem.auth.repository.LibrarianRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final LibrarianRepository librarianRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->{
            Librarian librarian = librarianRepository.findByUsername(username).orElseThrow(() ->new LibrarianNotFoundException(username));

            return new User(librarian.getUsername(),librarian.getPassword(), Collections.singleton(new SimpleGrantedAuthority("Librarian")));
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
