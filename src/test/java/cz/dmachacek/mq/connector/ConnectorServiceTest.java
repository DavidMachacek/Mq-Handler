/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.connector;

import com.ibm.mq.MQMessage;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David
 */
public class ConnectorServiceTest {
    
    public ConnectorServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of clearQueue method, of class ConnectorService.
     */
    @Test
    public void testClearQueue() throws Exception {
        System.out.println("clearQueue");
        String queueName = "";
        ConnectorService instance = new ConnectorService();
        instance.clearQueue(queueName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMsg method, of class ConnectorService.
     */
    @Test
    public void testGetMsg() throws Exception {
        System.out.println("getMsg");
        String queueName = "";
        ConnectorService instance = new ConnectorService();
        List<String> expResult = null;
        List<String> result = instance.getMsg(queueName);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putMsg method, of class ConnectorService.
     */
    @Test
    public void testPutMsg() {
        System.out.println("putMsg");
        String queue = "";
        MQMessage msg = null;
        ConnectorService instance = new ConnectorService();
        instance.putMsg(queue, msg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
