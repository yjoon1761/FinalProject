package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.CardRepository;
import com.icia.petdicalbowwow.dao.PetDAO;
import com.icia.petdicalbowwow.dao.PetRepository;
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
public class PetService {

    private ModelAndView mav;

    private final PetRepository prepo;

    private final PetDAO pdao;

    private final HttpSession session;
    
    private final CardRepository crdrepo;

    public String getUUID(){
        return UUID.randomUUID().toString().substring(0,8);
    }

    public Path path = Paths.get(System.getProperty("user.dir"),"src/main/resources/static/profile");

    public ModelAndView pRegister(PetDTO pet, MemberDTO member) {
        mav = new ModelAndView();
        System.out.println("service1");
        MultipartFile pFile = pet.getPFile();
        String savePath = null;

        if (!pFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName = pFile.getOriginalFilename();
            String pFileName = uuid + "_" + originalFileName;

            pet.setPFileName(pFileName);

            savePath = path + "/" + pFileName;
        }

        PetEntity entity = PetEntity.toEntity(pet,member);
        System.out.println("service2");
        try {
            prepo.save(entity);
            pFile.transferTo(new File(savePath));
            System.out.println("service3");
        } catch (Exception e){
            System.out.println("service4");
            throw new RuntimeException(e);
        }
        System.out.println("service5");
        mav.setViewName("redirect:/pListForm");

        return mav;
    }


    public List<PetDTO> pList() {
        System.out.println("[1] html -> controller");
        List<PetDTO> list = new ArrayList<>();
        int mNo = (int) session.getAttribute("loginNo");
        System.out.println("mNo = " + mNo);

        List<PetEntity> entityList = prepo.findByMemMNO(mNo);

        for (PetEntity entity : entityList){
            list.add(PetDTO.toDTO(entity));
        }

        System.out.println("mNo = " + mNo + "\npet list = " + list);
        return list;
    }

    public List<PetDTO> pSearchList(SearchDTO search) {
        List<PetDTO> list = pdao.pSearchList(search);
        return list;
    }

    public ModelAndView pView(int pNo) {
        mav = new ModelAndView();
        Optional<PetEntity> entity = prepo.findById(pNo);

        PetDTO pet = new PetDTO();
        pet = PetDTO.toDTO(entity.get());

        mav.setViewName("pet/view");
        mav.addObject("view", pet);

        return mav;
    }

    public ModelAndView pModiForm(int pNo) {
        mav = new ModelAndView();
        Optional<PetEntity> entity = prepo.findById(pNo);

        PetDTO pet = new PetDTO();
        pet = PetDTO.toDTO(entity.get());

        mav.setViewName("pet/modify");
        mav.addObject("view", pet);

        return mav;
    }

    public ModelAndView pModify(PetDTO pet,MemberDTO member) {
        if (!pet.getPFileName().isEmpty()){
            String delPath = path + "/" + pet.getPFileName();

            File delFile = new File(delPath);

            if(delFile.exists()){
                delFile.delete();
            }
        }

        MultipartFile pFile = pet.getPFile();
        String savePath = null;

        if (!pFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName = pFile.getOriginalFilename();
            String pFileName = uuid + "_" + originalFileName;

            pet.setPFileName(pFileName);

            savePath = path + "/" + pFileName;
        }

        PetEntity entity = PetEntity.toEntity(pet,member);

        try{
            prepo.save(entity);
            pFile.transferTo(new File(savePath));
            mav.setViewName("redirect:/pView/"+pet.getPNo());
        } catch (Exception e){
            mav.setViewName("pet/list");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public ModelAndView pDelete(int pNo) {
        mav = new ModelAndView();

        Optional<PetEntity> entity = prepo.findById(pNo);

        if (!entity.get().getPFileName().isEmpty()){
            String delPath = path + "/" + entity.get().getPFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        prepo.deleteById(pNo);

        mav.setViewName("redirect:/");

        return mav;
    }

    public PetDTO setInfo(int pNo, int MNO) {
        PetDTO pet = new PetDTO();

        PetEntity entity = prepo.findByPNoAndMNO(pNo, MNO);

        pet = PetDTO.toDTO(entity);

        String date = pet.getPBirth().substring(0, 4);

        Calendar cal = Calendar.getInstance();

        int yyyy = cal.get(Calendar.YEAR);

        int date1 = yyyy - Integer.parseInt(date) + 1;

        pet.setPBirth(pet.getPBirth() +" (" + String.valueOf(date1) + "살)");

        return pet;
    }



    public List<PetDTO> petSearch(String keyword) {
        List<PetDTO> list = new ArrayList<>();

        List<PetEntity> entityList = prepo.findByPName(keyword);

        for (PetEntity entity : entityList){
            list.add(PetDTO.toDTO(entity));
        }

        return list;
    }

    public String userPetSelect() {
        String result = "NO";

        int mno = (int)session.getAttribute("loginNo");

        List<PetEntity> entityList = prepo.findByMNO(mno);

        if (entityList.isEmpty()){
            result = "OK";
        }

        return result;
    }

    public List<PetDTO> pRegiList() {
        System.out.println("[1] html -> controller");
        List<PetDTO> list = new ArrayList<>();
        int mNo = (int) session.getAttribute("loginNo");
        System.out.println("mNo = " + mNo);

        List<PetEntity> entityList = prepo.findByMemMNO(mNo);

        List<CardEntity> entities = crdrepo.findAllByCrdCheckMNo(mNo);

        if (entities.isEmpty()){
            for (PetEntity entity : entityList){
                list.add(PetDTO.toDTO(entity));
            }
        }else {
            for (PetEntity entity : entityList) {
                boolean found = false;
                for (CardEntity cardEntity : entities) {
                    if (Objects.equals(entity.getPNo(), cardEntity.getPet().getPNo())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    list.add(PetDTO.toDTO(entity));
                }
            }
        }
        return list;
    }
}
