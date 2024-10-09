package com.example.soloproject.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.soloproject.models.LoginUser;
import com.example.soloproject.models.Tracks;
import com.example.soloproject.models.User;
import com.example.soloproject.services.TracksService;
import com.example.soloproject.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {
	@Autowired
	private UserService uService;
	@Autowired
	private TracksService tService;
	@Autowired
	HttpSession session;
 
	//Login and Registration Page
	 @GetMapping("/")
	 public String index(Model model) 
	 {
	     model.addAttribute("newUser", new User());
	     model.addAttribute("newLogin", new LoginUser());
	     return "LoginAndRegister.jsp";
	 }
	 
	 //Route to add the new User to the database. Also, checks for any errors in the entry beforehand and shows validations if "errors" are detected. See User Model for "errors" details.
	 @PostMapping("/register")
	 public String registerUser(@Valid @ModelAttribute("newUser") User newUser, 
	         BindingResult result, Model model, HttpSession session) 
	 {
	     
		 uService.register(newUser, result);
	     
	     if(result.hasErrors()) {
	         model.addAttribute("newLogin", new LoginUser());
	         return "LoginAndRegister.jsp";}
	     
	     else {
	     session.setAttribute("userId", newUser.getId());
	     return "redirect:/welcome";}
	 }
	 
	 //Route to retrieve the User from the database. Also, checks for any errors in the entry beforehand and shows validations if "errors" are detected. See User Model for "errors" details.
	 @PostMapping("/login")
	 public String loginUser(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
	         BindingResult result, Model model, HttpSession session) 
	 {
	     
	     User user = uService.login(newLogin, result);
	 
	     if(result.hasErrors()) {
	         model.addAttribute("newUser", new User());
	         return "LoginAndRegister.jsp";}
	     
	     else {
	     session.setAttribute("userId", user.getId());
	     return "redirect:/welcome";}
	 }
	 
	 //Main page displaying Logged in User and all song entries
	 @GetMapping("/welcome")
	 public String Dashboard(@ModelAttribute("track")Tracks track, HttpSession session, Model model)
	 {
			Long userId = (Long) session.getAttribute("userId");
			if(userId==null) {
				return "redirect:/";
			}
			
			else {
			
			User user = uService.getUserByID(userId);
			
			model.addAttribute("user",user);
			
			List<Tracks> tracks = tService.alltracks();
			model.addAttribute("tracks", tracks);
			
			return "Dashboard.jsp";}
		}
	 
	 //Route to display the form for creating a new song
	 @GetMapping("/tracks/new")
	 public String newTrack(@ModelAttribute("track")Tracks track, HttpSession session, Model model) {
		 
		 Long userId = (Long) session.getAttribute("userId");
			if(userId==null) {
				return "redirect:/";
			}

		 return "newTrack.jsp";
	 }
	 

	 //Route to the Edit page of the song
	 @GetMapping("/tracks/{id}/edit")
	 public String editTrack(@PathVariable("id") Long id, Model model) {
		 
		 Long userId = (Long) session.getAttribute("userId");
			if(userId==null) {
				return "redirect:/";
			}
	        
	        model.addAttribute("tracks", tService.getTracksByID(id));
	        
	        return "editTrack.jsp";
	    }
	 
	 //Route to the Display Page of a song
	 @GetMapping("/tracks/{id}")
	 public String showTrack(@PathVariable("id") Long id, Model model, HttpSession session) {
		 
		 Long userId = (Long) session.getAttribute("userId");
			if(userId==null) {
				return "redirect:/";
			}else {
		 
		 model.addAttribute("track", tService.getTracksByID(id));
		 return "showTrack.jsp";}
	 }
	 
	 
	 @DeleteMapping("/tracks/delete/{trackId}")
		 public String deleteTrack (@PathVariable ("trackId") Long id) {
			 tService.deleteTrack(id);
			 return "redirect:/welcome";
		 }
	 
	 
	 
	 @GetMapping("/logout")
	 public String logOut(HttpSession session) {
	 session.invalidate();
	 //to keep information but still logout (session.setAttribute("userID", null);)
	 return "redirect:/";
 }

	 
	 //Route to handle the file upload via the form and add to the database
	 @PostMapping("/upload-track")
	 public String uploadTrack(@RequestParam("file") MultipartFile file,
	                           @Valid @ModelAttribute("track") Tracks track, // Add @Valid here
	                           BindingResult result, // Add BindingResult to capture validation errors
	                           HttpSession session, Model model) {

	     Long userId = (Long) session.getAttribute("userId");

	     if (userId == null) {
	         return "redirect:/";
	     }

	     // Validate inputs
	     if (result.hasErrors()) {
	         model.addAttribute("errorMessage", "All fields are required!");
	         return "newTrack.jsp"; // Return to form if there are validation errors
	     }

	     try {
	         // Validate file size (16 MB max)
	         if (file.getSize() > 16 * 1024 * 1024) {
	             model.addAttribute("errorMessage", "File size exceeds the maximum limit of 16 MB.");
	             return "newTrack.jsp";
	         }

	         // Retrieve the user and associate it with the track
	         User user = uService.getUserByID(userId);
	         track.setUser(user);

	         // Save the track with the file
	         tService.createTrack(track, file); // Pass the file here

	         // Redirect to the dashboard after successful upload
	         return "redirect:/welcome";
	     } catch (IOException e) {
	         // Handle file processing errors
	         model.addAttribute("errorMessage", "Error processing the file.");
	         return "newTrack.jsp";
	     }
	 }

	 //Route to update an existing track in the database including file and file name
	 @PutMapping("/tracks/{id}")
	 public String updateTrack(@PathVariable Long id,
	                          @Valid @ModelAttribute("tracks") Tracks tracks,
	                          BindingResult result,
	                          @RequestParam("file") MultipartFile file,
	                          HttpSession session, Model model) { // Add HttpSession parameter

	     Long userId = (Long) session.getAttribute("userId");

	     if (userId == null) {
	         return "redirect:/";
	     }
	     //Checks to see if the Track exists if not returns an errorPage.jsp
	     Tracks existingTrack = tService.getTracksByID(id);
	     if (existingTrack == null) {
	         model.addAttribute("errorMessage", "Track not found.");
	         return "errorPage.jsp";
	     }
	     
	     // Return to form if there are validation errors
	     if (result.hasErrors()) {
	         model.addAttribute("tracks", tracks);
	         return "editTrack.jsp"; 
	     }

	     // Update track information
	     try {
	         existingTrack.setTitle(tracks.getTitle());
	         existingTrack.setGenre(tracks.getGenre());

	         // Handle file upload logic
	         if (file != null && !file.isEmpty()) {
	             existingTrack.setFileName(file.getOriginalFilename());
	             existingTrack.setFileData(file.getBytes());
	         }

	         // Update the track using the service method
	         tService.updateTrack(id, existingTrack, file);
	         model.addAttribute("successMessage", "Track updated successfully!");

	     } catch (IOException e) {
	         model.addAttribute("errorMessage", "Error processing the file.");
	         return "editTrack.jsp"; // Return to form if there's an error
	     }

	     return "redirect:/welcome";
	 }
	}
	
	 
