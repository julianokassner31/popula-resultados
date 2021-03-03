package br.com.jkassner.popula_resultados.serviceImpl;

import org.hibernate.exception.DataException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.jkassner.popula_resultados.repository.abstractconcurso.AbstractConcursoRepository;
import br.com.jkassner.popula_resultados.service.ParserContentFileService;

import java.math.BigDecimal;
import java.util.Iterator;

public abstract class ParserContentFileAbstract<T> implements ParserContentFileService<T> {

    AbstractConcursoRepository<T> abstractConcursoRepository;

    public ParserContentFileAbstract(AbstractConcursoRepository<T> abstractConcursoRepository) {
        this.abstractConcursoRepository = abstractConcursoRepository;
    }

    public void downloaAndSaveConcursos() {

        String contentFile = download();
        Document doc = Jsoup.parse(contentFile);
        Element body = doc.body();
        Elements tables = body.getElementsByTag("table");
        Element table = tables.first();
        Elements trs = table.getElementsByTag("tr");
        Iterator<Element> iterator = trs.iterator();


        while (iterator.hasNext()) {
            try {

                // tr com os dados do sorteios
                Element trDadosConcurso = iterator.next();

                Elements concursoEl = trDadosConcurso.getElementsContainingText("Concurso");

                // primeira tr vem os th, pulo
                if (!concursoEl.isEmpty()) continue;

                T concurso = parserTrToConcurso(trDadosConcurso, iterator);

                abstractConcursoRepository.save(concurso);

            } catch(DataException dataException) {
                System.out.println("Erro ao executar sql: " + dataException.getSQL());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected int converterToInt(int pos, Elements td) {
        Element prDezena = td.get(pos);
        return Integer.parseInt(prDezena.text());
    }

    protected BigDecimal converterToBigDecimal(int pos, Elements td) {
        Element prDezena = td.get(pos);
        String replace = prDezena.text().replace(".", "").replace(",", ".");
        return new BigDecimal(replace);
    }
}
