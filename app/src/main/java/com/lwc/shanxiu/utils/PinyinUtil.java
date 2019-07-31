package com.lwc.shanxiu.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 *
 * @author 罗文忠
 * @version 1.0
 * @date 2013-4-18
 */
public class PinyinUtil {

	/**
	 * 获取中文汉语拼音全拼字符串
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-18
	 *
	 * @param string
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String toHanyuPinyinString(String string) {

		String pinyin = "";
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();

		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		try {
			pinyin = PinyinHelper.toHanyuPinyinString(string, outputFormat, "");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pinyin;
	}
	
	/**
	 * 获取汉语拼音首字母字符串
	 *
	 * @author 罗文忠
	 * @version 1.0
	 * @date 2013-4-18
	 *
	 * @param string
	 * @return
	 */
	public static String toHanyuPinyinHeadString(String string) {
		
		String pinyin = "";
		HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
		
		outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		
		try {
			pinyin = PinyinHelper.toHanyuPinyinHeadString(string, outputFormat, "");
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pinyin;
	}

}
