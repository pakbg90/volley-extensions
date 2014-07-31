package com.navercorp.volleyextensions.volleyer.http;

import java.util.HashMap;
import java.util.Map;

import com.navercorp.volleyextensions.volleyer.util.Assert;
import com.navercorp.volleyextensions.volleyer.util.StringUtils;

public final class ContentType {

	private static final char SEPERATOR_CHARACTER_OF_CONTENT_TYPE_HEADER = ';';
	public static final ContentType CONTENT_TYPE_APPLICATION_JSON = new ContentType("application/json");
	public static final ContentType CONTENT_TYPE_APPLICATION_XML = new ContentType("application/xml");
	public static final ContentType CONTENT_TYPE_TEXT_JSON = new ContentType("text/json");
	public static final ContentType CONTENT_TYPE_TEXT_XML = new ContentType("text/xml");
	public static final ContentType CONTENT_TYPE_TEXT_PLAIN = new ContentType("text/plain");

	private static final Map<String, ContentType> DEFAULT_CONTENT_TYPES;

	static {
		DEFAULT_CONTENT_TYPES = new HashMap<String, ContentType>();
		DEFAULT_CONTENT_TYPES.put(CONTENT_TYPE_APPLICATION_JSON.contentTypeString, CONTENT_TYPE_APPLICATION_JSON);
		DEFAULT_CONTENT_TYPES.put(CONTENT_TYPE_TEXT_JSON.contentTypeString, CONTENT_TYPE_TEXT_JSON);
		DEFAULT_CONTENT_TYPES.put(CONTENT_TYPE_APPLICATION_XML.contentTypeString, CONTENT_TYPE_APPLICATION_XML);
		DEFAULT_CONTENT_TYPES.put(CONTENT_TYPE_TEXT_XML.contentTypeString, CONTENT_TYPE_TEXT_XML);
		DEFAULT_CONTENT_TYPES.put(CONTENT_TYPE_TEXT_PLAIN.contentTypeString, CONTENT_TYPE_TEXT_PLAIN);
	}

	private String contentTypeString;

	private ContentType(String contentTypeString) {
		Assert.notNull(contentTypeString, "Content type");
		assertContentTypeString(contentTypeString);

		this.contentTypeString = ContentType.filterContentTypeString(contentTypeString);
	}

	private void assertContentTypeString(String contentTypeString) {
		if (StringUtils.isEmpty(contentTypeString)) {
			throw new IllegalArgumentException("Content type must not be empty.");
		}
	}
	
	@Override
	public String toString() {
		return contentTypeString;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof ContentType == false) {
			return false;
		}

		ContentType otherContentType = (ContentType) object;
		if (this == otherContentType) {
			return true;
		}

		if (contentTypeString.equals(otherContentType.contentTypeString)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return contentTypeString.hashCode();
	}
	/**
	 * Create a content type instance. {@code ContentType} instance must be created only by this method.
	 * @param contentTypeString string which may contains a content type. This parameter must not be null.
	 * @return {@code ContentType} instance
	 */
	public static ContentType createContentType(String contentTypeString) {
		ContentType contentType = getDefaultContentType(contentTypeString);
		if (contentType != null) {
			return contentType;
		}

		return new ContentType(contentTypeString);
	}
	/**
	 * Return an actual instance if the instance matched by {@code contentTypeString} already exists. 
	 * @param contentTypeString string which may contains a content type. This parameter must not be null.
	 * @return {@code ContentType} instance, or null if the instance doesn't exist.
	 */
	private static ContentType getDefaultContentType(String contentTypeString) {
		if (contentTypeString == null || contentTypeString.isEmpty()) {
			return null;
		}

		contentTypeString = filterContentTypeString(contentTypeString);
		return DEFAULT_CONTENT_TYPES.get(contentTypeString);
	}
	/**
	 * Filter a content type string to the common format that {@code ContentType} uses 
	 * @param contentTypeString string which may contains a content type. This parameter must not be null.
	 * @return filtered content type string
	 */
	private static String filterContentTypeString(String contentTypeString) {
		contentTypeString = deleteCharset(contentTypeString);
		contentTypeString = deleteSpaces(contentTypeString);
		contentTypeString = contentTypeString.toLowerCase();
		return contentTypeString;
	}
	/**
	 * Delete the charset key/value string from the content of string.
	 * @param contentTypeString string which may contains the charset key/value string. This parameter must not be null.
	 * @return string which doesn't contain the charset 
	 */
	private static String deleteCharset(String contentTypeString) {
		int index = contentTypeString.indexOf(SEPERATOR_CHARACTER_OF_CONTENT_TYPE_HEADER);
		// Skip if the seperator is not in the string
		if (index < 0) {
			return contentTypeString;
		}
		return contentTypeString.substring(0, index);
	}
	/**
	 * Delete all the spaces from the content of string.
	 * @param contentTypeString string which may contains some spaces. This parameter must not be null.
	 * @return string which doesn't contain spaces
	 */
	private static String deleteSpaces(String contentTypeString) {
		return contentTypeString.replaceAll(" ", "");
	}

}
