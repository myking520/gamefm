package com.myking520.github.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;


public interface IResouce {
	boolean exists();
	boolean isReadable();
	boolean isOpen();
	URL getURL() throws IOException;
	URI getURI() throws IOException;
	File getFile() throws IOException;
	long contentLength() throws IOException;
	long lastModified() throws IOException;
	IResouce createRelative(String relativePath) throws IOException;
	String getFilename();
	String getDescription();
	InputStream getInputStream() throws IOException;
	boolean isWritable();
	OutputStream getOutputStream() throws IOException;

}
