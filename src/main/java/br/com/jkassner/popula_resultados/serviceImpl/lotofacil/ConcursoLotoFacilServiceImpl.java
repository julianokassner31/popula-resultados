package br.com.jkassner.popula_resultados.serviceImpl.lotofacil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.jkassner.pojo.model.ConcursoLotoFacil;
import br.com.jkassner.popula_resultados.repository.abstractconcurso.AbstractConcursoRepository;
import br.com.jkassner.popula_resultados.service.DownloadService;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;
import br.com.jkassner.popula_resultados.serviceImpl.AbstractConcursoServiceImpl;

@Service
public class ConcursoLotoFacilServiceImpl extends AbstractConcursoServiceImpl<ConcursoLotoFacil> {

    @Autowired
    public ConcursoLotoFacilServiceImpl(
    		@Qualifier("concursoLotoFacilRepository")
    			AbstractConcursoRepository<ConcursoLotoFacil> concursoLotoFacilRepository,
            @Qualifier("downloadTodosConcursosZipLotoFacil")
                DownloadService downloadService,
            @Qualifier("parseContentFileLotoFacilServiceImpl")
                ParserContentFileService<ConcursoLotoFacil> parseContentFileServiceImpl
    ) {

        super(concursoLotoFacilRepository, downloadService, parseContentFileServiceImpl);
    }
}
