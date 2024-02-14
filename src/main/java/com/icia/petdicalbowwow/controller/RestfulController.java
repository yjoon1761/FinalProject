package com.icia.petdicalbowwow.controller;
import com.icia.petdicalbowwow.dao.MemberDAO;
import com.icia.petdicalbowwow.dao.MemberRepository;
import com.icia.petdicalbowwow.dto.*;
import com.icia.petdicalbowwow.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestfulController {

    private final MemberService msvc;
    private final BoardService bsvc;
    private final CompanyService csvc;
    private final DoctorService dsvc;
    private final PetService psvc;
    private final MemberRepository mrepo;
    private final MemberDAO mdao;
    private final PasswordEncoder pwEnc;
    private final VacationService vsvc;
    private final CommentService csvc1;
    private final AllergyService asvc;
    private final DiseaseService disvc;
    private final KindService ksvc;
    private final VaccinationService vasvc;
    private final CategoryService catsvc;
    private final ReservationService ressvc;
    private final CardService crdsvc;
    private final RecordsService rsvc;
    private final HttpSession session;

    private ModelAndView mav;

    @PostMapping("/boList")
    public List<BoardDTO> boList(@RequestParam("catNo") int catNo){
        System.out.println("[1] jsp → controller");
        List<BoardDTO> list = bsvc.boList(catNo);
        System.out.println("[5] :" + list);
        return list;
    }

    @PostMapping("/membList")
    public List<BoardDTO> membList(@RequestParam("MNO") int MNO){
        System.out.println("[1] jsp → controller");
        List<BoardDTO> list = bsvc.membList(MNO);
        System.out.println("[5] :" + list);
        return list;
    }

    @PostMapping("/mybListc")
    public List<BoardDTO> mybListc(@RequestParam("comNo") int comNo){
        System.out.println("[1] jsp → controller");
        List<BoardDTO> list = bsvc.mybListc(comNo);
        System.out.println("[5] :" + list);
        return list;
    }

    @PostMapping("/resNo")
    public List<CompanyDTO> resNo(){
        System.out.println("[1] jsp → controller");
        List<CompanyDTO> list = csvc.resNo();
        System.out.println("[5] :" + list);
        return list;
    }

    @PostMapping("/pList")
    public List<PetDTO> pList(){
        System.out.println("[1] jsp → controller");
        List<PetDTO> list = psvc.pList();
        return list;
    }

    @PostMapping("/pSearchList")
    public List<PetDTO> pSearchList(@ModelAttribute SearchDTO search){
        List<PetDTO> list = psvc.pSearchList(search);
        return list;
    }

    @PostMapping("emailCheck")
    public String emailCheck(@RequestParam("findPwEmail") String findPwEmail){
        String uuid = msvc.emailCheck(findPwEmail);
        return uuid;
    }

    // midCheck : 아이디 중복체크
    @PostMapping("idCheck")
    public String idCheck(@RequestParam("idVal") String idVal){
        String result = "";
        String checkMid = msvc.midCheck(idVal);
        String checkCid = csvc.cidCheck(idVal);
        if(checkMid.equals("OK") && checkCid.equals("OK")) {
            result = "OK";
        }
        return result;
    }

    // nickNameCheck : 닉네임 중복체크
    @RequestMapping(value = {"nickNameCheck", "/mView/{MNO}/nickNameCheck"}, method = RequestMethod.POST)
    public String nickNameCheck(@RequestParam("nickName") String nickName,
                                @PathVariable(value = "MNO", required = false) String MNO) {
        System.out.println("nickName = " + nickName);
        String result = msvc.nickNameCheck(nickName);
        System.out.println("result = " + result);
        return result;
    }

    // 휴대폰 본인인증
    @RequestMapping(value = {"phoneCheck", "/mView/{MNO}/phoneCheck"}, method = RequestMethod.POST)
    public String phoneCheck(@RequestParam("phone") String userPhoneNumber,
                             @PathVariable(value = "MNO", required = false) String MNO) {
        //난수 생성
        int randomNumber = (int)((Math.random()* (9999 - 1000 + 1)) + 1000);
        msvc.phoneCheck(userPhoneNumber,randomNumber);
        return Integer.toString(randomNumber);
    }

    // mList : 회원목록
    @PostMapping("/mList")
    public List<MemberDTO> mList() {
        System.out.println("[1] jsp → controller");
        List<MemberDTO> list = msvc.mList();
        System.out.println("[5] :" + list);
        return list;
    }

    // mSearchList : 회원검색목록
    @PostMapping("mSearchList")
    public List<MemberDTO> mSearchList(@ModelAttribute SearchDTO search){
        List<MemberDTO> list = msvc.mSearchList(search);
        return list;
    }


//     checkCbsNum : 사업자 진위확인
//    @GetMapping("checkCbsNum/{cbsNum}")
//    public void checkCbsNum(@PathVariable int cbsNum) {
//        System.out.println("cbsNum = " + cbsNum);
//        csvc.checkCbsNum(cbsNum);
//    }

    @PostMapping("mIdFindAjax")
    public String mIdFindAjax(@RequestParam(value = "findIdEmail") String findIdEmail,
                              @RequestParam(value = "findIdPhone") String findIdPhone) {
        String result = msvc.mIdFindAjax(findIdEmail, findIdPhone);
        return result;
    }

    // emailCheckAjax
    @PostMapping("emailCheckAjax")
    public String emailCheckAjax(@RequestParam(value = "findPwId") String findPwId,
                                 @RequestParam(value = "findPwEmail") String findPwEmail) {
        String result = msvc.emailCheckAjax(findPwId, findPwEmail);
        return result;
    }

    @PostMapping("cIdFindAjax")
    public String cIdFindAjax(@RequestParam(value = "findIdEmail") String findIdEmail,
                              @RequestParam(value = "findIdPhone") String findIdPhone) {
        String result = csvc.cIdFindAjax(findIdEmail, findIdPhone);
        return result;
    }


    //     emailCheckAjax
    @PostMapping("emailCheckAjax2")
    public String emailCheckAjax2(@RequestParam(value = "findPwId") String findPwId,
                                  @RequestParam(value = "findPwEmail") String findPwEmail) {
        String result = csvc.emailCheckAjax2(findPwId, findPwEmail);
        return result;
    }

    @PostMapping("emailCheck2")
    public String emailCheck2(@RequestParam("findPwEmail") String findPwEmail){
        String uuid = csvc.emailCheck(findPwEmail);
        return uuid;
    }

    @PostMapping("/cList")
    public List<CompanyDTO> cList() {
        List<CompanyDTO> list = csvc.cList();
        return list;
    }

    //cSearchList : 기업 검색 목록
    @PostMapping("/cSearchList")
    public List<CompanyDTO> cSearchList(@ModelAttribute SearchDTO search){
        List<CompanyDTO> list = csvc.cSearchList(search);
        return list;
    }

    @PostMapping("sRegister")
    public String sRegister(@RequestParam(value = "dateList", required = false) List<String> dateList,
                            @RequestParam(value = "comNo", required = false) int comNo) {
        String result = "";
        System.out.println("comNo = " + comNo);
        System.out.println("dateList = " + dateList);
        if(dateList == null) {
            result = "업데이트 사항이 없습니다.";
        } else {
            result = vsvc.sRegister(dateList, comNo);
        }
        return result;
    }

    @PostMapping("sReady")
    public String sReady(@RequestParam("comNo") int comNo) {
        System.out.println("comNo = " + comNo);
        JSONArray list = vsvc.sReady(comNo);
        System.out.println("list = " + list);
        return list.toString();
    }

    @PostMapping("rReady")
    public String rReady(@RequestParam("comNo") int comNo) {
        System.out.println("rReady");
        System.out.println("comNo = " + comNo);
        JSONArray list = ressvc.rReady(comNo);
        return list.toString();
    }

    @PostMapping("/vList")
    public List<VacationDTO> vList(@RequestParam("comNo") int comNo) {
        System.out.println("[1] jsp → controller : " + comNo);
        List<VacationDTO> list = vsvc.vList(comNo);
        System.out.println("[5] :" + list);
        return list;
    }

    @PostMapping("/bSearchList")
    public List<BoardDTO> bSearchList(@ModelAttribute SearchDTO search){
        List<BoardDTO> list = bsvc.bSearchList(search);
        return list;
    }

    @PostMapping("/cmtList")
    public List<CommentDTO> cmtList(@RequestParam("bNo") int bNo){
        List<CommentDTO> list = csvc1.cmtList(bNo);
        return list;
    }

    @PostMapping("/cmtRegister")
    public List<CommentDTO> cmtRegister(@ModelAttribute CommentDTO comment,
                                        @ModelAttribute MemberDTO member,
                                        @ModelAttribute BoardDTO board,
                                        @ModelAttribute CompanyDTO company,
                                        @ModelAttribute CategoryDTO category){
        List<CommentDTO> list = csvc1.cmtRegister(comment, board, member, company, category);
        System.out.println(member);
        System.out.println(company);
        return list;
    }

    @PostMapping("/cmtModify")
    public List<CommentDTO> cmtModify(@ModelAttribute CommentDTO comment,
                                      @ModelAttribute MemberDTO member,
                                      @ModelAttribute BoardDTO board,
                                      @ModelAttribute CompanyDTO company,
                                      @ModelAttribute CategoryDTO category){
        System.out.println("comment : " + comment);
        System.out.println("member : " + member);
        System.out.println("board : " + board);
        List<CommentDTO> list = csvc1.cmtModify(comment, board, member, company, category);
        return list;
    }

    @PostMapping("/cmtDelete")
    public List<CommentDTO> cmtDelete(@ModelAttribute CommentDTO comment,
                                      @ModelAttribute MemberDTO member,
                                      @ModelAttribute BoardDTO board,
                                      @ModelAttribute CompanyDTO company,
                                      @ModelAttribute CategoryDTO category){
        List<CommentDTO> list = csvc1.cmtDelete(comment, board, member, company, category);
        return list;
    }

    //kindList:품종 목록
    @PostMapping("/kindList")
    public List<KindDTO> kList(){
        List<KindDTO> list=ksvc.kList();
        return list;
    }

    //vaccinationList:품종 목록
    @PostMapping("/vaccineList")
    public List<VaccinationDTO> vList(){
        List<VaccinationDTO> list=vasvc.vList();
        return list;
    }

    //vaccinationList:품종 목록
    @PostMapping("/diseaseList")
    public List<DiseaseDTO> disList(){
        List<DiseaseDTO> list=disvc.dList();
        return list;
    }

    //allergy : 알러지 목록
    @PostMapping("/allergyList")
    public List<AllergyDTO> aList(){
        List<AllergyDTO> list = asvc.aList();
        return list;
    }

    //category : 리스트 불러오기
    @PostMapping("/categoryList")
    public List<CategoryDTO> categoryList(){
        List<CategoryDTO> list = catsvc.categoryList();

        return list;
    }

    // dIdCheck : 의사 등록
    @PostMapping("/dIdCheck")
    public String dIdCheck(@RequestParam("dId") int dId){
        System.out.println(dId);
        String result = dsvc.dIdCheck(dId);

        return result;
    }

    // dList : 의사 목록
    @PostMapping("/dList")
    public List<DoctorDTO> dList(){
        List<DoctorDTO> list = dsvc.dList();
        return list;
    }

    @PostMapping("/dSearchList")
    public List<DoctorDTO> dSearchList(@ModelAttribute SearchDTO search){
        List<DoctorDTO> list = dsvc.dSearchList(search);
        return list;
    }

    // 회원 예약 내역 불러오기
    @PostMapping("/rList")
    public List<ReservationDTO> rList(@RequestParam("MNO") int MNO){
        List<ReservationDTO> list = ressvc.rList(MNO);
        System.out.println("[5] :" + list);
        return list;
    }


    @PostMapping("/rList2")
    public ReservationDTO rList2(@RequestParam("resNo") int resNo){
        ReservationDTO list = ressvc.rList2(resNo);
        return list;
    }

    //회사 차트 리스트
    @PostMapping("/comRecList")
    public List<RecordsDTO> comRecList(){
        System.out.println("testt");
        List<RecordsDTO> list = rsvc.comRecList();
        System.out.println("testt");
        System.out.println(list);
        return list;
    }
    //개인 차트 리스트
    @PostMapping("/memRecList")
    public List<RecordsDTO> memRecList(){
        List<RecordsDTO> list = rsvc.memRecList();
        return list;
    }

    @PostMapping("/crdCheckList")
    public List<CardDTO> crdCheckList(){
        List<CardDTO> list = crdsvc.crdCheckList();
        return list;
    }

    @PostMapping("/crdUpdate")
    public List<CardDTO> crdUpdate(@ModelAttribute CardDTO card,
                                   @ModelAttribute PetDTO pet,
                                   @RequestParam("keyValue") String keyValue){
        System.out.println("card : " + card);
        System.out.println("pet : " + pet);
        System.out.println("keyValue : " + keyValue);
        List<CardDTO> list = crdsvc.crdUpdate(card, pet, keyValue);
        return list;
    }

    @PostMapping("/petSearch")
    public List<PetDTO> petSearch(@RequestParam("keyword") String keyword){
        List<PetDTO> list = psvc.petSearch(keyword);

        return list;
    }

    @PostMapping("/setInfo")
    public PetDTO setInfo(@RequestParam("pNo") int pNo,
                          @RequestParam("MNO") int MNO){
        PetDTO pet = psvc.setInfo(pNo, MNO);

        return pet;
    }

    @PostMapping("/crdList")
    public List<CardDTO> crdList(){
        List<CardDTO> list = crdsvc.crdList();
        System.out.println("controller : " + list);
        return list;
    }

    @PostMapping("/crdDelete")
    public List<CardDTO> crdDelete(@ModelAttribute CardDTO card,
                                   @ModelAttribute PetDTO pet){
        System.out.println(card);
        List<CardDTO> list = crdsvc.crdDelete(card, pet);

        return list;
    }

    // 기업 예약 내역 불러오기
    @PostMapping("/resList")
    public List<ReservationDTO> resList(@RequestParam("comNo") int comNo,
                                        @RequestParam(value = "todayStr", required = false) String todayStr,
                                        @RequestParam(value = "selectedDate", required = false, defaultValue="none") String selectedDate){
        List<ReservationDTO> list = ressvc.resList(comNo, todayStr, selectedDate);
        System.out.println("[5] :" + list);
        return list;
    }

    // 예약자 이름으로 예약 내역 검색
    @PostMapping("/resSearchList")
    public List<ReservationDTO> resSearchList(@RequestParam("keyword") String keyword,
                                              @RequestParam("selectedDate") String selectedDate) {
        List<ReservationDTO> list = ressvc.resSearchList(keyword, selectedDate);
        return list;
    }

    // 회원 탈퇴를 위한 비밀번호 체크(member)
    @PostMapping("/mpwCheck")
    public String mpwCheck(@ModelAttribute MemberDTO member) {
        String result = msvc.mpwCheck(member);
        return result;
    }

    // 회원 탈퇴를 위한 비밀번호 체크(company)
    @PostMapping("/cpwCheck")
    public String cpwCheck(@ModelAttribute CompanyDTO company) {
        String result = csvc.cpwCheck(company);
        return result;
    }

    // 예약 일정 삭제
    @PostMapping("/resDelete")
    public String resDelete(@ModelAttribute ReservationDTO reservation,
                            @RequestParam("cname") String cname) {
        System.out.println("resDate = " + reservation.getResDate());
        System.out.println("resTime = " + reservation.getResTime());
        System.out.println("cname = " + cname);
        String result = ressvc.resDelete(reservation, cname);
        return result;
    }



    @PostMapping("/crList")
    public List<ReservationDTO> crList(@RequestParam("today") String today){
        System.out.println(today);
        List<ReservationDTO> list = ressvc.crList(today);

        return list;
    }

    @PostMapping("/userPetSelect")
    public String userPetSelect(){

        String result = psvc.userPetSelect();

        return result;
    }

    @PostMapping("/mrecList")
    public List<RecordsDTO> mrecList(@RequestParam("dateString")String dateString,@RequestParam int mNo){
        List<RecordsDTO> list = rsvc.mrecList(dateString,mNo);
        return list;
    }

    @PostMapping("/recList")
    public List<RecordsDTO> recList(@RequestParam("dateString")String dateString,@RequestParam int comNo){
        List<RecordsDTO> list = rsvc.recList(dateString,comNo);
        return list;
    }

    @PostMapping("/chartCheck")
    public List<DoctorDTO> chartCheck(@RequestParam int comNo){
        System.out.println("chartCheck comNo : " + comNo);
        List<DoctorDTO> list = dsvc.chartCheck(comNo);

        return list;
    }

    @PostMapping("/pRegiList")
    public List<PetDTO> pRegiList(){
        System.out.println("[1] jsp → controller");
        List<PetDTO> list = psvc.pRegiList();
        return list;
    }

    // 의사 번호 확인
    @PostMapping("/dIdCheck2")
    public String dIdCheck2(@RequestParam("comNo") int comNo,
                            @RequestParam("dId") int dId) {
        System.out.println("comNo = " + comNo);
        String result = dsvc.dIdCheck2(comNo, dId);
        return result;
    }
}