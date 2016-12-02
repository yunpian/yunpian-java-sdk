/**
 * 
 */
package com.yunpian.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunpian.sdk.constants.YunpianConstants;

/**
 * 云片配置
 * 
 * @ThreadSafe
 * @author dzh
 * @date Nov 22, 2016 7:46:10 PM
 * @since 1.2.0
 */
public class YunpianConf implements YunpianConstants {

	static final Logger LOG = LoggerFactory.getLogger(YunpianConf.class);

	/**
	 * 
	 */
	private Properties _conf = new Properties();

	private File file;

	private String apikey;

	private InputStream in;

	private Properties props;

	public YunpianConf with(String apikey) {
		this.apikey = apikey;
		return this;
	}

	/**
	 * 
	 * @param file
	 *            absolute path of yunpian.properties
	 * @return
	 */
	public YunpianConf with(File file) {
		this.file = file;
		return this;
	}

	/**
	 * 
	 * @param in
	 *            InputStream of yunpian.properties
	 * @return
	 */
	public YunpianConf with(InputStream in) {
		this.in = in;
		return this;
	}

	/**
	 * 
	 * @param props
	 *            properties of yunpian.properties
	 * @return
	 */
	public YunpianConf with(Properties props) {
		this.props = props;
		return this;
	}

	/**
	 * 覆盖次序 props > in > file
	 * 
	 * @return
	 */
	public YunpianConf build() {
		try {
			load(file);
			load(in);
			load(props);

			if (this.props.isEmpty()) {
				LOG.info("load default yunpian.properties");
				load(YunpianClient.class.getResourceAsStream("/yunpian.properties"));
			}
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e.fillInStackTrace());
		} finally {
			file = null;
			in = null;
			props = null;
		}
		LOG.info("apikey-{} conf-{}", getApikey(), _conf.toString());
		return this;
	}

	/**
	 * 
	 * @param file
	 *            yunpian.properties
	 * @return
	 * @throws IOException
	 */
	final YunpianConf load(File file) throws Exception {
		if (file != null && file.exists()) {
			load(new FileInputStream(file));
		}
		return this;
	}

	/**
	 * 
	 * @param in
	 *            InputStream of yunpian.properties
	 * @return
	 * @throws IOException
	 */
	final YunpianConf load(InputStream in) throws Exception {
		if (in != null) {
			_conf.load(in);
		}
		return this;
	}

	final YunpianConf load(Properties props) {
		if (props != null) {
			_conf.clear();
			_conf.putAll(props);
		}
		return this;
	}

	/**
	 * 查找顺序: 系统配置->云片配置->默认值
	 * 
	 * @param key
	 * @param defVal
	 * @return
	 */
	public String getConf(String key, String defVal) {
		return System.getProperty(key, _conf.getProperty(key, defVal));
	}

	public int getConfInt(String key, String defVal) {
		String v = getConf(key, defVal);
		return Integer.parseInt(v);
	}

	// public static class EmptyYunpianConf extends YunpianConf {
	// public String getConf(String key, String defVal) {
	// return "";
	// }
	// }

	/**
	 * apikey定义优先级:
	 * <p>
	 * <ol type="1">
	 * <li>System.getProperty("yp.apikey")</li>
	 * <li>yunpian.properties</li>
	 * <li>new YunpianClient(apikey)</li>
	 * </ol>
	 * </p>
	 */
	public String getApikey() {
		return System.getProperty(YP_APIKEY, _conf.getProperty(YP_APIKEY, apikey));
	}

	@Override
	public String toString() {
		return "YunpianConf-" + _conf.toString();
	}

}
