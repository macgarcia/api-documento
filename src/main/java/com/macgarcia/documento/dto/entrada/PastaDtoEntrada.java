package com.macgarcia.documento.dto.entrada;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.macgarcia.documento.entity.Pasta;

public class PastaDtoEntrada implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome não pode ser nulo.")
	private String nome;

	@Positive(message = "Identificador inválido.")
	@NotNull(message = "Identificador do usuário, não pode ser nulo.")
	private Long idUsuario;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Pasta criar() {
		return new Pasta(nome, idUsuario);
	}

}
