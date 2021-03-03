package br.com.jkassner.popula_resultados.serviceImpl.lotofacil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jkassner.pojo.model.TipoLoteriaDownload;
import br.com.jkassner.popula_resultados.service.UnzipService;
import br.com.jkassner.popula_resultados.serviceImpl.download.DownloadServiceAbstract;

@Service("downloadTodosConcursosZipLotoFacil")
public class DownloadTodosConcursosZipLotoFacilImpl extends DownloadServiceAbstract {

	@Autowired
	UnzipService unzipService;
	
	@Override
	public String getUri() {
		return TipoLoteriaDownload.LOTOFACIL_TODOS.getUri();
	}
	
	@Override
	protected String getContent() {
		
		return unzipService.unzipFile(is);
	}
}
