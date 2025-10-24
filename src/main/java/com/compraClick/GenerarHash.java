package com.compraClick;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHash {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "MiPassAdmin123!";
        String hash = encoder.encode(raw);
        System.out.println(hash);
    }
}
