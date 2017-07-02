/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.connector;

import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import cz.dmachacek.mq.exception.MqException;
import java.util.List;

/**
 *
 * @author dmachace
 */
public interface ConnectorInterface {
    
    List<String> getMsg(String queueName) throws MQException;
    
    void putMsg(String queue, MQMessage msg) throws MqException;

    void clearQueue(String queueName) throws MQException;
    
}



