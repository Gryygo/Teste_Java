package com.test_jav.test_jav.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test_jav.test_jav.model.Dependente;
import com.test_jav.test_jav.repository.DependenteRepository;
import com.test_jav.test_jav.service.DependenteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
// @RestController
@Controller
@RequestMapping("/dependentes")
public class DependenteController {

    private DependenteRepository dependenteRepository;
    private DependenteService dependenteService;

    // CRIA NOVO DEPENDENTE
    @PostMapping("/salvar")
    public String postDependente(@ModelAttribute("dependente") @Valid Dependente dependente, BindingResult result) {

        String url = "redirect:/socios/%id".replace("%id", String.valueOf(dependente.getSocio().getId()));

        if (result.hasErrors()) {
            return url;
        }

        dependenteService.salvar(dependente);


        return url;
    }

    // RETORNA TODOS OS DEPENDENTES
    @GetMapping
    public String getAllDependentes(Model model, Dependente dependente,@RequestParam(defaultValue = "0") int page) {

        model.addAttribute("dependentesList", dependenteRepository.findAll(PageRequest.of(page, 6, Sort.by("id"))));
        model.addAttribute("titulo", "dependentes");

        return "dependentes";
    }


    // RETORNA PESQUISA PARA SÓCIO
    @GetMapping("/search")
    public String searchDependentes(Model model, @RequestParam String nomeS) {

        model.addAttribute("dependentesPesquisa", dependenteRepository.findByNomeContaining(nomeS));

        return "dependentesSearchResults";
    }

    // RETORNA DEPENDENTE ESPECÍFICO PELO ID
    @GetMapping("/{depId}")
    public String getOneDependente(@PathVariable Long depId, Model model, Dependente dependente) {
        Optional<Dependente> dependenteOpt = dependenteService.findById(depId);

        model.addAttribute("dependente", dependenteOpt.get());

        return dependenteOpt.isPresent()
                ? "dependenteDetail"
                : "notFound";
    }

    // MODIFICA DEPENDENTE ESPECÍFICO PELO ID
    @RequestMapping("/editar/{depId}")
    public String updateDependente(@PathVariable Long depId, @Valid Dependente dependente, BindingResult result) {
        if (!dependenteService.existsById(depId)) {
            return "notFound";
        }
        
        String url = "redirect:/dependentes/%id".replace("%id", String.valueOf(depId));

        if (result.hasErrors()) {
            return url;
        }

        dependente.setId(depId);
        dependente = dependenteService.salvar(dependente);

        return url;
    }

    // EXCLUI DEPENDENTE ESPECÍFICO PELO ID
    @RequestMapping("/excluir/{dependenteId}")
    public String deleteDependente(@PathVariable Long dependenteId) {
        if (!dependenteService.existsById(dependenteId)) {
            return "notFound";
        }

        dependenteService.excluir(dependenteId);
        return "redirect:/dependentes";
    }

}
