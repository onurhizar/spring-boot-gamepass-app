package com.onurhizar.gamepass;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-test")
class GamePassApplicationTests {

	@Test
	void contextLoads() {
	}

}
