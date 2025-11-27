package com.project007;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project007.db.IUserService;
import com.project007.db.User;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		//
		 new SpringApplicationBuilder(DemoApplication.class)
                .run(args);
		try(SeContainer container = SeContainerInitializer.newInstance().initialize())
        {
          IUserService us =  container.select(IUserService.class).get();
          us.create(User.builder().name("robin").build());
          us.selectAll().forEach(System.out::println);
		  
        }catch (Exception e)
        {
            e.printStackTrace();
        }
	}
	@GetMapping("/hello")
	public String hello() {
		return "Hello, World!";
	}

}
