package com.example.pc.updata;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pc.updata.bmob.DaoManager;
import com.example.pc.updata.bmob.NoteFrament2;
import com.example.pc.updata.tools.MyBaseAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.leefeng.promptlibrary.PromptButton;
import me.leefeng.promptlibrary.PromptButtonListener;
import me.leefeng.promptlibrary.PromptDialog;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.calendarView)
    com.haibin.calendarview.CalendarView calendarView;

    @BindView(R.id.mylist)
    ListView mylist;

    @BindView(R.id.btn_add)
    ImageView btn_add;

    @BindView(R.id.im_menu)
    ImageView im_menu;
    boolean ismenu= false;
    MyBaseAdapter myBaseAdapter;
    List<NoteFrament2> noteFrament2s;

    PromptDialog promptDialog ;

    @BindView(R.id.tv_tj)
    TextView tv_tj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        promptDialog=new PromptDialog(this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, FabuActivity.class);
                startActivity(intent);
            }
        });
        tv_tj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main2Activity.this, Detail1Activity.class);
                startActivity(intent);
            }
        });
        im_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(!ismenu){
                        ismenu=true;
                        calendarView.setVisibility(View.GONE);
                        mylist.setVisibility(View.VISIBLE);
                    }else {
                        ismenu=false;
                        calendarView.setVisibility(View.VISIBLE);
                        mylist.setVisibility(View.GONE);
                    }
            }
        });
        noteFrament2s = DaoManager.getInstance(this).slectNoteFrament3();
        myBaseAdapter = new MyBaseAdapter<NoteFrament2>(Main2Activity.this, R.layout.item_jijin_top4, noteFrament2s) {
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
                Glide.with( Main2Activity.this)
                        .load(noteFrament.getImg())
                        .asBitmap()
                        .into(im_content);

                ll_grop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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

                        Intent intent = new Intent(Main2Activity.this, EditBijiActivity.class);
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
                                DaoManager.getInstance(Main2Activity.this).delNoteFrament2(noteFrament);
                                noteFrament2s = DaoManager.getInstance(Main2Activity.this).slectNoteFrament3();
                                myBaseAdapter.setList(noteFrament2s);
                                myBaseAdapter.notifyDataSetChanged();

                            }
                        }));
                    }
                });

            }
        };

        mylist.setAdapter(myBaseAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteFrament2s = DaoManager.getInstance(Main2Activity.this).slectNoteFrament3();
        myBaseAdapter.setList(noteFrament2s);
        myBaseAdapter.notifyDataSetChanged();
    }
}
