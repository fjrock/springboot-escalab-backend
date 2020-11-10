package com.escalab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escalab.model.Producto;

public interface IProductoRepo extends JpaRepository<Producto, Integer>{

}
