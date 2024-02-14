package com.icia.petdicalbowwow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.petdicalbowwow.dao.CardRepository;
import com.icia.petdicalbowwow.dao.CompanyRepository;
import com.icia.petdicalbowwow.dao.MemberDAO;
import com.icia.petdicalbowwow.dao.MemberRepository;
import com.icia.petdicalbowwow.dto.*;
import com.icia.petdicalbowwow.model.KakaoPay;
import com.icia.petdicalbowwow.model.KakaoPayReady;
import com.icia.petdicalbowwow.model.KakaoProfile;
import com.icia.petdicalbowwow.model.OAuthToken;
import jakarta.transaction.Transactional;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.service.DefaultMessageService;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private ModelAndView mav;
    private final MemberDAO mdao;
    private final MemberRepository mrepo;
    private final CompanyRepository crepo;
    private final CardRepository crdrepo;
    private final JavaMailSender mailSender;
    private final PasswordEncoder pwEnc;
    private final HttpSession session;
    KakaoPayReady kakaoPayReady;
    KakaoPay kakaoPay;
    private PetDTO pet;
    private CardDTO card;

    public String getuuid(){
        return UUID.randomUUID().toString().substring(0,8);
    }
    public Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/profile");

    public String emailCheck(String findPwEmail) {
        // (1) 인증번호 생성
        String uuid = getuuid();

        // (2) 이메일 내용 작성
        String str = "<h2>안녕하세요. 펫디컬 바우와우 입니다.</h2>"
                + "<p> 인증번호는 <b>"+ uuid + "</b> 입니다.</p>";

        // (3) 이메일 전송
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            // 메일 제목
            mail.setSubject("펫디컬 바우와우 비밀번호 찾기 인증번호");

            // 메일 내용
            mail.setText(str, "UTF-8", "html");

            // 메일 수신자
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(findPwEmail));

            // 메일 전송
            mailSender.send(mail);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;
    }

    public String midCheck(String MID) {
        String result = "NO";

        // SELECT MID FROM MEMBERT WHERE MID = #{mId}
        // 조회한 결과를 entity에 대입한다.
        Optional<MemberEntity> entity = mrepo.findByMID(MID);

        if(entity.isEmpty()){
            result = "OK";
        }

        return result;
    }

    public String nickNameCheck(String nickName) {
        System.out.println("nickName = " + nickName);
        String result = "NO";
        Optional<MemberEntity> entity = mrepo.findByNickName(nickName);
//        System.out.println("entity = " + entity);
        if(entity.isEmpty()){
            result = "OK";
        }
        return result;
    }

    public void phoneCheck(String userPhoneNumber, int randomNumber) {
        String api_key = "NCSXYUIES2LTT49H";
        String api_secret = "TEJP6DTQCWQUGBTFGIIFI0OF4MDHLO1S";

        DefaultMessageService messageService =  NurigoApp.INSTANCE.initialize(api_key, api_secret, "https://api.coolsms.co.kr");
        net.nurigo.sdk.message.model.Message coolsms = new net.nurigo.sdk.message.model.Message();
        coolsms.setFrom("010-8365-4440");
        coolsms.setTo(userPhoneNumber);
        coolsms.setText("[펫디컬바우와우] 인증번호는 " + "["+ randomNumber +"] 입니다.");

        try {
            // send 메소드로 ArrayList<Message> 객체를 넣어도 동작합니다!
            messageService.send(coolsms);
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인할 수 있습니다!
            System.out.println(exception.getFailedMessageList());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public ModelAndView mJoin(MemberDTO member) {
        mav = new ModelAndView();

        // [1] 파일 업로드 준비
        MultipartFile mProfile = member.getMFile();
        String savePath = null;

        if(!mProfile.isEmpty()){
            String uuid = getuuid();
            String originalFileName = mProfile.getOriginalFilename();
            String mProfileName = uuid + "_" + originalFileName;

            member.setMFileName(mProfileName);

            savePath = path + "/" + mProfileName;
        }

        // [2] 비밀번호 암호화
        member.setMPw(pwEnc.encode(member.getMPw()));
        // member.getMPw() : 우리가 입력한 비밀번호(member객체에서 불러오기)
        // pwEnc.encode() : 비밀번호 암호화
        // member.setMPw() : member객체에 다시 저장

        // [3] DTO → Entity로 변형
        MemberEntity entity = MemberEntity.toEntity(member);

        // [4] 가입 (성공시 파일 업로드)
        try {
            mrepo.save(entity);
            mProfile.transferTo(new File(savePath));
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        // [5] 성공, 실패시 페이지 이동
        mav.setViewName("redirect:/");

        return mav;
    }
    @Transactional
    public ModelAndView kLogin(String code) {
        mav = new ModelAndView();
        // Content type 입력
        String contentType = "application/x-www-form-urlencoded;charset-utf-8";
        // REST API 키 입력
        String clientId = "beb82237075bbae4f308ebdc707b3ffc";
        // Redirect URI 입력
        String redirectUri = "http://localhost:9091/auth/kakao/callback";
        // Token 요청 주소 입력
        String requestUri = "https://kauth.kakao.com/oauth/token";
        // Profile(사용자정보) 요청 주소
        String requestUri2 = "https://kapi.kakao.com/v2/user/me";

        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 요청할 HttpHeader가 'key-value' 형태라고 헤더에 알려주는 역할
        headers.add("Content-type", contentType);

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        // 생성자로 전달 할 때 파라미터 순서는 HttpBody, HttpHeader
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // rt.exchange → 해당 메소드가 HttpEntity 오브젝트를 받기 때문
        ResponseEntity<String> response = rt.exchange(
                requestUri,         // 요청 주소
                HttpMethod.POST,    // 요청 방식
                kakaoTokenRequest,  // 요청 헤더와 본문
                String.class        // 응답 데이터 타입
        );

        // Json 데이터를 Object에 담을 때 사용하는 라이브러리
        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oauthToken = null;

        try {
            oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
//        System.out.println("Access token = " + oauthToken.getAccess_token());

        RestTemplate rt2 = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();

        // 요청할 HttpHeader가 'key-value' 형태라고 헤더에 알려주는 역할
        headers2.add("Content-type", contentType);
        headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        // rt.exchange → 해당 메소드가 HttpEntity 오브젝트를 받기 때문
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
        ResponseEntity<String> response2 = rt2.exchange(
                requestUri2,         // 요청 주소
                HttpMethod.POST,    // 요청 방식
                kakaoProfileRequest,  // 요청 헤더
                String.class        // 응답 데이터 타입
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String kakaoUserEmail = kakaoProfile.kakao_account.getEmail();
        String kakaoUserId = kakaoUserEmail + "_kakao";
        String kakaoUserPw = "pbw_" + String.valueOf(kakaoProfile.getId());
        session.setAttribute("loginAdminKey", String.valueOf(kakaoProfile.getId()));
        System.out.println("카카오 회원 번호 = " + kakaoProfile.getId());
        System.out.println("카카오 회원 아이디 = " + kakaoUserId);
        System.out.println("카카오 회원 비밀번호(raw) = " + kakaoUserPw);
        System.out.println("카카오 이메일 = " + kakaoUserEmail);

        // 가입자 비가입자 확인
        Optional<MemberEntity> entity1 = mrepo.findByMID(kakaoUserId);

        if(entity1.isEmpty()) {  // 비가입자의 경우 회원가입 먼저 진행

            System.out.println("비가입자로 회권가입을 진행합니다.");
            MemberDTO member = new MemberDTO();

            // Member 객체에 정보 저장(아이디, 비밀번호, oauth, 이메일)
            member.setMID(kakaoUserId);
            member.setMPw(pwEnc.encode(kakaoUserPw));
            member.setMOauth("kakaoFirst");
            member.setMEmail(kakaoUserEmail);
            MemberEntity entity2 = MemberEntity.toEntity(member);
            mrepo.save(entity2);
            member.setMPw(kakaoUserPw);
            System.out.println("회원가입 완료. 로그인 진행");
            mav = this.mLogin(member);
        } else {
            System.out.println("userId = " + entity1.get().getMID());
            System.out.println("기존 가입자. 로그인 진행");   // 로그인
            MemberDTO member = new MemberDTO();
            member = new MemberDTO();
            member.setMID(kakaoUserId);
            member.setMPw(kakaoUserPw);
            member.setMOauth("kakao");
            mav = this.mLogin(member);
        }
        return mav;
    }

    @Transactional
    public ModelAndView mLogin(MemberDTO member) {
        mav = new ModelAndView();

        Optional<MemberEntity> entity1 = mrepo.findByMID(member.getMID());
        // entity1 = MNO, MID 정보가 담김

        if(entity1.isPresent()) {

            if (pwEnc.matches(member.getMPw(), entity1.get().getMPw())) {
                MemberDTO member2 = new MemberDTO();
                member2 = MemberDTO.toDTO(entity1.get());
                System.out.println("멤   버 : " + member2);
                session.setAttribute("loginNo", member2.getMNO());
                session.setAttribute("loginmId", member2.getMID());
                session.setAttribute("loginnickname", member2.getNickName());
                session.setAttribute("profile", member2.getMFileName());
                session.setAttribute("loginOauth", member2.getMOauth());
                session.setAttribute("comNo", -1);

                if (session.getAttribute("loginOauth") == null) {
                    mav.setViewName("redirect:/");
                } else if (session.getAttribute("loginOauth").equals("kakaoFirst")) {
                    mav.setViewName("redirect:/mView/" + member2.getMNO());
                } else {
                    System.out.println("11111111111111111");
                    mav.setViewName("redirect:/");
                }
            } else {
                mav.setViewName("redirect:/");
            }
        }else {
            mav.setViewName("redirect:/");
        }
        return mav;
    }

    public List<MemberDTO> mList() {
        List<MemberDTO> list = new ArrayList<>();

        // entity에 SELECT * FROM MEMBERT 의 조회 결과가 담김
        List<MemberEntity> entityList = mrepo.findAll();
        for(MemberEntity entity : entityList){
            list.add(MemberDTO.toDTO(entity));
        }
        return list;
    }

    public List<MemberDTO> mSearchList(SearchDTO search) {
        List<MemberDTO> list = mdao.mSearchList(search);
        return list;
    }

    public ModelAndView mView(int MNO) {
        mav = new ModelAndView();
        Optional<MemberEntity> entity = mrepo.findById(MNO);
        MemberDTO member = new MemberDTO();
        member = MemberDTO.toDTO(entity.get());
        System.out.println("mView : " + member);
        mav.setViewName("meminfo/mview");
        mav.addObject("view", member);

        return mav;
    }

    // 회원수정
    public ModelAndView mModify(MemberDTO member) {
        mav = new ModelAndView();
        // 기존에 있던 사진 삭제
        // 만약에 가지고 온 데이터에 프로필사진"명" 이 있다면(비어있지 않다면)
        if(!member.getMFileName().isEmpty()){
            // delPath에 사진의 경로와 이름을 담는다
            String delPath = path + "/" + member.getMFileName();

            // delFile로 해당 파일을 담는다
            File delFile = new File(delPath);

            // delFile이 존재한다면
            if(delFile.exists()){
                // 해당 파일을 삭제한다.
                delFile.delete();
            }
        }

        // [1] 파일 업로드 준비
        MultipartFile mProfile = member.getMFile();
        String savePath = null;
        if(mProfile == null) {
            System.out.println("업로드할 파일이 없습니다.");
        } else if(!mProfile.isEmpty()){
            String uuid = getuuid();
            String originalFileName = mProfile.getOriginalFilename();
            String mProfileName = uuid + "_" + originalFileName;

            member.setMFileName(mProfileName);

            savePath = path + "/" + mProfileName;
        }

        // [2] 비밀번호 암호화
        if(member.getMOauth() == null) {
            member.setMPw(pwEnc.encode(member.getMPw()));
            // member.getMPw() : 우리가 입력한 비밀번호(member객체에서 불러오기)
            // pwEnc.encode() : 비밀번호 암호화
            // member.setMPw() : member객체에 다시 저장
        }
        // [3] DTO → Entity로 변형
        MemberEntity entity = MemberEntity.toEntity(member);

        // [4] 수정 (성공시 파일 업로드)
        try {
            mrepo.save(entity);
            if(mProfile == null) {
                System.out.println("업로드할 파일이 없습니다.");
            } else {
                mProfile.transferTo(new File(savePath));
            }
            session.setAttribute("profile", member.getMFileName());
            mav.setViewName("redirect:/mView/"+member.getMNO());
        } catch (Exception e){
            mav.setViewName("redirect:/mView/"+member.getMNO());
            throw new RuntimeException(e);
        }
        return mav;
    }


    public ModelAndView mDelete(int MNO) {
        mav = new ModelAndView();

        // MNO 정보로 프로필 사진 삭제
        Optional<MemberEntity> entity = mrepo.findById(MNO);

        if(entity.get().getMFileName() == null || !entity.get().getMFileName().isEmpty()){
            // delPath에 사진의 경로와 이름을 담는다
            String delPath = path + "/" + entity.get().getMFileName();

            // delFile로 해당 파일을 담는다
            File delFile = new File(delPath);

            // delFile이 존재한다면
            if(delFile.exists()){
                // 해당 파일을 삭제한다.
                delFile.delete();
            }
        }

        // MNO에 대한 정보 삭제
        mrepo.deleteById(MNO);

        // 로그아웃 진행 후 index로 이동
        session.invalidate();
        mav.setViewName("redirect:/");

        return mav;
    }

    public ModelAndView kLogout() {
        mav = new ModelAndView();
        session.invalidate();
        mav.setViewName("redirect:https://kauth.kakao.com/oauth/logout?client_id=beb82237075bbae4f308ebdc707b3ffc&logout_redirect_uri=http://localhost:9091");
        return mav;
    }

    public ModelAndView kDiscon() {
        mav = new ModelAndView();
        String requestUri3 = "https://kapi.kakao.com/v1/user/unlink";
        String serviceAdminKey = "22480b2afa2b2f1f660d9b1418dbe3a6";
        RestTemplate rt = new RestTemplate();

        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        // 전송할 HttpBody가 'key-value' 형태라고 헤더에 알려주는 역할
        headers.add("Authorization", "KakaoAK " + serviceAdminKey);

        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        System.out.println("session값 = " + (String) session.getAttribute("loginAdminKey"));
        params.add("target_id", (String) session.getAttribute("loginAdminKey"));
        System.out.println("HttpBody = " + params);

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        // rt.exchange → 해당 메소드가 HttpEntity 오브젝트를 받기 때문
        HttpEntity<MultiValueMap<String, String>> kakaoDisconRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange(
                requestUri3,         // 요청 주소
                HttpMethod.POST,    // 요청 방식
                kakaoDisconRequest, // HttpEntity
                String.class        // 응답 데이터 타입
        );
        System.out.println("response = " + response.getBody());
        mav.setViewName("index");
        return mav;
    }

    public ModelAndView mIdFind(String findIdEmail, String findIdPhone) {
        mav = new ModelAndView();
        String findId = mdao.mIdFind(findIdEmail, findIdPhone);

        mav.setViewName("member/idResult");
        session.setAttribute("findId", findId);

        return mav;
    }

    public String mIdFindAjax(String findIdEmail, String findIdPhone) {
        String findId = mdao.mIdFind(findIdEmail, findIdPhone);
        System.out.println(findId);
        session.setAttribute("findId", findId);
        return findId;
    }

    public ModelAndView mModifyPw(String MPW) {
        mav = new ModelAndView();
        String MID = (String) session.getAttribute("findPwId");
        System.out.println("MID : " +  MID);
        Optional<MemberEntity> entity = mrepo.findByMID(MID);
//        System.out.println("entity = " + entity);
        MemberDTO member = new MemberDTO();
        member = MemberDTO.toDTO(entity.get());

        member.setMPw(MPW);
        mav = this.mModify(member);
        System.out.println("비밀번호 재설정 완료");
        mav.setViewName("redirect:/");
        return mav;
    }

    public String emailCheckAjax(String findPwId, String findPwEmail) {
        String findId = mdao.mIdFind2(findPwId, findPwEmail);
        System.out.println("findId = " + findId);
        return findId;
    }

    public ModelAndView kPayReady(CardDTO card, PetDTO pet) {
        mav = new ModelAndView();

        // 전역변수에 넘겨받은 args 저장
        this.card = card;
        this.pet = pet;

        // 서비스 앱 어드민 키
        String readyUrl = "https://kapi.kakao.com/v1/payment/ready";
        String serviceKey = "22480b2afa2b2f1f660d9b1418dbe3a6";
        String serviceKey2 = "KakaoAK " + serviceKey;
        String approval_url = "http://localhost:9091/kPay";
        String cancel_url = "http://localhost:9091/cancel";
        String fail_url = "http://localhost:9091/fail";
        int price = 3000;
        int quantity = 1;
        int total_amount = (price * quantity);
        int tax_free_amount = 0;

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", serviceKey2);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody Sting-String 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", "petdicalbowwowTest_0001");
        params.add("partner_user_id", "test1");
        params.add("item_name", "바우와우 카드");
        params.add("approval_url", approval_url);
        params.add("cancel_url", cancel_url);
        params.add("fail_url", fail_url);
        params.add("quantity", String.valueOf(quantity));
        params.add("total_amount", String.valueOf(total_amount));
        params.add("tax_free_amount", String.valueOf(tax_free_amount));

        HttpEntity<MultiValueMap<String, String>> kPayReady = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = rt.exchange(
                readyUrl,
                HttpMethod.POST,
                kPayReady,
                String.class
        );

        System.out.println("response = " + response);
        System.out.println("responseBody = " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kakaoPayReady = new KakaoPayReady();
            kakaoPayReady = objectMapper.readValue(response.getBody(), KakaoPayReady.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("pc_url = " + kakaoPayReady.getNext_redirect_pc_url());
        mav.setViewName("redirect:" + kakaoPayReady.getNext_redirect_pc_url());
        return mav;
    }

    public ModelAndView kPay(String pg_token, CardDTO card, PetDTO pet) {
        mav = new ModelAndView();

        // 전역변수에 저장된 값을 메소드 변수에 저장
        pet = this.pet;
        card = this.card;

        String serviceKey = "22480b2afa2b2f1f660d9b1418dbe3a6";
        String serviceKey2 = "KakaoAK " + serviceKey;
        String approveUrl = "https://kapi.kakao.com/v1/payment/approve";

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", serviceKey2);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        System.out.println("HttpHeaders = " + headers);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("cid", "TC0ONETIME");
        params.add("tid", kakaoPayReady.getTid());
        params.add("partner_order_id", "petdicalbowwowTest_0001");
        params.add("partner_user_id", "test1");
        params.add("pg_token", pg_token);

        System.out.println("HttpBody = " + params);

        HttpEntity<MultiValueMap<String, String>> kPay = new HttpEntity<>(params, headers);

        System.out.println("HttpEntity = " + kPay);

        ResponseEntity<String> response = rt.exchange(
                approveUrl,
                HttpMethod.POST,
                kPay,
                String.class
        );
        System.out.println("response = " + response);
        System.out.println("body = " + response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kakaoPay = new KakaoPay();
            kakaoPay = objectMapper.readValue(response.getBody(), KakaoPay.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("요청고유번호 = " + kakaoPay.getAid());
        System.out.println("구매개수 = " + kakaoPay.getQuantity());
        System.out.println("총 결제금액 = " + kakaoPay.amount.getTotal());
        System.out.println("결제시각 = " + kakaoPay.getApproved_at());

        String crdId = getuuid();
        card.setCrdId(crdId);
        card.setCrdNum(kakaoPay.getAid());
        card.setCrdCheck(0);
        CardEntity entity = CardEntity.toEntity(card, pet);
        crdrepo.save(entity);
        Optional<CardEntity> entity2 = crdrepo.findByCrdNum(card.getCrdNum());
        CardDTO card2 = new CardDTO();
        card2 = CardDTO.toDTO(entity2.get());

        mav.addObject("view", card2);
        mav.setViewName("/member/success");
        return mav;
    }

    public String mpwCheck(MemberDTO member) {
        String result;
        Optional<MemberEntity> entity = mrepo.findById(member.getMNO());
        if(entity.get().getMOauth() == null) {
            if(pwEnc.matches(member.getMPw(), entity.get().getMPw())) {
                System.out.println("비밀번호 일치");
                mDelete(member.getMNO());
                result = "OK";
            } else {
                result = "NO";
            }
        } else {    // 카카오 유저 회원탈퇴의 경우
            if(pwEnc.matches(member.getMPw(), entity.get().getMPw())) {
                System.out.println("비밀번호 일치");
                kDiscon();  // 접속정보 삭제
                kLogout();  // 계정 로그아웃
                mDelete(member.getMNO());
                result = "OK";
            } else {
                result = "NO";
            }
        }
        System.out.println("result = " + result);
        return result;
    }
}