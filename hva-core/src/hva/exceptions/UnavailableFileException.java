package hva.exceptions;

import java.io.Serial;

/**
 * Thrown when the specified file does not exist or there is an error while processing this file. 
 */
public class UnavailableFileException extends Exception {
	@Serial
	private static final long serialVersionUID = 202407081733L;

	/** The requested filename. */
	String _filename;

	public UnavailableFileException(String filename) {
	  super("Erro a processar ficheiro " + filename);
	  _filename = filename;
	}

	public String getFilename() {
		return _filename;
	}

}
