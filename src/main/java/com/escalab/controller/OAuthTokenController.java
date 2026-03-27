package com.escalab.controller;

import java.time.Instant;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthTokenController {

	private final AuthenticationManager authenticationManager;
	private final JwtEncoder jwtEncoder;

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${app.security.jwt-expiration-seconds}")
	private long expirationSeconds;

	public OAuthTokenController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtEncoder = jwtEncoder;
	}

	@PostMapping("/oauth/token")
	public ResponseEntity<?> token(
			@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
			@RequestParam(value = "grant_type", required = false) String grantType) {

		if (!"password".equals(grantType)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error("unsupported_grant_type"));
		}

		if (!isValidClientCredentials(authorizationHeader)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error("invalid_client"));
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		if (!authentication.isAuthenticated()) {
			throw new BadCredentialsException("Credenciales invalidas");
		}

		Instant now = Instant.now();
		List<String> roles = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority())
				.collect(Collectors.toList());

		JwtClaimsSet claims = JwtClaimsSet.builder()
				.issuer("escalab-backend")
				.subject(authentication.getName())
				.issuedAt(now)
				.expiresAt(now.plusSeconds(expirationSeconds))
				.claim("roles", roles)
				.build();

		String accessToken = jwtEncoder.encode(
				JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims)).getTokenValue();

		Map<String, Object> response = new LinkedHashMap<>();
		response.put("access_token", accessToken);
		response.put("token_type", "Bearer");
		response.put("expires_in", expirationSeconds);
		response.put("scope", "read write");

		return ResponseEntity.ok(response);
	}

	private boolean isValidClientCredentials(String authorizationHeader) {
		if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Basic ")) {
			return false;
		}
		String encodedCredentials = authorizationHeader.substring(6);
		byte[] decodedBytes;
		try {
			decodedBytes = java.util.Base64.getDecoder().decode(encodedCredentials);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		String decoded = new String(decodedBytes);
		String[] parts = decoded.split(":", 2);
		return parts.length == 2 && clientId.equals(parts[0]) && clientSecret.equals(parts[1]);
	}

	private Map<String, Object> error(String code) {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("error", code);
		return map;
	}
}

