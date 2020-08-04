package com.demo.popupwindowdemo.domin;

import java.util.List;

public class HotCreditCardPageData extends BaseData<HotCreditCardPageData> {
    public String title;
    public String query_status;
    public Select card_select;

    public static class Select {
        public String bank_title;
        public String topic_title;
        public String level_title;
        public String annual_fee_title;
        public List<SelectItem> bank;
        public List<SelectItem> topic;
        public List<SelectItem> level;
        public List<SelectItem> annual_fee;
    }

    public static class SelectItem {
        public String id;
        public String value;

        public SelectItem(String id, String value) {
            this.id = id;
            this.value = value;
        }
    }
}
