package com.jmsassesment.jmsprojectassessment.repositorios;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubesRepository extends JpaRepository<Clube, Long> {
    List<Clube> findByNome(String nome);
}
