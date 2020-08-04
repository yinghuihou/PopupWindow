package com.demo.popupwindowdemo;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.demo.popupwindowdemo.domin.HotCreditCardPageData;
import com.demo.popupwindowdemo.view.HotCreditCardSelectLayout;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {

    HotCreditCardSelectLayout cardSelectLayout;

    //下拉菜单拉下来后的黑色透明背景
    private View maskLayer;

    //此默认标题代表从外界点击某个银行icon进来时候，直接第一个筛选框显示当前选中的银行名称
    private String mDefaultTitle = "全部银行";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        cardSelectLayout = findViewById(R.id.card_select);
        maskLayer = findViewById(R.id.mask_layer);

        //点击某个银行图标进入页面，筛选栏银行栏目要设为对应银行的名字
        cardSelectLayout.setBankTitle(mDefaultTitle);
        cardSelectLayout.updateData(getData());

        //点击背景收起Window
        maskLayer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        cardSelectLayout.dismissAllPopWindow();
                        dismissMaskLayer();
                        break;
                }
                return false;
            }
        });

        cardSelectLayout.setOnSelectClickListener(new HotCreditCardSelectLayout.OnSelectClick() {
            @Override
            public void onSelectTitleClick() {
                showMaskLayer();
            }

            @Override
            public void onItemClick(HashMap<String, String> map) {
                //此处拿到用户选择的数据，传给服务端获取想要的列表数据
                //。。。。。。
            }

            @Override
            public void closeBackground() {
                dismissMaskLayer();
            }

        });
    }


    private HotCreditCardPageData.Select getData() {
        HotCreditCardPageData.Select selectData = new HotCreditCardPageData.Select();
        selectData.bank_title = "全部银行";
        selectData.topic_title = "全部主题";
        selectData.level_title = "卡等级";
        selectData.annual_fee_title = "年费";

        ArrayList<HotCreditCardPageData.SelectItem> bankList = new ArrayList<>();
        bankList.add(new HotCreditCardPageData.SelectItem("0", "中信银行"));
        bankList.add(new HotCreditCardPageData.SelectItem("1", "建设银行"));
        bankList.add(new HotCreditCardPageData.SelectItem("2", "浦发银行"));
        bankList.add(new HotCreditCardPageData.SelectItem("3", "招商银行"));

        ArrayList<HotCreditCardPageData.SelectItem> topicList = new ArrayList<>();
        topicList.add(new HotCreditCardPageData.SelectItem("0", "皮卡丘联名卡"));
        topicList.add(new HotCreditCardPageData.SelectItem("1", "白金联名卡"));
        topicList.add(new HotCreditCardPageData.SelectItem("2", "京东联名卡"));
        topicList.add(new HotCreditCardPageData.SelectItem("3", "淘宝支付卡"));

        ArrayList<HotCreditCardPageData.SelectItem> levelList = new ArrayList<>();
        levelList.add(new HotCreditCardPageData.SelectItem("0", "普卡"));
        levelList.add(new HotCreditCardPageData.SelectItem("1", "金卡"));
        levelList.add(new HotCreditCardPageData.SelectItem("2", "白金卡"));

        ArrayList<HotCreditCardPageData.SelectItem> feeList = new ArrayList<>();
        feeList.add(new HotCreditCardPageData.SelectItem("0", "终身免年费"));
        feeList.add(new HotCreditCardPageData.SelectItem("1", "交易免年费"));

        selectData.bank = bankList;
        selectData.topic = topicList;
        selectData.level = levelList;
        selectData.annual_fee = feeList;

        return selectData;
    }

    //显示popupWindow弹出时候的背景遮罩层
    public void showMaskLayer() {
        if (maskLayer.getVisibility() == VISIBLE) {
            return;
        }
        Animation showAction = AnimationUtils.loadAnimation(this, R.anim.alpha_in);
        maskLayer.startAnimation(showAction);
        maskLayer.setVisibility(VISIBLE);
    }

    //取消遮罩层
    public void dismissMaskLayer() {
        if (maskLayer.getVisibility() == GONE) {
            return;
        }
        Animation hiddenAction = AnimationUtils.loadAnimation(this, R.anim.alpha_out);
        maskLayer.startAnimation(hiddenAction);
        maskLayer.setVisibility(GONE);
    }
}
