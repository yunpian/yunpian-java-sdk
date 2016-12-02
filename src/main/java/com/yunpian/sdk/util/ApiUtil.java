/**
 * 
 */
package com.yunpian.sdk.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.constants.YunpianConstants;

/**
 * api参数／返回值处理工具
 * 
 * @author dzh
 * @date Nov 25, 2016 12:08:31 PM
 * @since 1.2.0
 */
public class ApiUtil {

	static final Logger LOG = LoggerFactory.getLogger(ApiUtil.class);

	static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static final Date str2date(String date) {
		if (date == null || "".equals(date))
			return null;

		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		try {
			return format.parse(date);
		} catch (ParseException e) {
			LOG.error(date + " " + e.getMessage(), e.fillInStackTrace());
		}
		return null;
	}

	public static final String urlEncode(String text, String charset) {
		if (charset == null || "".equals(charset))
			charset = YunpianConstants.HTTP_CHARSET_DEFAULT;
		try {
			return URLEncoder.encode(text, charset);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e.fillInStackTrace());
		}
		return text;
	}

}
