/**
 * 
 */
package com.yunpian.sdk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.ContentTooLongException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * @author dzh
 * @date Dec 18, 2017 12:38:22 AM
 * @since 1.2.0
 */
public class HttpEntityWrapper implements HttpEntity {

    private HttpEntity entity;

    public HttpEntityWrapper(HttpEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean isRepeatable() {
        return getContentLength() != -1;
    }

    @Override
    public boolean isChunked() {
        return !isRepeatable();
    }

    @Override
    public boolean isStreaming() {
        return !isRepeatable();
    }

    @Override
    public long getContentLength() {
        return entity.getContentLength();
    }

    @Override
    public Header getContentType() {
        return entity.getContentType();
    }

    @Override
    public Header getContentEncoding() {
        return entity.getContentEncoding();
    }

    @Override
    public void consumeContent() {}

    @Override
    public InputStream getContent() throws IOException {
        if (this.getContentLength() < 0) { throw new ContentTooLongException("Content length is unknown"); }
        // else if (this.contentLength > 25 * 1024) {
        // throw new ContentTooLongException("Content length is too long: " + this.contentLength);
        // }
        final ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        writeTo(outstream);
        outstream.flush();
        return new ByteArrayInputStream(outstream.toByteArray());
    }

    @Override
    public void writeTo(final OutputStream outstream) throws IOException {
        entity.writeTo(outstream);
    }

}
