package com.crazyshopping.ui.home;


import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.crazyshopping.R;
import com.crazyshopping.base.BaseFragment;
import com.crazyshopping.bean.home.HomeBean;
import com.crazyshopping.interfaces.home.IHomeContent;
import com.crazyshopping.presenter.home.HomeContentPresenter;
import com.crazyshopping.ui.cart.DetailBrandActivity;
import com.crazyshopping.ui.cart.DetailGoodActivity;
import com.crazyshopping.ui.home.adapter.HomeListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<IHomeContent.HomeContentPresenter> implements IHomeContent.HomeContentView {

    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private List<HomeBean.HomeListBean> list;
    private HomeListAdapter homeListAdapter;

    @Override
    protected void initData() {
        presenter.getHomeContentBean();
    }

    @Override
    protected IHomeContent.HomeContentPresenter iitPresenter() {
        return new HomeContentPresenter();
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(list, context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        homeListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currenType;
                switch (type) {
                    case HomeBean.ITEM_TYPE_BANNER:
                    case HomeBean.ITEM_TYPE_TITLE:
                    case HomeBean.ITEM_TYPE_HOT:
                    case HomeBean.ITEM_TYPE_TITLETOP:
                    case HomeBean.ITEM_TYPE_TOPIC:
                    case HomeBean.ITEM_TYPE_TAB:
                        return 2;
                    case HomeBean.ITEM_TYPE_BRAND:
                    case HomeBean.ITEM_TYPE_NEW:
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        return 1;
                }
                return 0;
            }
        });
        rvHome.setLayoutManager(gridLayoutManager);
        homeListAdapter.bindToRecyclerView(rvHome);
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int type = list.get(position).currenType;
                Intent intent = new Intent();
                switch (type) {
                    case HomeBean.ITEM_TYPE_BANNER:
                        break;
                    case HomeBean.ITEM_TYPE_BRAND:
                        HomeBean.DataBean.BrandListBean brandBean = (HomeBean.DataBean.BrandListBean) list.get(position).data;
                        intent.putExtra("id", brandBean.getId());
                        intent.setClass(context, DetailBrandActivity.class);
                        startActivity(intent);
                        break;
                    case HomeBean.ITEM_TYPE_NEW:
                        HomeBean.DataBean.NewGoodsListBean newBean = (HomeBean.DataBean.NewGoodsListBean) list.get(position).data;
                        intent.putExtra("id", newBean.getId());
                        intent.setClass(context, DetailGoodActivity.class);
                        startActivity(intent);
                        break;
                    case HomeBean.ITEM_TYPE_HOT:
                        HomeBean.DataBean.HotGoodsListBean bean = (HomeBean.DataBean.HotGoodsListBean) list.get(position).data;
                        intent.putExtra("id", bean.getId());
                        intent.setClass(context, DetailGoodActivity.class);
                        startActivity(intent);
                        break;
                    case HomeBean.ITEM_TYPE_TITLE:
                        break;
                    case HomeBean.ITEM_TYPE_TOPIC:
                        break;
                    case HomeBean.ITEM_TYPE_CATEGORY:
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void getHomeContentReturn(List<HomeBean.HomeListBean> result) {
        Log.d(TAG, "getHomeContentReturn: " + result);
        list.addAll(result);
        homeListAdapter.notifyDataSetChanged();
    }

    private static final String TAG = "HomeFragment";
}
