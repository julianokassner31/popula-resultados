package br.com.jkassner.popula_resultados.serviceImpl.lotofacil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.jkassner.pojo.model.Cidade;
import br.com.jkassner.pojo.model.ConcursoLotoFacil;
import br.com.jkassner.pojo.model.DezenasLotoFacilOrdenadas;
import br.com.jkassner.popula_resultados.repository.lotofacil.ConcursoLotoFacilRepository;
import br.com.jkassner.popula_resultados.service.DownloadService;
import br.com.jkassner.popula_resultados.serviceImpl.ParserContentFileAbstract;

@Service("parseContentFileLotoFacilServiceImpl")
public class ParserContentFileLotoFacilConcursoServiceImpl extends ParserContentFileAbstract<ConcursoLotoFacil> {

    @Autowired
    @Qualifier("downloadTodosConcursosZipLotoFacil")
    DownloadService downloadTodosConcursos;

    @Autowired
    public ParserContentFileLotoFacilConcursoServiceImpl(ConcursoLotoFacilRepository lotoFacilRepository) {
        super(lotoFacilRepository);
    }


    @Override
    public void populaResultados() {
        downloaAndSaveConcursos();
    }

    @Override
    public String download() {
        return downloadTodosConcursos.download();
    }
    
    @Override
    public ConcursoLotoFacil parserTrToConcurso(Element trDadosConcurso, Iterator<Element> iterator) {

        Elements tdsDados = trDadosConcurso.getElementsByTag("td");
        Element nrConcurso = tdsDados.get(0);

        long id = Long.parseLong(nrConcurso.text());
        ConcursoLotoFacil concursoLotoFacil = new ConcursoLotoFacil();
        concursoLotoFacil.setIdConcurso(id);

        Element dtSorteio = tdsDados.get(1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date data = null;
        try {
			data = simpleDateFormat.parse(dtSorteio.text());
		} catch (ParseException e) {
			e.printStackTrace();
		}

        concursoLotoFacil.setDtSorteio(data);

        setDezenas(concursoLotoFacil, tdsDados);

        concursoLotoFacil.setVlArrecadacaoTotal(converterToBigDecimal(17, tdsDados));

        concursoLotoFacil.setNrGanhadores15Num(converterToInt(18, tdsDados));

        Element elCidade = tdsDados.get(19);
        Element elUf = tdsDados.get(20);
        addCidade(concursoLotoFacil, elCidade, elUf);

        concursoLotoFacil.setNrGanhadores14Num(converterToInt(21, tdsDados));
        concursoLotoFacil.setNrGanhadores13Num(converterToInt(22, tdsDados));
        concursoLotoFacil.setNrGanhadores12Num(converterToInt(23, tdsDados));
        concursoLotoFacil.setNrGanhadores11Num(converterToInt(24, tdsDados));

        concursoLotoFacil.setVlRateio15Num(converterToBigDecimal(25, tdsDados));
        concursoLotoFacil.setVlRateio14Num(converterToBigDecimal(26, tdsDados));
        concursoLotoFacil.setVlRateio13Num(converterToBigDecimal(27, tdsDados));
        concursoLotoFacil.setVlRateio12Num(converterToBigDecimal(28, tdsDados));
        concursoLotoFacil.setVlRateio11Num(converterToBigDecimal(29, tdsDados));

        concursoLotoFacil.setVlAcumulado15Num(converterToBigDecimal(30, tdsDados));
        concursoLotoFacil.setVlEstimativaPremio(converterToBigDecimal(31, tdsDados));
        concursoLotoFacil.setVlAcumuladoEspecial(converterToBigDecimal(32, tdsDados));

        int nrRowspan = Integer.parseInt(tdsDados.get(0).attributes().get("rowspan"));
        if (nrRowspan > 1) {

            // criado porque la encima ja contei uma tr
            int count = nrRowspan - 1;
            for (int i = 0; i < count; i++) {

                // tr com as cidades vem separado do concurso
                Element trCidades = iterator != null ? iterator.next() : trDadosConcurso.nextElementSibling();
                Elements tdsCidadesUf = trCidades.getElementsByTag("td");
                elCidade = tdsCidadesUf.get(0);
                elUf = tdsCidadesUf.get(1);
                
                addCidade(concursoLotoFacil, elCidade, elUf);
            }
        }
        
        return concursoLotoFacil;
    }

    private void setDezenas(ConcursoLotoFacil concursoLotoFacil, Elements tdsDados) {

        int primeira = converterToInt(2, tdsDados);
        concursoLotoFacil.setPrDezena(primeira);

        int segunda = converterToInt(3, tdsDados);
        concursoLotoFacil.setSeDezena(segunda);

        int terceira = converterToInt(4, tdsDados);
        concursoLotoFacil.setTeDezena(terceira);

        int quarta = converterToInt(5, tdsDados);
        concursoLotoFacil.setQaDezena(quarta);

        int quinta = converterToInt(6, tdsDados);
        concursoLotoFacil.setQiDezena(quinta);

        int sexta = converterToInt(7, tdsDados);
        concursoLotoFacil.setSxDezena(sexta);

        int setima = converterToInt(8, tdsDados);
        concursoLotoFacil.setStDezena(setima);

        int oitava = converterToInt(9, tdsDados);
        concursoLotoFacil.setOtDezena(oitava);

        int nona = converterToInt(10, tdsDados);
        concursoLotoFacil.setNoDezena(nona);

        int decima = converterToInt(11, tdsDados);
        concursoLotoFacil.setDcDezena(decima);

        int decimaPrimeira = converterToInt(12, tdsDados);
        concursoLotoFacil.setDprDezena(decimaPrimeira);

        int decimaSegunda = converterToInt(13, tdsDados);
        concursoLotoFacil.setDseDezena(decimaSegunda);

        int decimaTerceira = converterToInt(14, tdsDados);
        concursoLotoFacil.setDteDezena(decimaTerceira);

        int decimaQuarta = converterToInt(15, tdsDados);
        concursoLotoFacil.setDqaDezena(decimaQuarta);

        int decimaQuinta = converterToInt(16, tdsDados);
        concursoLotoFacil.setDqiDezena(decimaQuinta);

        List<Integer> listaDezenas = Arrays.asList(primeira, segunda, terceira, quarta, quinta, sexta, setima, oitava,
                nona, decima, decimaPrimeira, decimaSegunda, decimaTerceira, decimaQuarta, decimaQuinta);

        setDenasOrdenadas(listaDezenas, concursoLotoFacil);
    }

    private void setDenasOrdenadas(List<Integer> listaDezenas, ConcursoLotoFacil concursoLotoFacil) {
        listaDezenas.sort(Comparator.naturalOrder());

        DezenasLotoFacilOrdenadas dezenasLotoFacilOrdenadas = new DezenasLotoFacilOrdenadas();
        dezenasLotoFacilOrdenadas.setPrimeira(listaDezenas.get(0));
        dezenasLotoFacilOrdenadas.setSegunda(listaDezenas.get(1));
        dezenasLotoFacilOrdenadas.setTerceira(listaDezenas.get(2));
        dezenasLotoFacilOrdenadas.setQuarta(listaDezenas.get(3));
        dezenasLotoFacilOrdenadas.setQuinta(listaDezenas.get(4));
        dezenasLotoFacilOrdenadas.setSexta(listaDezenas.get(5));
        dezenasLotoFacilOrdenadas.setSetima(listaDezenas.get(6));
        dezenasLotoFacilOrdenadas.setOitava(listaDezenas.get(7));
        dezenasLotoFacilOrdenadas.setNona(listaDezenas.get(8));
        dezenasLotoFacilOrdenadas.setDecima(listaDezenas.get(9));
        dezenasLotoFacilOrdenadas.setDecimaPrimeira(listaDezenas.get(10));
        dezenasLotoFacilOrdenadas.setDecimaSegunda(listaDezenas.get(11));
        dezenasLotoFacilOrdenadas.setDecimaTerceira(listaDezenas.get(12));
        dezenasLotoFacilOrdenadas.setDecimaQuarta(listaDezenas.get(13));
        dezenasLotoFacilOrdenadas.setDecimaQuinta(listaDezenas.get(14));

        dezenasLotoFacilOrdenadas.setConcursoLotoFacil(concursoLotoFacil);

        concursoLotoFacil.setDezenasLotoFacilOrdenadas(dezenasLotoFacilOrdenadas);
    }

    private void addCidade(ConcursoLotoFacil concursoLotoFacil, Element elCidade, Element elUf) {
        String nmCidade = elCidade.text().replace("&nbsp;", "").trim();
        String uf = elUf.text().replace("&nbsp;", "").trim();

        if (!StringUtil.isBlank(nmCidade) || !StringUtil.isBlank(uf)) {
            Cidade cidade = new Cidade();
            cidade.setNmCIdade(nmCidade.toUpperCase());
            cidade.setUf(uf.toUpperCase());
            cidade.setConcursoLotoFacil(concursoLotoFacil);
            concursoLotoFacil.getCidades().add(cidade);
        }
    }
}


