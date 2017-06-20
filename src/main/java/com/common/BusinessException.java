package com.common;

/**
 * 自定义异常
 * @author Administrator
 *
 */
public class BusinessException extends RuntimeException {
 
    private static final long serialVersionUID = -5998149773463397666L;
    public BusinessException(String frdMessage)  
    {  
        super(createFriendlyErrMsg(frdMessage));  
    }  
   
    public BusinessException(Throwable throwable)  
    {  
        super(throwable);  
    }  
   
    public BusinessException(Throwable throwable, String frdMessage)  
    {  
        super(throwable);  
    }  
   
    private static String createFriendlyErrMsg(String msgBody)  
    {  
        String prefixStr = "";  
        String suffixStr = "";  
   
        StringBuffer friendlyErrMsg = new StringBuffer("");  
   
        friendlyErrMsg.append(prefixStr);  
   
        friendlyErrMsg.append(msgBody);  
   
        friendlyErrMsg.append(suffixStr);  
   
        return friendlyErrMsg.toString();  
    }  
}