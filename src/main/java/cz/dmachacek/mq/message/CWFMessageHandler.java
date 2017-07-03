/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author dmachace
 */
public class CWFMessageHandler extends MessageHandler {
    public String getCwfElement(List msgList, String msgType, int posFrom, int posTo) {
        HashMap msgMap = getMsgMap(msgList);
        String retValue = null;
        Set<String> entrySet = msgMap.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getKey().toString().equals(msgType)) {
                retValue = entry.getValue().toString().substring(posFrom, posTo);
            }
        }
        return retValue;
    }
    
    public HashMap getMsgMap(List msgList) {
        HashMap<String, String> msgMap = new HashMap<>();
        for (int i =0; i< msgList.size(); i++) {
            msgMap.put(msgList.get(i).toString().substring(0, 20).trim(), msgList.get(i).toString());
        }
        return msgMap;
    }

}
