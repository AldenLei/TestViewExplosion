package com.lmg.alden.jsoupdata;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txt;
    String strStyle;
    String title;
    BaozouDetailView baozouDetailView;
    private LinearLayout content_lv;
    private List<HashMap<String,String>> listData = new ArrayList<>();
    private LinkedHashMap<String,String> map;
    private LinearLayout.LayoutParams params;
    private int p= 0;
    private int img = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initviews();

    }

    private void initviews() {
        btn= (Button)findViewById(R.id.btn_click);
       // baozouDetailView = (BaozouDetailView)findViewById(R.id.baozou_lv);
        content_lv = (LinearLayout)findViewById(R.id.content_lv);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,25,0,25);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                           Document document = Jsoup.connect("http://baozouribao.com/documents/43669").get();
                           // Document document = Jsoup.connect("http://baozouribao.com/documents/43796").get();

                            //Document document = Jsoup.connect("http://baozouribao.com/documents/43694").get();
                            Element element = document.body();

                            Elements eleTitle = document.select("div.row.article-page-picture.pr");
                            strStyle = eleTitle.select("[style]").attr("style");
                            Pattern pattern = Pattern.compile("(http|https)(\\w|\\W)+?(jpg|png)");
                            Matcher matcher = pattern.matcher(strStyle);
                            if(matcher.find()) {
                                strStyle = matcher.group();
                            }
                            title = eleTitle.select(".pa").text();
                            final Element content = element.select("div.content.article-content").get(1);
                            int a = 0;

                            Elements elements = content.getAllElements();
                            map = new LinkedHashMap<String, String>();
                            for (Element element1:elements){

                                String nodeName = element1.nodeName();
                                String text=null;
                                if(nodeName.equals("p")){
                                    text = element1.text();
                                    map.put("p"+p,text);
                                    p++;
                                    listData.add(map);
                                }
                                else if(nodeName.equals("br")){
                                    text = "br";
                                }
                                else if(nodeName.equals("img")){
                                    if(!TextUtils.isEmpty(element1.attr("data-original"))){
                                        text = element1.attr("data-original");
                                    }else{
                                        text = element1.attr("src");
                                    }
                                    map.put("img"+img,text);
                                    img++;
                                    listData.add(map);

                                }
                                else if(nodeName.equals("a")){
                                    text = element1.attr("href");
                                    map.put("a",text);
                                }

                                Log.e("NODE NAME",nodeName+"=="+text);

                            }
                           // Log.e("TAG","==="+strStyle+"==="+title+"===\n"+content.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("LIST",listData.size()+"");
                                    Log.e("MAP",map.size()+"");
                                    Iterator iter = map.entrySet().iterator();

                                    while (iter.hasNext()) {
                                        Log.e("========","=====");
                                        Map.Entry entry = (Map.Entry) iter.next();
                                        String key = (String) entry.getKey();
                                        final String val = (String) entry.getValue();
                                        Log.e("KEY",key);
                                        Log.e("DATA",val);
                                        if(key.contains("p")){

                                            TextView textView  = new TextView(MainActivity.this);
                                            textView.setText(val);

                                            textView.setTextSize(18);
                                            content_lv.addView(textView);
                                        }
                                        else if(key.contains("img")){

                                            ImageView imageView = new ImageView(MainActivity.this);
                                            imageView.setPadding(0,0,0,0);
                                            imageView.setLayoutParams(params);
                                            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                                            Glide.with(MainActivity.this).load(val).placeholder(R.mipmap.ic_launcher).into(imageView);

                                            content_lv.addView(imageView);




                                        }
                                    }

                                }
                            });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }
    public  int dp2px(Context context, int dp)
    {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
