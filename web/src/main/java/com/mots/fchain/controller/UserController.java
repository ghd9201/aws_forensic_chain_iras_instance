package com.mots.fchain.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mots.fchain.common.ServletUtil;
import com.mots.fchain.model.User;
import com.mots.fchain.service.EvidenceService;
import com.mots.fchain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EvidenceService evidenceService;

    private static User user;


    private static final long serialVersionUID = 1L;

    // create GSON object
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping(value = "/index")
    public ModelAndView indexPage() throws Exception{
        ModelAndView mv = new ModelAndView("/index");
        return mv;
    }

    @GetMapping(value = "/index/login.do")
    public ModelAndView loginUser(HttpServletResponse resposne, HttpServletRequest req) throws Exception {
        ModelAndView mv = new ModelAndView("/index");

        return mv;
    }

    @PostMapping(value = "/index/login.do")
    public ModelAndView loginUser(@RequestParam String userId, @RequestParam String password, HttpServletResponse resposne, HttpServletRequest req) throws Exception{
        ModelAndView mv = new ModelAndView("/index");

        System.out.println("userId"+userId);
        System.out.println("password"+userId);

        this.user = new User(userService.login(userId, password));

        /* 로그인 완료 세션 저장 처리 */
        req.getSession().setAttribute("user", user);

        mv.addObject("user", user);

        return mv;
    }

    @GetMapping(value = "/mypage")
    public ModelAndView mainPage(HttpServletRequest req) throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/mypage");



        if(this.user == null){
            this.user = (User)req.getSession().getAttribute("user");
        }

        /* 로그인 완료 이벤트 처리 */
        mv.addObject("user",user);
        return mv;
    }

    @GetMapping(value = "/index/signupForm.do")
    public ModelAndView signupForm(@RequestParam User user) throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/index");

        /* 회원가입 입력 유효성 검증 실패 처리 */

        userService.signup(user);

        /* 회원가입 실패 이벤트 처리 */

        /* 회원가입 완료 이벤트 처리 */

        return mv;
    }

    /* 문서 조회 */
    @RequestMapping(value = "mypage/selectAllDocuments")
    protected void service(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // set encoding type to UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        // Parameter
        HashMap<String, String> p = ServletUtil.paramToHashMapUtf8(request);

        ArrayList<HashMap> result = evidenceService.selectAllEvidences();
        //ArrayList<HashMap> result = dao.commonSelectList("sql-document." + this.getClass().getSimpleName(), p);
        System.out.println( result );
        response.getWriter().write(gson.toJson(result));
    }
}

