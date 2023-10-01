package com.graphql.graphql.controllers;

import com.graphql.graphql.models.Curso;
import com.graphql.graphql.models.CursoInputDTO;
import com.graphql.graphql.models.Departamento;
import com.graphql.graphql.models.ResponseAPI;
import com.graphql.graphql.services.CursoService;
import com.graphql.graphql.services.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private DepartamentoService departamentoService;

    @QueryMapping
    public ResponseEntity<ResponseAPI>getCursos(@RequestBody String query){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Todos cursos", cursoService.listarCursos()));
    }
    @PostMapping
    public ResponseEntity<ResponseAPI>createCurso(@Argument CursoInputDTO curso){
        System.out.println( curso);
        Departamento departamento= departamentoService.buscarPorId(curso.departamento());
        Curso curso1= new Curso(curso.codigoCurso(), curso.designacao(), curso.descricao(), departamento);
        cursoService.criarCurso(curso1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Curso criado com sucesso", null));
    }


}
