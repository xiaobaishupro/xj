package com.example.pc.updata.Baes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;

/**
 * Created by pc on 2018/3/20.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // StatusBarUtil.setTranslucentForImageView(this,null);

    }

    public void showMessage(String mess){
        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
    }
}
