package com.macgarcia.documento.dto.entrada;

import java.io.Serializable;

import javax.servlet.http.Part;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class DocumentoDtoEntrada implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Descrição não pode ser nula.")
	private String descricao;
	
	@NotNull(message = "Documento não pode ser nulo.")
	private Part documento;
	
	@Positive(message = "Identificador inválido.")
	@NotNull(message = "Identificador da pasta não pode ser nulo.")
	private Long idPasta;

	public DocumentoDtoEntrada(String descricao, Long idPasta, Part arq) {
		this.descricao = descricao;
		this.idPasta = idPasta;
		this.documento = arq;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Part getDocumento() {
		return documento;
	}

	public void setDocumento(Part documento) {
		this.documento = documento;
	}

	public Long getIdPasta() {
		return idPasta;
	}

	public void setIdPasta(Long idPasta) {
		this.idPasta = idPasta;
	}

}
