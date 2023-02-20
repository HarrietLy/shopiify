package com.harriet.shopiify.auth.vo;

import lombok.Data;

@Data
public class SignupVO {
    private String username;
    private String email;
    private String password;
    private String role;
}
