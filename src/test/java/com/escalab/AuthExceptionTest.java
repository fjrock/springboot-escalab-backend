package com.escalab;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

class AuthExceptionTest {

	@Test
	void commenceWritesJson() throws Exception {
		AuthException entry = new AuthException();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setServletPath("/api/x");
		MockHttpServletResponse res = new MockHttpServletResponse();
		entry.commence(req, res, new BadCredentialsException("x"));
		assertEquals(401, res.getStatus());
		assertEquals("application/json", res.getContentType());
		assertEquals("UTF-8", res.getCharacterEncoding());
	}
}
