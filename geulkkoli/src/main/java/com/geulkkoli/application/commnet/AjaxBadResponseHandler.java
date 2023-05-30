package com.geulkkoli.application.commnet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import java.util.*;

/**
 * ControllerAdvice 의 Rest API 버전
 * AOP 를 이용한 예외처리 방식
 * 모든 RestController 에서 이 클래스에 선언된 예외가 발생했다면 이 클래스에서 처리한다.
 * try-catch 를 대체할 수 있으며 그로 인해 코드 가독성이 좋아지는 이점이 있다.
 * <p>
 * RestController 에서 ajax 로 validation 처리를 위해 작성하게 되었다.
 * validation 을 통과하지 못하면 errors.properties 의 String 을 반환하고
 * 통과한다면 Controller 에 선언된 값을 반환한다.
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AjaxBadResponseHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<String> validException(MethodArgumentNotValidException methodArgumentNotValidException) {
        String message = methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        List<ObjectError> errors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            if ("NotBlank".equals(error.getCode())) {
                message = messageSource.getMessage("NotNull.CommentBodyDTO.commentBody", null, LocaleContextHolder.getLocale());
            } else if ("Size".equals(error.getCode())) {
                var attributes = error.unwrap(ConstraintViolation.class).getConstraintDescriptor().getAttributes();
                Integer min = (Integer) attributes.get("min");
                Integer max = (Integer) attributes.get("max");
                message = messageSource.getMessage("Length.CommentBodyDTO.commentBody.regexp",  new Object[]{min, max}, LocaleContextHolder.getLocale());
            }
        }

        return new ResponseEntity<>(
                message,
                HttpStatus.BAD_REQUEST);
    }
}
