package com.hhp.funstory.view;

public interface OnMainCallback {
    void callBack (String key, Object data);
    void showFragment(String tag, Object[] data, Boolean isBack);
}
