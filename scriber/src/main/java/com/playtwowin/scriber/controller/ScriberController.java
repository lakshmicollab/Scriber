package com.playtwowin.scriber.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import com.playtwowin.model.User;
import com.playtwowin.repo.UserRepository;

@RestController
public class ScriberController {
	
	@Autowired
	UserRepository userRepo;

    @GetMapping("/scribe")
    public String scribeApi(){
        return "scriber";
    }
    @PostMapping
    public ResponseEntity<String> saveUser(@RequestParam String userName,@RequestParam String password,@RequestParam String firstName,@RequestParam String lastName,
    		@RequestParam String email){
    	userRepo.save(new User(0,userName, password, firstName, lastName, email));
    	
    	return new ResponseEntity<String>("New User added to Database", HttpStatus.OK);}

//    curl -k -X POST -F 'image=@/Pictures/running_cheetah.jpg' -v  http://localhost:8080/upload/
    @PostMapping("/upload")
    public String fileUploader(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }
}
