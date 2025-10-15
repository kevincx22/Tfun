package edu.demo.edubackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EduBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduBackendApplication.class, args);
	}

	// 初始化数据
	@Bean
	CommandLineRunner seed(edu.demo.edubackend.repo.UserRepo users,
						   org.springframework.security.crypto.password.PasswordEncoder encoder) {
		return args -> {
			users.findByEmail("student@demo.edu").orElseGet(() -> {
				var u = new edu.demo.edubackend.entity.User();
				u.setEmail("student@demo.edu"); u.setPasswordHash(encoder.encode("123456")); u.setRole("ROLE_STUDENT");
				return users.save(u);
			});
			users.findByEmail("teacher@demo.edu").orElseGet(() -> {
				var u = new edu.demo.edubackend.entity.User();
				u.setEmail("teacher@demo.edu"); u.setPasswordHash(encoder.encode("123456")); u.setRole("ROLE_TEACHER");
				return users.save(u);
			});
		};
	}

}



