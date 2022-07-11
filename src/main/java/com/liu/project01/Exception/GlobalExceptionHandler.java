//package com.liu.project01.Exception;//@date :2022/4/26 14:39
//
//
//import com.liu.project01.vo.RespBean;
//import com.liu.project01.vo.RespBeanEnum;
//import org.springframework.validation.BindException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//
////异常处理
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(Exception.class)
//    public RespBean ExceptionHandler(Exception e) {
//        if (e instanceof GlobalException) {
//            GlobalException ex = (GlobalException) e;
//            return RespBean.error(ex.getRespBeanEnum());
//        } else if (e instanceof BindException) {
//            BindException ex = (BindException) e;
//            RespBean error = RespBean.error(RespBeanEnum.BINDERROR);
//            error.setMessage("参数校验异常:" + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
//            return error;
//        }
//        return RespBean.error(RespBeanEnum.ERROR);
//    }
//}
