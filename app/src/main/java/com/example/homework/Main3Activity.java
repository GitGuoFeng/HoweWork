package com.example.homework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private ProgressBar progressBar;
    private Handler mHandler;
    private int mProgressStatus=0;
    private boolean isShowDelete=false;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);

            progressBar = (ProgressBar)findViewById(R.id.progressBar1);
            final ImageView imageView1 = (ImageView)findViewById(R.id.imageView1);
            final ImageView imageView2 = (ImageView)findViewById(R.id.imageView2);
            imageView1.setVisibility(View.GONE);
            imageView2.setVisibility(View.GONE);
            mHandler = new Handler(){
                @Override
                public void handleMessage(Message msg){
                    if (msg.what==0x111){
                        progressBar.setProgress(mProgressStatus);//更新进度
                    }else{
                        progressBar.setVisibility(View.GONE);//进度条不显示 并且不占用空间
                        Toast.makeText(Main3Activity.this,"加载完成" , Toast.LENGTH_SHORT).show();
                        imageView1.setVisibility(View.VISIBLE);
                        imageView2.setVisibility(View.VISIBLE);
                    }
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        mProgressStatus = doWork();//耗时操作完成的百分比
                        Message m = new Message();
                        if(mProgressStatus<100){
                            m.what=0x111;
                            mHandler.sendMessage(m);//发送消息
                        }else{
                            m.what=0x110;
                            mHandler.sendMessage(m);//发送消息
                            break;
                        }
                    }
                }
                private int doWork(){
                    mProgressStatus+= Math.random()*10;//改变完成进度
                    try{
                        Thread.sleep(200);//线程休眠200毫秒
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    return mProgressStatus;
                }
            }).start();//开启一个线程
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(Main3Activity.this).create();
                    alertDialog.setIcon(R.drawable.ic_launcher_background);
                    alertDialog.setTitle("删除？");//设置对话框的标题
                    alertDialog.setMessage("真的要删除了嘛");//显示要显示的内容
                    //添加取消按钮
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "手滑", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    //添加确定按钮
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            linearLayout.removeView(imageView1);
                        }
                    });
                    alertDialog.show();
                    return true;
                }
            });
        imageView2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(Main3Activity.this).create();
                alertDialog.setIcon(R.drawable.ic_launcher_background);
                alertDialog.setTitle("删除？");//设置对话框的标题
                alertDialog.setMessage("真的要删除了嘛");//显示要显示的内容
                //添加取消按钮
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "手滑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                //添加确定按钮
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        linearLayout.removeView(imageView2);
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

}
