package com.escalab;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.escalab.model.Usuario;
import com.escalab.repo.IUsuarioRepo;


@SpringBootTest
public class SpringbootEscalabBackendApplicationTests {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private IUsuarioRepo repo;
	
	@Test
	public void crearUsuario() {
		Usuario us = new Usuario();
		us.setIdUsuario(1);
		us.setUsername("francisco");
		us.setPassword(passwordEncoder.encode("1"));
		us.setEnabled(true);
		
		Usuario retorno = repo.save(us);
		
		assertTrue(retorno.getPassword().equalsIgnoreCase(us.getPassword()));
	}

}
