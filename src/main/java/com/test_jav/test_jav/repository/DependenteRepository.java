package com.test_jav.test_jav.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test_jav.test_jav.model.Dependente;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {
    
    List<Dependente> findByNomeContaining(String nome);
    // Boolean existsByNome(String nome);
    Optional<Dependente> findByNome(String nome);

}
