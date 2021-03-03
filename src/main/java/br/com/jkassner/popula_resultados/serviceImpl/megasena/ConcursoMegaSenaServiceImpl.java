package br.com.jkassner.popula_resultados.serviceImpl.megasena;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.jkassner.pojo.model.ConcursoMegaSena;
import br.com.jkassner.popula_resultados.repository.megasena.ConcursoMegaSenaRepository;
import br.com.jkassner.popula_resultados.service.DownloadService;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;
import br.com.jkassner.popula_resultados.serviceImpl.AbstractConcursoServiceImpl;

@Service("concursoMegaSenaServiceImpl")
public class ConcursoMegaSenaServiceImpl extends AbstractConcursoServiceImpl<ConcursoMegaSena> {

    @Autowired
    public ConcursoMegaSenaServiceImpl(ConcursoMegaSenaRepository repository,
									   @Qualifier("downloadTodosConcursosZipMegaSena")
                                               DownloadService downloadService,
									   @Qualifier("parseContentFileMegaSenaServiceImpl")
                                               ParserContentFileService<ConcursoMegaSena> parseContentFileServiceImpl) {

    	super(repository, downloadService, parseContentFileServiceImpl);
    }
}
