package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long familia_id;
    private String sobrenome;
    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL)
    private List<Member> membros = new ArrayList<>();
}
