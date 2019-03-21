package com.wangyz.transform.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.houbb.opencc4j.util.ZhConverterUtil;

/**
 * 简体、繁体相互转换工具类
 * 
 * @author w17250
 *
 */
public class TransformUtil {

	/**
	 * 简体转繁体
	 */
	private static int TYPE_SIMPLE_TO_TRADITIONAL = 1;

	/**
	 * 繁体转简体
	 */
	private static int TYPE_TRADITIONAL_TO_SIMPLE = 2;

	/**
	 * 正则表达式
	 */
	private static String REGEX = "<[a-zA-Z]+.*?>([\\s\\S]*?)</[a-zA-Z]*>";

	/**
	 * Pattern
	 */
	private static Pattern INNER_PATTERN = Pattern.compile(REGEX, Pattern.MULTILINE);

	/**
	 * 简体转繁体
	 * 
	 * @param source
	 *            待转换的XML文件
	 * @param dest
	 *            转换后保存的文件
	 * @return 转化结果
	 */
	public static boolean simpleToTraditional(String source, String dest) {
		return transform(source, dest, TYPE_SIMPLE_TO_TRADITIONAL);
	}

	/**
	 * 繁体转简体
	 * 
	 * @param source
	 *            待转换的XML文件
	 * @param dest
	 *            转换后保存的文件
	 * @return 转化结果
	 */
	public static boolean traditionalToSimple(String source, String dest) {
		return transform(source, dest, TYPE_TRADITIONAL_TO_SIMPLE);
	}

	/**
	 * 简体、繁体转换
	 * 
	 * @param source
	 *            待转换的XML文件
	 * @param dest
	 *            转换后保存的文件
	 * @param type
	 *            转换的类型:TYPE_SIMPLE_TO_TRADITIONAL:简体转繁体、
	 *            TYPE_TRADITIONAL_TO_SIMPLE：繁体转简体
	 * @return 转化结果
	 */
	private static boolean transform(String source, String dest, int type) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			Matcher matcher = null;
			String line = "";
			String group = "";
			String traditional = "";
			reader = new BufferedReader(new FileReader(source));
			writer = new BufferedWriter(new FileWriter(dest));
			while ((line = reader.readLine()) != null) {
				matcher = INNER_PATTERN.matcher(line);
				while (matcher.find()) {
					group = matcher.group(1);
					if (type == TYPE_SIMPLE_TO_TRADITIONAL) {
						traditional = ZhConverterUtil.convertToTraditional(group);
					} else if (type == TYPE_TRADITIONAL_TO_SIMPLE) {
						traditional = ZhConverterUtil.convertToSimple(group);
					} else {
						throw new Exception("not supported transform type");
					}

					line = line.replace(group, traditional);
				}
				writer.write(line);
				writer.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

}
