package com.escalab.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tokens")
public class TokenController {

	@GetMapping("/anular/{tokenId:.*}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<String> revocarToken(@PathVariable("tokenId") String token) {
		return ResponseEntity.ok("La revocacion server-side no aplica con JWT stateless en esta version");
	}
}

