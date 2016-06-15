package com.fpd.slamdunk.bussiness.test;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.fpd.api.callback.CallBackListener;
import com.fpd.basecore.dialog.SDDialog;
import com.fpd.basecore.dialog.SDProgressDialog;
import com.fpd.core.TestAction;
import com.fpd.core.response.CoreResponse;
import com.fpd.model.invite.InviteListEntity;
import com.fpd.model.invite.inviteEntityList;
import com.fpd.model.test.TestEntity;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

public class TestActivity extends CommenActivity{
    private static final int RESULT_CAMERA_ONLY = 200;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT = 300;
    MapView mapView;
    BaiduMap baiduMap;
    LocationClient mLocationClient = null;
    private ImageView imageView;
    private Uri imageUri;
    private Uri cropImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.test);

        imageView = (ImageView) findViewById(R.id.photo);



//        SDDialog dialog = new SDDialog(this,"成功", new SDDialog.Callback() {
//            @Override
//            public void sureCallBack() {
//                SDProgressDialog progressDialog = new SDProgressDialog(TestActivity.this);
//                progressDialog.showProgressDialog();
//            }
//
//            @Override
//            public void cancelCallBack() {
//                Toast.makeText(TestActivity.this,"取消",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        dialog.show();

//        String dir = null;
//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
//            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
//
//        File  file = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
//        File filex = new File(Environment.getExternalStorageDirectory(),"croptemp.jpg");
//        imageUri = Uri.fromFile(file);
//        cropImage = Uri.fromFile(filex);
//        takePhoto();

//        Log.d("dir",dir);
//        Log.d("file",dir+File.separator+"temp.jpg");
//        File  file = new File(Environment.getExternalStorageDirectory(),"test.jpg");
//        Log.d("fileName",file.getName());
//
//        TestAction ta = new TestAction(this);
//
//        ta.test("fpdfpd","123456", new CallBackListener<TestEntity>() {
//            @Override
//            public void onSuccess(TestEntity result) {
//                //页面渲染
//                Log.d("Code",result.getCode());
//            }
//
//            @Override
//            public void onFailure(String Message) {
//                //错误提示
//            }
//        });

//        mapView = (MapView) findViewById(R.id.baidumap);
//        baiduMap = mapView.getMap();
//        baiduMap.setMyLocationEnabled(true);
//        mLocationClient = new LocationClient(getApplicationContext());
//
//        initLocation();
//        mLocationClient.start();
//        mLocationClient.registerLocationListener(new MyLocationListener(mLocationClient, baiduMap));
    }

    private void takePhoto(){
        Intent intent = null;
        //intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
//        intent.putExtra("return-data", false);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_CAMERA_ONLY);
    }

    private void  initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case RESULT_CAMERA_ONLY:
                cropImg(data.getData());
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT:
                try {
                    Bitmap bt = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImage));
                    imageView.setImageBitmap(setScaleBitmap(bt,3));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    private Bitmap setScaleBitmap(Bitmap photo,int SCALE) {
        if (photo != null) {
            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
            //这里缩小了1/2,但图片过大时仍然会出现加载不了,但系统中一个BITMAP最大是在10M左右,我们可以根据BITMAP的大小
            //根据当前的比例缩小,即如果当前是15M,那如果定缩小后是6M,那么SCALE= 15/6
            Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
            //释放原始图片占用的内存，防止out of memory异常发生
            photo.recycle();
            return smallBitmap;
        }
        return null;
    }
    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        File  fileoutput = new File(Environment.getExternalStorageDirectory(),"x.jpg");
        if (fileoutput.exists())
            fileoutput.delete();
        FileOutputStream fout = null;
        try {
            fileoutput.createNewFile();
            fout = new FileOutputStream(fileoutput);
            newbmp.compress(Bitmap.CompressFormat.JPEG,100,fout);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fout.flush();
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return newbmp;
    }

    public void cropImg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 700);
        intent.putExtra("outputY", 700);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImage);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_CAMERA_CROP_PATH_RESULT);
    }
}