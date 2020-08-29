package com.macgarcia.documento.dto.saida;

import java.io.Serializable;

import com.macgarcia.documento.entity.Pasta;

public class PastaDtoSaida implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;

	public PastaDtoSaida(Pasta e) {
		this.id = e.getId();
		this.nome = e.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
