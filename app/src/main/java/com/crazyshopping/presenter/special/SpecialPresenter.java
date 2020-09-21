package com.crazyshopping.presenter.special;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.special.SpecialBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.special.ISpecial;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class SpecialPresenter extends BasePresenter<ISpecial.SpecialView> implements ISpecial.SpecialPresenter {
    @Override
    public void getSpecialBean() {
        addSubscribe(HttpManager.getInstance().getShopApi().getSpecialList()
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<SpecialBean>(mView) {
            @Override
            public void onNext(SpecialBean specialBean) {
                mView.getSpeciaListReturn(specialBean);
            }
        }));
    }
}
