package com.lc.video_for_pation;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.lc.video_for_pation.Utils.Get;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    private VideoView videoView;
    String v_path;
    Uri muri;
    String path;
    TextView pathtv;
    Get get;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image11);
        pathtv = findViewById(R.id.path);
        get = new Get();
    }

    public void bt(View view) {
        Intent intent = new Intent();
        /* 开启Pictures画面Type设定为image */
        //intent.setType("image/*");
        // intent.setType("audio/*"); //选择音频
        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
        // intent.setType("video/*;image/*");//同时选择视频和图片
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
        /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);
        //给VideoView设置播放来源
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       // 选取图片的返回值
//        if (requestCode == 1) {
//            //
//            if (resultCode == RESULT_OK) {
//                muri = data.getData();
//                Cursor cursor = getContentResolver().query(muri, null, null,
//                        null, null);
//                cursor.moveToFirst();
//                String imgNo = cursor.getString(4); // 图片编号
//                v_path = cursor.getString(1); // 图片文件路径
//                String v_size = cursor.getString(2); // 图片大小
//                String v_name = cursor.getString(3); // 图片文件名
//                Log.e(":", "v_path=" + v_path);
//                Log.e(":", "v_size=" + v_size);
//                Log.e(":", v_name);
//                Log.e(":", "uri:" + muri);
//                Log.e(":", "number:" + imgNo);
//
//                videoView = (VideoView) findViewById(R.id.videoView);
//                File file = new File(String.valueOf(Environment.getExternalStorageDirectory()));
//                // videoView.setVideoPath(file.getAbsolutePath()+"/WeiXin/wx_camera_1587652873008.mp4");
//                videoView.setVideoURI(muri);
//                //实例化一个媒体控制器
//                MediaController mediaController = new MediaController(this);
//                videoView.setMediaController(mediaController);
//                mediaController.setMediaPlayer(videoView);
//            }
//        }

        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void yizhen(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("image/*");//选择图片
        //intent.setType(“audio/*”); //选择音频
        intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        // intent.setType("*/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);

    }

}

