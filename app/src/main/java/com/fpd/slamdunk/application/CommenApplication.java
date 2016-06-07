package com.fpd.slamdunk.application;


import com.baidu.apistore.sdk.ApiStoreSDK;
import com.fpd.basecore.application.BaseApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

/**
 * Created by t450s on 2016/6/1.
 */
public class CommenApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiStoreSDK.init(this, "c64fc060ae6d9f0bf7599e35edd1f76b");
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "imageloader/Cache");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY-1)
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheFileCount(100)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                .writeDebugLogs()
                .memoryCacheExtraOptions(480,800)
                .build();
        ImageLoader.getInstance().init(configuration);
    }
}
