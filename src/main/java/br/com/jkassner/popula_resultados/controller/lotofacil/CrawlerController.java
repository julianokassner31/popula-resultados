package br.com.jkassner.popula_resultados.controller.lotofacil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jkassner.pojo.dto.ConcursoLotoFacilDto;
import br.com.jkassner.popula_resultados.serviceImpl.CrawlerService;

@RestController
@RequestMapping("/crawlerJogos")
@CrossOrigin(allowedHeaders = "*")
public class CrawlerController {

	@Autowired
	private CrawlerService crawlerService;
	
	@PostMapping("/lotofacil")
	public ResponseEntity<?> crawler(@RequestBody ConcursoLotoFacilDto concursoDto) {
		
		crawlerService.saveConcursoLotoFacil(concursoDto);
		
		return ResponseEntity.ok("Jogo salvo com sucesso");
	}
}
