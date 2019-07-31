package com.lwc.shanxiu.utils;

import android.content.Context;


import com.lwc.shanxiu.R;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/3/10 16:09
 * @email 294663966@qq.com
 * 上拉加载、下拉刷新 工具类
 */
public class BGARefreshLayoutUtils {

    /**
     *  初始化刷新控件， 默认可以上拉加载
     * @param context
     * @param mRefreshLayout
     */
    public static void initRefreshLayout(Context context, BGARefreshLayout mRefreshLayout) {

        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(context, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("加载中");
        // 设置整个加载更多控件的背景颜色资源 id
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.white);

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源 id
//        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
//         设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//        mRefreshLayout.setCustomHeaderView(mBanner, false);
    }

    /**
     * 初始化刷新控件
     * @param context
     * @param mRefreshLayout
     * @param isLoadMore TRUE 支持上拉加载， flase 不支持上拉加载
     */
    public static void initRefreshLayout(Context context, BGARefreshLayout mRefreshLayout, boolean isLoadMore) {

        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(context, isLoadMore);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("加载中");
        // 设置整个加载更多控件的背景颜色资源 id
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.white);

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
        // mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置整个加载更多控件的背景 drawable 资源 id
//        refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源 id
//        refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景 drawable 资源 id
//        refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
//         设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//        mRefreshLayout.setCustomHeaderView(mBanner, false);
    }

    /**
     * 隐藏加载更多ui
     *
     * @param mRefreshLayout
     */
    public static void endLoadingMore(BGARefreshLayout mRefreshLayout) {
        if (mRefreshLayout != null) {
            mRefreshLayout.endLoadingMore();
        }
    }

    /**
     * 隐藏刷新ui
     *
     * @param mRefreshLayout
     */
    public static void endRefreshing(BGARefreshLayout mRefreshLayout) {
        if (mRefreshLayout != null) {
            mRefreshLayout.endRefreshing();
        }
    }

    /**
     * 显示加载更多ui
     *
     * @param mRefreshLayout
     */
    public static void beginLoadingMore(BGARefreshLayout mRefreshLayout) {
        if (mRefreshLayout != null) {
            mRefreshLayout.beginLoadingMore();
        }
    }

    /**
     * 显示刷新ui
     * @param mRefreshLayout
     */
    public static void beginRefreshing(BGARefreshLayout mRefreshLayout) {
        if (mRefreshLayout != null) {
            mRefreshLayout.beginRefreshing();
        }
    }

}
