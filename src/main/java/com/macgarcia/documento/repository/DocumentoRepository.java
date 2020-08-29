package com.macgarcia.documento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.macgarcia.documento.entity.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	@Query("select d from Documento d where d.pasta.id = :idPasta")
	Page<Documento> findByIdPasta(@Param("idPasta") Long idPasta, Pageable page);

}
