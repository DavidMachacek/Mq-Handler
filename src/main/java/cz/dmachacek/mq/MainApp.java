/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq;

/**
 *
 * @author David
 */

import cz.dmachacek.mq.config.AppConfig;
import cz.dmachacek.mq.connector.ConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    @Autowired
    ConnectorService connectorService;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

        ctx.register(AppConfig.class);
        ctx.refresh();

    //ConnectorService myService = ctx.getBean(ConnectorService.class);
    //myService.();
    }
}
