package com.wxxiaomi.ming.electricbicycle.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wxxiaomi.ming.electricbicycle.R;
import com.wxxiaomi.ming.electricbicycle.common.GlobalManager;
import com.wxxiaomi.ming.electricbicycle.common.rx.SampleProgressObserver;
import com.wxxiaomi.ming.electricbicycle.dao.db.UserService;
import com.wxxiaomi.ming.electricbicycle.support.aliyun.OssEngine;
import com.wxxiaomi.ming.electricbicycle.support.img.PhotoTakeUtil;
import com.wxxiaomi.ming.electricbicycle.support.common.myglide.ImgShower;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MyInfoEditActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView userHead;
    private PhotoTakeUtil util;
    private String tmpHeadUrl;
    private EditText et_nickname;
    private EditText et_description;
    private EditText et_city;

    String head = null;
    String name = null;
    String description = null;
    String city = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_edit);
        toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        toolbar.setTitle("编辑个人信息");
        et_nickname  = (EditText) findViewById(R.id.et_nickname);
        et_description = (EditText) findViewById(R.id.et_description);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userHead = (ImageView) findViewById(R.id.userHead);
        userHead.setOnClickListener(this);
        ImgShower.showHead(this,userHead, GlobalManager.getInstance().getUser().userCommonInfo.avatar);
        util = new PhotoTakeUtil(this);
        et_nickname.setText(GlobalManager.getInstance().getUser().userCommonInfo.nickname);
        et_description.setText(GlobalManager.getInstance().getUser().userCommonInfo.description);
        et_city = (EditText) findViewById(R.id.et_city);
        et_city.setText(GlobalManager.getInstance().getUser().userCommonInfo.city);
        et_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(GlobalManager.getInstance().getUser().userCommonInfo.city)){
                    city = editable.toString();
                }else{
                    city = null;
                }

            }
        });
        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(GlobalManager.getInstance().getUser().userCommonInfo.description)){
                    description = editable.toString();
                }else{
                    description = null;
                }
            }
        });
        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals(GlobalManager.getInstance().getUser().userCommonInfo.nickname)){
                    name = editable.toString();
                }else{
                    name = null;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_act_info_edit, menu);//加载menu文件到布局
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_sumbit:
                onSumbitBtnClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 提交按钮点击事件
     */
    private void onSumbitBtnClick() {

        if(tmpHeadUrl!=null){
            head = tmpHeadUrl;
        }
        if(head==null&&name==null&&description==null&&city==null){
            finish();
        }
        UserService.getInstance().updateUserInfo(name,head,description,city)
                .subscribeOn(Schedulers.io())
                .subscribe(new SampleProgressObserver<Integer>(this) {
                    @Override
                    public void onNext(Integer integer) {
                        finish();
                    }
                });
    }

    private void onHeadClick() {
        util.takePhotoCut()
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        OssEngine.getInstance().initOssEngine(MyInfoEditActivity.this.getApplicationContext());
                        return UserService.getInstance().upLoadImgToOss(strings.get(0));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tmpHeadUrl = s;
                        Glide.with(MyInfoEditActivity.this).load(s).into(userHead);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.userHead:
                onHeadClick();
                break;
        }
    }

//    class MyTextChangListener implements TextWatcher {
//
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable editable) {
//
//        }
//    }

}