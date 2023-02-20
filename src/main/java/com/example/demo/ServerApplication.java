package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import com.example.demo.model.article.Article;
import com.example.demo.model.article.ArticleRepository;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserRepository;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	@Bean
	ApplicationRunner init(UserRepository userRepo, ArticleRepository articleRepo ) {
		return arg -> {

			userRepo.save(new User("Alayne Maynard","amaynard1@icq.com","PfyNu1Z"));
			userRepo.save(new User("Reid Pedrollo","rpedrollo2@google.ca","G7E8Cm"));
			articleRepo.save(new Article("a","a","a","a"));
			articleRepo.save(new Article("b","b","b","b"));
			articleRepo.save(new Article("c","c","c","c"));

		};
	}

}
