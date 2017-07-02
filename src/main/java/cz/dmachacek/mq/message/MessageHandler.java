/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.message;

import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.MQConstants;
import cz.dmachacek.mq.exception.MqException;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author dmachace
 */

public class MessageHandler {

    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    String charset;

    public MQMessage createMsg(String msgBody, String charset,byte[] msgId, byte[] correlId, String msgType) throws MqException {
        MQMessage msg = new MQMessage();

        msg.characterSet=1208;
        msg.messageId = msgId;
        msg.correlationId = correlId;
/*
        try {
            msg.characterSet = (new CcsidJavaNioTable()).findCCSID(charset);
        } catch (CcsidNotFoundException ex) {
            logger.warn("MQMD Charset not valid: {} (original Java Charset: {}), ERROR: {}", msg.characterSet, this.charset, ex);
        }
*/
        if (!msgType.equals("AppMsg")) {
                msg = setMcdHeader(msg, msgType, false);
            } else {
                logger.warn("Message with XML created. No header attached");
        }

        try {
            msg.write(msgBody.getBytes("UTF-8"));
            } catch (IOException ex) {
                throw new MqException("Write data to message failed", ex);
            }
        return msg;
    }

    public MQMessage setMcdHeader(MQMessage msg, String msgType, boolean isByte) {
        try {

            String mcdHeader = "<mcd><Msd>MRM</Msd><Set>DQMTNGG07Q001</Set><Type>"+msgType+"</Type><Fmt>CWF</Fmt></mcd>";
            while (mcdHeader.getBytes("UTF-8").length % 4 != 0)
                {   mcdHeader = mcdHeader + " ";
            }
            //write down the RFH2 header
            msg.format = MQConstants.MQFMT_RF_HEADER_2;
            msg.writeString(MQConstants.MQRFH_STRUC_ID);
            msg.writeInt4(MQConstants.MQRFH_VERSION_2);
            msg.writeInt4(MQConstants.MQRFH_STRUC_LENGTH_FIXED_2 + mcdHeader.getBytes("UTF-8").length  + 4);
            if (isByte) {
                msg.writeInt4(785); } else {
                msg.writeInt4(MQConstants.MQENC_NATIVE); } //MQConstants.MQENC_NATIVE
            if (isByte) {
                msg.writeInt4(870); } else {
                msg.writeInt4(MQConstants.MQCCSI_DEFAULT); } //MQConstants.MQCCSI_DEFAULT
            msg.writeString(MQConstants.MQFMT_NONE);
            msg.writeInt4(MQConstants.MQRFH_NO_FLAGS);
            msg.writeInt4(1208);//1208
            msg.writeInt4(mcdHeader.getBytes("UTF-8").length);

            //write down the payload
            msg.write(mcdHeader.getBytes("UTF-8"));
            logger.info("Message header successfully prepared for {} with RFH2 and MCD header attached", msgType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public MQMessage createMsgByte(byte[] msgBody) throws MqException {
        MQMessage msg = new MQMessage();
        msg.characterSet=1208;
        try {
            msg.clearMessage();
            msg = setMcdHeader(msg, "ContractMsg", true);
            msg.write(msgBody);
        } catch (IOException ex) {
            throw new MqException("Write data to message failed", ex);
        }
        return msg;
    }

}