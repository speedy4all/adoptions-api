package com.p5.adoptions.api.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainController
{

    @GetMapping("/hello")
    public ResponseEntity<String> greet(@RequestParam(name = "name", required = false) String name)
    {

        String owner = name == null ? "world" : name;

        return ResponseEntity.ok("Hello " + owner + ", from the Animal Shelter!");
    }

    @GetMapping("/owner")
    public String greetOwner()
    {
        return "Hello owner GET!";
    }
}
