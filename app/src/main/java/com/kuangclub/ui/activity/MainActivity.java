package com.kuangclub.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.kuangclub.R;
import com.kuangclub.ui.base.BaseActivity;
import com.kuangclub.ui.fragment.AccountFragment;
import com.kuangclub.ui.fragment.HomeFragment;
import com.kuangclub.ui.fragment.PoolFragment;
import com.kuangclub.ui.fragment.QuotationFragment;

/**
 * Created by Woodslake on 2018/7/27.
 */
public class MainActivity extends BaseActivity {
    private RadioGroup rgBottom;

    private Fragment homeFragment, poolFragment, quotationFragment, accountFragment;
    private Fragment curFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        homeFragment = new HomeFragment();
        poolFragment = new PoolFragment();
        quotationFragment = new QuotationFragment();
        accountFragment = new AccountFragment();
    }

    private void initView(){
        rgBottom = findViewById(R.id.rg_bottom);

        rgBottom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioCheck(checkedId);
            }
        });

        radioCheck(R.id.rb_home);
    }

    private void radioCheck(int checkedId){
        switch (checkedId){
            case R.id.rb_home:
                curFragment = homeFragment;
                break;
            case R.id.rb_pool:
                curFragment = poolFragment;
                break;
            case R.id.rb_quotation:
                curFragment = quotationFragment;
                break;
            case R.id.rb_account:
                curFragment = accountFragment;
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, curFragment);
        transaction.commit();
    }

}
