package com.graphql.graphql.controllers;

import com.graphql.graphql.models.Curso;
import com.graphql.graphql.models.CursoInputDTO;
import com.graphql.graphql.models.Departamento;
import com.graphql.graphql.models.ResponseAPI;
import com.graphql.graphql.services.CursoService;
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
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;
    @Autowired
    private DepartamentoService departamentoService;

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
        DataFetcher<List<Curso>> fetcher1 = data -> {
            return (List<Curso>) cursoService.listarCursos();
        };
        DataFetcher<Curso> createNewCurse = data -> {
            Map<String, Object> input = data.getArgument("input");
            return cursoService.criarCurso(input);
        };
        return RuntimeWiring.newRuntimeWiring().type("Query",
                        typeWriting -> typeWriting.dataFetcher("getCursos", fetcher1))
                .type("Mutation", typeWriting ->
                        typeWriting.dataFetcher("createCurso", createNewCurse))
                .build();
    }

    @PostMapping("/get")
    public ResponseEntity<ResponseAPI>getCursos(@RequestBody String query){
        ExecutionResult result= graphQL.execute(query);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Todos cursos", result));
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseAPI>createCurso(@RequestBody String curso){
        ExecutionResult result = graphQL.execute(curso);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseAPI("Curso criado com sucesso", null));
    }


}
