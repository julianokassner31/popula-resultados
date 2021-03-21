package br.com.jkassner.popula_resultados.serviceImpl;

import br.com.jkassner.pojo.dto.ConcursoLotoFacilDto;
import br.com.jkassner.pojo.dto.EnumDescricaoFaixa;
import br.com.jkassner.pojo.dto.RateioDto;
import br.com.jkassner.pojo.model.Cidade;
import br.com.jkassner.pojo.model.ConcursoLotoFacil;
import br.com.jkassner.pojo.model.DezenasLotoFacilOrdenadas;
import br.com.jkassner.popula_resultados.repository.lotofacil.ConcursoLotoFacilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class CrawlerService {

	@Autowired
	private ConcursoLotoFacilRepository concursoLotoFacilRepository;
	
	public void saveConcursoLotoFacil(ConcursoLotoFacilDto concursoDto) {
		
		ConcursoLotoFacil concurso = new ConcursoLotoFacil();
		
		concurso.setIdConcurso(concursoDto.getNumero());
		concurso.setDtSorteio(Date.from(concursoDto.getDataApuracao().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		concurso.setVlArrecadacaoTotal(concursoDto.getValorArrecadado());
		concurso.setVlAcumuladoEspecial(concursoDto.getValorAcumuladoConcursoEspecial());
		concurso.setVlAcumulado15Num(concursoDto.getValorAcumuladoProximoConcurso());
		
		setNumeroGanhadores(concursoDto, concurso);
		
		setDezenasSorteio(concursoDto, concurso);
		
		setDezenasOrdenadas(concursoDto, concurso);
		
		setCidades(concursoDto, concurso);
		
		concursoLotoFacilRepository.save(concurso);
	}
	
	private void setCidades(ConcursoLotoFacilDto concursoDto, ConcursoLotoFacil concurso) {
		concursoDto.getListaMunicipioUFGanhadores().forEach(cidadeDto -> {
			Cidade cidade = new Cidade();
			cidade.setConcursoLotoFacil(concurso);
			cidade.setNmCIdade(cidadeDto.getMunicipio());
			cidade.setUf(cidadeDto.getUf());
			
			concurso.getCidades().add(cidade);
		});
	}
	
	private void setDezenasOrdenadas(ConcursoLotoFacilDto concursoDto, ConcursoLotoFacil concurso) {
		DezenasLotoFacilOrdenadas dezenasLotoFacilOrdenadas = new DezenasLotoFacilOrdenadas();
		dezenasLotoFacilOrdenadas.setConcursoLotoFacil(concurso);
		
		dezenasLotoFacilOrdenadas.setPrimeira(concursoDto.getListaDezenas().get(0));
		dezenasLotoFacilOrdenadas.setSegunda(concursoDto.getListaDezenas().get(1));
		dezenasLotoFacilOrdenadas.setTerceira(concursoDto.getListaDezenas().get(2));
		dezenasLotoFacilOrdenadas.setQuarta(concursoDto.getListaDezenas().get(3));
		dezenasLotoFacilOrdenadas.setQuinta(concursoDto.getListaDezenas().get(4));
		dezenasLotoFacilOrdenadas.setSexta(concursoDto.getListaDezenas().get(5));
		dezenasLotoFacilOrdenadas.setSetima(concursoDto.getListaDezenas().get(6));
		dezenasLotoFacilOrdenadas.setOitava(concursoDto.getListaDezenas().get(7));
		dezenasLotoFacilOrdenadas.setNona(concursoDto.getListaDezenas().get(8));
		dezenasLotoFacilOrdenadas.setDecima(concursoDto.getListaDezenas().get(9));
		dezenasLotoFacilOrdenadas.setDecimaPrimeira(concursoDto.getListaDezenas().get(10));
		dezenasLotoFacilOrdenadas.setDecimaSegunda(concursoDto.getListaDezenas().get(11));
		dezenasLotoFacilOrdenadas.setDecimaTerceira(concursoDto.getListaDezenas().get(12));
		dezenasLotoFacilOrdenadas.setDecimaQuarta(concursoDto.getListaDezenas().get(13));
		dezenasLotoFacilOrdenadas.setDecimaQuinta(concursoDto.getListaDezenas().get(14));
		
		concurso.setDezenasLotoFacilOrdenadas(dezenasLotoFacilOrdenadas);
	}

	private void setDezenasSorteio(ConcursoLotoFacilDto concursoDto, ConcursoLotoFacil concurso) {
		concurso.setPrDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(0));
		concurso.setSeDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(1));
		concurso.setTeDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(2));
		concurso.setQaDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(3));
		concurso.setQiDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(4));
		concurso.setSxDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(5));
		concurso.setStDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(6));
		concurso.setOtDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(7));
		concurso.setNoDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(8));
		concurso.setDcDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(9));
		concurso.setDprDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(10));
		concurso.setDseDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(11));
		concurso.setDteDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(12));
		concurso.setDqaDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(13));
		concurso.setDqiDezena(concursoDto.getDezenasSorteadasOrdemSorteio().get(14));
	}
	
	private void setNumeroGanhadores(ConcursoLotoFacilDto concursoDto, ConcursoLotoFacil concursoLotoFacil) {
		
		for(RateioDto rateio : concursoDto.getListaRateioPremio()) {
			
			switch (EnumDescricaoFaixa.getByValue(rateio.getDescricaoFaixa())) {
				
				case ACERTOS_11:
					concursoLotoFacil.setVlRateio11Num(rateio.getValorPremio());
					concursoLotoFacil.setNrGanhadores11Num(rateio.getNumeroDeGanhadores());
					break;
					
				case ACERTOS_12:
					concursoLotoFacil.setVlRateio12Num(rateio.getValorPremio());
					concursoLotoFacil.setNrGanhadores12Num(rateio.getNumeroDeGanhadores());	
					break;
				
				case ACERTOS_13:
					concursoLotoFacil.setVlRateio13Num(rateio.getValorPremio());
					concursoLotoFacil.setNrGanhadores13Num(rateio.getNumeroDeGanhadores());
					break;
				
				case ACERTOS_14:
					concursoLotoFacil.setVlRateio14Num(rateio.getValorPremio());
					concursoLotoFacil.setNrGanhadores14Num(rateio.getNumeroDeGanhadores());
					break;
				
				case ACERTOS_15:
					concursoLotoFacil.setVlRateio15Num(rateio.getValorPremio());
					concursoLotoFacil.setNrGanhadores15Num(rateio.getNumeroDeGanhadores());
					break;
	
				default:
					break;
			}
		}
	}
}
