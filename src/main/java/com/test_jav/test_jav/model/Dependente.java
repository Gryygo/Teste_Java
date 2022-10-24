package com.test_jav.test_jav.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.test_jav.test_jav.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "tb_dependente")
public class Dependente {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 5, max = 50)
    private String nome;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    @Digits(fraction = 0, integer = 3)
    private Integer idade;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.SocioId.class)
    private Socio socio;

}
