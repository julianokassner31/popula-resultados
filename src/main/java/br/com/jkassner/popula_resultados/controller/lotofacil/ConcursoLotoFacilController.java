package br.com.jkassner.popula_resultados.controller.lotofacil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jkassner.pojo.model.ConcursoLotoFacil;
import br.com.jkassner.popula_resultados.controller.AbstractController;
import br.com.jkassner.popula_resultados.service.AbstractConcursoService;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;

@RestController
@RequestMapping("/lotofacil")
public class ConcursoLotoFacilController extends AbstractController<ConcursoLotoFacil> {

	@Autowired
	public ConcursoLotoFacilController(@Qualifier("concursoLotoFacilServiceImpl")
												   AbstractConcursoService<ConcursoLotoFacil> concursoService,
									   @Qualifier("parseContentFileLotoFacilServiceImpl")
											   ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl){

		super(concursoService, parseContentFileServiceImpl);
	}
}
