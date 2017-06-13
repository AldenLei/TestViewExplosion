package com.lmg.alden.testluncher;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.btn_back);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                /*Intent mIntent =  MainActivity.this.getPackageManager().getLaunchIntentForPackage("com.android.launcher");
                startActivity(mIntent);*/
                doStartApplicationWithPackageName("com.android.launcher2");
                finish();
            }
        });
    }
    public String getLauncherPackageName(Context context)
    {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if(res.activityInfo == null)
        {
            return "";
        }
        //如果是不同桌面主题，可能会出现某些问题，这部分暂未处理
        if(res.activityInfo.packageName.equals("android"))
        {
            return "";
        }else
        {
            Log.e("name",res.activityInfo.packageName);

            return res.activityInfo.packageName;
        }
    }
    private void doStartApplicationWithPackageName(String targetPackge) {


        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        //resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo=null;

        if(resolveinfoList.iterator().hasNext()) {
            Log.e("TAG","true"+resolveinfoList.size()+resolveinfoList.toString());

            for (int i=0;i<resolveinfoList.size();i++){
                Log.e("TAG","detail=="+resolveinfoList.get(i).toString());
                if(resolveinfoList.get(i).toString().contains(targetPackge)){
                    resolveinfo = resolveinfoList.get(i);
                    Log.e("TAG","=="+resolveinfo.toString());
                    //break;
                }
            }
        }else{
            Log.e("TAG","false");
            return;
        }
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }
}
