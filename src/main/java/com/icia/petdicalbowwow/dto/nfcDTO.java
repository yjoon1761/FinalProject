package com.icia.petdicalbowwow.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Alias("nfc")
public class nfcDTO {
    private List<String> key;
    private String finalKey;
}