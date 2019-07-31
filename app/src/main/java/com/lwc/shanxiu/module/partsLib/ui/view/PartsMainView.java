package com.lwc.shanxiu.module.partsLib.ui.view;


import com.lwc.shanxiu.module.bean.ADInfo;
import com.lwc.shanxiu.module.bean.PartsTypeBean;

import java.util.List;

/**
 * @author younge
 * @date 2018/12/26 0026
 * @email 2276559259@qq.com
 */
public interface PartsMainView {

    //void onBannerLoadSucess();

    /**
     * 获取配件类型
     * @param partsBeanList
     */
    void onLoadPartsType(List<PartsTypeBean> partsBeanList);

    /**
     * 获取加载错误信息
     * @param msg
     */
    void onLoadError(String msg);

    /**
     * 获取配件库轮播数据
     * @param adInfoList
     */
    void onBannerLoadSuccess(List<ADInfo> adInfoList);
}
