package com.securauth.securauthapi.payload;

import lombok.Data;

@Data
public class SignUpRequest {

    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;

}
