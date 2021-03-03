package br.com.jkassner.popula_resultados.controller.megasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jkassner.pojo.model.ConcursoMegaSena;
import br.com.jkassner.popula_resultados.controller.AbstractController;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;
import br.com.jkassner.popula_resultados.serviceImpl.megasena.ConcursoMegaSenaServiceImpl;

@RestController
@RequestMapping("/megasena")
public class ConcursoMegaSenaController extends AbstractController<ConcursoMegaSena> {

	@Autowired
	public ConcursoMegaSenaController(@Qualifier("concursoMegaSenaServiceImpl")
												  ConcursoMegaSenaServiceImpl concursoMegaSenaService,
									  @Qualifier("parseContentFileMegaSenaServiceImpl")
											  ParserContentFileService<ConcursoMegaSena> parserContentFileService) {

		super(concursoMegaSenaService, parserContentFileService);
	}
}
