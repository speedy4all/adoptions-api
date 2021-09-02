package com.p5.adoptions.securitty;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdoptionPasswordEncoder extends BCryptPasswordEncoder
{
}
