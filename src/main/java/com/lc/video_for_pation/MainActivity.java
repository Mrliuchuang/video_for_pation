package com.lc.video_for_pation;


import android.app.Activity;
import android.content.ContentResolver;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.lc.video_for_pation.Utils.Get;


import java.io.FileNotFoundException;

import static android.media.MediaMetadataRetriever.OPTION_PREVIOUS_SYNC;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private VideoView videoView;
    Uri uri;
    String path;
    TextView pathtv;
    Get get;
    ImageView local_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image11);
        local_img = findViewById(R.id.local_img);
        pathtv = findViewById(R.id.path);
        get = new Get();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void video_get_zhen(View view) {
        //获取path路径下视频的某帧
        //String mp4Path = Environment.getExternalStorageDirectory().getPath() + "/qwwz.mp4";
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        //获取第一帧图像的bitmap对象 单位是微秒
        Bitmap bitmap = mmr.getFrameAtTime((long) (4 * 1000), OPTION_PREVIOUS_SYNC);
        imageView.setImageBitmap(bitmap);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                    path = uri.getPath();
                    pathtv.setText(path);
                    Toast.makeText(this, path + "11111", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后

                    path = get.getPath(this, uri);
                    pathtv.setText(path);
                    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                } else {//4.4以下下系统调用方法
                    path = get.getRealPathFromURI(uri);
                    pathtv.setText(path);
                    Toast.makeText(MainActivity.this, path + "222222", Toast.LENGTH_SHORT).show();
                }
                // 获取到路径并播放视频
                // File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
                // videoView.setVideoPath(file.getAbsolutePath()+"/WeiXin/wx_camera_1587652873008.mp4");
                videoView = findViewById(R.id.videoView);
                videoView.setVideoURI(uri);
                //实例化一个媒体控制器
                MediaController mediaController = new MediaController(this);
                videoView.setMediaController(mediaController);
                mediaController.setMediaPlayer(videoView);
            }


        }

        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                String img_url = uri.getPath();//这是本机的图片路径
                Log.e("dd", ":" + img_url);
                ContentResolver cr = this.getContentResolver();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                    local_img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Log.e("Exception", e.getMessage(), e);
                }
            }
        }

    }


    public void File_path(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // intent.setType("image/*");//选择图片
        //intent.setType(“audio/*”); //选择音频
        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        // intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 2);
    }


    public void local_picture(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        // intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 3);
    }
}