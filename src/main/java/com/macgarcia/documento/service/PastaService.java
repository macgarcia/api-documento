package com.macgarcia.documento.service;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macgarcia.documento.dto.entrada.PastaDtoEntrada;
import com.macgarcia.documento.dto.saida.PastaDtoSaida;
import com.macgarcia.documento.repository.PastaRepository;

@Service
public class PastaService {
	
	private PastaRepository dao;
	private Validator validator;
	private String mensagemDeErro;
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	public PastaService(PastaRepository dao, Validator validator) {
		this.dao = dao;
		this.validator = validator;
	}

	@Transactional
	public boolean salvar(PastaDtoEntrada dto) {
		try {
			var novaPasta = dto.criar();
			dao.saveAndFlush(novaPasta);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public boolean atualizar(Long idPasta, String nome) {
		try {
			var pastaExistente = dao.findById(idPasta).get();
			pastaExistente.setNome(nome);
			dao.saveAndFlush(pastaExistente);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Transactional
	public boolean excluir(Long idPasta) {
		//Método que apaga uma pasta juntamente com o seu conteúdo.
		try {
			String contagemDeRegistros = "select count(*) from Documento d where d.pasta.id = :idPasta";
			var count = em.createQuery(contagemDeRegistros, Long.class).setParameter("idPasta", idPasta).getSingleResult();
			if (count > 0) {
				String exclusaoDeRegistros = "delete from Documento d where d.pasta.id = :idPasta";
				em.createQuery(exclusaoDeRegistros).setParameter("idPasta", idPasta).executeUpdate();
			}
			dao.deleteById(idPasta);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public Page<PastaDtoSaida> buscarTodasAsPastasDoUsuario(Long idUsuario, Pageable page) {
		var result = dao.findByIdUsuario(idUsuario, page);
		var list = result.stream()
				.map(e -> {return new PastaDtoSaida(e);})
				.collect(Collectors.toList());
		return new PageImpl<PastaDtoSaida>(list, page, result.getTotalElements());
	}

	public boolean verificarExistencia(Long idPasta) {
		return dao.existsById(idPasta);
	}

	public boolean validar(PastaDtoEntrada dto) {
		Set<ConstraintViolation<PastaDtoEntrada>> erros = validator.validate(dto);
		if (!erros.isEmpty()) {
			mensagemDeErro = erros.stream()
					.map(e -> e.getPropertyPath().toString().toUpperCase() + ": " + e.getMessageTemplate())
					.collect(Collectors.joining("\n"));
		}
		return true;
	}
	
	public String getMensagemDeErro() {
		return mensagemDeErro;
	}

}
