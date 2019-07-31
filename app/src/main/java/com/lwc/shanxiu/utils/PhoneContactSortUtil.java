package com.lwc.shanxiu.utils;

import java.text.Collator;
import java.util.Comparator;

import com.lwc.shanxiu.bean.PhoneContactBean;

/**
 * 手机通讯录联系人排序工具
 * 
 * @Description TODO
 * @author CodeApe
 * @version 1.0
 * @date 2013-12-18
 * @Copyright: Copyright (c) 2013 Shenzhen Tentinet Technology Co., Ltd. Inc. All rights reserved.
 * 
 */
public class PhoneContactSortUtil implements Comparator<PhoneContactBean> {

	@Override
	public int compare(PhoneContactBean lhs, PhoneContactBean rhs) {

		return sortByName(lhs, rhs);

	}

	/**
	 * 根据名称排序
	 * 
	 * @version 1.0
	 * @createTime 2013-12-18,下午5:17:08
	 * @updateTime 2013-12-18,下午5:17:08
	 * @createAuthor CodeApe
	 * @updateAuthor CodeApe
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 * 
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	private int sortByName(PhoneContactBean lhs, PhoneContactBean rhs) {
		Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);

		return cmp.compare(lhs.getPinyin(), rhs.getPinyin());
	}

}
