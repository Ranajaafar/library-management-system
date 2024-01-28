package maids.cc.librarymanagementsystem.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import maids.cc.librarymanagementsystem.auth.payload.reponse.JwtResponse;
import maids.cc.librarymanagementsystem.auth.payload.request.LoginRequest;
import maids.cc.librarymanagementsystem.auth.payload.request.SignupRequest;
import maids.cc.librarymanagementsystem.auth.service.AuthService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void authenticateLibrarianTest() throws Exception{
        JwtResponse jwtResponse=new JwtResponse("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmcxIiwiaWF0IjoxNzA2MzUyNzA3LCJleHAiOjE3MDY0MzkxMDd9.USrykbHIT3S5Vu2RiccuXGEFZpde2vZHBxwk3j_as_o");
        when(authenticationService.login(any(LoginRequest.class))).thenReturn(jwtResponse);

        LoginRequest loginRequest=new LoginRequest("","");
        ResultActions response = mockMvc.perform(post("/api/librarian/auth/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwtToken", CoreMatchers.is(jwtResponse.jwtToken())));


    }

    @Test
    public void registerLibrarianTest() throws Exception{
        doNothing().when(authenticationService).signUp(any(SignupRequest.class));

        SignupRequest signupRequest=new SignupRequest("barbie","girll1");
        ResultActions response = mockMvc.perform(post("/api/librarian/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Librarian registered successfully!"));

    }

}
