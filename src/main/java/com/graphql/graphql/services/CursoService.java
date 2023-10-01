package com.graphql.graphql.services;

import com.graphql.graphql.models.Curso;
import com.graphql.graphql.models.Departamento;
import com.graphql.graphql.repositories.CursoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CursoService {
    @Autowired
    private  CursoRepo cursoRepo;
    @Autowired
    private DepartamentoService departamentoService;

    public Curso criar(Curso curso){ return cursoRepo.save(curso);}
    public List<Curso> listarCursos(){
        return cursoRepo.findAll();
    }
    public Curso buscarCursoPorId(Long id) {return cursoRepo.findById(id).orElse(null);}
    public Curso buscarCursoPorCodigo(String codigo)  {
        return cursoRepo.findByCodigoCurso(codigo).orElse(null);
    }

    public Curso criarCurso(Map<String, Object> data){
        String codigoCurso= (String) data.get("codigoCurso");
        String desginacao= (String) data.get("desginacao");
        String descricao= (String) data.get("descricao");
        Departamento departamento= departamentoService.buscarPorId((Long) data.get("departamentoId"));

        Curso novoCurso= new Curso( codigoCurso, desginacao, descricao, departamento);
//        novoCurso.setCodigoCurso(codigoCurso);
//        novoCurso.setDesignacao(desginacao);
//        novoCurso.setDescricao(descricao);
//        novoCurso.setDepartamento(departamento);
        return cursoRepo.save(novoCurso);
    }



}
