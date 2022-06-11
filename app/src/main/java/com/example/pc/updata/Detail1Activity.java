package com.example.pc.updata;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.pc.updata.Baes.BaseActivity;
import com.example.pc.updata.bmob.DaoManager;
import com.example.pc.updata.bmob.NoteFrament2;
import com.example.pc.updata.tools.MyBaseAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

public class Detail1Activity extends BaseActivity {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_data)
    TextView tv_data;
    @BindView(R.id.btn_photo2)
    TextView btn_photo2;
    @BindView(R.id.tv_data2)
    TextView tv_data2;
    @BindView(R.id.btn_photo3)
    TextView btn_photo3;

    @BindView(R.id.btn_up)
    TextView btn_up;

    @BindView(R.id.mylist)
    ListView mylist;
    List<NoteFrament2> noteFrament2s;
     PromptDialog promptDialog;
    MyBaseAdapter myBaseAdapter;
    @BindView(R.id.pie_chart)
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHobbyOptionPicker(tv_data,1);
            }
        });
        btn_photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHobbyOptionPicker(tv_data2,2);
            }
        });
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (date1==null){
                    showMessage("请输入开始时间");
                    return;
                }
                if (date2==null){
                    showMessage("请输入结束时间");
                    return;
                }
                noteFrament2s = DaoManager.getInstance(Detail1Activity.this).slectNoteFrament3(date1,date2);
                myBaseAdapter.setList(noteFrament2s);
                myBaseAdapter.notifyDataSetChanged();
            }
        });

        noteFrament2s = DaoManager.getInstance(this).slectNoteFrament3();
        myBaseAdapter = new MyBaseAdapter<NoteFrament2>(Detail1Activity.this, R.layout.item_jijin_top4, noteFrament2s) {
            @Override
            protected void initialData(int position, View converView, ViewGroup arg2) {
                final NoteFrament2 noteFrament = getList().get(position);

                TextView tv_user_name = (TextView) converView.findViewById(R.id.tv_name);
                final ImageView im_content = (ImageView) converView.findViewById(R.id.im_content);
                final ImageView im_del = (ImageView) converView.findViewById(R.id.im_del);

                TextView tv_time = (TextView) converView.findViewById(R.id.tv_time);

                RelativeLayout ll_grop = (RelativeLayout) converView.findViewById(R.id.ll_grop);




                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time1 = sdf.format(noteFrament.getDate());
                tv_time.setText(time1);
                tv_user_name.setText(noteFrament.getTitel()+"");
                Glide.with( Detail1Activity.this)
                        .load(noteFrament.getImg())
                        .asBitmap()
                        .into(im_content);

                ll_grop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                  /*      Intent intent = new Intent( Main2Activity.this, Deatil2Activity.class);
                        intent.putExtra("data",noteFrament);
                        startActivity(intent);*/
                        if (im_content.getVisibility()==View.GONE){
                            im_content.setVisibility(View.VISIBLE);
                        }else {
                            im_content.setVisibility(View.GONE);
                        }

                    }
                });
                ll_grop.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        Intent intent = new Intent(Detail1Activity.this, EditBijiActivity.class);
                        intent.putExtra("data",noteFrament);
                        startActivity(intent);

                        return true;
                    }
                });
                im_del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        promptDialog.showWarnAlert("你确定要删除吗？", new PromptButton("取消", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {

                            }
                        }), new PromptButton("确定", new PromptButtonListener() {
                            @Override
                            public void onClick(PromptButton button) {
                                DaoManager.getInstance(Detail1Activity.this).delNoteFrament2(noteFrament);
                                noteFrament2s = DaoManager.getInstance(Detail1Activity.this).slectNoteFrament3();
                                myBaseAdapter.setList(noteFrament2s);
                                myBaseAdapter.notifyDataSetChanged();

                            }
                        }));
                    }
                });

            }
        };
        setPieChartData();
        mylist.setAdapter(myBaseAdapter);
    }
    Date date1 =null;
    Date date2 =null;
    private void initHobbyOptionPicker(final TextView textView, final int type) {

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

                if (type==1){
                    date1=date;
                }else{
                    date2=date;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String time1 = sdf.format(date);
                textView.setText(time1);

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
    protected void onResume() {
        super.onResume();

    }
    private void setPieChartData() {

        ArrayList<String> xValues = new ArrayList<String>(); // xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>(); // yVals用来表示封装每个饼块的实际数据

        xValues.add("全部数据");
        float  quarterly = 20;
        xValues.add("当前数据");
        float   quarterly2 = 80;
        yValues.add(new Entry(quarterly, 1));
        yValues.add(new Entry(quarterly2, 2));
        PieDataSet dataSet = new PieDataSet( yValues, "");
        dataSet.setSliceSpace(5f);//设置饼块之间的间隔
        dataSet.setSelectionShift(5f);//设置饼块选中时偏离饼图中心的距离

        PieData pieData = new PieData(xValues,dataSet);
        // pieData.setValueFormatter(new DefaultValueFormatter(1,"次"));//自己修改的方法
        // pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.DKGRAY);
        pieChart.setData(pieData);
        pieChart.highlightValues(null);
        pieChart.setDescription(null);
        pieChart.invalidate();
    }
}
