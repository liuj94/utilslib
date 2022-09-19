package com.zcitc.utilslibrary


class FastClickUtils {
companion object{
    @JvmStatic
     var lastClickTime: Long = 0
    fun isFastClick(): Boolean {
        val time = System.currentTimeMillis()
        var num = time - lastClickTime
        if (num < 1500) {
            return true
        }
        lastClickTime = time
        return false
    }
}

}