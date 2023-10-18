package com.example.demo.services;

import java.util.Optional;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.demo.models.LoginUser;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    // ##### GET USER BY ID #####
    public User findUser(Long id) {
    	Optional<User> optionalUser = userRepo.findById(id);
    	return optionalUser.isPresent() ? optionalUser.get() : null;
    }
    
    // ##### UPDATE USER #####
    public User updateUser(User user) {
    	return userRepo.save(user);
    }

    // ##### GET USER BY EMAIL #####
    public User findUserByEmail(String email) {
    	Optional<User> optionalUser = userRepo.findByEmail(email);
    	return optionalUser.isPresent() ? optionalUser.get() : null;
    }
    
    // ##### REGISTER #####
    public User register(User newUser, BindingResult result) {
    	
    	// Reject if password doesn't match confirmation
    	if(!newUser.getPassword().equals(newUser.getConfirm())) {
    		// "confirm" must match path in form:errors path="confirm"
    	    result.rejectValue("confirm", "Matches", "Passwords do not match.");
    	}

    	// Reject if email is taken (present in database)
    	Optional<User> optionalUser = userRepo.findByEmail(newUser.getEmail());
    	if(optionalUser.isPresent()) {
    		// "email" must match path in form:errors path="email"
    		result.rejectValue("email", "Taken", "This email already has an account!");
    	}

    	// Return null if result has errors
    	if(result.hasErrors()) {
    		// Exit the method and go back to the controller 
    		return null;
    	}
    	
		String initials = String.valueOf(newUser.getFirstName().charAt(0)) + String.valueOf(newUser.getLastName().charAt(0));
		initials = initials.toUpperCase();
		newUser.setInitials(initials);
        
        // Hash and set password, save user to database
    	String hashed = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
        return userRepo.save(newUser);
    }
    
    
    // ##### LOGIN #####
    public User login(LoginUser loginObject, BindingResult result) {
        
    	Optional<User> optionalUser = userRepo.findByEmail(loginObject.getEmail());
    	User user = new User();
    			
    	// Find user in the DB by email reject if not present
        if(optionalUser.isPresent()) {
        	user = optionalUser.get();
        	// Reject if BCrypt password match fails
        	if(!BCrypt.checkpw(loginObject.getPassword(), user.getPassword())) {
        		result.rejectValue("password", "Matches", "Invalid password.");
        	}
        } else {
        	result.rejectValue("email","NotFound", "This is not a registered email.");
        }
        
        
        // Return null if result has errors
        if(result.hasErrors()) {
        	return null;
        }
        // Otherwise, return the user object
        return user;
    }
    
    
    // ##### CREATE DEMO ACCOUNT #####
    public User demoAccount() {
    	
    	// Create a Random object
        Random random = new Random();
        // Create a random number
        int randNum = random.nextInt(10000) + 90000;
        
    	User newUser = new User();
    	
    	newUser.setFirstName("Demo");
    	newUser.setLastName("Account#" + randNum);
    	newUser.setEmail(newUser.getLastName() + "@demo");
		String initials = String.valueOf(newUser.getFirstName().charAt(0)) + String.valueOf(newUser.getLastName().charAt(0));
		newUser.setInitials(initials);

    	// set last name as password
    	String hashed = BCrypt.hashpw(newUser.getLastName(), BCrypt.gensalt());
    	newUser.setPassword(hashed);
    	
    	return userRepo.save(newUser);
    }
    
}