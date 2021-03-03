package br.com.jkassner.popula_resultados.serviceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.jkassner.pojo.model.Concurso;
import br.com.jkassner.popula_resultados.repository.abstractconcurso.AbstractConcursoRepository;
import br.com.jkassner.popula_resultados.service.AbstractConcursoService;
import br.com.jkassner.popula_resultados.service.DownloadService;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;
import lombok.extern.log4j.Log4j2;

/*
 * @created 08/11/2020 - 02:38
 * @project api-loteria
 * @author Juliano Kassner
 */
@Log4j2
public abstract class AbstractConcursoServiceImpl<T extends Concurso> implements AbstractConcursoService<T> {

   AbstractConcursoRepository<T> repository;

    DownloadService downloadService;

    ParserContentFileService<T> parserContentFileService;

    public AbstractConcursoServiceImpl(
            AbstractConcursoRepository<T> repository,
            DownloadService downloadService,
            ParserContentFileService<T> parserContentFileService
    ) {
        this.repository = repository;
        this.downloadService = downloadService;
        this.parserContentFileService = parserContentFileService;
    }

    @Override
    public T getUltimoConcurso() {
        
    	T ultimoConcursoLocal = repository.findFirstByOrderByDtSorteioDesc();

        LocalDate dataConcurso = Instant.ofEpochMilli(ultimoConcursoLocal.getDtSorteio().getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime agora = LocalDateTime.now();

        if (concursoEDeHoje(dataConcurso, agora.toLocalDate())) {
            return ultimoConcursoLocal;
        }
        
        try {
	        String contentFile = downloadService.download();
	        Document doc = Jsoup.parse(contentFile);
	        Element tableConcursos = getTableConcursos(doc);
	        Element trUltimoConcursoLocal = getTrUltimoConcursoLocal(tableConcursos, ultimoConcursoLocal);
	
	        // quando o ultimo concurso ainda continua sendo o da base local
	        // demora as vez para atualizar o aqruivo com o concurso mais recente
	        if (trUltimoConcursoLocal.nextElementSibling() == null) {
	            return ultimoConcursoLocal;
	        }
	
	        Element trUltimoConcursoWeb = getTrUltimoConcursoWeb(trUltimoConcursoLocal);
	
	        T concurso = parserContentFileService.parserTrToConcurso(trUltimoConcursoWeb, null);
	
	        Runnable runnable = () -> repository.save(concurso);
	
	        new Thread(runnable).start();
	        
	        return concurso;
        
        } catch (Exception ex) {
        	log.info("Ocorreu um erro ao buscar ultimo concurso atual\n"+ ex.getMessage());
        	log.info("Retornando ultimo concurso local idConcurso="+ultimoConcursoLocal.getIdConcurso());
        	
        	return ultimoConcursoLocal;
        }
    }

    private Element getTrUltimoConcursoWeb(Element trUltimoConcursoLocal) {

        Element trUltimoConcursoWeb = null;
        Element trPossivelUltimoConcursoWeb = trUltimoConcursoLocal.nextElementSibling();// aqui pode vir trs com
        // cidades do concurso local

        while (trUltimoConcursoWeb == null) {

            Elements tdPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.select("td:eq(0):matches(\\d)");

            if (tdPossivelUltimoConcursoWeb != null && !tdPossivelUltimoConcursoWeb.isEmpty()) {
                trUltimoConcursoWeb = trPossivelUltimoConcursoWeb;
                break;
            }

            trPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.nextElementSibling();

            tdPossivelUltimoConcursoWeb = trPossivelUltimoConcursoWeb.select("td:eq(0):matches(\\d)");

            if (tdPossivelUltimoConcursoWeb != null && !tdPossivelUltimoConcursoWeb.isEmpty()) {
                trUltimoConcursoWeb = tdPossivelUltimoConcursoWeb.parents().get(0);
            }
        }

        return trUltimoConcursoWeb;
    }

    private Element getTableConcursos(Document doc) {
        Elements tables = doc.getElementsByTag("table");

        return tables.first();
    }

    private Element getTrUltimoConcursoLocal(Element table, T ultimoConcursoLocal) {
        Elements tdUltimoConcursoLocal = table.select("td:eq(0):matches(" + ultimoConcursoLocal.getIdConcurso() + ")");

        return tdUltimoConcursoLocal.parents().get(0);
    }

    private boolean concursoEDeHoje(LocalDate dataConcurso, LocalDate hoje) {

        return dataConcurso.isEqual(hoje);
    }
}
