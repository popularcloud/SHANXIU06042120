package com.lwc.shanxiu.activity;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapCalcRouteResult;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviRouteNotifyData;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.lwc.shanxiu.R;
import com.lwc.shanxiu.utils.LLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯详情
 *
 * @author 何栋
 * @date 2017年11月29日
 */
public class NavigationActivity extends BaseActivity implements AMapNaviViewListener, AMapNaviListener {

	AMapNaviView mAMapNaviView;
	private AMapNavi mAMapNavi;
	private double mLat, mLon, eLat, eLon;
	protected List<NaviLatLng> mWayPointList;
	@Override
	protected int getContentViewId(Bundle savedInstanceState) {
		return R.layout.activity_navigation;
	}

	@Override
	protected void findViews() {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 实例化语音引擎

		//获取 AMapNaviView 实例
		mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
		mAMapNaviView.setAMapNaviViewListener(this);
		mAMapNaviView.onCreate(savedInstanceState);
		//获取AMapNavi实例
		mAMapNavi = AMapNavi.getInstance(getApplicationContext());
		mAMapNavi.setUseInnerVoice(true);
		//添加监听回调，用于处理算路成功
		mAMapNavi.addAMapNaviListener(this);
		mLat = getIntent().getDoubleExtra("mLat", 0);
		mLon = getIntent().getDoubleExtra("mLon", 0);
		eLat = getIntent().getDoubleExtra("eLat", 0);
		eLon = getIntent().getDoubleExtra("eLon", 0);

	}

	@Override
	protected void init() {

	}

	@Override
	protected void initGetData() {
	}

	@Override
	protected void widgetListener() {
	}

	@Override
	protected void onResume() {
		super.onResume();
		mAMapNaviView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mAMapNaviView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAMapNaviView.onDestroy();
		mAMapNavi.stopNavi();
		mAMapNavi.destroy();
	}

	// 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public void onNaviSetting() {

	}

	@Override
	public void onNaviCancel() {
		finish();
	}

	@Override
	public boolean onNaviBackClick() {
		return false;
	}

	@Override
	public void onNaviMapMode(int i) {

	}

	@Override
	public void onNaviTurnClick() {

	}

	@Override
	public void onNextRoadClick() {

	}

	@Override
	public void onScanViewButtonClick() {

	}

	@Override
	public void onLockMap(boolean b) {

	}

	@Override
	public void onNaviViewLoaded() {

	}

	@Override
	public void onMapTypeChanged(int i) {

	}

	@Override
	public void onNaviViewShowMode(int i) {

	}

	@Override
	public void onInitNaviFailure() {
	}

	@Override
	public void onInitNaviSuccess() {
/**
 * 方法:
 *   int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute);
 * 参数:
 * @congestion 躲避拥堵
 * @avoidhightspeed 不走高速
 * @cost 避免收费
 * @hightspeed 高速优先
 * @multipleroute 多路径
 *
 * 说明:
 *      以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
 * 注意:
 *      不走高速与高速优先不能同时为true
 *      高速优先与避免收费不能同时为true
 */
		int strategy=0;
		try {
			strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NaviLatLng sNavi = new NaviLatLng();
		sNavi.setLatitude(mLat);
		sNavi.setLongitude(mLon);
		List<NaviLatLng> sList = new ArrayList<>();
		sList.add(sNavi);

		NaviLatLng eNavi = new NaviLatLng();
		eNavi.setLatitude(eLat);
		eNavi.setLongitude(eLon);
		List<NaviLatLng> eList = new ArrayList<>();
		eList.add(eNavi);

		mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
	}

	@Override
	public void onStartNavi(int i) {

	}

	@Override
	public void onTrafficStatusUpdate() {

	}

	@Override
	public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

	}

	@Override
	public void onGetNavigationText(int i, String s) {

	}

	@Override
	public void onGetNavigationText(String s) {

	}

	@Override
	public void onEndEmulatorNavi() {
		LLog.e("联网 onEndEmulatorNavi");
	}

	@Override
	public void onArriveDestination() {
		LLog.e("联网 onArriveDestination");
	}

	@Override
	public void onCalculateRouteFailure(int i) {
		LLog.e("联网 onCalculateRouteFailure");
	}

	@Override
	public void onReCalculateRouteForYaw() {
		LLog.e("联网 onReCalculateRouteForYaw");
	}

	@Override
	public void onReCalculateRouteForTrafficJam() {
		LLog.e("联网 onReCalculateRouteForTrafficJam");
	}

	@Override
	public void onArrivedWayPoint(int i) {
		LLog.e("联网 onArrivedWayPoint");
	}

	@Override
	public void onGpsOpenStatus(boolean b) {
		LLog.e("联网 onGpsOpenStatus");
	}

	@Override
	public void onNaviInfoUpdate(NaviInfo naviInfo) {
		LLog.e("联网 onNaviInfoUpdate");
	}

	@Override
	public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {
		LLog.e("联网 onNaviInfoUpdated");
	}

	@Override
	public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {
		LLog.e("联网 updateCameraInfo");
	}

	@Override
	public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {
		LLog.e("联网 updateIntervalCameraInfo");
	}

	@Override
	public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {
		LLog.e("联网 onServiceAreaUpdate");
	}

	@Override
	public void showCross(AMapNaviCross aMapNaviCross) {
		LLog.e("联网 showCross");
	}

	@Override
	public void hideCross() {
		LLog.e("联网 hideCross");
	}

	@Override
	public void showModeCross(AMapModelCross aMapModelCross) {

	}

	@Override
	public void hideModeCross() {

	}

	@Override
	public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

	}

	@Override
	public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

	}

	@Override
	public void hideLaneInfo() {

	}

	@Override
	public void onCalculateRouteSuccess(int[] ints) {

	}

	@Override
	public void notifyParallelRoad(int i) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

	}

	@Override
	public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

	}

	@Override
	public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

	}

	@Override
	public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

	}

	@Override
	public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

	}

	@Override
	public void onPlayRing(int i) {

	}

	@Override
	public void onCalculateRouteSuccess(AMapCalcRouteResult aMapCalcRouteResult) {
		mAMapNavi.startNavi(NaviType.GPS);
	}

	@Override
	public void onCalculateRouteFailure(AMapCalcRouteResult aMapCalcRouteResult) {

	}

	@Override
	public void onNaviRouteNotify(AMapNaviRouteNotifyData aMapNaviRouteNotifyData) {

	}

	@Override
	public void onGpsSignalWeak(boolean b) {

	}
}


