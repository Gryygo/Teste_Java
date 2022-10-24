package com.test_jav.test_jav.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.test_jav.test_jav.exception.SocioException;
import com.test_jav.test_jav.model.Dependente;
import com.test_jav.test_jav.model.Socio;
import com.test_jav.test_jav.repository.DependenteRepository;
import com.test_jav.test_jav.repository.SocioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class DependenteService {

    private SocioRepository socioRepository;
    private DependenteRepository dependenteRepository;

    // Adiciona novo dependente
    @Transactional
    public Dependente salvar(Dependente dependente) {
        // boolean nomeRegistrado = dependenteRepository.existsByNome(dependente.getNome());
        boolean nomeRegistrado = dependenteRepository.findByNome(dependente.getNome())
                .stream()
                .anyMatch(socioExistente -> !socioExistente.equals(dependente));

        if (nomeRegistrado) {
            throw new SocioException("Já há um dependente registrado com esse nome.");
        }

        Socio socio = socioRepository.findById(dependente.getSocio().getId())
                .orElseThrow(() -> new SocioException("Sócio não encontrado."));

        dependente.setSocio(socio);
            
        return dependenteRepository.save(dependente);
    }

    // Exclui um dependente
    @Transactional
    public void excluir(Long depId) {
        dependenteRepository.deleteById(depId);
    }

    // Retorna todos os dependentes
    public List<Dependente> findAll() {
        return dependenteRepository.findAll();
    }

    // Retorna um dependente
    public Optional<Dependente> findById(Long depId) {
        return dependenteRepository.findById(depId);
    }

    // Checa se dependente já existe por id
    public boolean existsById(Long depId) {
        return dependenteRepository.existsById(depId);
    }

    // Checa se dependente já existe por nome
    public Optional<Dependente> findByNome(String depNome) {
        return dependenteRepository.findByNome(depNome);
    }
}
