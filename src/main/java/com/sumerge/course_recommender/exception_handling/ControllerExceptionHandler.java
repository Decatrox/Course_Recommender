//package com.sumerge.course_recommender.exception_handling;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@ControllerAdvice
//public class ControllerExceptionHandler {
//
////    @ExceptionHandler(MethodArgumentNotValidException.class);
////    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
////    @ResponseBody
//    @ExceptionHandler()
//    public ErrorMessage handleValidationError(MethodArgumentNotValidException ex) {
//        BindingResult bindingResult = ex.getBindingResult();
//        FieldError fieldError = bindingResult.getFieldError();
//        return new ErrorMessage("Validation Failed", fieldError.getDefaultMessage());
//    }
//}
