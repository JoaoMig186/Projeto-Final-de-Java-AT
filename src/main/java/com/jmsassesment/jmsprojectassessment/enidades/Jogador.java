package com.jmsassesment.jmsprojectassessment.enidades;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "table_players")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jogador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String posicao;
    private int numeroDaCamisa;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Clube clube;
}
