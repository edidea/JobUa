package com.ua.jobua.dto;
import lombok.Data;

@Data
public class EmployeeRegisterStep1Request {
    private String email;
    private String fullName;
    private String password;
    private String language;
}
