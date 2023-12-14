package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import com.jmsassesment.jmsprojectassessment.repositorios.ClubesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class ClubeControllerTestMemory {

    @Autowired
    ClubesRepository clubeService;

    @Test
    @DisplayName("Deve Inserir um clube")
    @BeforeEach
    void deveCriarUmClube(){
        List<Clube> listaInicial = clubeService.findAll();
        int todoOsClubesInicial = listaInicial.size(); // Tamanho inicial da lista

        Clube clube1 = Clube.builder()
                .nome("Atlético de Madrid")
                .build();
        clubeService.save(clube1);

        List<Clube> all = clubeService.findAll();
        assertEquals(todoOsClubesInicial + 1, all.size());

        Clube returnedValue = clubeService.findById(clube1.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Atlético de Madrid", returnedValue.getNome());

        Clube clube2 = Clube.builder()
                .nome("Borussia Dortmund")
                .build();
        clubeService.save(clube2);

        all = clubeService.findAll();
        assertEquals(todoOsClubesInicial + 2, all.size());

        returnedValue = clubeService.findById(clube2.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Borussia Dortmund", returnedValue.getNome());
    }

    @Test
    @DisplayName("Deve retornar todos os clubes")
    void deveTestarGetAllCubs(){
        List<Clube> clubes =  clubeService.findAll();
        assertEquals(2, clubes.size());
    }

    @Test
    @DisplayName("Deve retornar o clube por Id")
    void deveRetornarOclubPeloId(){
        Clube clube = clubeService.findById(1L).get();

        assertEquals(clube.getNome(), "Atlético de Madrid");
        assertEquals(clube.getId(), 1L);

        assertThrows(NoSuchElementException.class, ()->{
           clubeService.findById(-1L).get();
        });
    }

    @Test
    @DisplayName("Deve ataualizar o clube")
    void deveAtualizarClube(){
        assertNotNull(clubeService);
        Clube clube = clubeService.findById(1L).get();
        clube.setNome("Liverpool");
        clubeService.save(clube);

        Clube clubeAtualizado = clubeService.findById(1L).get();

        assertNotNull(clubeAtualizado);
        assertEquals("Liverpool", clubeAtualizado.getNome());
    }

    @Test
    @DisplayName("Deve remover um clube pelo ID")
    void deveRemoverClubePorId(){
        List<Clube> clubes = clubeService.findAll();
        clubeService.deleteById(1L);
        int tamanhoPosExclusao = clubes.size();
        assertEquals(tamanhoPosExclusao, clubes.size());
    }
}
