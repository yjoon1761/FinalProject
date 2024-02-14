package com.icia.petdicalbowwow.model;

import lombok.Data;

@Data
public class KakaoPay {

    public String aid;
    public String tid;
    public String cid;
    public String partner_order_id;
    public String partner_user_id;
    public String payment_method_type;
    public String item_name;
    public Integer quantity;
    public Amount amount;
    public String created_at;
    public String approved_at;

    @Data
    public class Amount {
        public Integer total;
        public Integer tax_free;
        public Integer vat;
        public Integer point;
        public Integer discount;
        public Integer green_deposit;

    }
}