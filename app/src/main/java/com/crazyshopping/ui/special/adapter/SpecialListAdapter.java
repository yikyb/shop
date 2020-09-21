package com.crazyshopping.ui.special.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazyshopping.R;
import com.crazyshopping.base.BaseAdapter;
import com.crazyshopping.bean.special.SpecialBean;

import java.util.List;

public class SpecialListAdapter extends BaseAdapter {
    public SpecialListAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_special_adapter;
    }

    @Override
    protected void bindData(BaseViewHolder vh, Object data, int position) {
        SpecialBean.DataBeanX.DataBean bean = (SpecialBean.DataBeanX.DataBean) data;
        ImageView img = (ImageView) vh.getViewById(R.id.img_special);
        TextView tvTitle = (TextView) vh.getViewById(R.id.txt_title_special);
        TextView tvSubTitle = (TextView) vh.getViewById(R.id.txt_subTitle_special);
        TextView tvSprice = (TextView) vh.getViewById(R.id.txt_price_special);
        Glide.with(context).load(bean.getScene_pic_url()).into(img);
        tvTitle.setText(bean.getTitle());
        tvSubTitle.setText(bean.getSubtitle());
        tvSprice.setText(bean.getPrice_info()+"元起");
    }
}
