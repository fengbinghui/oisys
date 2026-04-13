package cn.njcit.exception;

import cn.njcit.util.ResponseResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public ResponseResult handleBizException(BizException ex) {
        return ResponseResult.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleValidationException(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError == null ? "请求参数不合法" : fieldError.getDefaultMessage();
        return ResponseResult.error(400, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception ex) {
        return ResponseResult.error("系统异常，请稍后重试");
    }
}
