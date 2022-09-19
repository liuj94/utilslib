package com.zcitc.utilslibrary.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import java.lang.reflect.Method;

public class NavigationBarUtil {
    public static void initActivity(View content) {
        new NavigationBarUtil(content);
    }

    private View mObserved;//被监听的视图
    private int usableHeightView;//视图变化前的可用高度
    private ViewGroup.LayoutParams layoutParams;

    private NavigationBarUtil(View content) {
        mObserved = content;
        //给View添加全局的布局监听器监听视图的变化
        mObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                resetViewHeight();
            }
        });
        layoutParams = mObserved.getLayoutParams();
    }

    /**
     * 重置视图的高度，使不被底部虚拟键遮挡
     */
    private void resetViewHeight() {
        int usableHeightViewNow = CalculateAvailableHeight();
        //比较布局变化前后的View的可用高度
        if (usableHeightViewNow != usableHeightView) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            layoutParams.height = usableHeightViewNow;
            mObserved.requestLayout();//请求重新布局
            usableHeightView = usableHeightViewNow;
        }
    }

    /**
     * 计算试图高度
     * @return
     */
    private int CalculateAvailableHeight() {
        Rect r = new Rect();
        mObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);//如果不是沉浸状态栏，需要减去顶部高度
//        return (r.bottom );//如果是沉浸状态栏
    }
    /**
     * 获取底部导航栏高度
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        if(height<126){
            height = 126;
        }
        return height;
    }



    /**
     * 判断底部是否有虚拟键
     * @param context
     * @return
     */
    public static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0) {
                //开启手势，不显示虚拟键
                hasNavigationBar = false;
            }
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
            Log.v("dbw", "navBarOverride:" + navBarOverride);
        } catch (Exception e) {
        }
        Log.v("dbw", "hasNavigationBar:" + hasNavigationBar);
        return hasNavigationBar;

    }



}
