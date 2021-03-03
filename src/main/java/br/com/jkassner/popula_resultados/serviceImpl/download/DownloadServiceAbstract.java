package br.com.jkassner.popula_resultados.serviceImpl.download;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import br.com.jkassner.popula_resultados.service.DownloadService;
import br.com.jkassner.popula_resultados.utils.PathUtils;

public abstract class DownloadServiceAbstract implements DownloadService {

	private CloseableHttpClient client;
	protected InputStream is;

	public abstract String getUri();

	public String download() {
		this.downloadFile();
		String content = getContent();
		this.close();
		
		return content;
	}

	protected String getContent() {
		
		return PathUtils.convertInputStreamToString(is);
	}

	private void downloadFile() {
		client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(getUri());
		HttpResponse response;

		try {
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			is.close();
			client.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
