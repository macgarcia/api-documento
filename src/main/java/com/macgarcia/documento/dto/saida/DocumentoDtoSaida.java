package com.macgarcia.documento.dto.saida;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.macgarcia.documento.entity.Documento;

public class DocumentoDtoSaida implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String descricao;
	private String tipo;
	private Long tamanho;
	private byte[] documento;
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate dataUpload;

	public DocumentoDtoSaida(Documento e) {
		this.id = e.getId();
		this.descricao = e.getDescricao();
		this.tipo = e.getTipo();
		this.tamanho = e.getTamanho();
		this.documento = e.getDocumento();
		this.dataUpload = e.getDataUpload();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getTamanho() {
		return tamanho;
	}

	public void setTamanho(Long tamanho) {
		this.tamanho = tamanho;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public LocalDate getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(LocalDate dataUpload) {
		this.dataUpload = dataUpload;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
