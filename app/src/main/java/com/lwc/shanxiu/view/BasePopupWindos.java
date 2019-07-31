package com.lwc.shanxiu.view;

import android.widget.PopupWindow;

/**
 * @Description TODO
 * @author zhou wan
 * @date 2015年3月12日
 * @Copyright: Copyright (c) 2015 Shenzhen Tentinet Technology Co., Ltd. Inc.
 *             All rights reserved.
 */
public abstract class BasePopupWindos extends PopupWindow {

	/**
	 * 配置弹出框数据
	 * 
	 * @version 1.0
	 * @createTime 2015年3月12日,上午11:37:26
	 * @updateTime 2015年3月12日,上午11:37:26
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	protected abstract void setPopConfig();

	/**
	 * 设置监听
	 * 
	 * @version 1.0
	 * @createTime 2015年3月12日,上午11:38:11
	 * @updateTime 2015年3月12日,上午11:38:11
	 * @createAuthor zhou wan
	 * @updateAuthor
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	protected abstract void widgetListener();

}
