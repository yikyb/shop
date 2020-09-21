package com.crazyshopping.ui.special;


import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyshopping.R;
import com.crazyshopping.base.BaseFragment;
import com.crazyshopping.bean.special.SpecialBean;
import com.crazyshopping.interfaces.special.ISpecial;
import com.crazyshopping.presenter.special.SpecialPresenter;
import com.crazyshopping.ui.special.adapter.SpecialListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SpecialFragment extends BaseFragment<ISpecial.SpecialPresenter> implements ISpecial.SpecialView {
    @BindView(R.id.rv_Special)
    RecyclerView rvSpecial;
    private List<SpecialBean.DataBeanX.DataBean> specialList;
    private SpecialListAdapter specialListAdapter;

    @Override
    protected void initData() {
        presenter.getSpecialBean();
    }

    @Override
    protected ISpecial.SpecialPresenter iitPresenter() {
        return new SpecialPresenter();
    }

    @Override
    protected void initView() {
        specialList = new ArrayList<>();
        specialListAdapter = new SpecialListAdapter(context, specialList);
        rvSpecial.setLayoutManager(new LinearLayoutManager(context));
        rvSpecial.setAdapter(specialListAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_special;
    }


    @Override
    public void getSpeciaListReturn(SpecialBean result) {
        Log.d(TAG, "getSpeciaListReturn: "+result.getData().toString());
        specialList.addAll(result.getData().getData());
        specialListAdapter.notifyDataSetChanged();
    }

    private static final String TAG = "SpecialFragment";
}
