package com.crazyshopping.interfaces.home;


import com.crazyshopping.bean.home.HomeBean;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.interfaces.IBaseView;

import java.util.List;

public interface IHomeContent {
    interface HomeContentView extends IBaseView {
        void getHomeContentReturn(List<HomeBean.HomeListBean> result);
    }
    interface HomeContentPresenter extends IBasePresenter<HomeContentView> {
        void getHomeContentBean();
    }
}
