package com.harriet.shopiify.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harriet.shopiify.auth.dto.JwtResponseDTO;
import com.harriet.shopiify.auth.vo.LoginVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers=AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    public void harrietShouldSignInOK() throws Exception{
        LoginVO loginVO = new LoginVO("harriet", "123343");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/auth/signin")
                        .content(asJsonString(loginVO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}