package com.utn;

import com.utn.entity.ERol;
import com.utn.entity.Rol;
import com.utn.entity.UserEntity;
import com.utn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SoporteTecnicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoporteTecnicoApplication.class, args);
	}


	// Generar un usuario inicial para poder hacer login
	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository repository;

	@Bean
	CommandLineRunner init(){ // Comando que se ejecuta al iniciar el programa
		return args -> {
			UserEntity user = UserEntity.builder()
					.nombre("Victor")
					.apellido("Sosa")
					.username("userPrueba")
					.password(passwordEncoder.encode("12345"))
					.roles(Set.of(Rol.builder()
							.name(ERol.valueOf(ERol.ADMIN.name()))
							.build()))
					.build();
			repository.save(user);
		};
	}

}
