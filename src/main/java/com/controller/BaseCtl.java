package com.controller;

import com.common.BusinessException;
import com.domain.ResultModel;
import com.domain.ResultStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zengjie on 17/6/22.
 */
@Component
public class BaseCtl {

    @ExceptionHandler
    public ResponseEntity<ResultModel> exp(HttpServletRequest request, Exception ex) {
        request.setAttribute("ex", ex);
        ex.printStackTrace();
        // 根据不同错误转向不同页面
        if(ex instanceof BusinessException) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.OBJECT_PARAM_ERROR), HttpStatus.OK);
        }else {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USER_NOT_FOUND), HttpStatus.OK);
        }
    }
}
