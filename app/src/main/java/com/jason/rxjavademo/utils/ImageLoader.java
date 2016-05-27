package com.jason.rxjavademo.utils;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.io.File;

/**
 * 图片加载
 * Created by Chen Haitao on 2016/4/26.
 */
public class ImageLoader {

    /**
     * 从网络加载图片，url传入图片地址
     * @param context
     * @param url
     * @param resId
     * @param container
     */
    public static void load(Context context, String url, @DrawableRes int resId, ImageView container, BitmapTransformation transformation) {
        if (context != null && !TextUtils.isEmpty(url) && container != null) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .transform(transformation)
                    .into(container);
        }
    }

    /**
     * 从网络加载图片，url传入图片地址
     * @param context
     * @param url
     * @param resId
     * @param container
     */
    public static void load(Context context, String url, @DrawableRes int resId, ImageView container) {
        if (context != null && !TextUtils.isEmpty(url) && container != null) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(container);
        }
    }

    public static void load(Context context, String url, ImageView container) {
        if (context != null && !TextUtils.isEmpty(url) && container != null) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(container);
        }
    }
    /**
     * 从网络加载图片，url传入图片地址
     * @param context
     * @param url
     * @param resId
     * @param container
     */
    public static void loadWithNoCache(Context context, String url, @DrawableRes int resId, ImageView container, boolean isSkipCache) {
        if (context != null && !TextUtils.isEmpty(url) && container != null) {
            Glide.with(context)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .skipMemoryCache(isSkipCache)
                    .into(container);

        }
    }

    /**
     * 从本地加载图片，drawableId传入资源id
     * @param context
     * @param drawableId
     * @param resId
     * @param container
     */
    public static void load(Context context, @DrawableRes int drawableId, @DrawableRes int resId, ImageView container) {
        if (context != null && container != null) {
            Glide.with(context)
                    .load(drawableId)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(container);

        }
    }

    /**
     * Uri方式加载图片
     * @param context
     * @param uri
     * @param resId
     * @param container
     */
    public static void load(Context context, Uri uri, @DrawableRes int resId, ImageView container) {
        if (context != null && container != null && uri != null) {
            Glide.with(context)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(container);

        }
    }

    /**
     * 文件方式加载图片
     * @param context
     * @param file
     * @param resId
     * @param container
     */
    public static void load(Context context, File file, @DrawableRes int resId, ImageView container) {
        if (context != null && container != null && file.exists()) {
            Glide.with(context)
                    .load(file)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(resId)
                    .thumbnail(0.1f)
                    .crossFade()
                    .into(container);

        }
    }
}
