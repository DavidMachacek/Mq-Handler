/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.config;

import cz.dmachacek.mq.connector.ConnectorService;
import org.springframework.context.annotation.*;

/**
 *
 * @author David
 */
@Configuration
public class AppConfig {
    
    @Bean 
    public ConnectorService connectorService(){
       return new ConnectorService();
    }
}
