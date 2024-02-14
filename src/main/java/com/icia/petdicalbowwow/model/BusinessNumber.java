package com.icia.petdicalbowwow.model;

import lombok.Data;

import java.util.List;

@Data
public class BusinessNumber {

    public Integer request_cnt;
    public Integer match_cnt;
    public String status_code;
    public List<DataList> data;
}