package com.icia.petdicalbowwow.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
/*
    클래스안에 클래스가 들어가 있는 형태
    아래 경우는 DTO를 하나의 클래스에서 관리하기 위함

    # 규칙
    1) 클래스 내부 최상위 클래스들 중 하나는 반드시 public이어야 함
    2) 최상위 public 클래스는 오직 하나만 존재해야 함
    3) 하위 클래스를 패키지 바깥에서 호출하기 위해선 해당 클래스 또한 public이어야 함

 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;

    @Data
    public class Properties {
        public String nickname;
        public String profile_image;
        public String thumbnail_image;
    }

    @Data
    public class Kakao_account {
        public Boolean profile_nickname_needs_agreement;
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;

        @Data
        public class Profile {
            public String nickname;
            public String thumbnail_image_url;
            public String profile_image_url;
            public Boolean is_default_image;
        }
    }
}




