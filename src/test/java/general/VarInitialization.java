/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

import cz.dmachacek.mq.utils.FileReader;
import cz.dmachacek.mq.utils.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmachace
 */
public abstract class VarInitialization {
    
    public Properties props = Properties.INSTANCE;
    protected static final Logger logger = LoggerFactory.getLogger(VarInitialization.class);
    private final FileReader myFileHandler = new FileReader();   
    
/*
    protected byte[] getBytes(final String file) {
        final String msgText = myFileHandler.getFileContent(file);
        return msgText.getBytes();
        }
*/

}
