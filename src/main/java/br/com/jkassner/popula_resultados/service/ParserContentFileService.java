package br.com.jkassner.popula_resultados.service;

import org.jsoup.nodes.Element;

import java.util.Iterator;

public interface ParserContentFileService<T> {
    void populaResultados();
    T parserTrToConcurso(Element trDadosConcurso, Iterator<Element> iterator);
    String download();
}
