package com.web.ecomm.app.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Getter
@Setter
public class AuditConfigDaoImpl {

    @Autowired
    private DataSource dataSource;

    @Value("${jdbc.username}")
    private String schemaName;

}
