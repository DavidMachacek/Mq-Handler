/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.connector;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;
import cz.dmachacek.mq.exception.MqException;
import cz.dmachacek.mq.utils.Properties;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author David
 */
public class ConnectorService  implements ConnectorInterface {
    
    protected final Properties props;
    protected final String host;
    protected final Integer port;
    protected final String qmgrString;
    protected final String channel;
    protected final MQQueueManager qmgr;
    protected final Logger logger = LoggerFactory.getLogger(ConnectorService.class);
    
    public ConnectorService() {        
        this.props = Properties.INSTANCE;
        this.host = props.getHost();
        this.port= props.getPort();
        this.qmgrString = props.getQMgr();
        this.channel= props.getChannel();        
        this.qmgr = getManager(); 
        logger.info("   Connected to {} Queue Manager", qmgr);
    }
    
    private MQQueueManager getManager()  {        
        MQEnvironment.hostname = host;
        MQEnvironment.channel = channel;
        MQEnvironment.port = port;
        MQEnvironment.userID = "root";
        MQEnvironment.password = "4XUCHci4";

        logger.info("=== Initializing connection to MQ");
        logger.info("   Opening connection for Queue Manager {} on {}:{}", channel, host, port);
        
        try {
            logger.info("   Connecting to Queue Manager");
            return new MQQueueManager(qmgrString);
        } catch (MQException ex) {
            logger.info("   Exception while connecting to Queue Manager :{}", ex);
            throw new MqException(ex);
        }        
    }    
    
    @Override
    public void clearQueue(String queueName) throws MQException {
        int openOptions =  MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;

        MQQueue queue = qmgr.accessQueue(queueName, openOptions);
        MQMessage theMessage    = new MQMessage();
        MQGetMessageOptions gmo = new MQGetMessageOptions();

        gmo.options = MQC.MQGMO_BROWSE_FIRST;
        gmo.matchOptions = MQC.MQMO_NONE;
        gmo.waitInterval =5000;

        logger.info("=== Clearing queue: {}.. ", queueName);
        
        int i =0;
        boolean thereAreMessages = true;
        
        while(thereAreMessages){
            try{
                gmo.options= MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
                queue.get(theMessage,gmo);
                gmo.options = MQC.MQGMO_MSG_UNDER_CURSOR;
                String msgText = theMessage.readString(theMessage.getMessageLength());
                queue.get(theMessage,gmo);
                logger.info("    Removed message: {}", msgText.substring(0,20).trim());
                i++;

            }catch(MQException e){
                if(e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                    logger.warn("No messages available to remove!");
                }
                thereAreMessages = false;
            } catch (IOException e) {
                logger.error("IO exception: {}", e.getMessage());
            }
        }
        logger.info("{} messages removed.", i);
    }
    
    @Override
    public List<String> getMsg(String queueName) throws MQException{
        
        int openOptions = MQC.MQOO_FAIL_IF_QUIESCING | MQC.MQOO_INPUT_SHARED | MQC.MQOO_BROWSE;
        List<String> result = new LinkedList<>();
        MQQueue queue = qmgr.accessQueue(queueName, openOptions);
        MQMessage theMessage    = new MQMessage();
        MQGetMessageOptions gmo = new MQGetMessageOptions();
        
        gmo.options= MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_FIRST;
        gmo.matchOptions= MQC.MQMO_NONE;
        gmo.waitInterval=5000;
        logger.info("=== Reading messages from {}", queueName);
        boolean thereAreMessages=true;
        while(thereAreMessages){
            try{
                queue.get(theMessage,gmo);
                String msgText = theMessage.readString(theMessage.getMessageLength());
                result.add(msgText);
                logger.info("    Reading message (length {}): {}",msgText.length(), msgText);
                gmo.options = MQC.MQGMO_WAIT | MQC.MQGMO_BROWSE_NEXT;
            }catch(MQException e){
                if(e.reasonCode == MQException.MQRC_NO_MSG_AVAILABLE) {
                    logger.info("No messages avaiable!");
                }
                thereAreMessages=false;
            } catch (IOException e) {
                logger.error("No messages avaiable! {}", e.getMessage());
            }
        }
        return result;
    }
    
    @Override
    public void putMsg(String queue, MQMessage msg) throws MqException {
        int openOptions = CMQC.MQOO_OUTPUT | CMQC.MQOO_FAIL_IF_QUIESCING | MQC.MQGMO_CONVERT;
        try {
            MQQueue queueQ = qmgr.accessQueue(queue, openOptions);
            queueQ.put(msg);
            queueQ.close();
            logger.info("Sent message to: {}", queue);
        } catch (MQException ex) {
            throw new MqException(ex);
        }
    }
}
