package com.playtwowin.scriber.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScriberController {

    @GetMapping
    public String scribeApi(){
        return "scriber";
    }
}
