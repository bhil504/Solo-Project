package com.example.soloproject.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.soloproject.models.LoginUser;
import com.example.soloproject.models.User;
import com.example.soloproject.repositories.UserRepository;

@Service
public class UserService {
	
		@Autowired
		UserRepository uRepo;
		    
		// returns all the Users
	    public List<User> allUsers(){
	    	return uRepo.findAll();
	    }
	    
	    // creates a User
	    public User register(User u) {
	        return uRepo.save(u);
	    }
	    
	 // retrieves a User
	    public User getUserByID(Long id) {
	        Optional<User> optionalUser = uRepo.findById(id);
	        if(optionalUser.isPresent()) {
	            return optionalUser.get();
	        } else {
	            return null;
	        }
	    }
		
	    // updates a User
	    public User updateUser(User u) {
	        return uRepo.save(u);
	    }
	    
	  //deletes a User
	    public void deleteUser(Long id) {
	    	uRepo.deleteById(id);
	    }
	    
	    //Register and Login methods!
		public User register(User newUser, BindingResult result) {

			//Check if the email already exists in the database
			Optional<User> potentialUser = uRepo.findByEmail(newUser.getEmail());
			
			//if the email does exist in the database, throw an error
			if(potentialUser.isPresent()) {
				result.rejectValue("email", "Matches", "Account already exists. Please Sign in");
			}
			
			
			//Check if the password matches the confirm password
				//if not, throw an error
			if(!newUser.getPassword().equals(newUser.getConfirm())) {
			    result.rejectValue("confirm", "Matches", "The Confirm Password must match Password!");
			}
			
			if(result.hasErrors()) {
				// Exit the method and go back to the controller 
				// to handle the response
				return null;
			}
			
			//Hash and then use bCrypt password for basic security
			String hashedPass = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt(10));
			newUser.setPassword(hashedPass);
			


			return uRepo.save(newUser);
		    }
		
		// This method will be called from the controller
		// whenever a user submits a login form.
		public User login(LoginUser newLoginUser, BindingResult result) {
			
			// Find user in the DB by email
			// Reject if NOT present
			//Check if the email already exists in the database
	    	Optional<User> potentialUser = uRepo.findByEmail(newLoginUser.getEmail());
	    		
	    	//if the email does exist in the database, throw an error
	    	if(!potentialUser.isPresent()) {
	    			result.rejectValue("email", "Matches", "User Not Found");
	    			return null;
	    		}
	        
	    	// Reject if BCrypt password match fails
	    	User user = potentialUser.get();
	    	
	    	if(!BCrypt.checkpw(newLoginUser.getPassword(), user.getPassword()))
	    	{
	    		
	    		result.rejectValue("password","Matches", "Email/Password Invalid");
	    		return null;}
	    	
	    	// Return null if result has errors
	    	// Otherwise, return the user object
	    	if(result.hasErrors()) {
	    								return null;
	    							}
	    	return user;}
	    
	}
