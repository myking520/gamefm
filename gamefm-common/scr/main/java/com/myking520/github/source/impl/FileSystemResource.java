package com.myking520.github.source.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import com.myking520.github.source.AbstractResource;
import com.myking520.github.source.IResouce;
import com.myking520.github.utils.ResourceUtils;

public class FileSystemResource extends AbstractResource{
	private final File file;
	private final String path;

	public FileSystemResource(File file) {
		this.file = file;
		this.path =file.getPath();
	}
	public FileSystemResource(String path) {
		this.file = new File(path);
		this.path = path;
	}
	public final String getPath() {
		return this.path;
	}
	public boolean exists() {
		return this.file.exists();
	}
	public boolean isReadable() {
		return (this.file.canRead() && !this.file.isDirectory());
	}
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}
	public URL getURL() throws IOException {
		return this.file.toURI().toURL();
	}
	public URI getURI() throws IOException {
		return this.file.toURI();
	}
	public File getFile() {
		return this.file;
	}
	public long contentLength() throws IOException {
		return this.file.length();
	}
	public IResouce createRelative(String relativePath) {
		String pathToUse = ResourceUtils.applyRelativePath(this.path, relativePath);
		return new FileSystemResource(pathToUse);
	}
	public String getFilename() {
		return this.file.getName();
	}
	public String getDescription() {
		return "file [" + this.file.getAbsolutePath() + "]";
	}
	public boolean isWritable() {
		return (this.file.canWrite() && !this.file.isDirectory());
	}
	public OutputStream getOutputStream() throws IOException {
		return new FileOutputStream(this.file);
	}
	public boolean equals(Object obj) {
		return (obj == this ||
			(obj instanceof FileSystemResource && this.path.equals(((FileSystemResource) obj).path)));
	}
	public int hashCode() {
		return this.path.hashCode();
	}
}
