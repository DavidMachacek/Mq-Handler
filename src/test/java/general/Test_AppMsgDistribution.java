package general;

import com.ibm.mq.MQMessage;
import cz.dmachacek.mq.connector.ConnectorService;
import cz.dmachacek.mq.message.CWFMessageHandler;
import cz.dmachacek.mq.utils.FileReader;
import org.junit.*;
import org.junit.runners.MethodSorters;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

/**
 * Created by dmachace on 28.12.2015.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_AppMsgDistribution extends VarInitialization {
    static ConnectorService tConn = new ConnectorService();
    static ConnectorService pConn =new ConnectorService();
    static FileReader fReader = new FileReader();
    static CWFMessageHandler tMsg = new CWFMessageHandler();
    static List<String> msgList = new ArrayList<>();

    String tempMsg=null;
    String msgTypeTest;
    String expectedValue;
    String msgValueTest;
    int posFrom;
    int posTo;

    @Test
    public void testA_QueueClear () throws Exception {
        tConn.clearQueue("SIP.TAR.REQ.Q");
        tConn.clearQueue("PRF.SIP.ONL.Q");
    }

    @Test
    public void testB_PutMsg () throws Exception {
        final MQMessage msg = tMsg.createMsg(fReader.getFileContent("msg/AppMsg.xml"), props.getCharset(), null, null, "AppMsg");
        pConn.putMsg("PRF.SIP.ONL.Q", msg);
        Assert.assertNotNull(msg);
    }

    @Test
    public void testD_GetMsg () throws Exception {
        msgList = tConn.getMsg("SIP.TAR.REQ.Q");
        Assert.assertNotNull(msgList);
    }

    @Test
    public void testM_ContractMsg() throws Exception {
        msgTypeTest = "ContractMsg";
        expectedValue="Beetle";
        posFrom=4807;
        posTo=4813;

        msgValueTest = "Element was NOT found!!";
        for (String aMsgList : msgList) {
            if (aMsgList.substring(0, 20).trim().equals(msgTypeTest)) {
                tempMsg = aMsgList;
            }
        }
        if (tempMsg!=null) {
            msgValueTest = tempMsg.substring(posFrom, posTo); } else {
            logger.error("{} was NOT found!", msgTypeTest);
        }

        /*System.out.println("TEST ("+msgTypeTest+") - expected value >>"+expectedValue+"<< at position "+posFrom+"-"+posTo+". Got value: >>"+ msgValueTest+"<<");
        System.out.println(tempMsg);*/
        assertEquals(expectedValue, msgValueTest);
    }

    @Test
    public void testN_CustomerMsg() throws Exception {
        msgTypeTest = "CustomerMsg";
        expectedValue = "Praha";
        posFrom = 367;
        posTo = 372;

        msgValueTest = "Element was NOT found!!";
        for (String aMsgList : msgList) {
            if (aMsgList.substring(0, 20).trim().equals(msgTypeTest)) {
                tempMsg = aMsgList;
            }
        }
        if (tempMsg != null) {
            msgValueTest = tempMsg.substring(posFrom, posTo);
        } else {
            logger.error("{} was NOT found!", msgTypeTest);
        }
        /*System.out.println("TEST - expected value >>" + expectedValue + "<< at position " + posFrom + "-" + posTo + " from " + msgTypeTest + ". Got value: " + msgValueTest);
        System.out.println(tempMsg);*/
        assertEquals(expectedValue, msgValueTest);
    }

   @Test
    public void testZ_QueueClear () throws Exception {
        tConn.clearQueue("PRF.SIP.ONL.Q");
        tConn.clearQueue("SIP.TAR.REQ.Q");
    }
}