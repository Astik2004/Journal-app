package com.yadav.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yadav.journalApp.entity.JournalEntry;
import com.yadav.journalApp.entity.User;
import com.yadav.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public List<User>getAllUser()
	{
		return userService.getAll();
	}
	
	@PostMapping
	public ResponseEntity<User> createEntry(@RequestBody User user)
	{
		try {
			userService.saveEntry(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		User userInDb = userService.findByUserName(user.getUserName());
		if (userInDb != null) {
			userInDb.setUserName(user.getUserName());
			userInDb.setPassword(user.getPassword());
			userService.saveEntry(userInDb);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
