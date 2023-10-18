package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.LoginUser;
import com.example.demo.models.User;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
	@Autowired
	UserService userServ;

	// ##### DISPLAY LOGIN AND REGISTRATION PAGE #####
	@RequestMapping("/auth")
	public String index(
			@ModelAttribute("user") User user,
			@ModelAttribute("loginUser") LoginUser loginUser) {

		return "auth2.jsp";
	}

	
	// ##### REGISTER USER #####
	@PostMapping("/register")
	public String register(
			Model model,
			HttpSession session,
			@Valid @ModelAttribute("user") User user,
			BindingResult result) {
		
		User activeUser = userServ.register(user, result);
		
		if(result.hasErrors()) {
			model.addAttribute("loginUser", new LoginUser());
			return "auth2.jsp";
		}
		
		// set activeUser in session
		session.setAttribute("activeUserId", activeUser.getId());
		return "redirect:/dashboard";
	}
	
	
	// ##### LOGIN USER #####
	@PostMapping("/login")
	public String login(
			Model model,
			HttpSession session,
			@Valid @ModelAttribute("loginUser") LoginUser loginUser,
			BindingResult result) {
		
		User activeUser = userServ.login(loginUser, result);
		System.out.println(result);
		if(result.hasErrors()) {
			model.addAttribute("user", new User());
			return "auth2.jsp";
		}

		session.setAttribute("activeUserId", activeUser.getId());
		return "redirect:/dashboard";
	}
	
	// ##### LOGOUT USER #####
	@RequestMapping("/logout")
	public String logout(
			HttpSession session) {
		
		// clear activeUser in session
		session.setAttribute("activeUserId", null);
		
		return "redirect:/";
	}
	
	// ##### CREATE DEMO ACCOUNT #####
	@RequestMapping("/demo")
	public String demo(
			HttpSession session,
			Model model) {
		
		User activeUser = userServ.demoAccount();
		session.setAttribute("activeUserId", activeUser.getId());
		return "redirect:/dashboard";
	}
	
	
	// ##### CHANGE COLOR THEME #####
	@RequestMapping("/changeColorTheme/{color}")
	public String changeColorTheme(
			@PathVariable String color,
			HttpSession session) {
		
		User activeUser = userServ.findUser((Long) session.getAttribute("activeUserId"));

		activeUser.setColorTheme(color);
		userServ.updateUser(activeUser);
		
		return null;
	}
}
