package com.macgarcia.documento.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "PASTA")
public class Pasta extends EntityBase {

	private static final long serialVersionUID = 1L;

	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "ID_USUARIO", nullable = false)
	private Long idUsuario;
	
	@Deprecated
	public Pasta() {}

	public Pasta(@NotBlank(message = "Nome não pode ser nulo.") String nome,
			@Positive(message = "Identificador inválido.") 
			@NotNull(message = "Identificador do usuário, não pode ser nulo.") Long idUsuario) {
		
		this.nome = nome;
		this.idUsuario = idUsuario;
	}

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

}
