package com.myking520.github.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;


public class ResourceUtils {
	public static final String JAR_URL_SEPARATOR = "!/";
	public static final String FILE_URL_PREFIX = "file:";
	public static final String URL_PROTOCOL_FILE = "file";
	public static final String URL_PROTOCOL_JAR = "jar";
	public static final String URL_PROTOCOL_ZIP = "zip";
	public static URI toURI(URL url) throws URISyntaxException {
		return toURI(url.toString());
	}
	public static URI toURI(String location) throws URISyntaxException {
		return new URI(location.replaceAll(" ", "%20"));
	}
	public static URL extractJarFileURL(URL jarUrl) throws MalformedURLException {
		String urlFile = jarUrl.getFile();
		int separatorIndex = urlFile.indexOf(JAR_URL_SEPARATOR);
		if (separatorIndex != -1) {
			String jarFile = urlFile.substring(0, separatorIndex);
			try {
				return new URL(jarFile);
			}
			catch (MalformedURLException ex) {
				if (!jarFile.startsWith("/")) {
					jarFile = "/" + jarFile;
				}
				return new URL(FILE_URL_PREFIX + jarFile);
			}
		}
		else {
			return jarUrl;
		}
	}
	public static boolean isFileURL(URL url) {
		String protocol = url.getProtocol();
		return URL_PROTOCOL_FILE.equals(protocol);
	}
	public static boolean isJarURL(URL url) {
		String protocol = url.getProtocol();
		return (URL_PROTOCOL_JAR.equals(protocol) || URL_PROTOCOL_ZIP.equals(protocol));
	}
	public static File getFile(URL resourceUrl, String description) throws FileNotFoundException {
		if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol())) {
			throw new FileNotFoundException(
					description +"->"+ resourceUrl);
		}
		try {
			return new File(toURI(resourceUrl).getSchemeSpecificPart());
		}
		catch (URISyntaxException ex) {
			return new File(resourceUrl.getFile());
		}
	}
	public static File getFile(URI resourceUri, String description) throws FileNotFoundException {
		if (!URL_PROTOCOL_FILE.equals(resourceUri.getScheme())) {
			throw new FileNotFoundException(
					description +"->"+ resourceUri);
		}
		return new File(resourceUri.getSchemeSpecificPart());
	}
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf("/");
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith("/")) {
				newPath += "/";
			}
			return newPath + relativePath;
		}
		else {
			return relativePath;
		}
	}
	public static String getFilename(String path) {
		if (path == null) {
			return null;
		}
		int separatorIndex = path.lastIndexOf("/");
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}
	public static String classPackageAsResourcePath(Class<?> clazz) {
		if (clazz == null) {
			return "";
		}
		String className = clazz.getName();
		int packageEndIndex = className.lastIndexOf(".");
		if (packageEndIndex == -1) {
			return "";
		}
		String packageName = className.substring(0, packageEndIndex);
		return packageName.replace(".", "/");
	}
	

}
