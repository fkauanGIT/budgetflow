package com.myapp.budgetflow.dto;

import com.myapp.budgetflow.model.user.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long userId;
    private String username;
    private String email;
    private String password;
    private Role role;
}
