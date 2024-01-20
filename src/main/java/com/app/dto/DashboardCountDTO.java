package com.app.dto;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.persistence.Column;

@Data
@ConfigurationProperties(value = "userCount, productCount, MyOrderCount, ActiveOrderCount, companyCount, categoryCount")
public class DashboardCountDTO {
    @Column(name = "userCount")
    private Integer userCount = 0;
    @Column(name = "productCount")
    private Integer productCount = 0;
    @Column(name = "MyOrderCount")
    private Integer MyOrderCount = 0;
    @Column(name = "ActiveOrderCount")
    private Integer ActiveOrderCount = 0;
    @Column(name = "companyCount")
    private  Integer companyCount = 0;
    @Column(name = "categoryCount")
    private  Integer categoryCount = 0;
}
