package com.example.pc.updata;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.pc.updata.Baes.BaseActivity;
import com.example.pc.updata.bmob.DaoManager;
import com.example.pc.updata.bmob.NoteFrament2;
import com.example.pc.updata.tools.UriUtils;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import me.leefeng.promptlibrary.PromptDialog;

public class FabuActivity extends BaseActivity {

  

        @BindView(R.id.btn_back)
        ImageView btnBack;
        @BindView(R.id.et_wupin)
        EditText et_wupin;
        @BindView(R.id.et_content)
        EditText et_content;

    
    
        @BindView(R.id.btn_photo)
        TextView btn_photo;
    @BindView(R.id.tv_data)
    TextView tv_data;




        @BindView(R.id.tv_titel)
        TextView tv_titel;

    @BindView(R.id.btn_up)
    TextView btn_up;



        @BindView(R.id.image2)
        ImageView image2;



        PromptDialog promptDialog;

        //1视频 2 图片 3 音乐

        int  PHOTO_CAMERA =10;
        int type=0;

    @BindView(R.id.btn_photo2)
    TextView btn_photo2;
    Date mdata=new Date();
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_fabu);
            ButterKnife.bind(this);




        
            promptDialog = new PromptDialog(this);
            type =  getIntent().getIntExtra("type",0);


            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time1 = sdf.format(mdata);
        tv_data.setText(time1);

        btn_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHobbyOptionPicker();
            }
        });
        btn_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String titel = et_wupin.getText().toString();
                    String content = et_content.getText().toString();
                    if (titel.equals("")){
                        Toast.makeText(getApplicationContext(), "请输入标题", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (content.equals("")){
                        Toast.makeText(getApplicationContext(), "请输入笔记", Toast.LENGTH_SHORT).show();
                        return;
                    }



                    if (imagePath.equals("")){
                        Toast.makeText(getApplicationContext(), "请上传图片", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    addNetImg(imagePath,content,titel);
                }
            });
            btn_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    new RTPermission.Builder()
                            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                                    , Manifest.permission.READ_EXTERNAL_STORAGE
                                    , Manifest.permission.CAMERA
                            )
                            .start(FabuActivity.this, new OnPermissionResultListener()
                            {
                                @Override
                                public void onAllGranted(String[] allPermissions)
                                {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                                    intent.setType("image/*");
                                    startActivityForResult(intent,
                                            PHOTO_CAMERA);
                                }

                                @Override
                                public void onDeined(String[] dinedPermissions)
                                {
                                    //有权限未获得使用权的回调
                                    showMessage("请授权!");
                                }
                            });


                }
            });


         
   }


   List<String > types= new ArrayList<>();

    String imagePath="";
    //初始化爱好选择器
    private void initHobbyOptionPicker() {

        Calendar startDate = Calendar.getInstance();
        startDate.set(1993, 0, 1);
        Calendar endDate = Calendar.getInstance();
        int year=endDate.get(Calendar.YEAR);  //2021
        int month=endDate.get(Calendar.MONTH);//0代表1月，最大为11月
        int day1=endDate.get(Calendar.DATE);  //1
        endDate.set(year, month, day1);
        TimePickerView timePickerView = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time1 = sdf.format(date);
                tv_data.setText(time1);
            }
        })

        .setCancelText("取消")//取消按钮文字
        .setSubmitText("确定")//确认按钮文字
        .setTitleText("请选择时间")//标题文字
        .setTitleSize(20)//标题文字大小
        .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
        .isCyclic(false)//是否循环滚动
        .setLineSpacingMultiplier(2.0f)
        .setTextColorCenter(Color.BLACK)//设置选中项的颜色
        .setTitleColor(Color.BLACK)//标题文字颜色
        .setSubmitColor(Color.BLUE)//确定按钮文字颜色
        .setCancelColor(Color.BLUE)//取消按钮文字颜色
        .setType(new boolean[]{true, true, true, true, true, false})
        .setRangDate(startDate, endDate)
        .build();
        timePickerView.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && null != data && requestCode == PHOTO_CAMERA) {
            Uri uri = data.getData();

            imagePath      = UriUtils.getPath(this, uri);
            Glide.with( this )
                    .load( Uri.fromFile( new File( imagePath ) ) )
                    .into( image2);
        }
    }
    public void addNetImg(String imgurl, final String conetnt, final String titel){
        promptDialog.showLoading("请稍后");
        NoteFrament2 noteFrament = new NoteFrament2();
        noteFrament.setContent(conetnt);
        noteFrament.setDate(mdata);
        noteFrament.setTitel(titel);
        noteFrament.setType(type);
        noteFrament.setImg(imgurl);
        showMessage("添加成功！");
        DaoManager.getInstance(FabuActivity.this).instNoteFrament2(noteFrament);
        promptDialog.dismiss();
        finish();
    }



}
