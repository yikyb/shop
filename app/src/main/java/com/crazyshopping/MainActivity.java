package com.crazyshopping;


import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.crazyshopping.base.BaseActivity;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.ui.classify.ClassFragment;
import com.crazyshopping.ui.home.HomeFragment;
import com.crazyshopping.ui.own.OwnFragment;
import com.crazyshopping.ui.cart.ShopCarFragment;
import com.crazyshopping.ui.special.SpecialFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tab_main)
    TabLayout tabMain;

    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private SpecialFragment projectFragment;
    private OwnFragment ownFragment;
    private ShopCarFragment shopCarFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        fragmentManager.beginTransaction()
                .add(R.id.ll_mian, homeFragment)
                .show(homeFragment)
                .commit();
    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        classFragment = new ClassFragment();
        projectFragment = new SpecialFragment();
        shopCarFragment = new ShopCarFragment();
        ownFragment = new OwnFragment();
        tabMain.addTab(tabMain.newTab().setText("首页").setIcon(R.drawable.select_home));
        tabMain.addTab(tabMain.newTab().setText("专题").setIcon(R.drawable.select_special));
        tabMain.addTab(tabMain.newTab().setText("分类").setIcon(R.drawable.select_class));
        tabMain.addTab(tabMain.newTab().setText("购物车").setIcon(R.drawable.select_special));
        tabMain.addTab(tabMain.newTab().setText("我的").setIcon(R.drawable.select_own));
        initData();
        tabMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initFragment(int position) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                if (!homeFragment.isAdded()) {
                    beginTransaction.add(R.id.ll_mian, homeFragment).hide(classFragment).hide(projectFragment).hide(ownFragment).hide(shopCarFragment);
                }
                beginTransaction.show(homeFragment).hide(classFragment).hide(projectFragment).hide(ownFragment).hide(shopCarFragment).commit();
                break;
            case 1:
                if (!projectFragment.isAdded()) {
                    beginTransaction.add(R.id.ll_mian, projectFragment).hide(classFragment).hide(homeFragment).hide(ownFragment).hide(shopCarFragment);
                }
                beginTransaction.show(projectFragment).hide(classFragment).hide(homeFragment).hide(ownFragment).hide(shopCarFragment).commit();
                break;
            case 2:
                if (!classFragment.isAdded()) {
                    beginTransaction.add(R.id.ll_mian, classFragment).hide(homeFragment).hide(projectFragment).hide(ownFragment).hide(shopCarFragment);
                }
                beginTransaction.show(classFragment).hide(homeFragment).hide(projectFragment).hide(ownFragment).hide(shopCarFragment).commit();
                break;

            case 3:
                if (!shopCarFragment.isAdded()) {
                    beginTransaction.add(R.id.ll_mian, shopCarFragment).hide(classFragment).hide(homeFragment).hide(ownFragment).hide(projectFragment);
                }
                beginTransaction.show(shopCarFragment).hide(classFragment).hide(homeFragment).hide(ownFragment).commit();
                break;
            case 4:
                if (!ownFragment.isAdded()) {
                    beginTransaction.add(R.id.ll_mian, ownFragment).hide(classFragment).hide(projectFragment).hide(homeFragment).hide(shopCarFragment);
                }
                beginTransaction.show(ownFragment).hide(classFragment).hide(projectFragment).hide(homeFragment).hide(shopCarFragment).commit();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}