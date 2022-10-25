package com.test_jav.test_jav.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.test_jav.test_jav.exception.SocioException;
import com.test_jav.test_jav.model.Socio;
import com.test_jav.test_jav.repository.SocioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SocioService {

    private SocioRepository socioRepository;

    // Adiciona novo sócio
    @Transactional
    public Socio salvar(Socio socio) {
        // boolean nomeRegistrado = socioRepository.existsByNome(socio.getNome());
        boolean nomeRegistrado = socioRepository.findByNome(socio.getNome())
                .stream()
                .anyMatch(socioExistente -> !socioExistente.equals(socio));

        if (nomeRegistrado) {
            throw new SocioException("Já há um sócio registrado com esse nome.");
        }

        return socioRepository.save(socio);
    }

    // Exclui um sócio
    @Transactional
    public void excluir(Long socioId) {
        socioRepository.deleteById(socioId);
    }

    // Retorna um sócio
    public Optional<Socio> findById(Long socioId) {
        return socioRepository.findById(socioId);
    }

    // Checa se sócio já existe por id
    public boolean existsById(Long socioId) {
        return socioRepository.existsById(socioId);
    }

    // Checa se sócio já existe por nome
    public Optional<Socio> findByNome(String socioNome) {
        return socioRepository.findByNome(socioNome);
    }

}
