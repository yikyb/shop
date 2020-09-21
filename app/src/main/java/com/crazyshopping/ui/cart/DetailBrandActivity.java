package com.crazyshopping.ui.cart;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazyshopping.R;
import com.crazyshopping.base.BaseActivity;
import com.crazyshopping.bean.home.BrandDetailBean;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.presenter.cart.BrandPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBrandActivity extends BaseActivity<ICart.BrandPresenter> implements ICart.BrandView {
    @BindView(R.id.img_brand_detail)
    ImageView imgBrandDetail;
    @BindView(R.id.txt_name_brand_detail)
    TextView txtNameBrandDetail;
    @BindView(R.id.txt_des_brand_detail)
    TextView txtDesBrandDetail;

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        mPresenter.getBrandDetail(id);
    }

    @Override
    protected ICart.BrandPresenter initPresenter() {
        return new BrandPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_brand_detail;
    }

    @Override
    public void getBrandDetailReturn(BrandDetailBean result) {
        BrandDetailBean.DataBean.BrandBean brand = result.getData().getBrand();
        Glide.with(this).load(brand.getPic_url()).into(imgBrandDetail);
        txtNameBrandDetail.setText(brand.getName());
        txtDesBrandDetail.setText(brand.getSimple_desc());
    }

}
