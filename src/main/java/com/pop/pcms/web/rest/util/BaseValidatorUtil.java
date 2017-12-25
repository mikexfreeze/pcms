package com.pop.pcms.web.rest.util;

import java.util.List;

import net.sf.oval.ConstraintViolation;

/**
 * REST oval基础校验工具类
 * Created by zhangjinye on 2017/03/01.
 */
public class BaseValidatorUtil {

    /**
     * 根据校验参数返回校验参数对应的校验失败信息
     * @param
     * @return
     */
    public static <T> String valConstraint(T dto,String...args) {
    	net.sf.oval.Validator validator = new net.sf.oval.Validator();
        List<ConstraintViolation> ret = validator.validate(dto,args);
        if (ret!=null&&ret.size() > 0) {
        	StringBuilder msg=new StringBuilder();
        	for(ConstraintViolation cv : ret){
        		msg.append(cv.getMessage()+";");
        	}
            return msg.toString();
        }
        return null;
    }

}
