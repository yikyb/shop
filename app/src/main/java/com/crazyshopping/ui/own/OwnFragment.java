package com.crazyshopping.ui.own;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.crazyshopping.R;
import com.crazyshopping.base.BaseFragment;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.ui.own.activity.LoginActivity;

import butterknife.BindView;

public class OwnFragment extends BaseFragment {
    @BindView(R.id.layout_userInfo)
    FrameLayout layoutLogin;
    @BindView(R.id.layout_own)
    ConstraintLayout layoutOwn;

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter iitPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        layoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });
    }

    private void showPop() {
        View loginView = LayoutInflater.from(context).inflate(R.layout.layout_pop_login, null);
        Button phoneLogin = loginView.findViewById(R.id.phone_login);
        PopupWindow popupWindow = new PopupWindow(loginView, 360, FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        setBackGroundAlaph(0.5f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlaph(1f);
            }
        });
        popupWindow.showAtLocation(layoutOwn, Gravity.CENTER,0,0);
        phoneLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, LoginActivity.class));
                popupWindow.dismiss();
            }
        });
    }

    private void setBackGroundAlaph(float v) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = v;
        window.setAttributes(attributes);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_own;
    }
}
