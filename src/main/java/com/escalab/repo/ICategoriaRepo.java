package com.escalab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escalab.model.Categoria;


public interface ICategoriaRepo extends JpaRepository<Categoria, Integer> {

}
