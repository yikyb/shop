package com.crazyshopping.interfaces.own;

import com.crazyshopping.bean.own.LoginBean;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.interfaces.IBaseView;

public interface ILogin {
    interface LoginView extends IBaseView{
        void getLoginReturn(LoginBean result);
    }
    interface LoginPresenter extends IBasePresenter<LoginView>{
        void getLoginBean(String username,String password);
    }
}
