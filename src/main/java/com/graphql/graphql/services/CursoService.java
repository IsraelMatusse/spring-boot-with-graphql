package com.graphql.graphql.services;

import com.graphql.graphql.models.Curso;
import com.graphql.graphql.repositories.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {
    @Autowired
    private  CursoRepo cursoRepo;

    public Curso criarCurso(Curso curso){ return cursoRepo.save(curso);}
    public List<Curso> listarCursos(){
        return cursoRepo.findAll();
    }
    public Curso buscarCursoPorId(Long id) {return cursoRepo.findById(id).orElse(null);}
    public Curso buscarCursoPorCodigo(String codigo)  {
        return cursoRepo.findByCodigoCurso(codigo).orElse(null);
    }



}
