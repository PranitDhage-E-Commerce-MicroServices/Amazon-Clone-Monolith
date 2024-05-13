package com.web.ecomm.app.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@Getter
@Setter
public class AuditConfigRepositoryImpl {

    private final DataSource dataSource;

    @Value("${jdbc.username}")
    private String schemaName;

    public AuditConfigRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
