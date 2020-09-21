package com.crazyshopping.ui.own.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crazyshopping.R;
import com.crazyshopping.base.BaseActivity;
import com.crazyshopping.bean.own.LoginBean;
import com.crazyshopping.interfaces.own.ILogin;
import com.crazyshopping.presenter.own.LoginPresenter;
import com.crazyshopping.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<ILogin.LoginPresenter> implements ILogin.LoginView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initData() {

    }

    @Override
    protected ILogin.LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            mPresenter.getLoginBean(username, password);
        } else {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login;
    }

    @Override
    public void getLoginReturn(LoginBean result) {
        if (result.getData().getCode() == 200) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            SpUtils.getInstance().setValue("token", result.getData().getToken());
            finish();
        }
    }

}
