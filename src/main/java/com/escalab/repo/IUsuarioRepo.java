package com.escalab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.escalab.model.Usuario;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer> {

	@Query(value = "select * from usuario where nombre = :username", nativeQuery = true)
	Usuario findOneByUsername(String username);
}
