package com.escalab.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.escalab.model.Rol;
import com.escalab.model.Usuario;
import com.escalab.repo.IUsuarioRepo;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

	@Mock
	private IUsuarioRepo repo;

	@InjectMocks
	private UsuarioServiceImpl service;

	@Test
	void loadUserByUsername_ok() {
		Rol r = new Rol();
		r.setNombre("USER");
		Usuario u = new Usuario();
		u.setUsername("a");
		u.setPassword("p");
		u.setRoles(List.of(r));
		when(repo.findOneByUsername("a")).thenReturn(u);

		UserDetails ud = service.loadUserByUsername("a");
		assertEquals("a", ud.getUsername());
		assertEquals("p", ud.getPassword());
	}

	@Test
	void loadUserByUsername_notFound() {
		when(repo.findOneByUsername("x")).thenReturn(null);
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("x"));
	}
}
