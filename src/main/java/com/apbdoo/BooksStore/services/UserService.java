package com.apbdoo.BooksStore.services;


import com.apbdoo.BooksStore.dto.UserDTO;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {

        this.userRepository = userRepository;

    }

    public void seedUsers() {
      //  seedUser("admin", "password", "Bianca", " Barcan", "bianca.barcan40@gmail.com","Adresa 1",false, "admin");
        seedUser("user", "$2y$12$hLLwaGyNBpGGxebalo746.7t//IhXE8p.dnPv6OmiWJhh2jNGUGy2", " ", " ", "gutacristian98@gmail.com", "Adresa 2",false, "user");
        seedUser("ADMIN", hashPwd, "Irina", "Branoiu", "email", " ", false, "ADMIN");
    }

    private void seedUser(String username, String password, String firstName, String lastName, String email, String address, boolean deactivated, String role) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = new User()
                    .setUsername(username)
                    .setPasswordHash(password)
                    .setEmail(email)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setAddress(address)
                    .setDeactivated(deactivated)
                    .setRole(role);
            userRepository.save(user);
        }
    }

    String pwd = "ADMIN";
    BCryptPasswordEncoder bc= new BCryptPasswordEncoder();
    String hashPwd= bc.encode(pwd);

    public List<UserDTO> getAll() {
        List<UserDTO> users = new ArrayList<>();
        userRepository.findAll().forEach(u -> users.add(new UserDTO(u)));
        return users;
    }

    public UserDTO getCurrentUser(Principal principal) {
        return new UserDTO(userRepository.findByUsername(principal.getName()));
    }

    public UserDTO updateUser(Principal principal, Integer id, UserDTO updatedUser) {
        Optional<User> requestedUser = userRepository.findById(id);
        User currentUser = userRepository.findByUsername(principal.getName());
        if (requestedUser != null && requestedUser.get().getId() == currentUser.getId()) {
            currentUser
                    .setFirstName(updatedUser.getFirstName())
                    .setLastName(updatedUser.getLastName())
                    .setEmail(updatedUser.getEmail())
                    .setAddress(updatedUser.getAddress());
            return new UserDTO(userRepository.save(currentUser));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!");
        }
    }


}
