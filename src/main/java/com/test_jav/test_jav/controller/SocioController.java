package com.test_jav.test_jav.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test_jav.test_jav.model.Socio;
import com.test_jav.test_jav.service.SocioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/socios")
public class SocioController {

    private SocioService socioService;

    // CRIA NOVO SÓCIO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Socio postSocio(@RequestBody @Valid Socio socio) {
        return socioService.salvar(socio);
    }

    // RETORNA TODOS OS SÓCIOS
    @GetMapping
    public List<Socio> getAllSocios() {
        return socioService.findAll();
    }

    // RETORNA SÓCIO ESPECÍFICO PELO ID
    @GetMapping("/{socioId}")
    public ResponseEntity<Socio> getOneSocio(@PathVariable Long socioId) {
        Optional<Socio> socioOpt = socioService.findById(socioId);

        return socioOpt.isPresent()
                ? ResponseEntity.ok(socioOpt.get())
                : ResponseEntity.notFound().build();
    }

    // MODIFICA SÓCIO ESPECÍFICO PELO ID
    @PutMapping("/{socioId}")
    public ResponseEntity<Socio> updateSocio(@PathVariable Long socioId, @RequestBody @Valid Socio socio) {
        if (!socioService.existsById(socioId)) {
            return ResponseEntity.notFound().build();
        }

        socio.setId(socioId);
        socio = socioService.salvar(socio);
        return ResponseEntity.ok(socio);
    }

    // EXCLUI SÓCIO ESPECÍFICO PELO ID
    @DeleteMapping("/{socioId}")
    public ResponseEntity<Void> deleteSocio(@PathVariable Long socioId) {
        if (!socioService.existsById(socioId)) {
            return ResponseEntity.notFound().build();
        }

        socioService.excluir(socioId);
        return ResponseEntity.noContent().build();
    }

}
