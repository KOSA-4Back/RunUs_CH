package com.mysite.chat.global.error.handler;

import org.springframework.stereotype.Controller;

/**
 * packageName    : org.omsf.error.handler
 * fileName       : ErrorController
 * author         : Yeong-Huns
 * date           : 2024-06-30
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-30        Yeong-Huns       최초 생성
 */
@Controller
public class ErrorHandler { // 추후 에러페이지 생성 후 매핑
    /*@GetMapping("/errorTestPage")
    public String showErrorTestPage() {
        return "error/errorTestPage";
    }
    

    @GetMapping("/error/403")
    public ModelAndView show403ErrorPage(ErrorCode errorCode, String detailMessage) {
    	ModelAndView mav = new ModelAndView("error/errorPage"); // 에러 페이지로 이동
    	
        mav.addObject("code", ErrorCode.METHOD_NOT_ALLOWED.getCode());
        mav.addObject("message", ErrorCode.METHOD_NOT_ALLOWED.getMessage());
        mav.addObject("detail", detailMessage);
        return mav;
    }*/
}
