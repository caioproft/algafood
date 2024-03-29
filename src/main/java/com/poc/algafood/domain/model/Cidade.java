package com.poc.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "cidade")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private Long id;

  @Column(nullable = false)
  private String nome;

  @ManyToOne
  @JoinColumn(name = "estado_id", nullable = false)
  private Estado estado;
}
