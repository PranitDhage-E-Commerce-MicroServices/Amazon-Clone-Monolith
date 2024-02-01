package com.web.ecomm.app.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "credentials")
@Builder
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Integer userId;

    @JsonProperty("email")
    @Column(name = "email")
    @NotNull(message = "Email can not be Null")
    private String email;

    @JsonProperty(value = "password")
    @Column(name = "password")
    @NotNull(message = "Password can not be Null")
    private String password;

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials(Integer userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
