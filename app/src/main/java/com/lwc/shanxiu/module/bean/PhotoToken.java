package com.lwc.shanxiu.module.bean;

import java.io.Serializable;

/**
 * @author 何栋
 * @version 1.0
 * @date 2017/4/21 14:33
 * @email 294663966@qq.com
 */
public class PhotoToken implements Serializable{

    private String accessKeyId;
    private String secretKeyId;
    private String securityToken;
    private String domain;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretKeyId() {
        return secretKeyId;
    }

    public void setSecretKeyId(String secretKeyId) {
        this.secretKeyId = secretKeyId;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
