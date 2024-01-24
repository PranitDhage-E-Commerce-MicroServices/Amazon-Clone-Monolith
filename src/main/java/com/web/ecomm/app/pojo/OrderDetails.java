package com.web.ecomm.app.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Data
@Entity
@Table(name = "orderdetails")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdetails_id")
    @JsonProperty("orderdetails_id")
    private Integer orderdetailsId;

    @JsonProperty("price")
    @Column(name = "price")
    private Float price;

    @JsonProperty("quantity")
    @Column(name = "quantity")
    private Integer quantity;

    @JsonProperty("total_amount")
    @Column(name = "total_amount")
    private Float totalAmount;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    @JsonProperty("ordered_on")
    @Column(name = "ordered_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;



    @JoinColumn(name = "myorder_id", nullable = false)
    @ManyToOne(optional = false)
    private Myorder myorder;

    @JoinColumn(name = "prod_id", nullable = false)
    @ManyToOne(optional = false)
    private Products product;


    public OrderDetails() {
    }

    public OrderDetails(Float price, Integer quantity, Float totalAmount, Myorder myorder, Products product) {
        this.price = price;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.myorder = myorder;
        this.product = product;
    }

}
