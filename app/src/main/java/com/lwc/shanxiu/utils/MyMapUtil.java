package com.lwc.shanxiu.utils;

import android.content.Context;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.lwc.shanxiu.bean.NearBean;
import com.lwc.shanxiu.fragment.MapFragment;

import java.util.ArrayList;
import java.util.List;

public class MyMapUtil {
	private static boolean isLoading = false;
	private static boolean isAdding = false;
	private static ArrayList<NearBean> nearList2;
	private static List<Marker> mapMarkers2;
	private static ArrayList<String> imagepaths;

	/**
	 * 设置地图相关监听
	 *
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	public static void setUpMap(AMap aMap, Marker mGPSMarker, MapFragment mainActivity) {
		aMap.setOnMarkerDragListener(mainActivity);// 设置marker可拖拽事件监听器
		aMap.setOnMapLoadedListener(mainActivity);// 设置amap加载成功事件监听器
		aMap.setOnMarkerClickListener(mainActivity);// 设置点击marker事件监听器
		aMap.setOnInfoWindowClickListener(mainActivity);// 设置点击infoWindow事件监听器
		aMap.setInfoWindowAdapter(mainActivity);// 设置自定义InfoWindow样式
		aMap.setTrafficEnabled(true);// 显示实时交通状况
		aMap.getUiSettings().setCompassEnabled(false); // 是否显示指南针
		aMap.setLocationSource(mainActivity);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
		aMap.getUiSettings().setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	}

	protected static boolean isAddingMarker = true;

	/**
	 * 在地图上添加marker
	 * @updateInfo (此处输入修改内容,若无修改可不写.)
	 */
	public static void addMarkersToMap(final Context mainActivity, final AMap aMap, final List<Marker> mapMarkers, MapView mapView,
			final ArrayList<NearBean> nearList, final int type, MapFragment mapFragment) {

		if (!isAdding) {
			isAdding = true;
			for (int i = 0; i < mapMarkers.size(); i++) {
				mapMarkers.get(i).hideInfoWindow();
				mapMarkers.get(i).destroy();
			}
			mapMarkers.clear();
			if (imagepaths == null) {
				imagepaths = new ArrayList<String>();
			} else {
				imagepaths.clear();
			}

			for (int i = 0; i < nearList.size(); i++) {
				imagepaths.add(nearList.get(i).getPicture());
			}

			if (isAddingMarker) {

				mapFragment.showRoute();
//				if (nearList.size() == 1 && TApplication.userInfoBean.getHasOrder().equals(ServerConfig.TRUE)) {
//					mapFragment.showRoute();
//				} else {
//					for (int i = 0; i < nearList.size(); i++) {
//						ImageView img = new ImageView(mainActivity);
//						img.setBackgroundResource(R.drawable.ditu_weixiuyuan);
//						LatLng lat = new LatLng(Double.valueOf(nearList.get(i).getLat()), Double.valueOf(nearList.get(i).getLon()));
//						MarkerOptions draggable = new MarkerOptions().anchor(0.5f, 0.5f).position(lat).title(nearList.get(i).getName())
//								.snippet(nearList.get(i).getUid()).icon(BitmapDescriptorFactory.fromView(img)).draggable(false);
//						Marker marker = aMap.addMarker(draggable);
//
//						mapMarkers.add(marker);
//					}
				}
				isAdding = false;
				isLoading = false;
				isAddingMarker = true;


//		if (nearList.size() == 0) {
//			isLoading = false;
//			for (int i = 0; i < mapMarkers.size(); i++) {
//				mapMarkers.get(i).hideInfoWindow();
//				mapMarkers.get(i).destroy();
//			}
//			mapMarkers.clear();
//		} else {
//			if (!isAdding) {
//				isAdding = true;
//				for (int i = 0; i < mapMarkers.size(); i++) {
//					mapMarkers.get(i).hideInfoWindow();
//					mapMarkers.get(i).destroy();
//				}
//				mapMarkers.clear();
//				if (imagepaths == null) {
//					imagepaths = new ArrayList<String>();
//				} else {
//					imagepaths.clear();
//				}
//
//				for (int i = 0; i < nearList.size(); i++) {
//					imagepaths.add(ServerConfig.SERVER_API_URL + "/" + nearList.get(i).getPicture());
//				}
//
//				if (isAddingMarker) {
//					if (nearList.size() == 1 && TApplication.userInfoBean.getHasOrder().equals(ServerConfig.TRUE)) {
//						mapFragment.showRoute();
//					} else {
//						for (int i = 0; i < nearList.size(); i++) {
//							ImageView img = new ImageView(mainActivity);
//							img.setBackgroundResource(R.drawable.ditu_weixiuyuan);
//							LatLng lat = new LatLng(Double.valueOf(nearList.get(i).getLat()), Double.valueOf(nearList.get(i).getLon()));
//							MarkerOptions draggable = new MarkerOptions().anchor(0.5f, 0.5f).position(lat).title(nearList.get(i).getName())
//									.snippet(nearList.get(i).getUid()).icon(BitmapDescriptorFactory.fromView(img)).draggable(false);
//							Marker marker = aMap.addMarker(draggable);
//
//							mapMarkers.add(marker);
//						}
//					}
//					isAdding = false;
//					isLoading = false;
//					isAddingMarker = true;

					// isAddingMarker = false;
					// for (int i = 0; i < nearList.size(); i++) {
					// new AsyncImageloader(3, R.drawable.default_portrait_100, 200, 200).loadImageBitmap(mainActivity, i,
					// ServerConfig.SERVER_API_URL + "/" + nearList.get(i).getPicture(), true, new ImageCallback() {
					//
					// @Override
					// public void imageLoaded(int position, Bitmap bitmap, String imagePath) {
					// if (bitmap != null && imagepaths.contains(imagePath)) {
					// imagepaths.remove(imagePath);
					// try {
					// nearList.get(position).setBitmap(bitmap);
					// } catch (Exception e) {
					// isAddingMarker = true;
					// return;
					// }
					// if (imagepaths.size() == 0) {
					// isAddingMarker = true;
					// new Handler().post(new Runnable() {
					//
					// @Override
					// public void run() {
					// addIcons(mainActivity, nearList, aMap, mapMarkers, type);
					// }
					// });
					// }
					// }
					// }
					// });
					// }
//				}
//			}
		}
	}

	/**
	 * 过滤
	 */
	private static void fliterData(final AMap aMap, final List<Marker> mapMarkers, final ArrayList<NearBean> nearList) {
		if (mapMarkers2 == null) {
			mapMarkers2 = new ArrayList<Marker>();
		} else {
			mapMarkers2.clear();
		}
		if (nearList2 == null) {
			nearList2 = new ArrayList<NearBean>();
		} else {
			nearList2.clear();
		}
		for (int i = 0; i < mapMarkers.size(); i++) {
			mapMarkers.get(i).hideInfoWindow();
			for (int j = 0; j < nearList.size(); j++) {
				if (nearList.get(j).equals(mapMarkers.get(i).getTitle())) {
					mapMarkers.get(i).setPosition(new LatLng(Double.valueOf(nearList.get(j).getLat()), Double.valueOf(nearList.get(j).getLon())));
//					aMap.invalidate();// 刷新地图
					aMap.reloadMap();
					mapMarkers2.add(mapMarkers.get(i));
				} else {
					nearList2.add(nearList.get(j));
					mapMarkers.get(i).destroy();
				}
			}
		}
		mapMarkers.clear();
		mapMarkers.addAll(mapMarkers2);
		nearList.clear();
		nearList.addAll(nearList2);
	}

	// protected static void addIcons(Context mainActivity, ArrayList<NearBean> nearList, AMap aMap, List<Marker> mapMarkers, int type) {
	// for (int i = 0; i < nearList.size(); i++) {
	// View view = View.inflate(mainActivity, R.layout.view_icon_map, null);
	// ImageView img = (ImageView) view.findViewById(R.id.img_map);
	// img.setImageBitmap(nearList.get(i).getBitmap());
	// LinearLayout ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
	// if (!TextUtils.isEmpty(nearList.get(i).getHasorder()) && nearList.get(i).getHasorder().equals(ServerConfig.TRUE)) {
	// ll_content.setBackgroundResource(R.drawable.map_icon_red);
	// } else {
	// ll_content.setBackgroundResource(R.drawable.map_icon_blue);
	// }
	// LatLng lat = new LatLng(Double.valueOf(nearList.get(i).getLat()), Double.valueOf(nearList.get(i).getLon()));
	// MarkerOptions draggable = new MarkerOptions().anchor(0.5f, 0.5f).position(lat).title(nearList.get(i).getName())
	// .snippet(nearList.get(i).getUid()).icon(BitmapDescriptorFactory.fromView(view)).draggable(false);
	// Marker marker = aMap.addMarker(draggable);
	//
	// mapMarkers.add(marker);
	// }
	// isAdding = false;
	// isLoading = false;
	// }

//	public static void getNearByPeople(final Context context, final AMap aMap, final List<Marker> mapMarkers, final MapView mapView) {
//		if (TApplication.userInfoBean.getLatitude() == 0.0) {
//			ToastUtil.showToast(context, "定位失败，无法获取附近信息！");
//			return;
//		}
//		if (!isLoading) {
//			isLoading = true;
//			RequestExecutor.addTask(new BaseTask(context, "加载中...", false) {
//
//				@Override
//				public ResponseBean sendRequest() {
//					return new UserBiz().getNearPeople("");
//				}
//
//				@SuppressWarnings("unchecked")
//				@Override
//				public void onSuccess(ResponseBean result) {
//					TApplication.nearList.clear();
//					TApplication.nearList.addAll((ArrayList<NearBean>) ((ListBean) result.getObject()).getModelList());
//					if (TApplication.nearList.size() == 0) {
//						ToastUtil.showToast(context, "您的附近没有工程师！");
//					} else {
//						ToastUtil.showToast(context, "搜索完成！");
//						RepairsListActivity.type = 1;
//						IntentUtil.gotoActivity(context, RepairsListActivity.class);
//					}
//					// addMarkersToMap(context, aMap, mapMarkers, mapView, TApplication.nearList, 1);
//				}
//
//				@Override
//				public void onFail(ResponseBean result) {
//					isLoading = false;
//					aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
//							new LatLng(TApplication.currentBestLocation.getLatitude(), TApplication.currentBestLocation.getLongitude()), 18, 30, 0)),
//							1000, null);
//					updateUserData(aMap, context, mapMarkers, mapView);
//				}
//			});
//		} else {
//			ToastUtil.showLongToast(context, "正在搜索！");
//		}
//
//	}

//	/***
//	 * 修改用户信息
//	 *
//	 * @version 1.0
//	 * @param context
//	 * @param mapView
//	 * @param mapMarkers
//	 * @createTime 2015年3月13日,上午11:53:57
//	 * @updateTime 2015年3月13日,上午11:53:57
//	 * @createAuthor zhou wan
//	 * @updateAuthor
//	 * @updateInfo (此处输入修改内容,若无修改可不写.)
//	 *        1.修改性别
//	 */
//	private static void updateUserData(final AMap aMap, final Context context, final List<Marker> mapMarkers, final MapView mapView) {
//		RequestExecutor.addTask(new BackTask() {
//			@Override
//			public ResponseBean sendRequest() {
//				return new UserBiz().updateUserInfo(null, null, null, null, null, null, null, null, new AMapLocation(aMap.getMyLocation()));
//			}
//
//			@Override
//			public void onSuccess(ResponseBean result) {
////				getNearByPeople(context, aMap, mapMarkers, mapView);
//			}
//
//			@Override
//			public void onFail(ResponseBean result) {
//				ToastUtil.showToast(context, "搜索失败！");
//			}
//
//		});
//
//	}
}
