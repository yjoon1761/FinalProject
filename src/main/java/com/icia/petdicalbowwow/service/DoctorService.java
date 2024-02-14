package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.DoctorDAO;
import com.icia.petdicalbowwow.dao.DoctorRepository;
import com.icia.petdicalbowwow.dto.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private ModelAndView mav;

    private final DoctorDAO ddao;

    private final DoctorRepository drepo;

    private final HttpSession session;

    public String getUUID(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/profile");

    public ModelAndView dRegister(DoctorDTO doctor, CompanyDTO company) {
        mav = new ModelAndView();

        MultipartFile dFile = doctor.getDFile();
        String savePath = null;

        if (!dFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName = dFile.getOriginalFilename();
            String dFileName = uuid + "_" + originalFileName;

            doctor.setDFileName(dFileName);

            savePath = path + "/" + dFileName;
        }

        DoctorEntity entity = DoctorEntity.toEntity(doctor, company);

        try {
            drepo.save(entity);
            dFile.transferTo(new File(savePath));
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        mav.setViewName("redirect:/dListForm");

        return mav;
    }

    public List<DoctorDTO> dList() {
        System.out.println("[1] html -> controller");
        List<DoctorDTO> list = new ArrayList<>();
        int comNo = (int)session.getAttribute("comNo");
        System.out.println("comNo="+comNo);

        List<DoctorEntity> entityList = drepo.findByCom_comNo(comNo);
        for (DoctorEntity entity : entityList) {
            list.add(DoctorDTO.toDTO(entity));
        }
        return list;
    }

    public List<DoctorDTO> dSearchList(SearchDTO search) {
        List<DoctorDTO> list = ddao.dSearchList(search);
        return list;
    }

    public ModelAndView dView(int dNo) {
        mav = new ModelAndView();
        Optional<DoctorEntity> entity = drepo.findById(dNo);
        DoctorDTO doctor = new DoctorDTO();
        doctor = DoctorDTO.toDTO(entity.get());
        mav.setViewName("doctor/view");
        mav.addObject("view", doctor);

        return mav;
    }

    public ModelAndView dModiForm(int dNo) {
        mav = new ModelAndView();

        Optional<DoctorEntity> entity = drepo.findById(dNo);
        DoctorDTO doctor = new DoctorDTO();
        doctor = DoctorDTO.toDTO(entity.get());
        mav.setViewName("doctor/modify");
        mav.addObject("modify", doctor);

        return mav;
    }

    public ModelAndView dModify(DoctorDTO doctor, CompanyDTO company) {
        mav = new ModelAndView();

        if (!doctor.getDFileName().isEmpty()){
            String delPath = path + "/" + doctor.getDFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        MultipartFile dFile = doctor.getDFile();
        String savePath = null;

        if (!dFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName = dFile.getOriginalFilename();
            String dFileName = uuid + "_" + originalFileName;

            doctor.setDFileName(dFileName);

            savePath = path + "/" + dFileName;
        }

        DoctorEntity entity = DoctorEntity.toEntity(doctor, company);

        try {
            drepo.save(entity);
            dFile.transferTo(new File(savePath));
            mav.setViewName("redirect:/dView/"+doctor.getDNo());
        }catch (Exception e){
            mav.setViewName("doctor/list");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public ModelAndView dDelete(int dNo) {
        mav = new ModelAndView();

        Optional<DoctorEntity> entity = drepo.findById(dNo);

        if (!entity.get().getDFileName().isEmpty()){
            String delPath = path + "/" + entity.get().getDFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        drepo.deleteById(dNo);

        mav.setViewName("doctor/list");

        return mav;
    }

    public String dIdCheck(int dId) {
        String doctor = ddao.dIdCheck(dId);
        System.out.println(doctor);
        if(doctor == null){
            doctor = "OK";
        }

        return doctor;
    }

    public String dIdCheck2(int comNo, int dId) {
        String result = "NO";
        Optional<DoctorEntity> entity = drepo.findByComNo(comNo, dId);
        if (entity.isPresent()) {
            result = "OK";
        }
        return result;
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

    public List<DoctorDTO> chartCheck(int comNo) {

        List<DoctorEntity> entityList = drepo.findByCom_comNo(comNo);

        List<DoctorDTO> list = new ArrayList<>();

        for (DoctorEntity entity : entityList) {
            list.add(DoctorDTO.toDTO(entity));
        }

        return list;


    }
}
