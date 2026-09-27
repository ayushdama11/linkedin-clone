package org.linkedinclone.userservice.dto;

import lombok.Data;

@Data
public class SignupRequestDto {

    private String name, email, password;
}
