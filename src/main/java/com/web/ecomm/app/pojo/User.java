package com.web.ecomm.app.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@Getter
@Setter
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)  //it will not send password to client side
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("userId")
    @Column(name = "user_id")
    private Integer userId;

    @JsonProperty("name")
    @Column(name = "name", length = 25)
    @NotBlank(message = "Name is required")
    @Length(min = 3, max = 25, message = "Invalid len of Name")
    private String name;

    @JsonProperty("phone")
    @Column(name = "phone")
    @NotBlank(message = "Phone can not be Null")
    private String phone;

    @JsonProperty("email")
    @Column(name = "email", unique = true)
    @Email(message = "Email can not be Null")
    private String email;

    @JsonProperty(value = "password")
//    @Pattern(regexp = "^(.{0,7}|[^0-9]*|[^A-Z]*|[^a-z]*|[a-zA-Z0-9]*)$", message = "Blank or Invalid password")
    @NotBlank(message = "Password can not be Blank")
    @Column(name = "password", length = 200, nullable = false)
    private String password;

    @JsonProperty("status")
    @Column(name = "status")
    private Integer status;

    @Enumerated(EnumType.STRING)
    @JsonProperty("role")
    @Column(name = "role", columnDefinition = "varchar(30) default 'USER'")
    private Role role;

    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    @CreationTimestamp
    @JsonProperty("date")
    @Column(name = "date", nullable = false)
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

    @Override
    public String toString() {

        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Error in User toString : {}", e.getMessage());
        }

        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", role=" + role +
                ", date=" + date +
                ", addresses=" + addresses +
                ", carts=" + carts +
                ", myorders=" + myorders +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}