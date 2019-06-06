package com.pfl.common.bottom_tab;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.Transformations;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pfl.common.R;

import java.util.ArrayList;
import java.util.List;

public class CustomizeTabLayout extends FrameLayout {
    private LinearLayout mTabLinearLayout;
    private Context mContext;
    private ArrayList<TabBean> mTabBeans = new ArrayList<>();
    private int mTabCount;
    private int mCurrentTab;
    private OnTabSelectListener mListener;
    private ViewPager mViewPager;

    public void setmListener(OnTabSelectListener mListener) {
        this.mListener = mListener;
    }

    public CustomizeTabLayout(Context context) {
        this(context, null, 0);
    }

    public CustomizeTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizeTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        mTabLinearLayout = new LinearLayout(context);
        addView(mTabLinearLayout);
    }

    //添加导航栏数据
    public void setTabDate(List<TabBean> tabBeans) {
        if (tabBeans == null || tabBeans.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }
        this.mTabBeans.clear();
        this.mTabBeans.addAll(tabBeans);
        notifyDataSetChanged();
    }

    //更新数据
    private void notifyDataSetChanged() {
        mTabLinearLayout.removeAllViews();
        this.mTabCount = mTabBeans.size();
        View tabView;
        for (int i = 0; i < mTabCount; i++) {
            tabView = View.inflate(mContext, R.layout.layout_coutomizetab_top, null);
            tabView.setTag(i);
            addTab(i, tabView);
        }
        setCurrentTab(0);
    }

    /**
     * 创建并添加tab
     */
    private void addTab(final int position, View tabView) {
        TextView tv_tab_title = tabView.findViewById(R.id.tv_tab_title);
        tv_tab_title.setText(mTabBeans.get(position).getTitle());
        ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
        int unSelectIcon = mTabBeans.get(position).getUnSelectIcon();
        String unSelectUrl = mTabBeans.get(position).getUnSelectUrl();
        String SelectUrl = mTabBeans.get(position).getSelectUrl();
        if (android.text.TextUtils.isEmpty(unSelectUrl) || android.text.TextUtils.isEmpty(SelectUrl)) {
            iv_tab_icon.setImageResource(unSelectIcon);
        } else {
            Glide.with(mContext)
                    .load(unSelectUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(unSelectIcon)
                    .into(iv_tab_icon);
        }
        tabView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {
                    setCurrentTab(position);
                    if (mListener != null) {
                        mListener.onTabSelect(position);
                        mViewPager.setCurrentItem(position);
                    }
                } else {
                    if (mListener != null) {
                        mListener.onTabReselect(position);
                    }
                }
            }
        });
        LinearLayout.LayoutParams lp_tab = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        mTabLinearLayout.addView(tabView, position, lp_tab);
    }

    public void setCurrentTab(int currentTab) {
        this.mCurrentTab = currentTab;
        updateTabSelection(currentTab);
        if(null != mViewPager){
            mViewPager.setCurrentItem(currentTab);
        }
    }

    //更新tab
    private void updateTabSelection(int position) {
        for (int i = 0; i < mTabCount; ++i) {
            View tabView = mTabLinearLayout.getChildAt(i);
            boolean isSelect = i == position;
            TabBean tabBean = mTabBeans.get(i);
            TextView tab_title = tabView.findViewById(R.id.tv_tab_title);
            tab_title.setTextColor(isSelect ? tabBean.getSelectTextColor() : tabBean.getUnSelectTextColor());
            ImageView iv_tab_icon = tabView.findViewById(R.id.iv_tab_icon);
            if (TextUtils.isEmpty(tabBean.getSelectUrl()) || TextUtils.isEmpty(tabBean.getUnSelectUrl())) {
                iv_tab_icon.setImageResource(isSelect ? tabBean.getSelectIcon() : tabBean.getUnSelectIcon());
            } else {
                Glide.with(mContext)
                        .load(isSelect ? tabBean.getSelectUrl() : tabBean.getUnSelectUrl())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(isSelect ? tabBean.getSelectIcon() : tabBean.getUnSelectIcon())
                        .into(iv_tab_icon);
            }
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        if (null == viewPager) {
            throw new RuntimeException("ViewPager must is not null");
        }
        this.mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(new TabOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setCurrentTab(position);
            }
        });
    }

    public interface OnTabSelectListener {
        void onTabSelect(int position);

        void onTabReselect(int position);
    }

    static class TabOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}