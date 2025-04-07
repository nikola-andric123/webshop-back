package fr.codecake.com.controller;

import fr.codecake.com.exceptions.ResourceAlreadyExistsException;
import fr.codecake.com.model.User;
import fr.codecake.com.request.CreateUserRequest;
import fr.codecake.com.request.LoginRequest;
import fr.codecake.com.request.RegisterRequest;
import fr.codecake.com.response.ApiResponse;
import fr.codecake.com.response.JwtResponse;
import fr.codecake.com.security.jwt.JwtUtils;
import fr.codecake.com.security.user.ShopUserDetails;
import fr.codecake.com.service.user.IUserService;
import fr.codecake.com.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("Login Successful", jwtResponse));
        }catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody CreateUserRequest registerRequest){
        try{

            if(registerRequest.getPassword().length() < 3){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Password must be at least 3 characters", null));
            }
            User newUser = userService.createUser(registerRequest);
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(newUser.getEmail(), registerRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateTokenForUser(authentication);
            ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
            JwtResponse jwtResponse = new JwtResponse(userDetails.getId(), jwt);
            return ResponseEntity.ok(new ApiResponse("Register and Login Successful", jwtResponse));

        }catch (ResourceAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
