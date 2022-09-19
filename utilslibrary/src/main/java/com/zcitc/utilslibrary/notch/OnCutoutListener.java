package com.zcitc.utilslibrary.notch;

/**
 * <p>
 * 针对API>= 28时，判断手机是否含有刘海区
 * </p>
 **/
public interface OnCutoutListener {

    /**
     * 预留判断是否有刘海
     * @param isHas
     */
    void isHasCutout(boolean isHas);
}
