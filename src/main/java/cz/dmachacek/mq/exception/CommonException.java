/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.exception;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmachace
 */
public class CommonException extends RuntimeException {
    protected static final Logger logger = LoggerFactory.getLogger(CommonException.class);        
    private int errorCode;
    private String errorMsg;
    
    public CommonException(Exception ex)  {
        super(ex);
  
        if (ex instanceof ParserConfigurationException) {
            this.errorMsg = ((ParserConfigurationException) ex).getMessage();
            logger.warn("ParserConfigurationException caught! {}", ex.getMessage());
        }
        if (ex instanceof SAXException) {
            this.errorMsg = ((SAXException) ex).getMessage();
            logger.warn("SAXException caught! {}", ex.getMessage());
        }
        if (ex instanceof IOException) {
            this.errorMsg = ((IOException) ex).getMessage();
            logger.warn("IOException caught! {}", ex.getMessage());
        }
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String message, Exception ex) {
        super(message, ex);
        if (ex instanceof ParserConfigurationException) {
            this.errorMsg = ((ParserConfigurationException) ex).getMessage();
        }
        if (ex instanceof SAXException) {
            this.errorMsg = ((SAXException) ex).getMessage();
        }
        if (ex instanceof IOException) {
            this.errorMsg = ((IOException) ex).getMessage();
        }
    }

    public int getErrorCode() {
        return errorCode;
    }
}
