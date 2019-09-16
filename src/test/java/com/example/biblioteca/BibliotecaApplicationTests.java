package com.example.biblioteca;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BibliotecaApplicationTests {

	@Test @Ignore
	public void contextLoads() {
		assertTrue(true);
	}

	@Test
	public void firstTest() {
		assertTrue(true);
	}

}
