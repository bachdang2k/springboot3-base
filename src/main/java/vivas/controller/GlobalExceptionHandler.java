package vivas.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import vivas.common.enums.common.SysEnum;
import vivas.config.GeneralResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleExceptions(Exception anExc, WebRequest request) {
        log.error("Loi xay ra", anExc);
        Integer code = SysEnum.ERROR__2.getCode();
        String message = String.format(SysEnum.ERROR__2.getMessage(), anExc.getMessage());
        /*
        Integer code = ExpEnum.EXCEPTION.getCode();
        String message = String.format("%s: %s", ExpEnum.EXCEPTION.getMessage(), anExc.getMessage());
        if (anExc instanceof InvalidFormatException) {
            logger.info(anExc.getMessage() + " [InvalidFormatException]");
        } else if (anExc instanceof HttpMessageNotReadableException) {
            logger.info(anExc.getMessage() + " [HttpMessageNotReadableException]");
        } else if (anExc instanceof HttpMediaTypeNotSupportedException) {
            logger.info(anExc.getMessage() + " [HttpMediaTypeNotSupportedException]");
        } else if (anExc instanceof MethodArgumentNotValidException) {
            logger.info(anExc.getMessage() + " [MethodArgumentNotValidException]");
        } else if (anExc instanceof HttpRequestMethodNotSupportedException) {
            logger.info(anExc.getMessage() + " [HttpRequestMethodNotSupportedException]");
        } else if (anExc instanceof AccessDeniedException) {
            logger.info(anExc.getMessage() + " [AccessDeniedException]");
            code = ExpEnum.ERROR_999996.getCode();
            message = ExpEnum.ERROR_999996.getMessage();
        } else {
            logger.error("Loi xay ra", anExc);
        }
        String content = AppHelper.response(code, message);
        logger.info("Ket qua: {}", content);

         */
        return ResponseEntity.ok(GeneralResponse.builder().code(code).message(message).build());
    }
}
