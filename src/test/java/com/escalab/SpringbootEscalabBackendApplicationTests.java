package com.escalab;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import com.escalab.model.Usuario;
import com.escalab.repo.IUsuarioRepo;

@SpringBootTest
@Transactional
class SpringbootEscalabBackendApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioRepo repo;

	@Test
	void contextLoads() {
		assertNotNull(passwordEncoder);
	}

	@Test
	void crearUsuarioEnH2() {
		Usuario us = new Usuario();
		us.setIdUsuario(99);
		us.setUsername("testuser_jacoco");
		us.setPassword(passwordEncoder.encode("secret"));
		us.setEnabled(true);
		us.setRoles(Collections.emptyList());

		Usuario retorno = repo.save(us);
		assertTrue(retorno.getPassword().equals(us.getPassword()));
	}
}
