package com.myking520.github.source.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.myking520.github.source.AbstractResource;
import com.myking520.github.source.IResouce;
import com.myking520.github.utils.ClassUtils;
import com.myking520.github.utils.ResourceUtils;

public class ClassPathResource extends AbstractResource{
	private final String path;

	private ClassLoader classLoader;

	private Class<?> clazz;


	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}

	public ClassPathResource(String path, ClassLoader classLoader) {
		this.path = path;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}

	public ClassPathResource(String path, Class<?> clazz) {
		this.path = path;
		this.clazz = clazz;
	}

	protected ClassPathResource(String path, ClassLoader classLoader, Class<?> clazz) {
		this.path =path;
		this.classLoader = classLoader;
		this.clazz = clazz;
	}

	public final String getPath() {
		return this.path;
	}

	public final ClassLoader getClassLoader() {
		return (this.clazz != null ? this.clazz.getClassLoader() : this.classLoader);
	}


	@Override
	public boolean exists() {
		return (resolveURL() != null);
	}


	protected URL resolveURL() {
		if (this.clazz != null) {
			return this.clazz.getResource(this.path);
		}
		else if (this.classLoader != null) {
			return this.classLoader.getResource(this.path);
		}
		else {
			return ClassLoader.getSystemResource(this.path);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		InputStream is;
		if (this.clazz != null) {
			is = this.clazz.getResourceAsStream(this.path);
		}
		else if (this.classLoader != null) {
			is = this.classLoader.getResourceAsStream(this.path);
		}
		else {
			is = ClassLoader.getSystemResourceAsStream(this.path);
		}
		if (is == null) {
			throw new FileNotFoundException(this.getDescription());
		}
		return is;
	}


	@Override
	public URL getURL() throws IOException {
		URL url = resolveURL();
		if (url == null) {
			throw new FileNotFoundException(getDescription());
		}
		return url;
	}


	@Override
	public IResouce createRelative(String relativePath) {
		String pathToUse = ResourceUtils.applyRelativePath(this.path, relativePath);
		return new ClassPathResource(pathToUse, this.classLoader, this.clazz);
	}


	@Override
	public String getFilename() {
		return ResourceUtils.getFilename(this.path);
	}


	@Override
	public String getDescription() {
		StringBuilder builder = new StringBuilder("class path resource [");
		String pathToUse = path;
		if (this.clazz != null && !pathToUse.startsWith("/")) {
			builder.append(ResourceUtils.classPackageAsResourcePath(this.clazz));
			builder.append('/');
		}
		if (pathToUse.startsWith("/")) {
			pathToUse = pathToUse.substring(1);
		}
		builder.append(pathToUse);
		builder.append(']');
		return builder.toString();
	}

	@Override
	public int hashCode() {
		return this.path.hashCode();
	}


}
