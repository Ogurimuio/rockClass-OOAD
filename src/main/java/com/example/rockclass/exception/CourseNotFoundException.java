package com.example.rockclass.exception;

public class CourseNotFoundException extends Exception {

    private  static final long serialVersionUID =1L;

    private  String errorCode;

    public CourseNotFoundException(){ super();}

    public CourseNotFoundException(String message){ super(message);}

    public CourseNotFoundException(String errorCode,String message){
        super(message);
        this.errorCode=errorCode;
    }

    public CourseNotFoundException(String errorCode,String message,Throwable cause){
        super(message);
        this.errorCode=errorCode;
    }

    public CourseNotFoundException(String message,Throwable cause){ super(message);}

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}
