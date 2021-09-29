package com.tulingxueyuan.mall.common.exception;


import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常处理
 * Created by macro on 2020/2/27.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
      //应该是在捕获异常的时候可以使用传入错误信息
    /**
     * 处理已知异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        if (e.getErrorCode() != null) {
            System.out.println("状态码"+e.getErrorCode());
            return CommonResult.failed(e.getErrorCode());
        }
        System.out.println("错误信息"+e.getMessage());
        return CommonResult.failed(e.getMessage());
    }


    /**
     * 处理未知异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult handleRuntimeException(RuntimeException e) {
        log.error("运行时异常：",e);
        return CommonResult.failed(ResultCode.UNKNOWN);
    }


    /**
     * 验证jsr303失败的异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

    /**
     * 数据绑定异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public CommonResult handleValidException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField()+fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }
}
