package com.fpd.slamdunk.bussiness.selectimg;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.fpd.slamdunk.CommenActivity;
import com.fpd.slamdunk.R;
import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by t450s on 2016/6/10.
 */
public class SelectPhotoActivity extends CommenActivity {
    private static final int RESULT_CAMERA_ONLY = 200;
    private static final int RESULT_CAMERA_CROP_PATH_RESULT = 300;
    private static final int RESULT_CAMERA_PICK = 400;
    private ImageView imageView;
    private ButtonFlat take_photo;
    private ButtonFlat pick_photo;
    private ButtonRectangle photo_done;
    private Uri imageUri;
    private Uri cropImage;
    private File  fileoutput;
    private Button backButton;
    private TextView topTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.select_photo);

        imageView = (ImageView) findViewById(R.id.select_photo_img);
        take_photo = (ButtonFlat) findViewById(R.id.select_photo_take);
        pick_photo = (ButtonFlat) findViewById(R.id.select_photo_pick);
        photo_done = (ButtonRectangle) findViewById(R.id.select_photo_done);
        backButton = (Button) findViewById(R.id.back_button);
        topTitle = (TextView) findViewById(R.id.top_title);
        topTitle.setText("图片选择");
        String dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();

        File file = new File(Environment.getExternalStorageDirectory(),"temp.jpg");
        File filex = new File(Environment.getExternalStorageDirectory(),"croptemp.jpg");
        imageUri = Uri.fromFile(file);
        cropImage = Uri.fromFile(filex);
        setClick();
    }

    private void setClick(){
        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        pick_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickPhoto();
            }
        });
        photo_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("file",fileoutput.getAbsolutePath());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void takePhoto(){
        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_CAMERA_ONLY);
    }

    private void pickPhoto(){
        Intent intent = null;
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_CAMERA_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK)
            return;
        switch (requestCode){
            case RESULT_CAMERA_ONLY:
                cropImg(imageUri);
                break;
            case RESULT_CAMERA_PICK:
                cropImg(data.getData());
                break;
            case RESULT_CAMERA_CROP_PATH_RESULT:
                try {
                    Bitmap bt = BitmapFactory.decodeStream(getContentResolver().openInputStream(cropImage));
                    imageView.setImageBitmap(zoomBitmap(compressImage(bt),980,550));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        fileoutput = new File(Environment.getExternalStorageDirectory(),"x.jpg");
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

    private Bitmap compressImage(Bitmap image) {
        if (image !=null) {
            ByteArrayOutputStream target_baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, target_baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (target_baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                target_baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, target_baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(target_baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
            image.recycle();
            return bitmap;
        }
        return null;
    }


    public void cropImg(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        intent.putExtra("outputX", 980);
        intent.putExtra("outputY", 550);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropImage);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, RESULT_CAMERA_CROP_PATH_RESULT);
    }
}
