package com.playtwowin.scriber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playtwowin.model.User;
import com.playtwowin.repo.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;

	@PostMapping
	public ResponseEntity<String> saveUser(@RequestParam String userName, @RequestParam String password,
			@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {
		
		userRepo.save(new User(0, userName, password, firstName, lastName, email));

		return new ResponseEntity<String>("New User added to Database", HttpStatus.OK);
	}

}
