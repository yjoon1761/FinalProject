package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.MemberRepository;
import com.icia.petdicalbowwow.dao.ReservationDAO;
import com.icia.petdicalbowwow.dao.ReservationRepository;
import com.icia.petdicalbowwow.dto.*;
import com.icia.petdicalbowwow.model.ReservationModel;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationDAO resdao;
    private final ReservationRepository resrepo;
    private final MemberRepository mrepo;
    private ModelAndView mav;
    private final HttpSession session;

    public JSONArray rReady(int comNo) {
        MultiValueMap<String, String> list = new LinkedMultiValueMap<>();

        // 날짜 리스트 받아오기
        List<String> datesList = new ArrayList<>();
        datesList = resdao.rGetDates(comNo);
        System.out.println("datesList = " + datesList);

        // 시간 리스트 받아오기
        JSONObject jsonObj;
        JSONArray jsonArr = new JSONArray();
        for(int i = 0; i < datesList.size(); i++) {
            List<String> timesList = new ArrayList<>();
            ReservationModel resModels = new ReservationModel();
            resModels.setComNo(comNo);
            resModels.setResDate(datesList.get(i));
            timesList = resdao.rGetTimes(resModels);
            System.out.println("timeList = " + timesList);
            for(int j = 0; j < timesList.size(); j++) {
                list.add(datesList.get(i), timesList.get(j));
            }
        }
        jsonObj = new JSONObject(list);
        jsonArr.put(jsonObj);
        System.out.println("multivaluemap = " + list);
        return jsonArr;
    }

    public ModelAndView rRegister(ReservationDTO reservation, PetDTO pet, CompanyDTO company) {
        mav = new ModelAndView();
        ReservationEntity entity = ReservationEntity.toEntity(reservation, pet, company);
        resrepo.save(entity);
        mav.setViewName("redirect:/");
        return mav;
    }


    public ModelAndView rRegister2(ReservationDTO reservation, PetDTO pet, CompanyDTO company) {
        mav = new ModelAndView();

        company.setComNo((Integer) session.getAttribute("comNo"));

        ReservationEntity entity = ReservationEntity.toEntity(reservation, pet, company);

        resrepo.save(entity);


        mav.setViewName("redirect:/cIndexForm");

        return mav;
    }

    public List<ReservationDTO> resList(int comNo, String todayStr, String selectedDate) {
        List<ReservationDTO> list = new ArrayList<>();

        List<ReservationEntity> entityList = resrepo.findAllByComNo(comNo);
        ReservationEntity entity = new ReservationEntity();
        System.out.println("service");
        for (int i = 0; i < entityList.size(); i++) {
            entity = entityList.get(i);
            if(selectedDate.equals(entity.getResDate())) {
                list.add(ReservationDTO.toDTO(entity));
            } else if(selectedDate.equals("none") && entity.getResDate().equals(todayStr)) {
                list.add(ReservationDTO.toDTO(entity));
            }
        }
        return list;
    }

    public List<ReservationDTO> resSearchList(String keyword, String selectedDate) {
        List<ReservationDTO> list = new ArrayList<>();
        List<ReservationEntity> entityList = resrepo.findAllByKeyword(keyword, selectedDate);

        for(ReservationEntity entity : entityList) {
            list.add(ReservationDTO.toDTO(entity));
        }
        return list;
    }

    @Transactional
    public String resDelete(ReservationDTO reservation, String cname) {
        String result = "NO";
        try{
            resrepo.deleteByVals(reservation.getResDate(), reservation.getResTime(), cname);
            result = "OK";
        } catch(Exception e){
            throw new RuntimeException(e);
        }
        return result;
    }

    public List<ReservationDTO> crList(String today) {
        System.out.println("## [2]");
        List<ReservationDTO> list = new ArrayList<>();

        List<ReservationEntity> entityList = resrepo.findAllByResDate(today);
        System.out.println("## [3]");

        for (ReservationEntity entity : entityList) {
            list.add(ReservationDTO.toDTO(entity));
        }
        System.out.println("## [4]");

        return list;
    }

    @Transactional
    public List<ReservationDTO> rList(int mno) {
        List<ReservationDTO> list = new ArrayList<>();

        List<ReservationEntity> entityList = resrepo.findAllByMember(mno);

        for (ReservationEntity entity : entityList){
            list.add(ReservationDTO.toDTO(entity));
        }

        return list;
    }

    public ReservationDTO rList2(int resNo) {
        Optional<ReservationEntity> entity = resrepo.findById(resNo);

        ReservationDTO list = ReservationDTO.toDTO(entity.get());

        return list;
    }
}
