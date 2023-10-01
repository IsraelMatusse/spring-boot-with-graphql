package com.graphql.graphql.services;

import com.graphql.graphql.models.Departamento;
import com.graphql.graphql.repositories.DepartamentoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepo departamentoRepo;
    public Departamento criar(Departamento model){
        return departamentoRepo.save(model);
    }
    public List<Departamento> listarTodos(){
        return departamentoRepo.findAll();
    }
    public Departamento buscarPorId(Long id){
        return departamentoRepo.findById(id).orElse(null);
    }

    public Departamento criarNovoDepartamento(Map<String, Object> input){
        // Extracao dados do mapa
        String codigo = (String) input.get("codigo");
        String designacao = (String) input.get("designacao");
        String objectivo = (String) input.get("objectivo");
        String endereco = (String) input.get("endereco");

        // Criacao de um novo objeto Departamento
        Departamento novoDepartamento = new Departamento();
        novoDepartamento.setCodigo(codigo);
        novoDepartamento.setDesignacao(designacao);
        novoDepartamento.setObjectivo(objectivo);
        novoDepartamento.setEndereco(endereco);

        return departamentoRepo.save(novoDepartamento);
    }

}
