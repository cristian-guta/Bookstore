package com.apbdoo.BooksStore.dto;


import com.apbdoo.BooksStore.models.User;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserDTO {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String address;
    private Boolean deactivated;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.deactivated = user.getDeactivated();
        this.role = user.getRole();
    }

    public UserDTO(String username, String password, String email, String firstName, String lastName, String address, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.role = role;
    }
}