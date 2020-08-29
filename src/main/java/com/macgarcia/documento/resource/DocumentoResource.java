package com.macgarcia.documento.resource;

import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macgarcia.documento.dto.entrada.DocumentoDtoEntrada;
import com.macgarcia.documento.service.DocumentoService;

@RestController
@RequestMapping(value = "/documentos")
@CrossOrigin
public class DocumentoResource {

	private DocumentoService service;

	@Autowired
	public DocumentoResource(DocumentoService service) {
		this.service = service;
	}

	@PostMapping(value = "/{descricao}/{idPasta}")
	public ResponseEntity<?> salvarDocumento(@PathVariable("descricao") String descricao,
											 @PathVariable("idPasta") Long idPasta,
											 @RequestBody Part arq) {
		
		var dto = new DocumentoDtoEntrada(descricao, idPasta, arq);
		
		var validou = service.validar(dto);
		if (!validou) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(service.getMensagemDeErro());
		}
		
		var result = service.montarDocumento(dto);
		if (result.isEmpty()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro na montagem do documento.");
		}
		
		var salvou = service.salvarDocumento(result.get());
		if (!salvou) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o documento.");
		}
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping(value = "/{idDocumento}")
	public ResponseEntity<?> excluirDocumento(@PathVariable("idDocumento") Long idDocumento) {
		var existe = service.verificarExistencia(idDocumento);
		if (!existe) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dados inexistentes.");
		}
		
		var excluiu = service.excluirDocumento(idDocumento);
		if (!excluiu) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar excluir o documento.");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@GetMapping(value = "/all/{idPasta}")
	public ResponseEntity<?> buscarTodosOsDocumentosDaPasta(@PathVariable("idPasta") Long idPasta,
															@PageableDefault(page = 0, size = 6) Pageable page) {
		var result = service.buscarDocumentosDaPasta(idPasta, page);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
