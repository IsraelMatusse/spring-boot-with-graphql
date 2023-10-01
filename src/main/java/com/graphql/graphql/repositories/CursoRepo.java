package com.graphql.graphql.repositories;

import com.graphql.graphql.models.Curso;
import com.graphql.graphql.models.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {

    Optional<Curso>findByCodigoCurso(String codigo);


}
