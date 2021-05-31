package pl.mirekgab.springbootcrud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import pl.mirekgab.springbootcrud.model.Client;
import pl.mirekgab.springbootcrud.service.ClientRepository;

@SpringBootApplication
public class SpringBootCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudApplication.class, args);
	}
        
        @Bean
        public CommandLineRunner clientData(ClientRepository clientRepository) {
            return (args) -> {                
            };
        }
        
        @Bean 
        public MirekgabUriBuilder mirekgabUriBuilder() {
            return new MirekgabUriBuilder();
        }
        
}
