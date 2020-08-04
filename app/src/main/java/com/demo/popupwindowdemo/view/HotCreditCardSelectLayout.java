package com.demo.popupwindowdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.demo.popupwindowdemo.R;
import com.demo.popupwindowdemo.adapter.AdapterBase;
import com.demo.popupwindowdemo.databinding.LayoutHotCreditCardSelectBinding;
import com.demo.popupwindowdemo.databinding.LayoutPopwindowCustomViewBinding;
import com.demo.popupwindowdemo.domin.HotCreditCardPageData;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

/**
 * @author houyinghui
 * @date 18/11/2
 */
public class HotCreditCardSelectLayout extends LinearLayout {
    private static final int TYPE_MORE = 0;
    private static final int TYPE_BANK = 1;
    private static final int TYPE_TOPIC = 2;
    private static final int TYPE_LEVEL = 3;
    private static final int TYPE_FEE = 4;
    private LayoutHotCreditCardSelectBinding mBinding;
    private LayoutPopwindowCustomViewBinding moreItemBinding;
    private PopupWindow mBankPopupwindow;
    private PopupWindow mTopicPopupwindow;
    private PopupWindow mMorePopupwindow;
    private OnSelectClick mListener;
    private HotCreditCardPageData.Select mData;
    private CardSelectAdapter mBankAdapter;
    private CardSelectAdapter mTopicAdapter;

    private HashMap<String, String> mParams = new HashMap<>();

    public HotCreditCardSelectLayout(Context context) {
        this(context, null);
    }

    public HotCreditCardSelectLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HotCreditCardSelectLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_hot_credit_card_select, this, true);
        initView();
    }

    private void initView() {
        //此处设置默认值是因为用户可以不选择更多里边的选项，需要将默认值传给服务端
        mParams.put("level_id", "0");
        mParams.put("annual_fee", "0");
        mBinding.allBank.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBankPopupwindow != null && mBankPopupwindow.isShowing()) {
                    dismissPopWindow(mBankPopupwindow);
                } else {
                    if (mBankPopupwindow == null) {
                        mBankPopupwindow = initmPopupWindowView(mData.bank, TYPE_BANK);
                    }
                    showPopWindow(TYPE_BANK, v);
                }
            }
        });
        mBinding.allTopic.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopicPopupwindow != null && mTopicPopupwindow.isShowing()) {
                    dismissPopWindow(mTopicPopupwindow);
                } else {
                    if (mTopicPopupwindow == null) {
                        mTopicPopupwindow = initmPopupWindowView(mData.topic, TYPE_TOPIC);
                    }
                    showPopWindow(TYPE_TOPIC, v);
                }
            }
        });

        mBinding.more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (mMorePopupwindow != null && mMorePopupwindow.isShowing()) {
                    dismissPopWindow(mMorePopupwindow);
                } else {
                    if (mMorePopupwindow == null) {
                        mMorePopupwindow = initmPopupWindowView(mData);
                    }
                    showPopWindow(TYPE_MORE, v);
                }
            }
        });
    }

    public void showPopWindow(int type, final View view) {
        dismissAllPopWindow();
        String selectItemText = "";
        switch (type) {
            case TYPE_BANK:
                selectItemText = mBinding.allBank.getText().toString();
                mBinding.allBank.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBankPopupwindow.showAsDropDown(view);
                    }
                }, 50);
                setOpenState(mBinding.allBank, mBinding.bankImg);
                mBankAdapter.setSelectItem(selectItemText);
                break;
            case TYPE_TOPIC:
                selectItemText = mBinding.allTopic.getText().toString();
                mBinding.allTopic.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTopicPopupwindow.showAsDropDown(view);
                    }
                }, 50);
                setOpenState(mBinding.allTopic, mBinding.topicImg);
                mTopicAdapter.setSelectItem(selectItemText);
                break;
            case TYPE_MORE:
                setOpenState(mBinding.more, mBinding.moreImg);
                mBinding.more.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mMorePopupwindow.showAsDropDown(view);
                    }
                }, 50);
                String levelID = mParams.get("level_id");
                String annualID = mParams.get("annual_fee");
                try {
                    if (!TextUtils.equals(levelID, "0")) {
                        moreItemBinding.cardLevelContent.check(Integer.valueOf(mParams.get("level_id")));
                    }
                    if (!TextUtils.equals(annualID, "0")) {
                        moreItemBinding.cardAnnualFeeContent.check(Integer.valueOf(mParams.get("annual_fee")));
                    }
                } catch (NumberFormatException e) {

                }
                break;
            default:
                break;
        }
        if (mListener != null) {
            mListener.onSelectTitleClick();
        }
    }

    //关闭单个popWindow
    public void dismissPopWindow(PopupWindow popupWindow) {
        popupWindow.dismiss();
        if (mListener != null) {
            mListener.closeBackground();
        }
    }

    public void dismissAllPopWindow() {
        if (mBankPopupwindow != null && mBankPopupwindow.isShowing()) {
            mBankPopupwindow.dismiss();
        }
        if (mTopicPopupwindow != null && mTopicPopupwindow.isShowing()) {
            mTopicPopupwindow.dismiss();
        }
        if (mMorePopupwindow != null && mMorePopupwindow.isShowing()) {
            mMorePopupwindow.dismiss();
        }
    }

    //点击单个银行icon进入时候，银行筛选项标题需要更新为对应的银行
    public void setBankTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mBinding.allBank.setText(title);
        }
    }

    //设置打开下拉菜单时候筛选title的样式
    public void setOpenState(TextView textView, ImageView imageView) {
        textView.setTextColor(getResources().getColor(R.color.main_blue_color));
        imageView.setImageResource(R.drawable.img_small_arrow_up);
    }

    public void updateData(HotCreditCardPageData.Select data) {
        if (data == null) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        mData = data;
    }

    private PopupWindow initmPopupWindowView(final List<HotCreditCardPageData.SelectItem> list, final int type) {
        // 获取popwindow布局文件的视图
        View customView = LayoutInflater.from(getContext()).inflate(R.layout.layout_popwindow_list_view,
                null, false);
        MaxHeightListView listView = customView.findViewById(R.id.list_view);
        //填充数据
        if (type == TYPE_BANK) {
            mBankAdapter = new CardSelectAdapter(getContext());
            mBankAdapter.setList(list);
            listView.setAdapter(mBankAdapter);
        } else if (type == TYPE_TOPIC) {
            mTopicAdapter = new CardSelectAdapter(getContext());
            mTopicAdapter.setList(list);
            listView.setAdapter(mTopicAdapter);
        }

        final PopupWindow popupwindow = generatePopupWindow(customView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener == null) {
                    return;
                }
                if (type == TYPE_BANK) {
                    mParams.put("bank_id", String.valueOf(list.get(position).id));
                    mBinding.allBank.setText(list.get(position).value);
                    mListener.onItemClick(mParams);
                } else if (type == TYPE_TOPIC) {
                    mParams.put("topic_id", String.valueOf(list.get(position).id));
                    mBinding.allTopic.setText(list.get(position).value);
                    mListener.onItemClick(mParams);
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    dismissPopWindow(popupwindow);
                }
            }
        });
        return popupwindow;
    }

    //生成更多页面的Window
    private PopupWindow initmPopupWindowView(HotCreditCardPageData.Select data) {
        // 获取自定义布局文件pop.xml的视图
        moreItemBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_popwindow_custom_view,
                null, false);
        //填充数据
        moreItemBinding.cardLevel.setText(data.level_title);
        moreItemBinding.cardAnnualFee.setText(data.annual_fee_title);
        setMoreViewData(data.level, moreItemBinding.cardLevelContent);
        setMoreViewData(data.annual_fee, moreItemBinding.cardAnnualFeeContent);
        final PopupWindow popupwindow = generatePopupWindow(moreItemBinding.getRoot());
        moreItemBinding.confirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int levelId = moreItemBinding.cardLevelContent.getCheckedRadioButtonId();
                int annualFeeId = moreItemBinding.cardAnnualFeeContent.getCheckedRadioButtonId();
                if (levelId == -1) {
                    mParams.put("level_id", "0");
                } else {
                    mParams.put("level_id", String.valueOf(levelId));
                }
                if (annualFeeId == -1) {
                    mParams.put("annual_fee", "0");
                } else {
                    mParams.put("annual_fee", String.valueOf(annualFeeId));
                }

                if (mListener != null) {
                    mListener.onItemClick(mParams);
                }
                if (popupwindow != null && popupwindow.isShowing()) {
                    dismissPopWindow(popupwindow);
                }
            }
        });
        return popupwindow;
    }

    //根据view初始化popupWindow
    public PopupWindow generatePopupWindow(View customView) {
        final PopupWindow popupwindow = new PopupWindow(customView,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupwindow.setOutsideTouchable(false);
        popupwindow.setAnimationStyle(R.style.popup_animation);
        popupwindow.setFocusable(false);
        //弹出框消失，筛选条颜色和箭头归位
        popupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                resetSelectView();
            }
        });
        return popupwindow;
    }

    //生成更多页面的button组合，button两次点击可取消
    public void setMoreViewData(List<HotCreditCardPageData.SelectItem> list, final RadioGroup layout) {
        for (int i = 0; i < list.size(); i++) {
            HotCreditCardPageData.SelectItem item = list.get(i);
            final ToggleRadioButton button = new ToggleRadioButton(getContext());
            button.setText(item.value);
            button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            button.setTextColor(getResources().getColorStateList(R.color.selector_check_button_text_color));
            button.setGravity(Gravity.CENTER);
            button.setButtonDrawable(R.color.transparent);
            button.setBackground(getResources().getDrawable(R.drawable.selector_check_button_bg));
            button.setWidth(270);
            button.setHeight(84);

            if (i != 0) {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins(69, 0, 0, 0);
                button.setLayoutParams(params);
            }

            if (!TextUtils.isEmpty(item.id)) {
                try {
                    button.setId(Integer.valueOf(item.id));
                } catch (NumberFormatException e) {
                    //no op
                }
            }
            layout.addView(button);
        }
    }

    //恢复三个筛选栏目的颜色和箭头方向
    public void resetSelectView() {
        mBinding.allBank.setTextColor(getResources().getColor(R.color.load_txt_color_3));
        mBinding.bankImg.setImageResource(R.drawable.img_small_arrow_down);
        mBinding.allTopic.setTextColor(getResources().getColor(R.color.load_txt_color_3));
        mBinding.topicImg.setImageResource(R.drawable.img_small_arrow_down);
        mBinding.moreImg.setImageResource(R.drawable.img_small_arrow_down);
        if (moreItemBinding != null) {
            moreItemBinding.cardLevelContent.clearCheck();
            moreItemBinding.cardAnnualFeeContent.clearCheck();
        }

        if (!TextUtils.equals(mParams.get("level_id"), "0") || !TextUtils.equals(mParams.get("annual_fee"), "0")) {
            mBinding.more.setTextColor(getResources().getColor(R.color.main_blue_color));
        } else {
            mBinding.more.setTextColor(getResources().getColor(R.color.load_txt_color_3));
        }
    }

    private class CardSelectAdapter extends AdapterBase<HotCreditCardPageData.SelectItem> {
        private Context context;
        private String itemString;

        public CardSelectAdapter(Context context) {
            super(context);
            this.context = context;
        }

        public void setSelectItem(String text) {
            if (text == null) {
                return;
            }
            itemString = text;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if (convertView == null) {
                textView = new TextView(context);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                convertView = textView;
            } else {
                textView = (TextView) convertView;
            }
            HotCreditCardPageData.SelectItem data = mList.get(position);
            textView.setText(data.value);
            if (TextUtils.equals(data.value, itemString)) {
                textView.setTextColor(getResources().getColor(R.color.main_blue_color));
            } else {
                textView.setTextColor(getResources().getColor(R.color.load_txt_color_6));
            }
            return convertView;
        }
    }

    public void setOnSelectClickListener(OnSelectClick listener) {
        mListener = listener;
    }

    public interface OnSelectClick {
        void onSelectTitleClick();

        void onItemClick(HashMap<String, String> params);

        void closeBackground();
    }

}
