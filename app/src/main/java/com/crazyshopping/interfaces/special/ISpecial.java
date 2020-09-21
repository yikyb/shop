package com.crazyshopping.interfaces.special;

import com.crazyshopping.bean.special.SpecialBean;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.interfaces.IBaseView;

public interface ISpecial {
    interface SpecialView extends IBaseView{
        void getSpeciaListReturn(SpecialBean result);
    }
    interface SpecialPresenter extends IBasePresenter<SpecialView>{
        void getSpecialBean();
    }
}
