package com.project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.sql.Blob;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String sobrenome;
    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private Blob image;
    private Date data_admissao = new Date();
    private String data_batismo;
    private String data_nascimento;
    private String nacionalidade;
    private String endereco;
    private String contato;
    private String observacoes;

}
