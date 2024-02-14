package com.icia.petdicalbowwow.service;

import com.icia.petdicalbowwow.dao.BoardDAO;
import com.icia.petdicalbowwow.dao.BoardRepository;
import com.icia.petdicalbowwow.dto.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private ModelAndView mav;

    private final BoardDAO bdao;

    private final BoardRepository brepo;

    private final HttpSession session;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public Path path = Paths.get(System.getProperty("user.dir"), "src/main/resources/static/upload");

    public String getUUID() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public ModelAndView bRegister(BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {
        mav = new ModelAndView();

        String savePath = null;

        if (!board.getBFile().isEmpty()) {
            MultipartFile bFile = board.getBFile();
            String uuid = getUUID();
            String orginalFileName = bFile.getOriginalFilename();
            String bFileName = uuid + "_" + orginalFileName;

            board.setBFileName(bFileName);

            savePath = path + "/" + bFileName;
        }else {
            String bFileName = "default";

            board.setBFileName(bFileName);
        }
        System.out.println("board date = " + board.getBDate());
        BoardEntity entity = BoardEntity.toEntity(board, member, company, category);

        try {
            System.out.println("entity date = " + entity.getBDate());
            brepo.save(entity);
            if (!board.getBFile().isEmpty()){
                MultipartFile bFile = board.getBFile();
                bFile.transferTo(new File(savePath));
            }
            if (category.getCatNo() == 1){
                mav.setViewName("redirect:/bList/" + 1);
            } else if (category.getCatNo() == 2) {
                mav.setViewName("redirect:/bList/" + 2);
            } else if (category.getCatNo() == 3) {
                mav.setViewName("redirect:/bList/" + 3);
            }
        } catch (Exception e) {
            mav.setViewName("board/register");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public List<BoardDTO> boList(int catNo) {
        System.out.println("[2] controller → service");
        List<BoardDTO> list = new ArrayList<>();
        System.out.println("catNo = " + catNo);
        List<BoardEntity> entityList = brepo.findAllByCatNoOrderByBNoDesc(catNo);

        for (BoardEntity entity : entityList){
            list.add(BoardDTO.toDTO(entity));
        }
        System.out.println("[4] entity → dto : " + list);

        return list;
    }

    public List<BoardDTO> membList(int MNO) {
        System.out.println("[2] controller → service");
        List<BoardDTO> list = new ArrayList<>();

        System.out.println("service = " + MNO);

        List<BoardEntity> entityList = brepo.findAllByMNOOrderByBNoDesc(MNO);

        for (BoardEntity entity : entityList){
            list.add(BoardDTO.toDTO(entity));
        }
        System.out.println("[4] entity → dto : " + list);

        return list;
    }

    public List<BoardDTO> mybListc(int comNo) {
        System.out.println("[2] controller → service");
        List<BoardDTO> list = new ArrayList<>();

        System.out.println("service = " + comNo);

        List<BoardEntity> entityList = brepo.findAllByComNoOrderByBNoDesc(comNo);

        for (BoardEntity entity : entityList){
            list.add(BoardDTO.toDTO(entity));
        }
        System.out.println("[4] entity → dto : " + list);

        return list;
    }

    public List<BoardDTO> bSearchList(SearchDTO search) {
        List<BoardDTO> list = new ArrayList<>();

        List<BoardEntity> entityList = new ArrayList<>();

        if(search.getCategory().equals("BTITLE")){
            entityList = brepo.findBybTitleContainingAndCatNo(search.getKeyword(), search.getCatNo());
        } else if(search.getCategory().equals("BCONTENT")){
            entityList = brepo.findBybContentContainingAndCatNo(search.getKeyword(), search.getCatNo());
        } else if(search.getCategory().equals("BWRITER")){
            System.out.println("BWRITER$$");
            entityList = brepo.findA(search.getKeyword(), search.getCatNo());
        }

        for (BoardEntity entity : entityList){
            list.add(BoardDTO.toDTO(entity));
        }

        return list;
    }

    public ModelAndView bView(int bNo) {
        mav = new ModelAndView();

        String loginId = (String)session.getAttribute("loginId");

        if (loginId==null){
            loginId = "Guest";
        }

        Cookie[] cookies = request.getCookies();

        Cookie viewCookie = null;

        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("cookie_" + loginId + "_" + bNo)){
                    viewCookie = cookie;
                }
            }
        }

        if (viewCookie==null){
            Cookie newCookie = new Cookie("cookie_" + loginId + "_" + bNo, "cookie_" + loginId + "_" + bNo);
            newCookie.setMaxAge(60 * 60 * 1);
            response.addCookie(newCookie);

            brepo.bHit(bNo);
        }

        Optional<BoardEntity> entity = brepo.findById(bNo);
        BoardDTO board = BoardDTO.toDTO(entity.get());

        String bDate = String.valueOf(board.getBDate());
        board.setBDate(Date.valueOf(bDate.substring(0,10)));
        System.out.println(board);

        if (session.getAttribute("loginNo") == null && session.getAttribute("comNo") == null){
            session.setAttribute("loginNo", -2);
            session.setAttribute("comNo", -2);
        }


        mav.setViewName("board/bview");
        mav.addObject("view", board);

        return mav;
    }

    public ModelAndView bModiForm(int bNo) {
        mav = new ModelAndView();

        Optional<BoardEntity> entity = brepo.findById(bNo);
        BoardDTO board = BoardDTO.toDTO(entity.get());

        mav.setViewName("board/modify");
        mav.addObject("modify", board);

        return mav;
    }

    public ModelAndView bModify(BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {

        String savePath = null;

        if (!board.getBFile().isEmpty()){
            if (!Objects.equals(board.getBFileName(), "default")){
                String delPath = path + "/" + board.getBFileName();

                File delFile = new File(delPath);

                if (delFile.exists()){
                    delFile.delete();
                }

                MultipartFile bFile = board.getBFile();

                if (!bFile.isEmpty()){
                    String uuid = getUUID();
                    String originalFileName = bFile.getOriginalFilename();
                    String bFileName = uuid + "_" + originalFileName;

                    board.setBFileName(bFileName);

                    savePath = path + "/" + bFileName;
                }
            }
        }


        BoardEntity entity = BoardEntity.toEntity(board, member, company, category);
        System.out.println("entity = " + entity.toString());
        try {
            brepo.save(entity);
            if (!board.getBFile().isEmpty()){
                MultipartFile bFile = board.getBFile();
                bFile.transferTo(new File(savePath));
            }
            mav.setViewName("redirect:/bView/"+board.getBNo());
        }catch (Exception e){
            mav.setViewName("redirect:/");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public ModelAndView bDelete(int bNo) {
        mav = new ModelAndView();

        Optional<BoardEntity> entity = brepo.findById(bNo);

        if (!entity.get().getBFileName().isEmpty()){
            String delPath = path + "/" + entity.get().getBFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        brepo.deleteById(bNo);

//        session.invalidate();
        mav.setViewName("redirect:/bList/" + entity.get().getCat().getCatNo());

        return mav;
    }

    public ModelAndView gView(int bNo) {
        mav = new ModelAndView();

        String loginId = (String)session.getAttribute("loginId");

        if (loginId==null){
            loginId = "Guest";
        }

        Cookie[] cookies = request.getCookies();

        Cookie viewCookie = null;

        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("cookie_" + loginId + "_" + bNo)){
                    viewCookie = cookie;
                }
            }
        }

        if (viewCookie==null){
            Cookie newCookie = new Cookie("cookie_" + loginId + "_" + bNo, "cookie_" + loginId + "_" + bNo);
            newCookie.setMaxAge(60 * 60 * 1);
            response.addCookie(newCookie);

            brepo.bHit(bNo);
        }

        Optional<BoardEntity> entity = brepo.findById(bNo);
        BoardDTO board = BoardDTO.toDTO(entity.get());

        String bDate = String.valueOf(board.getBDate());
        board.setBDate(Date.valueOf(bDate.substring(0,10)));
        System.out.println(board);

        if (session.getAttribute("loginNo") == null && session.getAttribute("comNo") == null){
            session.setAttribute("loginNo", -2);
            session.setAttribute("comNo", -2);
        }


        mav.setViewName("board/gview");
        mav.addObject("view", board);

        return mav;
    }

    public ModelAndView gModiForm(int bNo) {
        mav = new ModelAndView();

        Optional<BoardEntity> entity = brepo.findById(bNo);
        BoardDTO board = BoardDTO.toDTO(entity.get());

        mav.setViewName("board/modify");
        mav.addObject("modify", board);

        return mav;
    }

    public ModelAndView gModify(BoardDTO board, MemberDTO member, CompanyDTO company, CategoryDTO category) {
        System.out.println("gModify 시작");
        System.out.println("board = " + board);
        mav = new ModelAndView();

        if (!board.getBFileName().isEmpty()){
            String delPath = path + "/" + board.getBFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        MultipartFile bFile = board.getBFile();
        String savePath = null;

        if (!bFile.isEmpty()){
            String uuid = getUUID();
            String originalFileName = bFile.getOriginalFilename();
            String bFileName = uuid + "_" + originalFileName;

            board.setBFileName(bFileName);

            savePath = path + "/" + bFileName;
        }
        BoardEntity entity = BoardEntity.toEntity(board, member, company, category);
        System.out.println("entity = " + entity.toString());
        try {
            brepo.save(entity);
            bFile.transferTo(new File(savePath));
            mav.setViewName("redirect:/gView/"+board.getBNo());
        }catch (Exception e){
            mav.setViewName("redirect:/");
            throw new RuntimeException(e);
        }

        return mav;
    }

    public ModelAndView gDelete(int bNo) {
        mav = new ModelAndView();

        Optional<BoardEntity> entity = brepo.findById(bNo);

        if (!entity.get().getBFileName().isEmpty()){
            String delPath = path + "/" + entity.get().getBFileName();

            File delFile = new File(delPath);

            if (delFile.exists()){
                delFile.delete();
            }
        }

        brepo.deleteById(bNo);

        mav.setViewName("redirect:/bList/" + entity.get().getCat().getCatNo());

        return mav;

    }

}


