package com.ua.jobua.dto;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String language; // EN / UA
}
