package com.macgarcia.documento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macgarcia.documento.entity.Pasta;

@Repository
public interface PastaRepository extends JpaRepository<Pasta, Long>{

	Page<Pasta> findByIdUsuario(Long idUsuario, Pageable page);

}
