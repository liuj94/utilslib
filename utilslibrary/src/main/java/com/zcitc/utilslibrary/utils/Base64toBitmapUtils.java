package com.zcitc.utilslibrary.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Base64toBitmapUtils {
    public static Bitmap getBase64toBitmap(String base64) {
//        Base64.encodeToString(BitmapToBytesUtil.bitmapToBytes(bitmap), Base64.DEFAULT)
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    public static Bitmap base64ToBitmap(String base64Data) {
        try {
            base64Data = base64Data.replace("data:image/jpeg;base64,","");
            base64Data = base64Data.replace("data:image/png;base64,","");
            base64Data = base64Data.replace("data:image/jpg;base64,","");
            base64Data = base64Data.replace("data:image/x-icon;base64,","");
            byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
