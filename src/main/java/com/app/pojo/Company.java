package com.app.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import jakarta.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comp_id")
    @JsonProperty("comp_id")
    private Integer compId;

    @Column(name = "comp_title")
    @JsonProperty("comp_title")
    private String compTitle;

    @Column(name = "comp_description")
    @JsonProperty("comp_description")
    private String compDescription;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Products> products;


}
