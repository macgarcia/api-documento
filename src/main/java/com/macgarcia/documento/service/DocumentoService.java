package com.macgarcia.documento.service;

import java.io.InputStream;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.macgarcia.documento.dto.entrada.DocumentoDtoEntrada;
import com.macgarcia.documento.dto.saida.DocumentoDtoSaida;
import com.macgarcia.documento.entity.Documento;
import com.macgarcia.documento.entity.Pasta;
import com.macgarcia.documento.repository.DocumentoRepository;
import com.macgarcia.documento.repository.PastaRepository;

@Service
public class DocumentoService {

	private Validator validator;
	private PastaRepository pastaDao;
	private DocumentoRepository dao;

	private String mensagemDeErro;

	@Autowired
	public DocumentoService(Validator validator, PastaRepository pastaDao, DocumentoRepository dao) {
		this.validator = validator;
		this.pastaDao = pastaDao;
		this.dao = dao;
	}

	@Transactional
	public boolean salvarDocumento(Documento documento) {
		try {
			dao.saveAndFlush(documento);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Transactional
	public boolean excluirDocumento(Long idDocumento) {
		try {
			dao.deleteById(idDocumento);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// =====================================================================================================

	public boolean validar(DocumentoDtoEntrada dto) {
		Set<ConstraintViolation<DocumentoDtoEntrada>> erros = validator.validate(dto);
		if (!erros.isEmpty()) {
			mensagemDeErro = erros.stream()
					.map(e -> e.getPropertyPath().toString().toUpperCase() + ": " + e.getMessageTemplate())
					.collect(Collectors.joining("\n"));
			return false;
		}
		return true;
	}

	public String getMensagemDeErro() {
		return mensagemDeErro;
	}

	public Optional<Documento> montarDocumento(DocumentoDtoEntrada dto) {
		String tipo = dto.getDocumento().getContentType();
		long tamanho = dto.getDocumento().getSize();
		if (dto.getDocumento().getContentType() != null) {
			try {
				InputStream is = dto.getDocumento().getInputStream();
				int count = 0;
				int index = 0;
				byte[] b = new byte[(int) dto.getDocumento().getSize()];
				while (count < b.length && (index = is.read(b, count, b.length - count)) >= 0) {
					count += index;
				}
				Function<Documento, Pasta> busca = buscarPastaDoDocumento(dto.getIdPasta(), pastaDao);
				Documento doc = new Documento(dto.getDescricao(), tamanho, tipo, b, busca);
				return Optional.of(doc);
			} catch (Exception e) {
				return Optional.empty();
			}
		}
		return Optional.empty();
	}

	// Função que busca a pasta para o novo documento.
	private Function<Documento, Pasta> buscarPastaDoDocumento(Long idPasta, PastaRepository repository) {
		var pasta = repository.findById(idPasta).get();
		return (Documento) -> {
			return pasta;
		};
	}

	public boolean verificarExistencia(Long idDocumento) {
		return dao.existsById(idDocumento);
	}

	@Transactional
	public Page<DocumentoDtoSaida> buscarDocumentosDaPasta(Long idPasta, Pageable page) {
		var result = dao.findByIdPasta(idPasta, page);
		var list = result.stream().map(e -> {return new DocumentoDtoSaida(e);}).collect(Collectors.toList());
		return new PageImpl<DocumentoDtoSaida>(list, page, result.getTotalElements());
	}

}
