package com.app.audit;

import com.app.dao.AuditConfigDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AuditConfig {

    @Autowired
    private AuditConfigDaoImpl auditConfigDao;

//    @Bean
//    public AuditAspect auditAspect() {
//        AuditAspect auditAspect = new AuditAspect();
//        return auditAspect;
//    }

}
