package com.zhonghao.encrycapability.util;

import com.zhonghao.encrycapability.ThreadPool.DecryExecutor;
import com.zhonghao.encrycapability.ThreadPool.EncryExecutor;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class RC4Demo {
	public static String decry_RC4(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return asString(RC4Base(data, key));
	}

	public static String decry_RC4(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return new String(RC4Base(HexString2Bytes(data), key));
	}

	public static byte[] encry_RC4_byte(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		byte b_data[] = data.getBytes();
		return RC4Base(b_data, key);
	}

	public static String encry_RC4_string(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return toHexString(asString(encry_RC4_byte(data, key)));
	}

	private static String asString(byte[] buf) {
		StringBuffer strbuf = new StringBuffer(buf.length);
		for (int i = 0; i < buf.length; i++) {
			strbuf.append((char) buf[i]);
		}
		return strbuf.toString();
	}

	private static byte[] initKey(String aKey) {
		byte[] b_key = aKey.getBytes();
		byte state[] = new byte[256];

		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0) {
			return null;
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch & 0xFF);
			if (s4.length() == 1) {
				s4 = '0' + s4;
			}
			str = str + s4;
		}
		return str;// 0x表示十六进制
	}

	private static byte[] HexString2Bytes(String src) {
		int size = src.length();
		byte[] ret = new byte[size / 2];
		byte[] tmp = null;
		tmp = src.getBytes();
		for (int i = 0; i < size / 2; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	private static byte uniteBytes(byte src0, byte src1) {
		char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (char) (_b0 << 4);
		char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	private static byte[] RC4Base(byte[] input, String mKkey) {
		int x = 0;
		int y = 0;
		byte key[] = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}

	public  static String encryDataUserRc4(int THREADNUM, String KEY,
										 ExecutorService executorService, RC4Demo rc4Demo,
										 List<String> encodeList, File file) {
		 String result1 ="";
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			for (int i = 0; i < lines.size(); i++) {
				encodeList.add("");// 先初始化行数个“”，每个“”代表一行，是用来占位的。这个数组里边存的就是行数
			}
			System.out.println("lines:" + lines.size());

			long startTime = System.nanoTime(); // 获取开始时间

			if (lines.size() < 80) {// 如果lines的大小小于80行则使用单线程
				executorService.execute(new EncryExecutor(rc4Demo, lines, KEY,
						encodeList, 0, lines.size()));
			} else {// 否则将lines除以创建的线程数，然后每个线程传入lines的子list，并且传入start和end
				int subListSize = (int) (Math.floor(lines.size() / THREADNUM));
				for (int i = 0; i < THREADNUM - 1; i++) {
					executorService.execute(new EncryExecutor(rc4Demo, lines
							.subList(i * subListSize, subListSize * (i + 1)),
							KEY, encodeList, i * subListSize, subListSize
									* (i + 1)));
				}// 不能确保的是文件的行数能平分,第十个线程就是加解密剩下的内容
				executorService.execute(new EncryExecutor(rc4Demo,
						lines.subList((THREADNUM - 1) * (subListSize),
								lines.size()), KEY, encodeList, (THREADNUM - 1)
								* (subListSize), lines.size()));
			}
			executorService.shutdown();
			long endTime = System.nanoTime(); // 获取结束时间
			result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
			System.out.println("程序运行时间： " + result1 + "ms");


			while (true) {
				if (executorService.isTerminated()) {
					System.out.println("所有的子线程都结束了！");
					FileUtils.writeLines(file, encodeList, false);
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

		return result1;
	}

	public  static String decryDataUserRc4(int THREADNUM, String KEY,
			ExecutorService executorService, RC4Demo rc4Demo,
			List<String> encodeList, File file) {
		String result1="";
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			for (int i = 0; i < lines.size(); i++) {
				encodeList.add("");
			}
			System.out.println("lines:" + lines.size());

			long startTime = System.nanoTime(); // 获取开始时间

			if (lines.size() < 80) {// 如果lines的大小小于40行则使用单线程
				executorService.execute(new DecryExecutor(rc4Demo, lines, KEY,
						encodeList, 0, lines.size()));
			} else {// 否则将lines除于创建的线程数，然后每个线程传入lines的子list，并且传入start和end
				int subListSize = (int) (Math.floor(lines.size() / THREADNUM));
				for (int i = 0; i < THREADNUM - 1; i++) {
					executorService.execute(new DecryExecutor(rc4Demo, lines
							.subList(i * subListSize, subListSize * (i + 1)),
							KEY, encodeList, i * subListSize, subListSize
									* (i + 1)));
				}// 不能确保的是文件的行数能平分,第十个线程就是加解密剩下的内容
				executorService.execute(new DecryExecutor(rc4Demo,
						lines.subList((THREADNUM - 1) * (subListSize),
								lines.size()), KEY, encodeList, (THREADNUM - 1)
								* (subListSize), lines.size()));
			}
			executorService.shutdown();
			long endTime = System.nanoTime(); // 获取结束时间

			result1 = new Formatter().format("%.2f", (double) ((endTime - startTime)) / 1000000).toString();
			System.out.println("程序运行时间： " + result1 + "ms");

			while (true) {
				if (executorService.isTerminated()) {
					System.out.println("所有的子线程都结束了！");
					FileUtils.writeLines(file, encodeList, false);
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return result1;
	}

}
