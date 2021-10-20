package com.poc.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "restaurante")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(name = "taxa_frete", nullable = false)
  private BigDecimal taxafrete;

  @Column private boolean ativo;

  @Column private boolean aberto;

  @ManyToOne
  @JoinColumn(name = "cozinha_id")
  private Cozinha cozinha;

  @Column @CreationTimestamp private LocalDateTime dataCadastro;

  @Column @UpdateTimestamp private LocalDateTime dataAtualizacao;
}
