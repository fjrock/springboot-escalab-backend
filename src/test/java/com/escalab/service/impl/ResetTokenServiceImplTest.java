package com.escalab.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.escalab.model.ResetToken;
import com.escalab.repo.IResetTokenRepo;

@ExtendWith(MockitoExtension.class)
class ResetTokenServiceImplTest {

	@Mock
	private IResetTokenRepo repo;

	@InjectMocks
	private ResetTokenServiceImpl service;

	@Test
	void findByToken() {
		ResetToken t = new ResetToken();
		when(repo.findByToken("abc")).thenReturn(t);
		assertEquals(t, service.findByToken("abc"));
	}

	@Test
	void guardar() {
		ResetToken t = new ResetToken();
		service.guardar(t);
		verify(repo).save(t);
	}

	@Test
	void eliminar() {
		ResetToken t = new ResetToken();
		service.eliminar(t);
		verify(repo).delete(t);
	}
}
