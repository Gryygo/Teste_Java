package com.test_jav.test_jav.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test_jav.test_jav.model.Socio;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    
    List<Socio> findByNomeContaining(String nome);
    // Boolean existsByNome(String nome);
    Optional<Socio> findByNome(String nome);

}
