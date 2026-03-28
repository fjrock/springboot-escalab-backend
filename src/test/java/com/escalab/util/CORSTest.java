package com.escalab.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.FilterConfig;

class CORSTest {

	@Test
	void initDestroyNoOp() throws Exception {
		CORS cors = new CORS();
		cors.init((FilterConfig) null);
		cors.destroy();
	}

	@Test
	void optionsShortCircuits() throws Exception {
		CORS cors = new CORS();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setMethod("OPTIONS");
		MockHttpServletResponse res = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		cors.doFilter(req, res, chain);
		assertEquals(200, res.getStatus());
	}

	@Test
	void getPassesChain() throws Exception {
		CORS cors = new CORS();
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.setMethod("GET");
		MockHttpServletResponse res = new MockHttpServletResponse();
		MockFilterChain chain = new MockFilterChain();
		cors.doFilter(req, res, chain);
		assertEquals(200, res.getStatus());
	}
}
