package com.test_jav.test_jav.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test_jav.test_jav.model.Dependente;
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

    // RETORNA TODOS OS SÓCIOS
    @GetMapping
    public String getAllSocios(Model model, Socio socio,@RequestParam(defaultValue = "0") int page) {

        model.addAttribute("sociosList", socioRepository.findAll(PageRequest.of(page, 6, Sort.by("id"))));
        model.addAttribute("titulo", "socios");
        System.out.println("==============ALL===========");
        System.out.println(socioRepository.findAll());

        return "socios";
    }


    // RETORNA PESQUISA PARA SÓCIO
    @GetMapping("/search")
    public String searchSocios(Model model, @RequestParam String nomeS) {

        model.addAttribute("sociosPesquisa", socioRepository.findByNomeContaining(nomeS));

        return "socioSearchResults";
    }

    // RETORNA SÓCIO ESPECÍFICO PELO ID
    @GetMapping("/{socioId}")
    public String getOneSocio(@PathVariable Long socioId, Model model, Dependente dependente) {
        Optional<Socio> socioOpt = socioService.findById(socioId);

        model.addAttribute("socio", socioOpt.get());

        return socioOpt.isPresent()
                ? "socioDetail"
                : "notFound";
    }

    // MODIFICA SÓCIO ESPECÍFICO PELO ID
    @RequestMapping("/editar/{socioId}")
    public String updateSocio(@PathVariable Long socioId, @Valid Socio socio) {
        if (!socioService.existsById(socioId)) {
            return "notFound";
        }

        socio.setId(socioId);
        socio.setDependentes(socioService.findById(socioId).get().getDependentes());
        socio = socioService.salvar(socio);
        String url = "redirect:/socios/%id".replace("%id", String.valueOf(socioId));

        return url;
    }

    // EXCLUI SÓCIO ESPECÍFICO PELO ID
    @RequestMapping("/excluir/{socioId}")
    public String deleteSocio(@PathVariable Long socioId) {
        if (!socioService.existsById(socioId)) {
            return "notFound";
        }

        socioService.excluir(socioId);
        return "redirect:/socios";
    }

}