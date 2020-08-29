package com.macgarcia.documento.entity;

import java.time.LocalDate;
import java.util.function.Function;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "DOCUMENTO")
public class Documento extends EntityBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "DESCRICAO", nullable = false)
	private String descricao;

	@Column(name = "TIPO", nullable = false)
	private String tipo;

	@Column(name = "TAMANHO", nullable = false)
	private Long tamanho;

	@CreationTimestamp
	@Column(name = "DATA_UPLOAD", nullable = false)
	private LocalDate dataUpload;

	@Lob
	@Column(name = "DOCUMENTO", nullable = false)
	private byte[] documento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PASTA", nullable = false)
	private Pasta pasta;
	
	@Deprecated
	public Documento() {}

	public Documento(String descricao, long tamanho, String tipo, byte[] b, Function<Documento, Pasta> busca) {
		this.descricao = descricao;
		this.tipo = tipo;
		this.documento = b;
		this.tamanho = tamanho;
		this.pasta = busca.apply(this);
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

	public LocalDate getDataUpload() {
		return dataUpload;
	}

	public void setDataUpload(LocalDate dataUpload) {
		this.dataUpload = dataUpload;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

}
