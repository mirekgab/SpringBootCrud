package pl.mirekgab.springbootcrud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
                clientRepository.save(new Client("client1"));
                clientRepository.save(new Client("client2"));
                clientRepository.save(new Client("client3"));
            };
        }
        
        @Bean 
        public MirekgabUriBuilder mirekgabUriBuilder() {
            return new MirekgabUriBuilder();
        }

}
