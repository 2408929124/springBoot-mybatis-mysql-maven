package com.lou.springboot.common;

import org.springframework.util.StringUtils;

public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String DEFAULT_FAIL_MESSAGE = "FAIL";

    public static Result getFailResult(String message){
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_PARAM_ERROR);
        if (StringUtils.isEmpty(message)) {
            result.setMessgae(DEFAULT_FAIL_MESSAGE);
        } else {
            result.setMessgae(message);
        }
        return result;
    }

    public static Result genSuccessResult(Object data) {
        Result result = new Result();
        result.setResultCode(Constants.RESULT_CODE_SUCCESS);
        result.setMessgae(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }

}
