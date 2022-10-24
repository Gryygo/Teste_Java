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

    private SocioService socioSerSocioService;

    // CRIA NOVO SÓCIO
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Socio postSocio(@RequestBody @Valid Socio socio) {
        return socioSerSocioService.salvar(socio);
    }

    // RETORNAS TODOS OS SÓCIOS
    @GetMapping
    public List<Socio> getAllSocios() {
        return socioSerSocioService.findAll();
    }

    // RETORNA SÓCIO ESPECÍFICO PELO ID
    @GetMapping("/{socioId}")
    public ResponseEntity<Socio> getOneSocio(@PathVariable Long socioId) {
        Optional<Socio> socioOpt = socioSerSocioService.findById(socioId);

        return socioOpt.isPresent()
            ? ResponseEntity.ok(socioOpt.get()) 
            : ResponseEntity.notFound().build();
    }

    // MODIFICA SÓCIO ESPECÍFICO PELO ID
    @PutMapping("/{socioId}")
    public ResponseEntity<Socio> updateSocio (@PathVariable Long socioId, @RequestBody @Valid Socio socio) {
        if (!socioSerSocioService.existsById(socioId)) {
            return ResponseEntity.notFound().build();
        }

        socio.setId(socioId);
        socio = socioSerSocioService.salvar(socio);
        return ResponseEntity.ok(socio);
    }

    // EXCLUI SÓCIO ESPECÍFICO PELO ID
    @DeleteMapping("/{socioId}")
    public ResponseEntity<Void> deleteSocio (@PathVariable Long socioId) {
        if (!socioSerSocioService.existsById(socioId)) {
            return ResponseEntity.notFound().build();
        }

        socioSerSocioService.excluir(socioId);
        return ResponseEntity.noContent().build();
    }

}
