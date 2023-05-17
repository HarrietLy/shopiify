package com.harriet.shopiify.auth.controller;
import javax.validation.Valid;

import com.harriet.shopiify.auth.dto.JwtResponseDTO;
import com.harriet.shopiify.auth.dto.MessageVO;
import com.harriet.shopiify.auth.jwt.JwtUtils;
import com.harriet.shopiify.auth.model.User;
import com.harriet.shopiify.auth.model.UserRole;
import com.harriet.shopiify.auth.repository.UserRepository;
import com.harriet.shopiify.auth.service.UserDetailsImpl;
import com.harriet.shopiify.auth.vo.LoginVO;
import com.harriet.shopiify.auth.vo.SignupVO;
//import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@Slf4j
@Tag(name="authentication")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginVO loginVO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVO.getUsername(), loginVO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> role = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponseDTO(jwt, userDetails.getId(), userDetails.getUsername(),userDetails.getEmail(), role));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser (@Valid @RequestBody SignupVO signupVO){
        if (userRepository.existsByUsername(signupVO.getUsername())){
            return ResponseEntity.badRequest().body(new MessageVO("Error: Username is already taken."));
        }
        if (userRepository.existsByEmail(signupVO.getEmail())){
            return ResponseEntity.badRequest().body(new MessageVO("Error: Email is already taken"));
        }
                String role = signupVO.getRole();
        UserRole matchedUserRole = null;
        for (UserRole userRole : UserRole.values()){
            if (userRole.name().equalsIgnoreCase(role)){
                matchedUserRole=userRole;
//                user.setRole(userRole.name());
                break;
            }
        }
        if (matchedUserRole==null) {return ResponseEntity.badRequest().body(new MessageVO("Error: Role is not valid"));}
        User user = new User(signupVO.getUsername(), signupVO.getEmail(), passwordEncoder.encode(signupVO.getPassword()), matchedUserRole.name());

        log.info("user: {}", user);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageVO("User registered successfully"));
    }


    @GetMapping("/test")
    public String allAccess(){
        return "Public Content";
    }
}
