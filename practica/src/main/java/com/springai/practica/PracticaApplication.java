package com.springai.practica;

import com.springai.practica.model.User;
import com.springai.practica.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticaApplication implements CommandLineRunner {

	private final UserRepository userRepository;

    public PracticaApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) {
		SpringApplication.run(PracticaApplication.class, args);
	}


	//Esta informacion es de prueba, se puede cambiar
	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByUsername("josepsito").isEmpty()) {
			User user = new User();
			user.setEmail("jhosef@gmail.com");
			user.setPassword("123456");
			user.setUsername("Josepsito");
			userRepository.save(user);
		}
	}
}
