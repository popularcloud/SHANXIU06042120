package com.lwc.shanxiu.configs;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.lwc.shanxiu.map.ToastUtil;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    private final String RSA_PATCH = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC1MFZUItk/4iIk088I/AYg2l9zdx5fdAXcwtFAUxt0OjS9MDxLyHSkngWe5SrmN1YM/8zrILKtmzGEfkPlynL+LUgS2euDaLqz1BEJKQ7xOgcwExr/PXyQqPboMdF8w3CofFBMxswk/7hagjOMp3zwHhxnb93cpEajckz/44Pvavi+iCYEzJdmx4NSktbRkU7eLH/WIu7CBktq01Zw3OBfAhrRBVAhbuITydzrUd9vnyfIopdbORqIGX9+F4TMZoP3GJBxCiiTIWH33awt3FxFN0fVPVux1nUJD+Y/M3GEYqBIOcqiCwpK4Sy+PdUyj76LEq1KpS3cQPS4uFuOEzchAgMBAAECggEAHd3o7ReNfjVVcYKkpxN8IyA8CbmB/TQP31MR0Suh4+fQV7tCxS824wfVX1rhVm+atKvZ6IxMR8fEQtXZdWCI0hM+xTS82L2DwS7c3AizmNn641bS5W4KQYJIqP/FS26f/HX5ep5uek7Y4bqCqtXVB3O/NOHAzaUaq8iknq2vSuCskP6y9v4JqqqPSbouSCFlVGIVAUKpeH5Ooj11f+INk+LCw1YoAAb47hVkHn42X6dNzSw0rY60SKvDhQnYdZyPYQkarRU/TickdnFXFdUDaCTuIV7PWYgkmmJjeB59xKvizuyzy7OmVgsefxj4CahKi6HDfPhqzLdzauzL1Lfc/QKBgQD3IR7mNpkUFrQgLNgvK5uxCRhaUfMI4RUzhJl4oBvno/dhmPvjjpdNAaVw2tIj3CRdOicdUBKSSz1b6P1GV7BUDONag4lmxMg3c1CD3BxUb957Mx/dCwZqhDxwoVbe+RGpMmLtgembFTgD9xIJucc/d7g5FU5Z4kAKfAAGKltxKwKBgQC7sUlmuTSccFJ4WXj3W6ck+3VRuiEwPI9jGdwgd6O366L8X81wUDrr8XTSWA5woBIKB6VCH7v4UPQ188+uV8bq5Q3bSCVxXlhYhhKbb5UcBQR6fe5eJ55YU+RDoJyFk/lSZRIEtr0rxl5Ji8ZqUo1L0g95MOj5P7z+8Idbfb6a4wKBgQDpURnNG6r2rmCtJ+mKz08Um5OkYk5kCa1skDodCnyN+93pBPh7ZPovt+gsXkubk5G5etfG8vw1pldt2NCWgt902x4jLKCiqBKnV3WiqEMO7PdNtXzVwjzyf5pYc9qCdQBZj7qapdaZljTmeXNMP1t/7lW9fwIUFmmD/IJBT+g5NQKBgBn8t/8DEae/XYvQR5FoHUJflqTUMpgtKmU8RWAxcHecpppu4VD1qtLUQaqGCqnJX/YHjPgU0L/W5Zk6wryO5rnLMRn1aUnDGFRZa+YdkvWoNDCJkyFyNthf9TDv4fwUuuaPm+kPLC4Nbyybr8M03t9qnD3Zacho7NAXmYYvaN6lAoGAXcZS3iZqjyv2R1RWkhpExFeKYzfDbWVZ4MXPHUr1VRh8y4HxPMVRStu8IwmZTWH0dL2aVFyVTHjv0P3DzdnzR3/3p5c0FB5scYN0fHawyicsX++GcH9eEBUtKZoT+1qb747HD6noUg36oZx4Qa6XmeBKVvuzypbAcqV8EYdUcVM=";
    @Keep
    @SophixEntry(TApplication.class)
    static class RealApplicationStub {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }
    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                             .getPackageInfo(this.getPackageName(), 0)
                             .versionName;
        } catch (Exception e) {
        }
        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("24690586-1", "322efd9b64afe6f1a03c7e61fabb4bf1", RSA_PATCH)
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            ToastUtil.show(getApplicationContext(), "应用数据更新，需要重启app！");
                            System.exit(0);
                        }
                    }
                }).initialize();
    }
}