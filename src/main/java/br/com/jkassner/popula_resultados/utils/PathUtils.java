package br.com.jkassner.popula_resultados.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PathUtils {

	public static final String PATH_TMP_DIR = System.getProperty("java.io.tmpdir");
	public static final String SEPARATOR = File.separator;
	
	public static String convertInputStreamToString(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();

		try {
			int len;
			while ((len = inputStream.read()) != -1) {
				sb.append((char)len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}

}
