package com.zcitc.utilslibrary.utils;



import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.widget.Toast;

import com.hjq.toast.ToastUtils;

public class Anti_hijackingUtils {

    /**
     * 用于执行定时任务
     */
    private Timer timer = null;

    /**
     * 用于保存当前任务
     */
    private List<MyTimerTask> tasks = null;

    /**
     * 唯一实例
     */
    private static Anti_hijackingUtils anti_hijackingUtils;

    private Anti_hijackingUtils() {
        // 初始化
        tasks = new ArrayList<MyTimerTask>();
        timer = new Timer();
    }

    /**
     * 获取唯一实例
     *
     * @return 唯一实例
     */
    public static Anti_hijackingUtils getinstance() {
        if (anti_hijackingUtils == null) {
            anti_hijackingUtils = new Anti_hijackingUtils();
        }
        return anti_hijackingUtils;
    }

    /**
     * 在activity的onPause()方法中调用
     *
     * @param activity
     */
    public void onPause(final Activity activity) {
        MyTimerTask task = new MyTimerTask(activity);
        tasks.add(task);
        timer.schedule(task, 2000);
    }

    /**
     * 在activity的onResume()方法中调用
     */
    public void onResume() {
        if (tasks.size() > 0) {
            tasks.get(tasks.size() - 1).setCanRun(false);
            tasks.remove(tasks.size() - 1);
        }
    }

    /**
     * 自定义TimerTask类
     */
    class MyTimerTask extends TimerTask {
        /**
         * 任务是否有效
         */
        private boolean canRun = true;
        private Activity activity;

        public void setCanRun(boolean canRun) {
            this.canRun = canRun;
        }

        public MyTimerTask(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (canRun) {
                        // 程序退到后台，进行风险警告   程序切换至后台运行，请注意观察运行环境是否安全！
                        ToastUtils.show( "宁波房产app退至后台运行");
                        tasks.remove(MyTimerTask.this);
                    }
                }
            });
        }
    }
}

