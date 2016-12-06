package com.yunpian.sdk.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by bingone on 15/11/18.
 */
import org.apache.commons.codec.binary.Base64;

/**
 * Tea算法
 * 
 * 每次操作可以处理8个字节数据 KEY为16字节,应为包含4个int型数的int[]，一个int为4个字节 加密解密轮数应为8的倍数，推荐加密轮数为64轮
 */
@Deprecated
public class TeaUtil {
	// 加密
	public static byte[] encrypt(byte[] content, int offset, int[] key, int times) {// times为加密轮数
		int[] tempInt = byteToInt(content, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			sum += delta;
			y += ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			z += ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
		}
		tempInt[0] = y;
		tempInt[1] = z;
		return intToByte(tempInt, 0);
	}

	// 解密
	public static byte[] decrypt(byte[] encryptContent, int offset, int[] key, int times) {
		int[] tempInt = byteToInt(encryptContent, offset);
		int y = tempInt[0], z = tempInt[1], sum = 0xC6EF3720, i;
		int delta = 0x9e3779b9; // 这是算法标准给的值
		int a = key[0], b = key[1], c = key[2], d = key[3];

		for (i = 0; i < times; i++) {
			z -= ((y << 4) + c) ^ (y + sum) ^ ((y >> 5) + d);
			y -= ((z << 4) + a) ^ (z + sum) ^ ((z >> 5) + b);
			sum -= delta;
		}
		tempInt[0] = y;
		tempInt[1] = z;

		return intToByte(tempInt, 0);
	}

	// byte[]型数据转成int[]型数据
	private static int[] byteToInt(byte[] content, int offset) {

		int[] result = new int[content.length >> 2]; // 除以2的n次方 == 右移n位 即
														// content.length / 4 ==
														// content.length >> 2
		for (int i = 0, j = offset; j < content.length; i++, j += 4) {
			result[i] = transform(content[j + 3]) | transform(content[j + 2]) << 8 | transform(content[j + 1]) << 16
					| (int) content[j] << 24;
		}
		return result;

	}

	// int[]型数据转成byte[]型数据
	private static byte[] intToByte(int[] content, int offset) {
		byte[] result = new byte[content.length << 2]; // 乘以2的n次方 == 左移n位 即
														// content.length * 4 ==
														// content.length << 2
		for (int i = 0, j = offset; j < result.length; i++, j += 4) {
			result[j + 3] = (byte) (content[i] & 0xff);
			result[j + 2] = (byte) ((content[i] >> 8) & 0xff);
			result[j + 1] = (byte) ((content[i] >> 16) & 0xff);
			result[j] = (byte) ((content[i] >> 24) & 0xff);
		}
		return result;
	}

	// 若某字节被解释成负的则需将其转成无符号正数
	private static int transform(byte temp) {
		int tempInt = (int) temp;
		if (tempInt < 0) {
			tempInt += 256;
		}
		return tempInt;
	}

	/**
	 * 通过TEA算法加密信息
	 * 
	 * @param info
	 * @param key
	 * @return
	 */
	public static byte[] encryptByTea(String info, int[] key) throws UnsupportedEncodingException {
		byte[] temp = info.getBytes("UTF-8");
		int n = 8 - temp.length % 8;// 若temp的位数不足8的倍数,需要填充的位数
		byte[] encryptStr = new byte[temp.length + n];
		encryptStr[0] = (byte) n;
		System.arraycopy(temp, 0, encryptStr, n, temp.length);
		byte[] result = new byte[encryptStr.length];
		for (int offset = 0; offset < result.length; offset += 8) {
			byte[] tempEncrpt = TeaUtil.encrypt(encryptStr, offset, key, 32);
			System.arraycopy(tempEncrpt, 0, result, offset, 8);
		}
		return result;
	}

	public static String encryptForYunpianV2(String info, String secret) throws UnsupportedEncodingException {
		if (StringUtil.isNullOrEmpty(secret))
			return info;
		int[] key = getKeyByApikey(getApiSecret(secret));
		byte[] bytes = encryptByTea(info, key);
		Base64 base64 = new Base64();
		return base64.encodeAsString(bytes);
	}

	public static String getApiSecret(String secret) {
		return secret + secret + secret + secret;
	}

	public static String decryptForYunpianV2(String msg, String secret) throws IOException {
		int[] key = getKeyByApikey(secret + secret + secret + secret);
		// byte[] secretInfo = Base64.decodeBase64(secret);
		Base64 base64 = new Base64();
		byte[] secretInfo = base64.decode(msg);
		return decryptByTea(secretInfo, key);
	}

	/**
	 * 通过TEA算法解密信息
	 * 
	 * @param secretInfo
	 * @param KEY
	 * @return
	 */
	public static String decryptByTea(byte[] secretInfo, int[] KEY) throws UnsupportedEncodingException {
		byte[] decryptStr = null;
		byte[] tempDecrypt = new byte[secretInfo.length];
		for (int offset = 0; offset < secretInfo.length; offset += 8) {
			decryptStr = TeaUtil.decrypt(secretInfo, offset, KEY, 32);
			System.arraycopy(decryptStr, 0, tempDecrypt, offset, 8);
		}

		int n = tempDecrypt[0];
		return new String(tempDecrypt, n, decryptStr.length - n, "UTF-8");
	}

	// private static byte[] stringToByte(String str){
	// String[] list = str.split(",");
	// byte[] b = new byte[list.length];
	// for (int i = 0; i < list.length; i++) {
	// b[i] = Byte.parseByte(list[i]);
	// }
	// return b;
	// }

	public static int[] getKeyByApikey(String apikey) {
		int[] key = new int[4];
		key[0] = Integer.parseInt(apikey.substring(0, 7), 16);
		key[1] = Integer.parseInt(apikey.substring(8, 15), 16);
		key[2] = Integer.parseInt(apikey.substring(16, 23), 16);
		key[3] = Integer.parseInt(apikey.substring(24, 31), 16);
		return key;
	}
}
