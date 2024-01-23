package com.app.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)  //it will not send password to client side
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userId")
    @Column(name = "user_id")
    private Integer userId;

    @JsonProperty("name")
    @Column(name = "user_name", length = 25)
    @NotBlank(message = "Name is required")
    @Length(min = 3, max = 25, message = "Invalid len of Name")
    private String userName;

    @JsonProperty("phone")
    @Column(name = "user_phone")
    @NotBlank(message = "Phone can not be Null")
    private String userPhone;

    @JsonProperty("email")
    @Column(name = "user_email")
    @Email(message = "Email can not be Null")
    private String userEmail;

    @JsonProperty(value = "password")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[#@$*]).{5,20})", message = "Blank or Invalid password")
    @NotBlank(message = "Password can not be Blank")
    @Column(name = "user_password", length = 20, nullable = false)
    private String userPassword;

    @JsonProperty("status")
    @Column(name = "user_status")
    private Integer userStatus;

    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    @Column(name = "user_role", columnDefinition = "varchar(30) default 'USER'")
    private Role userRole;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    @JsonProperty("signedUpOn")
    @Column(name = "signedUp_on", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    // FOR P.K in another side
    @JsonIgnore        // to ignore this property during signin causing lazy init
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @JsonIgnore        // to ignore this property during signin causing lazy init
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    @JsonIgnore        // to ignore this property during signin causing lazy init
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Myorder> myorders;

}