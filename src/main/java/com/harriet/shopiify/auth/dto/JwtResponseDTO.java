package com.harriet.shopiify.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class JwtResponseDTO {
    private String token;

    private Long type;
    private String username;
    private String email;
    private List<String> role;
}
