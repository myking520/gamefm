package com.myking520.github.source.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.myking520.github.source.AbstractResource;
import com.myking520.github.source.IResouce;

public class PathResource extends AbstractResource{
	private final Path path;
	public PathResource(Path path) {
		this.path = path.normalize();
	}


	public PathResource(String path) {
		this.path = Paths.get(path).normalize();
	}


	public PathResource(URI uri) {
		this.path = Paths.get(uri).normalize();
	}


	public final String getPath() {
		return this.path.toString();
	}

	@Override
	public boolean exists() {
		return Files.exists(this.path);
	}

	@Override
	public boolean isReadable() {
		return (Files.isReadable(this.path) && !Files.isDirectory(this.path));
	}

	@Override
	public InputStream getInputStream() throws IOException {
		if (!exists()) {
			throw new FileNotFoundException(getPath() );
		}
		if (Files.isDirectory(this.path)) {
			throw new FileNotFoundException(getPath() + "isDirectory");
		}
		return Files.newInputStream(this.path);
	}
	@Override
	public URL getURL() throws IOException {
		return this.path.toUri().toURL();
	}

	@Override
	public URI getURI() throws IOException {
		return this.path.toUri();
	}

	@Override
	public File getFile() throws IOException {
		try {
			return this.path.toFile();
		}
		catch (UnsupportedOperationException ex) {
			throw ex;
		}
	}

	@Override
	public long contentLength() throws IOException {
		return Files.size(this.path);
	}
	@Override
	public long lastModified() throws IOException {
		return Files.getLastModifiedTime(path).toMillis();
	}

	public IResouce createRelative(String relativePath) throws IOException {
		return new PathResource(this.path.resolve(relativePath));
	}

	@Override
	public String getFilename() {
		return this.path.getFileName().toString();
	}

	@Override
	public String getDescription() {
		return "path [" + this.path.toAbsolutePath() + "]";
	}

	@Override
	public boolean isWritable() {
		return Files.isWritable(this.path) && !Files.isDirectory(this.path);
	}


	@Override
	public OutputStream getOutputStream() throws IOException {
		if (Files.isDirectory(this.path)) {
			throw new FileNotFoundException(getPath() + " isDirectory");
		}
		return Files.newOutputStream(this.path);
	}



	@Override
	public boolean equals(Object obj) {
		return (this == obj ||
			(obj instanceof PathResource && this.path.equals(((PathResource) obj).path)));
	}


	@Override
	public int hashCode() {
		return this.path.hashCode();
	}

}
