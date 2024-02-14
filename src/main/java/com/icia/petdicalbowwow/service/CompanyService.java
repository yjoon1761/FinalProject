package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.CompanyDAO;
import com.icia.petdicalbowwow.dao.CompanyRepository;
import com.icia.petdicalbowwow.dto.*;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private ModelAndView mav;

    private final CompanyDAO cdao;

    private final CompanyRepository crepo;

    private final HttpSession session;

    private final PasswordEncoder pwEnc;

    private final JavaMailSender mailSender;


    public Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/profile");

    public List<CompanyDTO> resNo() {
        System.out.println("[2] controller → service");
        List<CompanyDTO> list = new ArrayList<>();

        // entity에 SELECT * FROM MEMBERT 의 조회 결과가 담김
        List<CompanyEntity> entityList = crepo.findAll();
        System.out.println("[3] repository → service : " + entityList);

        for(CompanyEntity entity : entityList){
            list.add(CompanyDTO.toDTO(entity));
        }
        System.out.println("[4] entity → dto : " + list);
        return list;
    }

//    public void checkCbsNum(int cbsNum) {
//        String cbsNumStr = String.valueOf(cbsNum);
//        String requestUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status";
//        // 서비스 키
//        String serviceKey = "T77QIGc7aWpXzvO/hhJyhEpsp2zWGB/LbRjyZ2Xyv9OBjcGm68Ln71v8Jna3yy0Sya3rDFZV17jkUvJYHGtC/A==";
//        requestUrl += "?serviceKey=" + serviceKey;
//
//        RestTemplate rt = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Accept", "application/json");
//        headers.add("Content-Type", "application/json");
//
//        // HttpBody 오브젝트 생성
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        List<String> list = new ArrayList<>();
//        params.add("b_no", cbsNumStr);
//
//        HttpEntity<MultiValueMap<String, String>> cbsNumCheck = new HttpEntity<>(params, headers);
//
//        ResponseEntity<String> response = rt.exchange(
//                requestUrl,
//                HttpMethod.POST,
//                cbsNumCheck,
//                String.class
//        );
//        System.out.println("response = " + response);
//        ObjectMapper objectMapper = new ObjectMapper();
//        BusinessNumber businessNumber = null;
//
//        try {
//            businessNumber = objectMapper.readValue(response.getBody(), BusinessNumber.class);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("businessNumber = " + businessNumber);
//        System.out.println("data = " + businessNumber.getData());
//    }

    public String getUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public ModelAndView cJoin(CompanyDTO company) {

        mav = new ModelAndView();

        MultipartFile cFile = company.getCFile();
        String savePath = null;

        if(!cFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName=cFile.getOriginalFilename();
            String cFileName = uuid +"_"+ originalFileName;

            company.setCFileName(cFileName);

            savePath = path + "/" + cFileName;
        }
        // [2] 비밀번호 암호화
        company.setCPw(pwEnc.encode(company.getCPw()));
        // member.getMPw() : 우리가 입력한 비밀번호(member객체에서 불러오기)
        // pwEnc.encode() : 비밀번호 암호화
        // member.setMPw() : member객체에 다시 저장


        // [3] DTO → Entity로 변형
        CompanyEntity entity = CompanyEntity.toEntity(company);

        // [4] 가입 (성공시 파일 업로드)
        try {
            crepo.save(entity);
            cFile.transferTo(new File(savePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // [5] 성공, 실패시 페이지 이동
        mav.setViewName("redirect:/");

        return mav;
    }


    public ModelAndView cLogin(CompanyDTO company) {
        mav = new ModelAndView();
        String CID = company.getCId();
        Optional<CompanyEntity> entity = crepo.findBycId(CID);

        if (entity.isPresent()) {
            if (pwEnc.matches(company.getCPw(), entity.get().getCPw())) {

                mav.setViewName("redirect:/");
            } else {
                mav.setViewName("redirect:/");
            }
        } else {
            mav.setViewName("redirect:/");
        }

        if (entity.isPresent() && pwEnc.matches(company.getCPw(), entity.get().getCPw())) {
            session.setAttribute("logincId", entity.get().getCId());
            session.setAttribute("cName", entity.get().getCName());
            session.setAttribute("profile", entity.get().getCFileName());
            session.setAttribute("comNo", entity.get().getComNo());
            session.setAttribute("loginNo", -1);
            mav.setViewName("redirect:/");
        } else {
            mav.setViewName("redirect:/");
        }

        return mav;
    }

    public ModelAndView cLogin2(CompanyDTO company) {
        mav = new ModelAndView();
        String CID = company.getCId();
        Optional<CompanyEntity> entity = crepo.findBycId(CID);

        if (entity.isPresent()) {
            if (pwEnc.matches(company.getCPw(), entity.get().getCPw())) {

                mav.setViewName("company/cindex");
            } else {
                mav.setViewName("company/cindex");
            }
        } else {
            mav.setViewName("company/cindex");
        }

//        System.out.println("entity.isPresent() : " + entity.isPresent());
//        System.out.println("아이디비번 : " + pwEnc.matches(company.getCPw(), entity.get().getCPw()));
//        System.out.println("company.getCId() : " + company.getCId());

        if (entity.isPresent() && pwEnc.matches(company.getCPw(), entity.get().getCPw())) {
            session.setAttribute("logincId", entity.get().getCId());
            session.setAttribute("cName", entity.get().getCName());
            session.setAttribute("profile", entity.get().getCFileName());
            session.setAttribute("comNo", entity.get().getComNo());
            session.setAttribute("loginNo", -1);
            mav.setViewName("company/cindex");
        } else {
            mav.setViewName("company/login");
        }

        return mav;
    }

    public List<CompanyDTO> cList() {
        List<CompanyDTO> list = new ArrayList<>();
        List<CompanyEntity> entityList = crepo.findAll();

        for (CompanyEntity entity : entityList) {
            list.add(CompanyDTO.toDTO(entity));
        }
        return list;
    }


    public List<CompanyDTO> cSearchList(SearchDTO search) {
        List<CompanyDTO> list = cdao.cSearchList(search);
        return list;

    }

    public ModelAndView cView(int comNo) {
        mav = new ModelAndView();

        Optional<CompanyEntity> entity = crepo.findById(comNo);
        CompanyDTO company = new CompanyDTO();
        company = CompanyDTO.toDTO(entity.get());
        System.out.println(company);
        mav.setViewName("company/cview");
        mav.addObject("view", company);

        return mav;
    }

    public ModelAndView cModify(CompanyDTO company) {
        mav = new ModelAndView();

        // 기존에 있던 사진 삭제
        // 만약에 가지고 온 데이터에 프로필사진"명" 이 있다면(비어있지 않다면)
        if(!company.getCFileName().isEmpty()){
            // delPath에 사진의 경로와 이름을 담는다
            String delPath = path + "/" + company.getCFileName();

            // delFile로 해당 파일을 담는다
            File delFile = new File(delPath);

            // delFile이 존재한다면
            if(delFile.exists()){
                // 해당 파일을 삭제한다.
                delFile.delete();
            }
        }

        MultipartFile cFile = company.getCFile();
        String savePath = null;
        if(!cFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName=cFile.getOriginalFilename();
            String cFileName = uuid +"_"+ originalFileName;

            company.setCFileName(cFileName);

            savePath = path + "/" + cFileName;
        }
        // [2] 비밀번호 암호화
        company.setCPw(pwEnc.encode(company.getCPw()));
        // member.getMPw() : 우리가 입력한 비밀번호(member객체에서 불러오기)
        // pwEnc.encode() : 비밀번호 암호화
        // member.setMPw() : member객체에 다시 저장


        // [3] DTO → Entity로 변형
        CompanyEntity entity = CompanyEntity.toEntity(company);

        // [4] 가입 (성공시 파일 업로드)
        try {
            crepo.save(entity);
            cFile.transferTo(new File(savePath));
            // [5] 성공시 페이지 이동
            session.setAttribute("profile", company.getCFileName());
            mav.setViewName("redirect:/cView/"+company.getComNo());
        } catch (Exception e) {
            // [5] 실패시 페이지 이동
            mav.setViewName("redirect:/cView/"+company.getComNo());
            throw new RuntimeException(e);
        }


        return mav;
    }


    public ModelAndView cDelete(int ComNo) {
        mav = new ModelAndView();

        crepo.deleteById(ComNo);

        session.invalidate();
        mav.setViewName("redirect:/");

        return mav;

    }

    public String emailCheck(String cEmail) {
        //(1) 인증번호 생성
        String uuid = getUUID();

        //(2) 이메일 내용 작성
        String str = "<h2>펫디컬 바우와우 아이디 찾기 인증 번호 입니다.</h2>"
                + "<p> 인증번호는 <b>" + uuid + "</b> 입니다.</p>";

        //(3)이메일 전송
        MimeMessage mail = mailSender.createMimeMessage();

        try {
            //메일 제목
            mail.setSubject("펫디컬 바우와우 아이디 찾기 인증번호");

            //메일 내용
            mail.setText(str, "UTF-8", "html");

            mail.addRecipient(Message.RecipientType.TO, new InternetAddress(cEmail));

            //메일전송
            mailSender.send(mail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return uuid;

    }

    public String cidCheck(String CID) {
        String result = "NO";

        Optional<CompanyEntity> entity = crepo.findBycId(CID);

        if(entity.isEmpty()){
            result = "OK";
        }

        return result;
    }
    public ModelAndView cIdFind(String findIdEmail, String findIdPhone) {
        mav = new ModelAndView();
        String cIdFind = cdao.cIdFind(findIdEmail, findIdPhone);
        System.out.println(cIdFind);
        mav.setViewName("company/idResult");
        session.setAttribute("cIdFind", cIdFind);
        System.out.println("cid = " + mav);
        return mav;
    }

    public String cIdFindAjax(String findIdEmail, String findIdPhone) {
        String findId = cdao.cIdFind(findIdEmail, findIdPhone);
        System.out.println(findId);
        session.setAttribute("findId", findId);
        return findId;
    }

    public ModelAndView cModifyPw(String CPW) {
        mav = new ModelAndView();
        String CID = (String) session.getAttribute("findPwId");


        Optional<CompanyEntity> entity = crepo.findBycId(CID);
        System.out.println("entity = " + entity);
        CompanyDTO company = new CompanyDTO();
        company = CompanyDTO.toDTO(entity.get());

        company.setCPw(CPW);
        mav = this.cModify(company);
        System.out.println("비밀번호 재설정 완료");
        mav.setViewName("redirect:/");
        return mav;
    }

    public String emailCheckAjax2(String findPwId, String findPwEmail) {
        String findId = cdao.cIdFind2(findPwId, findPwEmail);
        System.out.println("findId = " + findId);
        return findId;
    }

    public String cpwCheck(CompanyDTO company) {
        String result;
        Optional<CompanyEntity> entity = crepo.findById((company).getComNo());
        if(pwEnc.matches(company.getCPw(), entity.get().getCPw())) {
            System.out.println("비밀번호 일치");
            cDelete(company.getComNo());
            result = "OK";
        } else {
            result = "NO";
        }
        System.out.println("result = " + result);
        return result;
    }
}
