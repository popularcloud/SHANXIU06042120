package com.lwc.shanxiu.module.partsLib.ui.bean;

/**
 * @author younge
 * @date 2018/12/27 0027
 * @email 2276559259@qq.com
 */
public class PartsBean {


    /**
     * createTime : 2018-12-24 09:14:31
     * accessoriesName : b显卡
     * accessoriesDetailImg : http://cdn.mixiu365.com/1541640285816_291591.jpg,http://cdn.mixiu365.com/1541640285816_291591.jpg
     * attributeId : 1545384241551XB
     * accessoriesId : 1545614071647UD
     * attributeName : 品牌：七彩虹,游戏性能：发烧级
     * typeName : 显卡
     * accessoriesParam : 屏幕大小：11寸,内存：128G
     * accessoriesPrice : 1000
     * accessoriesImg : http://cdn.mixiu365.com/1541640285816_291591.jpg
     * typeId : 1545380016029ER
     * isValid : 1
     */

    private String createTime;
    private String accessoriesName;
    private String accessoriesDetailImg;
    private String attributeId;
    private String accessoriesId;
    private String attributeName;
    private String typeName;
    private String accessoriesParam;
    private Double accessoriesPrice;
    private String accessoriesImg;
    private String typeId;
    private int isValid;
    private int sumSize = 1;
   // private boolean itemIsChecked = false;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAccessoriesName() {
        return accessoriesName;
    }

    public void setAccessoriesName(String accessoriesName) {
        this.accessoriesName = accessoriesName;
    }

    public String getAccessoriesDetailImg() {
        return accessoriesDetailImg;
    }

    public void setAccessoriesDetailImg(String accessoriesDetailImg) {
        this.accessoriesDetailImg = accessoriesDetailImg;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAccessoriesId() {
        return accessoriesId;
    }

    public void setAccessoriesId(String accessoriesId) {
        this.accessoriesId = accessoriesId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAccessoriesParam() {
        return accessoriesParam;
    }

    public void setAccessoriesParam(String accessoriesParam) {
        this.accessoriesParam = accessoriesParam;
    }

    public Double getAccessoriesPrice() {
        return accessoriesPrice;
    }

    public void setAccessoriesPrice(Double accessoriesPrice) {
        this.accessoriesPrice = accessoriesPrice;
    }

    public String getAccessoriesImg() {
        return accessoriesImg;
    }

    public void setAccessoriesImg(String accessoriesImg) {
        this.accessoriesImg = accessoriesImg;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }

    public int getSumSize() {
        return sumSize;
    }

    public void setSumSize(int sumSize) {
        this.sumSize = sumSize;
    }

  /*  public boolean isItemIsChecked() {
        return itemIsChecked;
    }

    public void setItemIsChecked(boolean itemIsChecked) {
        this.itemIsChecked = itemIsChecked;
    }*/
}
