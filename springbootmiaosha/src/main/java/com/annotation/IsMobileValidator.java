package com.annotation;

import com.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

     private  boolean required=true;
    @Override
    public void initialize(IsMobile constraintAnnotation) {
         required = constraintAnnotation.required();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required){
               return ValidatorUtil.validator(value);
        }else {
           if (StringUtils.isBlank(value)){
                return  true;
            }else{
                return ValidatorUtil.validator(value);
            }
        }


    }
}
