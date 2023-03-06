package com.ymk.health;

import com.ymk.health.utils.MD5Util;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class HealthApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Test
    public void testEncodePass() {
        // System.out.println(passwordEncoder().encode(MD5Util.md5("admin")));
        // $2a$10$7AYOhNxO0vC8AxvDPlE0rOIgQCDxgpqMgFgFvEdl2lqNRL74qmCC2
        System.out.println(passwordEncoder().matches(MD5Util.md5("admin"), "$2a$10$7AYOhNxO0vC8AxvDPlE0rOIgQCDxgpqMgFgFvEdl2lqNRL74qmCC2"));
    }
}
