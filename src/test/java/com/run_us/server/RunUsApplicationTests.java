package com.run_us.server;

import com.run_us.server.config.TestRedisConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import({TestRedisConfiguration.class})
@ActiveProfiles("test")
class RunUsApplicationTests {

	@Test
	void contextLoads() {
	}

}
