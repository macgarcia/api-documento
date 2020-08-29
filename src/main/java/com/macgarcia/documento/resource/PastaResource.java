package com.macgarcia.documento.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macgarcia.documento.dto.entrada.PastaDtoEntrada;
import com.macgarcia.documento.service.PastaService;

@RestController
@RequestMapping(value = "/pastas")
@CrossOrigin
public class PastaResource {
	
	private PastaService service;
	
	@Autowired
	public PastaResource(PastaService service) {
		this.service = service;
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> criarPasta(@RequestBody PastaDtoEntrada dto) {
		var validou = service.validar(dto);
		if (!validou) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.getMensagemDeErro());
		}
		var salvou = service.salvar(dto);
		if (!salvou) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar a pasta.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping(value = "/{idUsuario}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> buscarPastaDoUsuario(@PathVariable("idUsuario") Long idUsuario, 
												  @PageableDefault(page = 0, size = 8) Pageable page) {
		var result = service.buscarTodasAsPastasDoUsuario(idUsuario, page);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@PutMapping(value = "/{idPasta}/{nome}")
	public ResponseEntity<?> atualizarPasta(@PathVariable("idPasta") Long idPasta,
											@PathVariable("nome") String nome) {
		var existe = service.verificarExistencia(idPasta);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inexistentes.");
		}
		var atualizou = service.atualizar(idPasta, nome);
		if (!atualizou) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar a pasta.");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@DeleteMapping(value = "/{idPasta}")
	public ResponseEntity<?> excluirPasta(@PathVariable("idPasta") Long idPasta) {
		var existe = service.verificarExistencia(idPasta);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inexistentes.");
		}
		var excluiu = service.excluir(idPasta);
		if (!excluiu) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir a pasta.");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
 