package com.escalab.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.escalab.dto.FiltroConsultaDTO;
import com.escalab.exception.ExceptionResponse;
import com.escalab.exception.ModeloNotFoundException;

class ModelCoverageTest {

	@Test
	void filtroConsultaDTO() {
		FiltroConsultaDTO f = new FiltroConsultaDTO();
		f.setRun("1-9");
		f.setNombre("n");
		f.setIdConsulta(1);
		f.setIdCategoria(2);
		f.setIdPersona(3);
		f.setIdProducto(4);
		assertEquals("1-9", f.getRun());
		assertEquals("n", f.getNombre());
		assertEquals(1, f.getIdConsulta());
		assertEquals(2, f.getIdCategoria());
		assertEquals(3, f.getIdPersona());
		assertEquals(4, f.getIdProducto());
	}

	@Test
	void persona() {
		Persona p = new Persona();
		p.setIdPersona(1);
		p.setNombre("n");
		p.setApellidoPaterno("a");
		p.setApellidoMaterno("b");
		p.setRun("12345678");
		p.setDv("k");
		p.setTelefono("123456789012");
		p.setTipoPersona("NAT");
		p.setEmail("a@b.co");
		p.setBanned(true);
		assertTrue(p.isBanned());
		p.setBanned(false);
		assertFalse(p.isBanned());
		assertEquals(1, p.getIdPersona());
	}

	@Test
	void producto() {
		LocalDateTime now = LocalDateTime.now();
		Producto p = new Producto();
		p.setIdProducto(1);
		p.setNombre("x");
		p.setStock(2);
		p.setFechaCreacion(now);
		p.setFechaActualizacion(now);
		p.setTipoIntercambio("t");
		p.setLugarEntrega("l");
		assertEquals(1, p.getIdProducto());
		assertEquals(now, p.getFechaCreacion());
	}

	@Test
	void categoria() {
		Categoria c = new Categoria();
		c.setIdCategoria(1);
		c.setNombre("n");
		assertEquals(1, c.getIdCategoria());
	}

	@Test
	void consultaEqualsHashCode() {
		assertNull(new Consulta().getIdConsulta());
		Consulta a = new Consulta();
		a.setIdConsulta(1);
		Consulta b = new Consulta();
		b.setIdConsulta(1);
		Consulta c = new Consulta();
		c.setIdConsulta(2);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertNotEquals(a, c);
		assertNotEquals(a, null);
		assertNotEquals(a, "x");
		Consulta nullId = new Consulta();
		Consulta nullId2 = new Consulta();
		assertEquals(nullId, nullId2);
		assertTrue(a.equals(a));
	}

	@Test
	void rolUsuario() {
		Rol r = new Rol();
		r.setIdRol(1);
		r.setNombre("USER");
		r.setDescripcion("d");
		Usuario u = new Usuario();
		u.setIdUsuario(1);
		u.setUsername("u");
		u.setPassword("p");
		u.setEnabled(true);
		u.setRoles(List.of(r));
		assertEquals("USER", u.getRoles().get(0).getNombre());
	}

	@Test
	void resetToken() {
		ResetToken t = new ResetToken();
		t.setId(1);
		t.setToken("tok");
		Usuario u = new Usuario();
		u.setUsername("x");
		t.setUser(u);
		t.setExpiracion(60);
		assertNotNull(t.getExpiracion());
		t.setExpiracion(LocalDateTime.now().minusMinutes(1));
		assertTrue(t.estaExpirado());
		t.setExpiracion(LocalDateTime.now().plusHours(1));
		assertFalse(t.estaExpirado());
	}

	@Test
	void consultaCategoriaProductoPersonaEntities() {
		ConsultaCategoria cc = new ConsultaCategoria();
		Consulta cx = new Consulta();
		Categoria cat = new Categoria();
		cc.setConsulta(cx);
		cc.setCategoria(cat);
		assertEquals(cx, cc.getConsulta());

		ConsultaProducto cp = new ConsultaProducto();
		Producto pr = new Producto();
		cp.setConsulta(cx);
		cp.setProducto(pr);
		assertEquals(pr, cp.getProducto());

		ConsultaPersona cpe = new ConsultaPersona();
		Persona pe = new Persona();
		cpe.setConsulta(cx);
		cpe.setPersona(pe);
		assertEquals(pe, cpe.getPersona());
	}

	@Test
	void consultaCategoriaPK() {
		ConsultaCategoriaPK pk = new ConsultaCategoriaPK();
		Categoria c = new Categoria();
		c.setIdCategoria(1);
		Consulta q = new Consulta();
		q.setIdConsulta(1);
		ReflectionTestUtils.setField(pk, "categoria", c);
		ReflectionTestUtils.setField(pk, "consulta", q);
		ConsultaCategoriaPK pk2 = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pk2, "categoria", c);
		ReflectionTestUtils.setField(pk2, "consulta", q);
		assertEquals(pk, pk2);
		assertEquals(pk.hashCode(), pk2.hashCode());
		assertNotEquals(pk, null);
		assertNotEquals(pk, "x");
		ConsultaCategoriaPK other = new ConsultaCategoriaPK();
		Categoria c2 = new Categoria();
		c2.setIdCategoria(2);
		ReflectionTestUtils.setField(other, "categoria", c2);
		ReflectionTestUtils.setField(other, "consulta", q);
		assertNotEquals(pk, other);
		ConsultaCategoriaPK sameCatBothConsultaNull = new ConsultaCategoriaPK();
		ConsultaCategoriaPK sameCatBothConsultaNull2 = new ConsultaCategoriaPK();
		Categoria cat = new Categoria();
		cat.setIdCategoria(1);
		ReflectionTestUtils.setField(sameCatBothConsultaNull, "categoria", cat);
		ReflectionTestUtils.setField(sameCatBothConsultaNull2, "categoria", cat);
		assertEquals(sameCatBothConsultaNull, sameCatBothConsultaNull2);
		ConsultaCategoriaPK thisConsNull = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(thisConsNull, "categoria", cat);
		ReflectionTestUtils.setField(thisConsNull, "consulta", null);
		ConsultaCategoriaPK otherConsSet = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(otherConsSet, "categoria", cat);
		ReflectionTestUtils.setField(otherConsSet, "consulta", new Consulta());
		assertNotEquals(thisConsNull, otherConsSet);
	}

	@Test
	void consultaProductoPK() {
		ConsultaProductoPK pk = new ConsultaProductoPK();
		Producto p = new Producto();
		Consulta q = new Consulta();
		ReflectionTestUtils.setField(pk, "producto", p);
		ReflectionTestUtils.setField(pk, "consulta", q);
		ConsultaProductoPK pk2 = new ConsultaProductoPK();
		ReflectionTestUtils.setField(pk2, "producto", p);
		ReflectionTestUtils.setField(pk2, "consulta", q);
		assertEquals(pk, pk2);
		assertTrue(pk.equals(pk));
		assertFalse(pk.equals(null));
		assertFalse(pk.equals("z"));
		ConsultaProductoPK nullCons = new ConsultaProductoPK();
		ReflectionTestUtils.setField(nullCons, "consulta", null);
		ConsultaProductoPK withCons = new ConsultaProductoPK();
		ReflectionTestUtils.setField(withCons, "consulta", new Consulta());
		assertNotEquals(nullCons, withCons);
		ConsultaProductoPK emptyA = new ConsultaProductoPK();
		ConsultaProductoPK emptyB = new ConsultaProductoPK();
		assertEquals(emptyA, emptyB);
	}

	@Test
	void consultaPersonaPK() {
		ConsultaPersonaPK pk = new ConsultaPersonaPK();
		Persona p = new Persona();
		Consulta q = new Consulta();
		ReflectionTestUtils.setField(pk, "persona", p);
		ReflectionTestUtils.setField(pk, "consulta", q);
		ConsultaPersonaPK pk2 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(pk2, "persona", p);
		ReflectionTestUtils.setField(pk2, "consulta", q);
		assertEquals(pk, pk2);
		assertTrue(pk.equals(pk));
		assertFalse(pk.equals(null));
		assertFalse(pk.equals(1));
		ConsultaPersonaPK nullCons = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(nullCons, "consulta", null);
		ConsultaPersonaPK withCons = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(withCons, "consulta", new Consulta());
		assertNotEquals(nullCons, withCons);
		ConsultaPersonaPK emptyA = new ConsultaPersonaPK();
		ConsultaPersonaPK emptyB = new ConsultaPersonaPK();
		assertEquals(emptyA, emptyB);
	}

	@Test
	void exceptionResponseAndModeloNotFound() {
		ExceptionResponse er = new ExceptionResponse(LocalDateTime.now(), "m", "d");
		er.setMensaje("x");
		er.setDetalles("y");
		er.setTimestamp(LocalDateTime.now());
		assertEquals("x", er.getMensaje());
		assertEquals(new ModeloNotFoundException("z").getMessage(), "z");
	}

	@Test
	void rolUsuarioAllAccessors() {
		Rol r = new Rol();
		r.setIdRol(1);
		r.setNombre("N");
		r.setDescripcion("D");
		assertEquals(1, r.getIdRol());
		assertEquals("N", r.getNombre());
		assertEquals("D", r.getDescripcion());
		Usuario u = new Usuario();
		u.setIdUsuario(2);
		u.setUsername("un");
		u.setPassword("pw");
		u.setEnabled(false);
		assertFalse(u.isEnabled());
		u.setEnabled(true);
		assertTrue(u.isEnabled());
		assertEquals(2, u.getIdUsuario());
	}

	@Test
	void resetTokenAllAccessors() {
		ResetToken t = new ResetToken();
		t.setId(9);
		t.setToken("t");
		Usuario u = new Usuario();
		t.setUser(u);
		assertEquals(9, t.getId());
		assertEquals("t", t.getToken());
		assertEquals(u, t.getUser());
	}

	@Test
	void consultaEqualsAndHashCodeBranches() {
		Consulta a = new Consulta();
		a.setIdConsulta(1);
		Consulta b = new Consulta();
		b.setIdConsulta(1);
		assertEquals(a, b);
		assertEquals(a.hashCode(), b.hashCode());
		assertTrue(a.equals(a));
		assertNotEquals(a, null);
		assertNotEquals(a, "other");
		Consulta c = new Consulta();
		c.setIdConsulta(2);
		assertNotEquals(a, c);
		Consulta n1 = new Consulta();
		Consulta n2 = new Consulta();
		assertEquals(n1, n2);
		assertEquals(n1.hashCode(), n2.hashCode());
		Consulta n3 = new Consulta();
		n3.setIdConsulta(3);
		assertNotEquals(n1, n3);
		Persona p = new Persona();
		Categoria cat = new Categoria();
		Producto pr = new Producto();
		a.setPersona(p);
		a.setCategoria(cat);
		a.setProducto(pr);
		assertEquals(p, a.getPersona());
		assertEquals(cat, a.getCategoria());
		assertEquals(pr, a.getProducto());
	}

	@Test
	void consultaPersonaPKHashCodeAndNullBranches() {
		Persona p1 = new Persona();
		p1.setIdPersona(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ConsultaPersonaPK pk = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(pk, "persona", null);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		pk.hashCode();
		ConsultaPersonaPK pk2 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(pk2, "persona", p1);
		ReflectionTestUtils.setField(pk2, "consulta", null);
		pk2.hashCode();
		ConsultaPersonaPK oth = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(oth, "consulta", null);
		ReflectionTestUtils.setField(oth, "persona", p1);
		ConsultaPersonaPK oth2 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(oth2, "consulta", q1);
		ReflectionTestUtils.setField(oth2, "persona", p1);
		assertNotEquals(oth, oth2);
		ConsultaPersonaPK oth3 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(oth3, "consulta", q1);
		ReflectionTestUtils.setField(oth3, "persona", null);
		ConsultaPersonaPK oth4 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(oth4, "consulta", q1);
		ReflectionTestUtils.setField(oth4, "persona", p1);
		assertNotEquals(oth3, oth4);
	}

	@Test
	void consultaProductoPKHashCodeAndNullBranches() {
		Producto p1 = new Producto();
		p1.setIdProducto(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ConsultaProductoPK pk = new ConsultaProductoPK();
		ReflectionTestUtils.setField(pk, "producto", null);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		pk.hashCode();
		ConsultaProductoPK pk2 = new ConsultaProductoPK();
		ReflectionTestUtils.setField(pk2, "producto", p1);
		ReflectionTestUtils.setField(pk2, "consulta", null);
		pk2.hashCode();
		ConsultaProductoPK a = new ConsultaProductoPK();
		ReflectionTestUtils.setField(a, "consulta", null);
		ReflectionTestUtils.setField(a, "producto", p1);
		ConsultaProductoPK b = new ConsultaProductoPK();
		ReflectionTestUtils.setField(b, "consulta", q1);
		ReflectionTestUtils.setField(b, "producto", p1);
		assertNotEquals(a, b);
		ConsultaProductoPK c = new ConsultaProductoPK();
		ReflectionTestUtils.setField(c, "consulta", q1);
		ReflectionTestUtils.setField(c, "producto", null);
		ConsultaProductoPK d = new ConsultaProductoPK();
		ReflectionTestUtils.setField(d, "consulta", q1);
		ReflectionTestUtils.setField(d, "producto", p1);
		assertNotEquals(c, d);
	}

	@Test
	void consultaEquals_idNullVsNonNull() {
		Consulta sinId = new Consulta();
		Consulta conId = new Consulta();
		conId.setIdConsulta(1);
		assertNotEquals(sinId, conId);
		assertNotEquals(conId, sinId);
	}

	@Test
	void consultaPersonaPK_whenPersonaOrConsultaDifferButNonNull() {
		Persona p1 = new Persona();
		p1.setIdPersona(1);
		Persona p2 = new Persona();
		p2.setIdPersona(2);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ConsultaPersonaPK a = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(a, "persona", p1);
		ReflectionTestUtils.setField(a, "consulta", q1);
		ConsultaPersonaPK b = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(b, "persona", p2);
		ReflectionTestUtils.setField(b, "consulta", q1);
		assertNotEquals(a, b);
		ConsultaPersonaPK c = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(c, "persona", p1);
		ReflectionTestUtils.setField(c, "consulta", q2);
		assertNotEquals(a, c);
	}

	@Test
	void consultaProductoPK_whenProductoOrConsultaDifferButNonNull() {
		Producto p1 = new Producto();
		p1.setIdProducto(1);
		Producto p2 = new Producto();
		p2.setIdProducto(2);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ConsultaProductoPK a = new ConsultaProductoPK();
		ReflectionTestUtils.setField(a, "producto", p1);
		ReflectionTestUtils.setField(a, "consulta", q1);
		ConsultaProductoPK b = new ConsultaProductoPK();
		ReflectionTestUtils.setField(b, "producto", p2);
		ReflectionTestUtils.setField(b, "consulta", q1);
		assertNotEquals(a, b);
		ConsultaProductoPK c = new ConsultaProductoPK();
		ReflectionTestUtils.setField(c, "producto", p1);
		ReflectionTestUtils.setField(c, "consulta", q2);
		assertNotEquals(a, c);
	}

	@Test
	void consultaCategoriaPK_whenCategoriaOrConsultaDifferButNonNull() {
		Categoria c1 = new Categoria();
		c1.setIdCategoria(1);
		Categoria c2 = new Categoria();
		c2.setIdCategoria(2);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ConsultaCategoriaPK a = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(a, "categoria", c1);
		ReflectionTestUtils.setField(a, "consulta", q1);
		ConsultaCategoriaPK b = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(b, "categoria", c2);
		ReflectionTestUtils.setField(b, "consulta", q1);
		assertNotEquals(a, b);
		ConsultaCategoriaPK c = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(c, "categoria", c1);
		ReflectionTestUtils.setField(c, "consulta", q2);
		assertNotEquals(a, c);
	}

	@Test
	void consultaCategoriaPKHashCodeAndNullBranches() {
		Categoria c1 = new Categoria();
		c1.setIdCategoria(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ConsultaCategoriaPK pk = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pk, "categoria", null);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		pk.hashCode();
		ConsultaCategoriaPK pk2 = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pk2, "categoria", c1);
		ReflectionTestUtils.setField(pk2, "consulta", null);
		pk2.hashCode();
		ConsultaCategoriaPK a = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(a, "consulta", null);
		ReflectionTestUtils.setField(a, "categoria", c1);
		ConsultaCategoriaPK b = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(b, "consulta", q1);
		ReflectionTestUtils.setField(b, "categoria", c1);
		assertNotEquals(a, b);
		ConsultaCategoriaPK c = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(c, "consulta", q1);
		ReflectionTestUtils.setField(c, "categoria", null);
		ConsultaCategoriaPK d = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(d, "consulta", q1);
		ReflectionTestUtils.setField(d, "categoria", c1);
		assertNotEquals(c, d);
	}

	@Test
	void consultaCategoriaPKEqualsBranches() {
		ConsultaCategoriaPK pk = new ConsultaCategoriaPK();
		Categoria c1 = new Categoria();
		c1.setIdCategoria(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ReflectionTestUtils.setField(pk, "categoria", c1);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		assertEquals(pk, pk);
		assertNotEquals(pk, null);
		assertNotEquals(pk, "x");
		ConsultaCategoriaPK pk2 = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pk2, "categoria", c1);
		ReflectionTestUtils.setField(pk2, "consulta", q1);
		assertEquals(pk, pk2);
		ConsultaCategoriaPK pk3 = new ConsultaCategoriaPK();
		Categoria c2 = new Categoria();
		c2.setIdCategoria(2);
		ReflectionTestUtils.setField(pk3, "categoria", c2);
		ReflectionTestUtils.setField(pk3, "consulta", q1);
		assertNotEquals(pk, pk3);
		ConsultaCategoriaPK pk4 = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pk4, "categoria", c1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ReflectionTestUtils.setField(pk4, "consulta", q2);
		assertNotEquals(pk, pk4);
		ConsultaCategoriaPK pkNullCat = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pkNullCat, "categoria", null);
		ReflectionTestUtils.setField(pkNullCat, "consulta", q1);
		ConsultaCategoriaPK pkNullCat2 = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pkNullCat2, "categoria", null);
		ReflectionTestUtils.setField(pkNullCat2, "consulta", q1);
		assertEquals(pkNullCat, pkNullCat2);
		ConsultaCategoriaPK pkNc = new ConsultaCategoriaPK();
		ReflectionTestUtils.setField(pkNc, "categoria", c1);
		ReflectionTestUtils.setField(pkNc, "consulta", q1);
		assertNotEquals(pkNullCat, pkNc);
	}

	@Test
	void consultaProductoPKEqualsBranches() {
		ConsultaProductoPK pk = new ConsultaProductoPK();
		Producto p1 = new Producto();
		p1.setIdProducto(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ReflectionTestUtils.setField(pk, "producto", p1);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		ConsultaProductoPK pk2 = new ConsultaProductoPK();
		ReflectionTestUtils.setField(pk2, "producto", p1);
		ReflectionTestUtils.setField(pk2, "consulta", q1);
		assertEquals(pk, pk2);
		ConsultaProductoPK pk3 = new ConsultaProductoPK();
		Producto p2 = new Producto();
		p2.setIdProducto(2);
		ReflectionTestUtils.setField(pk3, "producto", p2);
		ReflectionTestUtils.setField(pk3, "consulta", q1);
		assertNotEquals(pk, pk3);
		ConsultaProductoPK pk4 = new ConsultaProductoPK();
		ReflectionTestUtils.setField(pk4, "producto", p1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ReflectionTestUtils.setField(pk4, "consulta", q2);
		assertNotEquals(pk, pk4);
		ConsultaProductoPK nlp = new ConsultaProductoPK();
		ReflectionTestUtils.setField(nlp, "producto", null);
		ReflectionTestUtils.setField(nlp, "consulta", q1);
		ConsultaProductoPK nlp2 = new ConsultaProductoPK();
		ReflectionTestUtils.setField(nlp2, "producto", null);
		ReflectionTestUtils.setField(nlp2, "consulta", q1);
		assertEquals(nlp, nlp2);
		ConsultaProductoPK withP = new ConsultaProductoPK();
		ReflectionTestUtils.setField(withP, "producto", p1);
		ReflectionTestUtils.setField(withP, "consulta", q1);
		assertNotEquals(nlp, withP);
	}

	@Test
	void consultaPersonaPKEqualsBranches() {
		ConsultaPersonaPK pk = new ConsultaPersonaPK();
		Persona p1 = new Persona();
		p1.setIdPersona(1);
		Consulta q1 = new Consulta();
		q1.setIdConsulta(1);
		ReflectionTestUtils.setField(pk, "persona", p1);
		ReflectionTestUtils.setField(pk, "consulta", q1);
		ConsultaPersonaPK pk2 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(pk2, "persona", p1);
		ReflectionTestUtils.setField(pk2, "consulta", q1);
		assertEquals(pk, pk2);
		ConsultaPersonaPK pk3 = new ConsultaPersonaPK();
		Persona p2 = new Persona();
		p2.setIdPersona(2);
		ReflectionTestUtils.setField(pk3, "persona", p2);
		ReflectionTestUtils.setField(pk3, "consulta", q1);
		assertNotEquals(pk, pk3);
		ConsultaPersonaPK pk4 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(pk4, "persona", p1);
		Consulta q2 = new Consulta();
		q2.setIdConsulta(2);
		ReflectionTestUtils.setField(pk4, "consulta", q2);
		assertNotEquals(pk, pk4);
		ConsultaPersonaPK nl = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(nl, "persona", null);
		ReflectionTestUtils.setField(nl, "consulta", q1);
		ConsultaPersonaPK nl2 = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(nl2, "persona", null);
		ReflectionTestUtils.setField(nl2, "consulta", q1);
		assertEquals(nl, nl2);
		ConsultaPersonaPK withPe = new ConsultaPersonaPK();
		ReflectionTestUtils.setField(withPe, "persona", p1);
		ReflectionTestUtils.setField(withPe, "consulta", q1);
		assertNotEquals(nl, withPe);
	}
}
