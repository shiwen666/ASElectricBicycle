package com.wxxiaomi.ming.electricbicycle.support.rx;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.wxxiaomi.ming.common.net.ApiException;
import com.wxxiaomi.ming.electricbicycle.R;

/**
 * Created by 12262 on 2016/6/5.
 */
public abstract class ProgressObserver<T> extends MyObserver<T>{

    private ProgressDialog dialog;
    private AlertDialog msgDialog;
    private Context context;
    private String content;
    private boolean showMsg = true;


    public ProgressObserver(Context context) {
//        new ProgressDialog(context)
        dialog = new ProgressDialog(context);
        dialog = new ProgressDialog(context);
        dialog.setTitle("请等待");//设置标题
        dialog.setMessage("正在加载");
        dialog.setProgressStyle(R.style.MingDialog);
        this.context = context;
        msgDialog = new AlertDialog.Builder(context, R.style.MingDialog).setPositiveButton("确定", null).create();

    }

    @Override
    public void onStart() {
        dialog.show();
        super.onStart();
    }

    @Override
    protected void onError(ApiException ex) {
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
        showMsg = false;
        msgDialog.setMessage(ex.getDisplayMessage());
        msgDialog.show();
    }

    @Override
    public void onCompleted() {
        Log.i("wang","ProgressObserver-onCompleted()");
        if(showMsg)
        {
            dialog.dismiss();
        }

    }

    protected void showMsg(String content){
        showMsg = true;
        this.content = content;
        dialog.dismiss();
        msgDialog = new AlertDialog.Builder(context, R.style.MingDialog).setMessage(content).setPositiveButton("确定", null).create();
        msgDialog.show();
    }

    protected void closeDialog(){
        if(dialog.isShowing()){ dialog.dismiss();}
    }

}
