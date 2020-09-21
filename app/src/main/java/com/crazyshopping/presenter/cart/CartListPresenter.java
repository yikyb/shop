package com.crazyshopping.presenter.cart;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.cart.CartBean;
import com.crazyshopping.bean.cart.DeleteCartBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class CartListPresenter extends BasePresenter<ICart.ICartView> implements ICart.ICartPresenter {
    @Override
    public void getCartList() {
        addSubscribe(HttpManager.getInstance().getShopApi().getCartList()
        .compose(RxUtils.<CartBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<CartBean>(mView) {
            @Override
            public void onNext(CartBean cartBean) {
                mView.getCartListReturn(cartBean);
            }
        }));
    }

    @Override
    public void deleteCartList(String productId) {
        addSubscribe(HttpManager.getInstance().getShopApi().cartDelete(productId)
        .compose(RxUtils.<DeleteCartBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<DeleteCartBean>(mView) {
            @Override
            public void onNext(DeleteCartBean deleteCartBean) {
                mView.deleteCartListReturn(deleteCartBean);
            }
        }));
    }
}
