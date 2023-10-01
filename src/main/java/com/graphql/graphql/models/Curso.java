package com.graphql.graphql.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Departamento departamento;
    private String codigoCurso;
    private String designacao;
    private String descricao;

    public Curso(String  codigoCurso, String designacao, String descricao,Departamento departamento){
        this.codigoCurso=codigoCurso;
        this.descricao=descricao;
        this.designacao=designacao;
        this.departamento=departamento;
    }

}
