package com.icia.petdicalbowwow.dao;

import com.icia.petdicalbowwow.model.ReservationModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReservationDAO {

    List<String> rGetDates(int comNo);

    List<String> rGetTimes(ReservationModel resModels);
}
