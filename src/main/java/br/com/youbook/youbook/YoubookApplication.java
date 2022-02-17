package br.com.youbook.youbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class YoubookApplication {

	public static void main(String[] args) {
		SpringApplication.run(YoubookApplication.class, args);
                System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
