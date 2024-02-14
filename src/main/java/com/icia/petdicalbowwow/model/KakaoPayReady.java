package com.icia.petdicalbowwow.model;

import lombok.Data;

@Data
public class KakaoPayReady {
    public String tid;
    public Boolean tms_result;
    public String next_redirect_app_url;
    public String next_redirect_mobile_url;
    public String next_redirect_pc_url;
    public String android_app_scheme;
    public String ios_app_scheme;
    public String created_at;
}