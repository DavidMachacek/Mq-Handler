/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.exception;

import com.ibm.mq.MQException;

/**
 *
 * @author dmachace
 */

public class MqException extends RuntimeException {

    private String mqErrorCode;

    public MqException(Exception ex) {
        super(ex);
        if (ex instanceof MQException) {
            this.mqErrorCode = ((MQException) ex).getMessage();
            CommonException.logger.warn("MQException caught! {}", ex.getMessage());
        }
    }

    public MqException(String message) {
        super(message);
    }

    public MqException(String message, Exception ex) {
        super(message, ex);
        if (ex instanceof MQException) {
            this.mqErrorCode = ((MQException) ex).getMessage();
            CommonException.logger.warn("MQException caught! {}", ex.getMessage());
        }
    }

    public String getMqErrorCode() {
        return mqErrorCode;
    }
}
