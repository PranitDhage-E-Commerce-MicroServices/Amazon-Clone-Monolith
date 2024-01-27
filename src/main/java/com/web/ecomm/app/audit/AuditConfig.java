package com.web.ecomm.app.audit;

import com.web.ecomm.app.repository.AuditConfigRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditConfig {

    @Autowired
    private AuditConfigRepositoryImpl auditConfigDao;

//    @Bean
//    public AuditAspect auditAspect() {
//        AuditAspect auditAspect = new AuditAspect();
//        return auditAspect;
//    }

}
