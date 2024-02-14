package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.VacationRepository;
import com.icia.petdicalbowwow.dto.BoardDTO;
import com.icia.petdicalbowwow.dto.BoardEntity;
import com.icia.petdicalbowwow.dto.VacationDTO;
import com.icia.petdicalbowwow.dto.VacationEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class VacationService {

    private final VacationRepository vrepo;
    private static final Logger log = LoggerFactory.getLogger(VacationService.class);

    @Transactional
    public String sRegister(List<String> dateList, int comNo) {
        vrepo.deleteBycomNo(comNo);
        VacationDTO vacation = new VacationDTO();
        VacationEntity entity = new VacationEntity();
        for(int i = 0; i < dateList.size(); i++) {
            System.out.println(dateList.get(i));
            vacation.setVDate(dateList.get(i));
            vacation.setComNo(comNo);
            entity = VacationEntity.toEntity(vacation);

            try {
                vrepo.save(entity);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        return "OK";
    }

    public JSONArray sReady(int comNo) {
        List<VacationDTO> list = new ArrayList<>();
        List<VacationEntity> entityList = vrepo.findAllBycomNo(comNo);
        for(VacationEntity entity : entityList) {
            list.add(VacationDTO.toDTO(entity));
        }

        JSONObject jsonObj;
        JSONArray jsonArr = new JSONArray();

        HashMap<String, Object> hash = new HashMap<>();

        for(int i = 0; i < list.size(); i++) {
            hash.put("title", "휴무일");
            hash.put("start", list.get(i).getVDate());
            String splitDate = "";
            String splitDateArr[] = list.get(i).getVDate().split("-");
            for(int j = 0; j < splitDateArr.length; j++) {
                splitDate += splitDateArr[j];
            }
            hash.put("splitDate", splitDate);
            hash.put("backgroundColor", "orange");

            jsonObj = new JSONObject(hash);
            jsonArr.put(jsonObj);
        }
        log.info("jsonArr: {}", jsonArr);
        return jsonArr;
    }

    public List<VacationDTO> vList(int comNo) {
        System.out.println("[2] controller → service : " + comNo);
        List<VacationDTO> list = new ArrayList<>();

        List<VacationEntity> entityList = vrepo.findAllBycomNo(comNo);
        System.out.println("[3] repository → service : " + entityList);

        for (VacationEntity entity : entityList){
            list.add(VacationDTO.toDTO(entity));
        }
        System.out.println("[4] entity → dto : " + list);

        return list;
    }
}
