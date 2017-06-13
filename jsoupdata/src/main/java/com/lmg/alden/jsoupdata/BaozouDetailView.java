package com.lmg.alden.jsoupdata;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Alden on 2017/3/9.
 */

public class BaozouDetailView extends LinearLayout {
    private Context context;
    public BaozouDetailView(Context context) {
        super(context);
        this.context = context;
    }

    public BaozouDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public BaozouDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaozouDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }
    public void addTextView(String text,String color,float fontSize){
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,0));

        TextView textView  = new TextView(context);
        textView.setText("\t\t"+text);
        textView.setBackgroundColor(Color.GRAY);
        textView.setTextColor(Color.parseColor(color));
        textView.setTextSize(fontSize);
        addView(textView);
    }
    public void addTextView(String text,String color){
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,0));

        TextView textView  = new TextView(context);
        textView.setText("\t\t"+text);
        textView.setTextColor(Color.parseColor(color));
        textView.setTextSize(18);
        addView(textView);
    }
    public void addTextView(String text){
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,0));

        TextView textView  = new TextView(context);
        textView.setText("\t\t"+text);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        addView(textView);
    }
    public void addImageView(String imgUrl){
        ImageView imageView = new ImageView(context);
        imageView.setBackgroundColor(Color.GRAY);
        Glide.with(context).load(imgUrl).placeholder(R.mipmap.ic_launcher).into(imageView);
        addView(imageView);
    }


}
