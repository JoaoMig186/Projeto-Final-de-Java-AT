package com.jmsassesment.jmsprojectassessment.repositorios;

import com.jmsassesment.jmsprojectassessment.enidades.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JogadoresRepository extends JpaRepository<Jogador, Long> {
    List<Jogador> findByNome(String nome);

    List<Jogador> findByNumeroDaCamisaAndClubeNome(int numeroDaCamisa, String nomeClube);
}
