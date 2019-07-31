package com.lwc.shanxiu.utils;


import com.amap.api.maps.AMap;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.WalkPath;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.map.WalkRouteOverlay;

import android.content.Context;

public class RouteTool extends WalkRouteOverlay {
	public RouteTool(Context arg0, AMap arg1, WalkPath arg2, LatLonPoint arg3, LatLonPoint arg4) {
		super(arg0, arg1, arg2, arg3, arg4);
	}

	/*
	 * 修改终点marker样式，这里的R.drawable.none是我自己画的一个PNG图片，图片什么都看不到，而这么修改就等于是把这些marker都去掉了，只留下一条规划的路线，当然可以把BitmapDescriptor
	 * 的起点、终点等做成域封装起来供别的类修改，现在我比较懒，就用汉字说明就好了
	 */
	@Override
	protected BitmapDescriptor getEndBitmapDescriptor() {
		BitmapDescriptor reBitmapDescriptor = new BitmapDescriptorFactory().fromResource(R.drawable.ditu_zhongdian);
		return reBitmapDescriptor;
	}

	/* 修改起点marker样式 */
	@Override
	protected BitmapDescriptor getStartBitmapDescriptor() {
		BitmapDescriptor reBitmapDescriptor = new BitmapDescriptorFactory().fromResource(R.drawable.ditu_qidian);

		return reBitmapDescriptor;
	}

	/* 修改中间点marker样式 */
	@Override
	protected BitmapDescriptor getWalkBitmapDescriptor() {
		return super.getWalkBitmapDescriptor();
	}

}
