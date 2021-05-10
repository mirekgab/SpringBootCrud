package pl.mirekgab.springbootcrud;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT ,classes = SpringBootCrudApplication.class)
class SpringBootCrudApplicationTests {

	@Test
	void contextLoads() {
	}

}
