package com.jason.rxjavademo.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.jason.rxjavademo.R;
import com.jason.rxjavademo.adapter.CommonAdapter;
import com.jason.rxjavademo.adapter.ViewHolder;
import com.jason.rxjavademo.domian.AppInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;
import rx.functions.Func2;

public class MapScanActivity extends AppCompatActivity {

    @Bind(R.id.list)
    ListView listview;

    private BaseAdapter mAdapter;
    private ArrayList<AppInfo> mData;
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_scan);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getApplicationList();

        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<AppInfo>(this, mData, R.layout.item_app_info) {
            @Override
            public void convert(ViewHolder helper, AppInfo item, int position) {
                helper.setText(R.id.app_name, item.getName());
                helper.setImageDrawable(R.id.app_icon, item.getIconDra());
            }
        };
        listview.setAdapter(mAdapter);

        Observable.from(appInfos)
                .scan(new Func2<AppInfo, AppInfo, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo appInfo, AppInfo appInfo2) {
                        if (appInfo.getName().length() > appInfo2.getName().length()) {
                            return appInfo;
                        } else {
                            return appInfo2;
                        }
                    }
                })
                .distinct()
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mData.add(appInfo);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @OnClick({
            R.id.fab
    })
    public void onClick(View view) {
        mData.clear();
        mAdapter.notifyDataSetChanged();

        Observable.from(appInfos)
                .map(new Func1<AppInfo, AppInfo>() {
                    @Override
                    public AppInfo call(AppInfo appInfo) {
                        String name = appInfo.getName();
                        appInfo.setName(name.toUpperCase());
                        return appInfo;
                    }
                })
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppInfo o) {
                        mData.add(o);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void getApplicationList() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> packageinfos = pm.getInstalledPackages(0);

        for (PackageInfo pak : packageinfos) {
            AppInfo appInfo = new AppInfo();
            appInfo.setName((String) pm.getApplicationLabel(pak.applicationInfo));
            appInfo.setIconDra(pm.getApplicationIcon(pak.applicationInfo));
            appInfos.add(appInfo);
        }
    }



}
