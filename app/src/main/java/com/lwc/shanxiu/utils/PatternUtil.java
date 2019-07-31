package com.lwc.shanxiu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则匹配工具类
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-11-9
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PatternUtil {

	/** Emoji表情正则表达式 */
	public static final String PATTERN_FACE_EMOJI = "\\[emoji_[0-9]{3}\\]";
	/** Emoji表情名字长度 */
	public static final int FACE_EMOJI_LENGTH = 11;

	/** 迪车会表情公共正则表达式 */
	public static final String PATTERN_FACE_CONMENT = "\\{:46[5-8]_[6-9][0-9][0-9]:\\}";
	/** 迪车会默认表情正则表达式 */
	// 匹配：后面加任意一个字母
	public static final String PATTERN_FACE_DEFAULT3 = "\\:[DoPLQ]{1}";
	// 匹配：任意字母，4到7个：
	public static final String PATTERN_FACE_DEFAULT2 = "\\:[a-z]{4,7}\\:";
	// 匹配：后面加任意符号
	public static final String PATTERN_FACE_DEFAULT1 = "\\:[)(@$]{1}";
	// 匹配：lol
	public static final String PATTERN_FACE_LOL = ":lol";
	// 匹配：handshake
	public static final String PATTERN_FACE_HANDSHAKE = ":handshake";
	// 匹配：'(
	public static final String PATTERN_FACE_CRY = ":'\\(";
	// 匹配:loveliness:
	public static final String PATTERN_FACE_LOVE = ":loveliness:";
	// 匹配:hug:
	public static final String PATTERN_FACE_HUG = ":hug:";
	// 匹配;P
	public static final String PATTERN_FACE_TITTER = ";P";

	/**
	 * 正则校验手机号码
	 * 
	 * @version 1.0
	 * @createTime 2013-11-9,下午9:34:24
	 * @updateTime 2013-11-9,下午9:34:24
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param mobileNum
	 *        手机号码
	 * @return true 正确手机号码；false 非法手机号码
	 */
	public static boolean isValidMobilePhone(String mobileNum) {
		// LogUtil.out("mobile Number", mobileNum, false);
		// Pattern p = Pattern.compile("^((13[0-9])|(147)|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
		// Matcher m = p.matcher(mobileNum);
		// return m.matches();

		if (mobileNum.length() == 11) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否纯数字
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:42:31
	 * @updateTime 2013-12-18,上午10:42:31
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        需要判断的源字符串
	 * @return true 纯数字，false非纯数字
	 */
	public static boolean isNumer(String source) {
		Pattern p = Pattern.compile("^\\d*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 判断是纯字母串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:45:33
	 * @updateTime 2013-12-18,上午10:45:33
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        判断的源字符串
	 * @return true是纯字母字符串，false非纯字母字符串
	 */
	public static boolean isChar(String source) {
		Pattern p = Pattern.compile("[a-z]*[A-Z]*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 是否纯特殊符号串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午10:47:36
	 * @updateTime 2013-12-18,上午10:47:36
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        纯特殊符号串
	 * @return true纯特殊符号串，false非纯特殊符号串
	 */
	public static boolean isSymbol(String source) {
		Pattern p = Pattern.compile("[{\\[(<~!@#$%^&*()_+=-`|\"?,./;'\\>)\\]}]*");
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 是否合法帐号
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,下午4:43:04
	 * @updateTime 2013-12-18,下午4:43:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 * @return
	 */
	public static boolean isValidAccount(String source) {
		if (!source.matches("^[0-9]+$") && source.matches("^[a-zA-Z0-9\u4E00-\u9FA5]+$")) {
			int valueLength = 0;
			for (int i = 0; i < source.length(); i++) {
				if (source.substring(i, i + 1).matches("[\u4e00-\u9fa5]")) {
					valueLength += 2;
				} else {
					valueLength += 1;
				}
			}
			if (valueLength >= 4 && valueLength <= 20) {
				return true;
			}
		}
		return false;

	}

	// &&source.length() >= 4 && source.length() <= 20
	/**
	 * 描述：
	 * 
	 * @version 1.0
	 * @createTime 2014-3-30 下午3:41:17
	 * @updateTime 2014-3-30 下午3:41:17
	 * @createAuthor XinGo
	 * @updateAuthor
	 * @updateInfo (修改内容描述)
	 */
	public static boolean isValidAccountNomal(String source) {
		if (source.length() > 15 || source.length() < 5) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 是否正确密码
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午11:11:35
	 * @updateTime 2013-12-18,上午11:11:35
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        需要判断的密码
	 * @return true 合格的密码，false不合法的密码
	 */
	public static boolean isValidPassword(String source) {
		return source.matches("^[0-9a-zA-Z_]+$") && source.length() >= 6 && source.length() <= 16;

	}

	/**
	 * 是否是正确的邮箱格式
	 * 
	 * @version 1.0
	 * @createTime 2014年1月22日,上午11:54:05
	 * @updateTime 2014年1月22日,上午11:54:05
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        需要验证的邮箱
	 * @return
	 */
	public static boolean isValidEmail(String source) {
		Pattern p = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
		Matcher m = p.matcher(source);
		return !m.matches();
	}

	/**
	 * 判断是否符合正则的字符串
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,上午11:18:04
	 * @updateTime 2013-12-18,上午11:18:04
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param source
	 *        需要判断的源字符串
	 * @param pattern
	 *        用于判读的正则表达式
	 * @return true if the source is valid of pattern,else return false
	 */
	public static boolean isValidPattern(String source, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(source);
		return m.matches();
	}

	/**
	 * 正则校验身份证号码
	 * 
	 * @version 1.0
	 * @createTime 2013-11-15,下午4:47:17
	 * @updateTime 2013-11-15,下午4:47:17
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param idcard
	 *        身份证号码
	 * @return true 有效的身份证；false 无效的身份证
	 */
	public static boolean isValidIdCardNum(String idcard) {
		Pattern p1 = Pattern.compile("(/^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$/");
		Pattern p2 = Pattern.compile("/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$/");
		Matcher m1 = p1.matcher(idcard);
		Matcher m2 = p2.matcher(idcard);

		return m1.matches() && m2.matches();
	}

	/**
	 * 判断是否是一个表情
	 * 
	 * @version 1.0
	 * @createTime 2013-11-23,下午11:55:53
	 * @updateTime 2013-11-23,下午11:55:53
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param name
	 *        表情名字
	 * @return true 是表情，false不是表情
	 */
	public static boolean isExpression(String name) {
		Pattern p = Pattern.compile(PATTERN_FACE_EMOJI);
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * 校验车架号
	 *
	 * @version 1.0
	 * @createTime 2014-8-4,下午2:41:48
	 * @updateTime 2014-8-4,下午2:41:48
	 * @createAuthor liujingguo
	 * @updateAuthor liujingguo
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 *
	 * @param num
	 * @return
	 */
	public static boolean isValidCarNum(String num) {
		Pattern p = Pattern.compile("^[0-9A-Za-z]+$");
		Matcher m = p.matcher(num);
		return m.matches();
	}

	/***
	 * 是直辖市
	 * 
	 * @version 1.0
	 * @createTime 2015年3月18日,上午10:23:36
	 * @updateTime 2015年3月18日,上午10:23:36
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @return true or false
	 */
	public static boolean checkMunicipalityCity(String city) {
		if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 是否没有非法字符
	 * 
	 * @version 1.0
	 * @createTime 2015-4-1,下午4:42:02
	 * @updateTime 2015-4-1,下午4:42:02
	 * @createAuthor 綦巍
	 * @updateAuthor 綦巍
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param txt
	 * @return
	 */
	public static boolean isNotHaveIllegal(String txt) {
		return txt.matches("^[a-zA-Z0-9\u4E00-\u9FA5]+$");
	}

	/**
	 * 是否符合名字的规范
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月3日,下午3:04:09
	 * @updateTime 2015年4月3日,下午3:04:09
	 * @createAuthor 言钟钟
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param name
	 *        输入的名字字符串
	 * @return true 符合名字规范，false 反之
	 */
	public static boolean isName(String name) {
		// Pattern p = Pattern.compile("^([\u4e00-\u9fa5]{2,6}+$|([a-zA-Z]){4,12}+$)");
		// Pattern p = Pattern.compile("[\u4E00-\u9FA5]{2,20}(?:·[\u4E00-\u9FA5]{2,20})*");// 为了支持少数名族姓名，改为此行代码
		// Pattern p1 = Pattern.compile("[a-zA-Z]{2,20}(?:·[a-zA-Z]{2,20})*");
		// Matcher m = p.matcher(name);
		// Matcher m1 = p1.matcher(name);
		// return m.matches() || m1.matches();
		int length = 0;
		for (int i = 0; i < name.length(); i++) {
			if (isChinese(name.charAt(i))) {
				length = length + 2;
			} else {
				length++;
			}
		}
		if (length > 0 && length < 201) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 是否符合车友会名称的规范
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月3日,下午3:04:09
	 * @updateTime 2015年4月3日,下午3:04:09
	 * @createAuthor 言钟钟
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param name
	 *        输入的名字字符串
	 * @return true 符合名字规范，false 反之
	 */
	public static boolean isCarFriName(String name) {
		Pattern p = Pattern.compile("^([\u4e00-\u9fa5]{2,6}+$|([a-zA-Z]){4,12}+$)");
		// Pattern p = Pattern.compile("^([\u4e00-\u9fa5a-zA-Z0-9]{2,40}+$)");
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * 是否符合昵称的规范
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年5月30日,上午11:38:03
	 * @updateTime 2015年5月30日,上午11:38:03
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param name
	 * @return
	 */
	public static boolean isNickName(String name) {
		Pattern p = Pattern.compile("^([\u4e00-\u9fa5a-zA-Z0-9]{2,20}+$)");
		Matcher m = p.matcher(name);
		if (m.matches()) {
			int length = 0;
			for (int i = 0; i < name.length(); i++) {
				if (isChinese(name.charAt(i))) {
					length = length + 2;
				} else {
					length++;
				}
			}
			if (length > 3 && length < 21) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断qq号码
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月11日,下午2:45:07
	 * @updateTime 2015年4月11日,下午2:45:07
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static boolean isQQNumber(String str) {
		Pattern p = Pattern.compile("^[1-9][0-9]{4,11}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断qq群号码
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月17日,下午8:01:41
	 * @updateTime 2015年4月17日,下午8:01:41
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static boolean isQQGroupNumber(String str) {
		Pattern p = Pattern.compile("^[1-9][0-9]{5,11}$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 判断字符串是否为空，并且不能全为空格
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年4月10日,下午7:50:13
	 * @updateTime 2015年4月10日,下午7:50:13
	 * @createAuthor
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0 || str.trim().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据Unicode编码完美的判断中文汉字和符号
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月1日,下午3:49:05
	 * @updateTime 2015年6月1日,下午3:49:05
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param c
	 * @return
	 */
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	/**
	 * 完整的判断中文汉字和符号
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月1日,下午3:47:10
	 * @updateTime 2015年6月1日,下午3:47:10
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param strName
	 * @return
	 */
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 只能判断部分CJK字符（CJK统一汉字）
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月1日,下午3:47:22
	 * @updateTime 2015年6月1日,下午3:47:22
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static boolean isChineseByREG(String str) {
		if (str == null) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
		return pattern.matcher(str.trim()).find();
	}

	/**
	 * 只能判断部分CJK字符（CJK统一汉字）
	 * 
	 * 
	 * @version 1.0
	 * @createTime 2015年6月1日,下午3:47:28
	 * @updateTime 2015年6月1日,下午3:47:28
	 * @createAuthor yanzhong
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * @param str
	 * @return
	 */
	public static boolean isChineseByName(String str) {
		if (str == null) {
			return false;
		}
		// 大小写不同：\\p 表示包含，\\P 表示不包含
		// \\p{Cn} 的意思为 Unicode 中未被定义字符的编码，\\P{Cn} 就表示 Unicode中已经被定义字符的编码
		String reg = "\\p{InCJK Unified Ideographs}&&\\P{Cn}";
		Pattern pattern = Pattern.compile(reg);
		return pattern.matcher(str.trim()).find();
	}


}
