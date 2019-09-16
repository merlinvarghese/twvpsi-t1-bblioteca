package com.example.biblioteca;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class BibliotecaApplicationTests {

	@Test @Ignore
	void contextLoads() {
		assertTrue(true);
	}

	@Test
	void firstTest() {
		assertTrue(true);
	}

}
