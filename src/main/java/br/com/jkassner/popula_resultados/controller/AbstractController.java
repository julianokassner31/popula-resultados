package br.com.jkassner.popula_resultados.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.jkassner.popula_resultados.service.AbstractConcursoService;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;

public abstract class AbstractController<T> {

	protected AbstractConcursoService<T> concursoService;

    protected ParserContentFileService<T> parserContentFileService;
	
	public AbstractController(AbstractConcursoService<T> concursoService,
            ParserContentFileService<T> parserContentFileService) {
		
		this.concursoService = concursoService;
		this.parserContentFileService = parserContentFileService;
	}
	
	@GetMapping
    public ResponseEntity<?> buscaUltimoConcurso() {

        T concurso = concursoService.getUltimoConcurso();
        CacheControl cacheControl = CacheControl.maxAge(30, TimeUnit.MINUTES);

        return ResponseEntity.ok().cacheControl(cacheControl).body(concurso);
    }
	
	@GetMapping("/populaResultados")
    public ResponseEntity<?> populaResultados() {

        new Thread(() -> {
            parserContentFileService.populaResultados();
        }).start();

        return ResponseEntity.noContent().build();
    }
}
