package com.apbdoo.BooksStore.controllers;

import com.apbdoo.BooksStore.models.SignUpForm;
import com.apbdoo.BooksStore.models.User;
import com.apbdoo.BooksStore.repositories.UserRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private UserRepository repository;
    @RequestMapping(value="/signup")
    public String addUser(Model model){
        model.addAttribute("signUpForm", new SignUpForm());
        return "signup";
    }

    /*
     * Create new user
     * Check if user already exists & form validation
     *
     * @param signupForm
     * @param bindingResult
     * @return
     */


    public static boolean firstName( String firstName ) {
        return firstName.matches( "[A-Z][a-z]*" );}
    public static boolean lastName( String lastName ) {
        return lastName.matches( "[A-Z][a-zA-Z][^#&<>\"~;$^%{}?]*");}


    @RequestMapping(value="saveuser",method = RequestMethod.POST)
    public String save (@Valid @ModelAttribute("signUpForm") SignUpForm signupForm, BindingResult binding){
        if (!binding.hasErrors()) {
            if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) {
                String pwd=signupForm.getPassword();
                BCryptPasswordEncoder bc= new BCryptPasswordEncoder();
                String hashPwd= bc.encode(pwd);

                User newUser= new User();
                newUser.setPasswordHash(hashPwd);
                newUser.setUsername(signupForm.getUsername());
                if (emailValidator(signupForm.getEmail()))
                    newUser.setEmail(signupForm.getEmail());
                else
                    {binding.rejectValue("email","err.email", "Email not valid.");
                    return "signup";}

                newUser.setAddress(signupForm.getAddress());
                if(firstName(signupForm.getFirstName()))
                    newUser.setFirstName(signupForm.getFirstName());
                else
                     {binding.rejectValue("firstName","err.firstName", "FirstName not valid.");
                    return "signup";
                        }

                if(lastName(signupForm.getLastName()))
                    newUser.setLastName(signupForm.getLastName());
                else
                    {binding.rejectValue("lastName","err.lastName", "LastName not valid.");
                    return "signup";}

                newUser.setRole(signupForm.getRole());
                if (repository.findByUsername(signupForm.getUsername())==null) {
                    repository.save(newUser);
                }
                else {
                    binding.rejectValue("username","err.username", "Username already existed");
                    return "signup";
                }
            }
            else {
                binding.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
                return "signup";
            }

        }
        else {
            return "signup";
        }
        return "redirect:/login";
    }
    private boolean emailValidator(String email) {
        boolean isValid = false;
        try {
            //
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            System.out.println("You are in catch block -- Exception Occurred for: " + email);
        }
        return isValid;
    }

}