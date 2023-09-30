package com.graphql.graphql.repositories;

import com.graphql.graphql.models.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentoRepo extends JpaRepository<Departamento, String> {
}
