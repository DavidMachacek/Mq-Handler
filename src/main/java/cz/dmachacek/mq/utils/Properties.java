/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.utils;

import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dmachace
 */

public enum Properties {

    INSTANCE;

    private java.util.Properties jUtilProperties;
    private final Logger logger = LoggerFactory.getLogger(Properties.class);
    private String filePath;

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_CHANNEL = "SYSTEM.DEF.SVRCONN";
   // private static final String DEFAULT_DUMPPATH = "/var/log/SIP-TT/dump/";

    Properties() {
        this.init(this.getClass().getResource("/config.properties").getFile());
    }

    private void init(String filePath) {
        this.filePath = filePath;
        jUtilProperties = new java.util.Properties();
        try {
            logger.info("Opening properties file {}", this.filePath);
            jUtilProperties.load(new FileInputStream(this.filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public String getDumpPath() {
//        if (this.jUtilProperties.getProperty("dumpPath") == null) {
//            logger.warn("property dumpPath not set, defaults to " + Properties.DEFAULT_DUMPPATH);
//        }
//        return this.jUtilProperties.getProperty("dumpPath", Properties.DEFAULT_DUMPPATH);
//    }

    public String getQMgr() {
        return this.jUtilProperties.getProperty("qmgr");
    }

    public String getQueuePrf() {
        return this.jUtilProperties.getProperty("queuePrf");
    }
    
    public String getQueueTar() {
        return this.jUtilProperties.getProperty("queueTar");
    }

    public String getQueueFromTar() {
        return this.jUtilProperties.getProperty("queueFromTar");
    }

    public String getQueueSap() {
        return this.jUtilProperties.getProperty("queueSap");
    }

    public String getChannel() {
        if (this.jUtilProperties.getProperty("channel") == null) {
            logger.warn("property channel not set, defaults to " + Properties.DEFAULT_CHANNEL);
        }
        return this.jUtilProperties.getProperty("channel", Properties.DEFAULT_CHANNEL);
    }

    public String getCharset() {
        if (this.jUtilProperties.getProperty("charset") == null) {
            logger.warn("property charset not set, defaults to " + Properties.DEFAULT_CHARSET);
        }
        return this.jUtilProperties.getProperty("charset", Properties.DEFAULT_CHARSET);
    }

    public String getHost() {
        return this.jUtilProperties.getProperty("host");
    }

    public Integer getPort() {
        return Integer.parseInt(this.jUtilProperties.getProperty("port"));
    }

    public String getFilePath() {
        return filePath;
    }
}