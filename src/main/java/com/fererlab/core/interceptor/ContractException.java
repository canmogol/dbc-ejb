package com.fererlab.core.interceptor;

public class ContractException extends Exception {

    private String expression;
    private Object result;

    public ContractException(String message, String expression, Object result) {
        super(message);
        this.expression = expression;
        this.result = result;
    }

    public String getExpression() {
        return expression;
    }

    public Object getResult() {
        return result;
    }
}
