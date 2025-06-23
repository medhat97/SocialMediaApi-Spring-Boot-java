
package com.example.Ecommerce;

import com.example.Ecommerce.entity.Role;
import com.example.Ecommerce.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner EcommerceData(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("ROLE_USER").isEmpty()) {
				roleRepository.save(new Role(null, "ROLE_USER"));
			}
			if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
				roleRepository.save(new Role(null, "ROLE_ADMIN"));
			}
		};
	}
}


