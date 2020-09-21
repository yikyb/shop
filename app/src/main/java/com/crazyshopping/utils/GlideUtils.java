package com.crazyshopping.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crazyshopping.common.CircleTransform;

public class GlideUtils {
    private static final String TAG = "GlideUtils";
    public static void loadImg(Context context, String url, ImageView imageView){
        if (!TextUtils.isEmpty(url) && imageView!=null){
            Glide.with(context).load(url).into(imageView);
        }else {
            Log.d(TAG, "loadImg: 没有数据");
        }
    }
    public static void loadRoundImg(Context context,String url,ImageView imageView){
        if (!TextUtils.isEmpty(url) && imageView!=null){
            RequestOptions options = RequestOptions.bitmapTransform(new CircleTransform(context));
            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }
}
