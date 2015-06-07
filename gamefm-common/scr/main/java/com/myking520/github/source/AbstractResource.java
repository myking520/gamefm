package com.myking520.github.source;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import com.myking520.github.utils.ResourceUtils;

public abstract class AbstractResource implements IResouce {


	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public URL getURL() throws IOException {
		throw new FileNotFoundException(getDescription() + " 没有找到路径");
	}

	@Override
	public URI getURI() throws IOException {
		URL url = getURL();
		try {
			return ResourceUtils.toURI(url);
		}
		catch (URISyntaxException ex) {
			throw new IOException(url.toString(), ex);
		}
	}


	private long lastFileModified() throws IOException{
		long lastModified = getFileForLastModifiedCheck().lastModified();
		if (lastModified == 0L) {
			throw new FileNotFoundException(getDescription() +
					"读取时间时候发生错误");
		}
		return lastModified;
	}


	@Override
	public IResouce createRelative(String relativePath) throws IOException {
		throw new FileNotFoundException("不能创建 " + getDescription());
	}

	@Override
	public String getFilename() {
		return null;
	}


	@Override
	public String toString() {
		return getDescription();
	}


	@Override
	public int hashCode() {
		return getDescription().hashCode();
	}
	public File getFile() throws IOException {
		URL url = getURL();

		return ResourceUtils.getFile(url, getDescription());
	}

	protected File getFileForLastModifiedCheck() throws IOException {
		URL url = getURL();
		if (ResourceUtils.isJarURL(url)) {
			URL actualUrl = ResourceUtils.extractJarFileURL(url);
			return ResourceUtils.getFile(actualUrl, "Jar URL");
		}
		else {
			return getFile();
		}
	}


	protected File getFile(URI uri) throws IOException {
	
		return ResourceUtils.getFile(uri, getDescription());
	}


	@Override
	public boolean exists() {
		try {
			URL url = getURL();
			if (ResourceUtils.isFileURL(url)) {
				return getFile().exists();
			}
			else {
				URLConnection con = url.openConnection();
				HttpURLConnection httpCon =
						(con instanceof HttpURLConnection ? (HttpURLConnection) con : null);
				if (httpCon != null) {
					int code = httpCon.getResponseCode();
					if (code == HttpURLConnection.HTTP_OK) {
						return true;
					}
					else if (code == HttpURLConnection.HTTP_NOT_FOUND) {
						return false;
					}
				}
				if (con.getContentLength() >= 0) {
					return true;
				}
				if (httpCon != null) {
					httpCon.disconnect();
					return false;
				}
				else {
					InputStream is = getInputStream();
					is.close();
					return true;
				}
			}
		}
		catch (IOException ex) {
			return false;
		}
	}

	@Override
	public boolean isReadable() {
		try {
			URL url = getURL();
			if (ResourceUtils.isFileURL(url)) {
				File file = getFile();
				return (file.canRead() && !file.isDirectory());
			}
			else {
				return true;
			}
		}
		catch (IOException ex) {
			return false;
		}
	}

	@Override
	public long contentLength() throws IOException {
		URL url = getURL();
		if (ResourceUtils.isFileURL(url)) {
			return getFile().length();
		}
		else {
			URLConnection con = url.openConnection();
			return con.getContentLength();
		}
	}

	@Override
	public long lastModified() throws IOException {
		URL url = getURL();
		if (ResourceUtils.isFileURL(url) || ResourceUtils.isJarURL(url)) {
			return lastFileModified();
		}
		else {
			URLConnection con = url.openConnection();
			return con.getLastModified();
		}
	}
	@Override
	public boolean isWritable() {
		return false;
	}
	@Override
	public OutputStream getOutputStream() throws IOException {
		return null;
	}
}
