package com.lmg.alden.testviewexplosion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //测试git
    private Button button;
    private ImageView imgError,imgSuccess;
    ExplosionField explosionField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        explosionField = ExplosionField.attach2Window(this);

    }

    private void initView() {
        button = (Button)findViewById(R.id.btn_hello);
        imgError = (ImageView)findViewById(R.id.img_error);
        imgSuccess = (ImageView)findViewById(R.id.img_success);
        button.setOnClickListener(this);
        imgError.setOnClickListener(this);
        imgSuccess.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_hello:
                explosionField.explode(button);
                button.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_error:
                explosionField.explode(imgError);
                imgError.setVisibility(View.INVISIBLE);
                break;
            case R.id.img_success:
                explosionField.explode(imgSuccess);
                imgSuccess.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
