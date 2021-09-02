package com.p5.adoptions.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/v1/moderators")
@PreAuthorize("isAuthenticated() && hasRole('MOD')")
public class ModeratorController
{

    @GetMapping("/hello")
    @PreAuthorize("principal.username.startsWith('animal')")
    public String hello()
    {
        return "Hello Moderator!";
    }
}
