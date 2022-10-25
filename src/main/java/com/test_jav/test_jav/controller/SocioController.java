package com.test_jav.test_jav.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test_jav.test_jav.model.Socio;
import com.test_jav.test_jav.repository.SocioRepository;
import com.test_jav.test_jav.service.SocioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
// @RestController
@Controller
@RequestMapping("/socios")
public class SocioController {

    private SocioService socioService;
    private SocioRepository socioRepository;

    // CRIA NOVO SÓCIO
    @PostMapping("/salvar")
    // @ResponseStatus(HttpStatus.CREATED)
    public String postSocio(@ModelAttribute("socio") @Valid Socio socio) {
        socioService.salvar(socio);

        return "redirect:/socios/";
    }

    // @PostMapping("/salvar")
// @ResponseStatus(HttpStatus.CREATED)
// public String postSocio(@ModelAttribute("socio") @Valid Socio socio) {
//     socioService.salvar(socio);

//     return "redirect:/socios";
// }

    // RETORNA TODOS OS SÓCIOS
    @GetMapping
    public String getAllSocios(Model model, Socio socio,@RequestParam(defaultValue = "0") int page) {

        model.addAttribute("sociosList", socioRepository.findAll(PageRequest.of(page, 6)));
        model.addAttribute("titulo", "socios");

        return "socios";
    }

    // RETORNA SÓCIO ESPECÍFICO PELO ID
    @GetMapping("/{socioId}")
    public String getOneSocio(@PathVariable Long socioId, Model model) {
        Optional<Socio> socioOpt = socioService.findById(socioId);

        model.addAttribute("socio", socioOpt.get());

        return socioOpt.isPresent()
                ? "socioDetail"
                : "notFound";
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

// @PostMapping("/salvar")
// @ResponseStatus(HttpStatus.CREATED)
// public String postSocio(@ModelAttribute("socio") @Valid Socio socio) {
//     socioService.salvar(socio);

//     return "redirect:/socios";
// }