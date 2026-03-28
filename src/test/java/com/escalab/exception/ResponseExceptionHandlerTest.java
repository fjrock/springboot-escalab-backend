package com.escalab.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import com.escalab.controller.PersonaController;
import com.escalab.model.Persona;
import com.escalab.service.IPersonaService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResponseExceptionHandlerTest {

	@Test
	void manejarTodasExcepciones() {
		ResponseExceptionHandler h = new ResponseExceptionHandler();
		ResponseEntity<ExceptionResponse> r = h.manejarTodasExcepciones(new RuntimeException("boom"),
				new ServletWebRequest(new MockHttpServletRequest()));
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, r.getStatusCode());
	}

	@Test
	void manejarModeloException() {
		ResponseExceptionHandler h = new ResponseExceptionHandler();
		ResponseEntity<ExceptionResponse> r = h.manejarModeloException(new ModeloNotFoundException("nf"),
				new ServletWebRequest(new MockHttpServletRequest()));
		assertEquals(HttpStatus.NOT_FOUND, r.getStatusCode());
	}

	@Test
	void handleMethodArgumentNotValid() throws Exception {
		ResponseExceptionHandler h = new ResponseExceptionHandler();
		Persona target = new Persona();
		BeanPropertyBindingResult errors = new BeanPropertyBindingResult(target, "persona");
		errors.addError(new FieldError("persona", "email", "bad"));
		Method method = PersonaController.class.getDeclaredMethod("registrar", Persona.class);
		MethodParameter parameter = new MethodParameter(method, 0);
		MethodArgumentNotValidException ex = new MethodArgumentNotValidException(parameter, errors);
		ResponseEntity<Object> r = h.handleMethodArgumentNotValid(ex, new HttpHeaders(), HttpStatusCode.valueOf(400),
				new ServletWebRequest(new MockHttpServletRequest()));
		assertEquals(HttpStatus.BAD_REQUEST, r.getStatusCode());
	}

	@Test
	void integration_validationError() throws Exception {
		IPersonaService svc = mock(IPersonaService.class);
		PersonaController controller = new PersonaController();
		org.springframework.test.util.ReflectionTestUtils.setField(controller, "iPersonaService", svc);
		when(svc.registrar(any())).thenAnswer(i -> i.getArgument(0));

		MockMvc mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new ResponseExceptionHandler())
				.build();
		mvc.perform(post("/persona").contentType("application/json").content("{\"email\":\"not-an-email\"}"))
				.andExpect(status().isBadRequest());
	}
}
