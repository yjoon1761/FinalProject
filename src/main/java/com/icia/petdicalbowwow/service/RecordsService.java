package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.*;
import com.icia.petdicalbowwow.dto.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.beans.Transient;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordsService {

    private ModelAndView mav;

    private final DoctorRepository drepo;

    private final RecordsRepository rrepo;

    private final RecordsDAO rdao;

    private final HttpSession session;

    @Transactional
    public ModelAndView recRegister(RecordsDTO records, PetDTO pet, CompanyDTO company) {
        mav = new ModelAndView();
        System.out.println("service" + records);
        System.out.println("service" + pet);
        System.out.println("service" + company);
        RecordsEntity entity = RecordsEntity.toEntity(records, pet, company);
        System.out.println("service : " + entity);
        try {
            rrepo.save(entity);
            System.out.println("save : " + entity);
        } catch (Exception e) {
            System.out.println("end");
            throw new RuntimeException(e);
        }
        mav.setViewName("records/clist");

        return mav;
    }

    //날짜별로 list 뽑기 (dateString, comNo)
    public List<RecordsDTO> recList(String dateString, int comNo) {
        System.out.println("[1]html -> controller");
        List<RecordsDTO> list = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return null; // or throw an exception
        }

        List<RecordsEntity> entityList = rrepo.findAllByrDate(dateString, comNo);

        for (RecordsEntity entity : entityList) {
            list.add(RecordsDTO.toDTO(entity));
        }

        System.out.println("sssss");
        System.out.println(list);

        return list;

    }


    public List<RecordsDTO> rSearchList(SearchDTO search) {

        List<RecordsDTO> list = rdao.rSearchList(search);
        return list;
    }

    public ModelAndView recView(int rNo) {
        mav = new ModelAndView();
        Optional<RecordsEntity> entity = rrepo.findById(rNo);

        RecordsDTO records = new RecordsDTO();
        records = RecordsDTO.toDTO(entity.get());

        mav.addObject("view", records);
        mav.addObject("view", records);
        mav.setViewName("records/view");

        return mav;
    }


    public ModelAndView recModiForm(int rNo) {
        mav = new ModelAndView();
        Optional<RecordsEntity> entity = rrepo.findById(rNo);

        RecordsDTO records = new RecordsDTO();
        records = RecordsDTO.toDTO(entity.get());

        mav.setViewName("records/modify");
        mav.addObject("view", records);

        return mav;
    }


    public ModelAndView recModify(RecordsDTO records, PetDTO pet, CompanyDTO company) {
        mav = new ModelAndView();
        System.out.println("service2" + records);
        System.out.println("service2" + pet);
        System.out.println("service2" + company);
        RecordsEntity entity = RecordsEntity.toEntity(records, pet, company);

        try {
            rrepo.save(entity);
            mav.setViewName("redirect:/recView/" + records.getRNo());
            System.out.println("수정 : " + records);

        } catch (Exception e) {
            mav.setViewName("records/list");
            throw new RuntimeException(e);
        }


        return mav;
    }


    public List<RecordsDTO> memRecList() {
        System.out.println("[1]html -> controller");
        List<RecordsDTO> list = new ArrayList<>();
        int mNo = (int) session.getAttribute("loginNo");
        System.out.println("mNo="+mNo);


        List<RecordsEntity> entityList = rrepo.findByMemMNO(mNo);

        for (RecordsEntity entity : entityList) {
            list.add(RecordsDTO.toDTO(entity));
        }

        return list;
    }


    public List<RecordsDTO> comRecList() {
        System.out.println("[1]html -> controller");
        List<RecordsDTO> list = new ArrayList<>();
        int comNo = (int)session.getAttribute("comNo");
        System.out.println("comNo = " + comNo);

        ////////////////////////////////

        List<RecordsEntity> entityList = rrepo.findByCom_comNo(comNo);

        for (RecordsEntity entity : entityList) {
            list.add(RecordsDTO.toDTO(entity));
        }
        System.out.println("comNo = " + comNo + "\nlist = " + list);


        return list;
    }

    public List<RecordsDTO> mrecList(String dateString, int mNo) {
        System.out.println("[1]html -> controller");
        List<RecordsDTO> list = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return null; // or throw an exception
        }

        List<RecordsEntity> entityList = rrepo.findAllByrDate1(dateString,mNo);

        for(RecordsEntity entity : entityList){
            list.add(RecordsDTO.toDTO(entity));
        }

        System.out.println("11111");
        System.out.println(list);

        return list;
    }

    public ModelAndView recRegiForm(int dId) {
        System.out.println("[2] controller → service");
        mav = new ModelAndView();

        Optional<DoctorEntity> entity = drepo.findAllBydId(dId);
        System.out.println("dId : " +  dId);

        DoctorDTO doctor = new DoctorDTO();

        if (entity.isPresent()){
            doctor = DoctorDTO.toDTO(entity.get());
        }
        System.out.println(doctor);
        mav.addObject("doctor", doctor);
/*        mav.addObject("rName",doctor.getDName());
        mav.addObject("comNo",doctor.getCompany().getComNo());*/

        mav.setViewName("records/register");

        return mav;

    }
}
