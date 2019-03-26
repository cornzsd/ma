package com.exception;

import com.model.CodeMsg;

public class GlobalException extends  RuntimeException {
   CodeMsg cm;
    private static final long serialVersionUID = 1L;
    public  GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm=cm;

    }

    public CodeMsg getCm() {
        return cm;
    }
}
