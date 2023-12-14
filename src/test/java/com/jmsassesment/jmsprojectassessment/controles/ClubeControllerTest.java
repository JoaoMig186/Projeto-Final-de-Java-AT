package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import com.jmsassesment.jmsprojectassessment.repositorios.ClubesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClubeControllerTest {

    @Autowired
    ClubesRepository clubeService;
    @Test
    @BeforeEach
    @DisplayName("Deve retornar todos os clubes")
    void deveTestarGetAllCubs(){
        List<Clube> clubes =  clubeService.findAll();
        assertTrue(clubes.size() > 5);
    }

    @Test
    @DisplayName("Deve retornar o clube por Id")
    void deveRetornarOclubPeloId(){
        Clube clube = clubeService.findById(1L).get();

        assertEquals(clube.getNome(), "Barcelona");
        assertEquals(clube.getId(), 1L);

        assertThrows(NoSuchElementException.class, ()->{
           clubeService.findById(-1L).get();
        });
    }

    @Test
    @DisplayName("Deve retornar o jogador pelo nome")
    void deveRetornarJogadorPeloNome(){
        List<Clube> clubes = clubeService.findByNome("Barcelona");

        assertEquals(clubes.get(0).getId(), 1L);
        assertEquals(clubes.get(0).getNome(), "Barcelona");
    }

    @Test
    @DisplayName("Deve Inserir um clube")
    void deveCriarUmClube(){
        List<Clube> listaInicial = clubeService.findAll();
        int todoOsClubesInicial = listaInicial.size(); // Tamanho inicial da lista

        Clube clube1 = Clube.builder()
                .nome("Liverpool")
                .build();
        clubeService.save(clube1);

        List<Clube> all = clubeService.findAll();
        assertEquals(todoOsClubesInicial + 1, all.size());

        Clube returnedValue = clubeService.findById(clube1.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Liverpool", returnedValue.getNome());

        Clube clube2 = Clube.builder()
                .nome("Napoli")
                .build();
        clubeService.save(clube2);

        all = clubeService.findAll();
        assertEquals(todoOsClubesInicial + 2, all.size());

        returnedValue = clubeService.findById(clube2.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Napoli", returnedValue.getNome());
    }

    @Test
    @DisplayName("Deve ataualizar o clube")
    void deveAtualizarClube(){
        assertNotNull(clubeService);
        Clube clube = clubeService.findById(5L).get();
        clube.setNome("PSG");
        clubeService.save(clube);

        Clube clubeAtualizado = clubeService.findById(5L).get();

        assertNotNull(clubeAtualizado);
        assertEquals("PSG", clubeAtualizado.getNome());
    }
    @Test
    @DisplayName("Deve remover um clube pelo ID")
    void deveRemoverClubePorId(){
        List<Clube> clubesAntesExclusao = clubeService.findAll();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> clubeService.deleteById(3L));
        assertTrue(exception.getMessage().contains("Referential integrity constraint violation"));
        List<Clube> clubesAposTentativaExclusao = clubeService.findAll();
        assertEquals(clubesAntesExclusao.size(), clubesAposTentativaExclusao.size());
    }
}
