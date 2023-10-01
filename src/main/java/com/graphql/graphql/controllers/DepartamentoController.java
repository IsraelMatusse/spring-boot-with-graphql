package com.graphql.graphql.controllers;

import com.graphql.graphql.models.Departamento;
import com.graphql.graphql.models.ResponseAPI;
import com.graphql.graphql.repositories.DepartamentoRepo;
import com.graphql.graphql.services.DepartamentoService;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/departamento")
public class DepartamentoController {


    @Autowired
    private DepartamentoService departamentoService;
    @Autowired
    private DepartamentoRepo departamentoRepo;
    @Value("classpath:graphql.graphqls")
    private Resource schemaResource;

    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException {
        File schemaFile = schemaResource.getFile();
        TypeDefinitionRegistry registry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(registry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildWiring() {
        DataFetcher<List<Departamento>> fetcher1 = data -> {
            return (List<Departamento>) departamentoService.listarTodos();
        };
        DataFetcher<Departamento> createDepartamentoFetcher = data -> {
            Map<String, Object> input = data.getArgument("input");
            return departamentoService.criarNovoDepartamento(input);
        };
        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting -> typeWriting.dataFetcher("getAllDepartamento", fetcher1))
                        .type("Mutation", typeWriting ->
                        typeWriting.dataFetcher("createDepartamento", createDepartamentoFetcher))
                .build();
    }
    @PostMapping
    public ResponseEntity<ResponseAPI> criar(@RequestBody Departamento departamento){
        departamentoService.criar(departamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseAPI("Departamento criado com sucesso", null));
    }
    @GetMapping
    public ResponseEntity<ResponseAPI> listar(){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Todos departamentos do sistema", departamentoService.listarTodos()));
    }

    @PostMapping("/get-all")
    public ResponseEntity<ResponseAPI> getAll(@RequestBody String query) {
        ExecutionResult result = graphQL.execute(query);
        return  ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Todos departamentos", result));
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseAPI> create(@RequestBody String mutation) {
        ExecutionResult result = graphQL.execute(mutation);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Departamento criado com sucesso", null));
    }


}
