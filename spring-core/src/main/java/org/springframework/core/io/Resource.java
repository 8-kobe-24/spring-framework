/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.core.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.springframework.lang.Nullable;

/**
 * Interface for a resource descriptor that abstracts from the actual
 * type of underlying resource, such as a file or class path resource.
 *
 * <p>An InputStream can be opened for every resource if it exists in
 * physical form, but a URL or File handle can just be returned for
 * certain resources. The actual behavior is implementation-specific.
 *
 * @author Juergen Hoeller
 * @since 28.12.2003
 * @see #getInputStream()
 * @see #getURL()
 * @see #getURI()
 * @see #getFile()
 * @see WritableResource
 * @see ContextResource
 * @see UrlResource
 * @see FileUrlResource
 * @see FileSystemResource
 * @see ClassPathResource
 * @see ByteArrayResource
 * @see InputStreamResource
 */
public interface Resource extends InputStreamSource {

	/**
	 * 资源是否存在
	 *
	 * @return true/false
	 */
	boolean exists();

	/**
	 * 资源是否可读
	 *
	 * @return true/false
	 */
	default boolean isReadable() {
		return exists();
	}

	/**
	 * 资源所代表的句柄是否被一个 stream 打开了
	 *
	 * @return true/false
	 */
	default boolean isOpen() {
		return false;
	}

	/**
	 * 是否为 File
	 *
	 * @return true/false
	 */
	default boolean isFile() {
		return false;
	}

	/**
	 * 返回资源的 URL 的句柄
	 *
	 * @return URL
	 * @throws IOException IO异常
	 */
	URL getURL() throws IOException;

	/**
	 * 返回资源的 URI 的句柄
	 *
	 * @return URI
	 * @throws IOException IO异常
	 */
	URI getURI() throws IOException;

	/**
	 * 返回资源的 File 的句柄
	 *
	 * @return File
	 * @throws IOException IO异常
	 */
	File getFile() throws IOException;

	/**
	 * 返回 ReadableByteChannel
	 *
	 * @return ReadableByteChannel
	 * @throws IOException IO异常
	 */
	default ReadableByteChannel readableChannel() throws IOException {
		return Channels.newChannel(getInputStream());
	}

	/**
	 * 资源内容的长度
	 *
	 * @return 内容长度
	 * @throws IOException IO异常
	 */
	long contentLength() throws IOException;

	/**
	 * 资源最后的修改时间
	 *
	 * @return 修改时间
	 * @throws IOException IO异常
	 */
	long lastModified() throws IOException;

	/**
	 * 根据资源的相对路径创建新资源
	 *
	 * @param relativePath 相对路径
	 * @return 新资源
	 * @throws IOException IO异常
	 */
	Resource createRelative(String relativePath) throws IOException;

	/**
	 * 资源的文件名
	 *
	 * @return 文件名
	 */
	@Nullable
	String getFilename();

	/**
	 * 资源的描述
	 *
	 * @return 描述
	 */
	String getDescription();

}
