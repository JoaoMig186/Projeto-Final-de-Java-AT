package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import com.jmsassesment.jmsprojectassessment.enidades.Jogador;
import com.jmsassesment.jmsprojectassessment.repositorios.ClubesRepository;
import com.jmsassesment.jmsprojectassessment.repositorios.JogadoresRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class JogadorControllerTest {
    @Autowired
    JogadoresRepository jogadorService;
    @Autowired
    ClubesRepository clubesService;
    @Test
    @DisplayName("Deve retornar todos os jogadores")
    void deveTestarGetAllJogadores(){
        List<Jogador> jogadores =  jogadorService.findAll();
        assertTrue(jogadores.size() > 10);
    }

    @Test
    @DisplayName("Deve retornar o jogador por Id")
    void deveRetornarJogadorPeloId(){
        Jogador jogador = jogadorService.findById(5L).get();

        assertEquals(jogador.getId(), 5L);
        assertEquals(jogador.getNome(), "Sergio Ramos");
        assertEquals(jogador.getPosicao(), "Zagueiro");
        assertEquals(jogador.getNumeroDaCamisa(), 4);
        assertEquals(jogador.getClube().getId(), 2L);


        assertThrows(NoSuchElementException.class, ()->{
            jogadorService.findById(-1L).get();
        });
    }

    @Test
    @DisplayName("Deve retornar o jogador pelo nome")
    void deveRetornarJogadorPeloNome(){
        List<Jogador> jogadores = jogadorService.findByNome("Sergio Ramos");

        assertEquals(jogadores.get(0).getId(), 5L);
        assertEquals(jogadores.get(0).getNome(), "Sergio Ramos");
        assertEquals(jogadores.get(0).getPosicao(), "Zagueiro");
        assertEquals(jogadores.get(0).getNumeroDaCamisa(), 4);
        assertEquals(jogadores.get(0).getClube().getId(), 2L);
    }

    @Test
    @DisplayName("Deve retornar o jogador pelo nome e número da camisa")
    void deveRetornarJogadorPeloNomeEnumero(){
        List<Jogador> jogadores = jogadorService.findByNumeroDaCamisaAndClubeNome(4, "Real Madrid");

        assertEquals(jogadores.get(0).getId(), 5L);
        assertEquals(jogadores.get(0).getNome(), "Sergio Ramos");
        assertEquals(jogadores.get(0).getPosicao(), "Zagueiro");
        assertEquals(jogadores.get(0).getNumeroDaCamisa(), 4);
        assertEquals(jogadores.get(0).getClube().getId(), 2L);
    }

    @Test
    @DisplayName("Deve Inserir um jogador")
    void deveCriarUmJogador(){
        List<Jogador> listaInicial = jogadorService.findAll();
        int todoOsJogadoresInicial = listaInicial.size(); // Tamanho inicial da lista

        Clube clubeJogador1 = clubesService.findById(27L).get();
        Clube clubeJogador2 = clubesService.findById(32L).get();

        Jogador jogador1 = Jogador.builder()
                .nome("Haaland")
                .posicao("Atacante")
                .numeroDaCamisa(9)
                .clube(clubeJogador1)
                .build();
        jogadorService.save(jogador1);

        List<Jogador> all = jogadorService.findAll();
        assertEquals(todoOsJogadoresInicial + 1, all.size());

        Jogador returnedValue = jogadorService.findById(jogador1.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Haaland", returnedValue.getNome());
        assertEquals("Atacante", returnedValue.getPosicao());
        assertEquals(9, returnedValue.getNumeroDaCamisa());
        assertEquals(jogador1.getClube().getId(), returnedValue.getClube().getId());

        Jogador jogador2 = Jogador.builder()
                .nome("Griezmann")
                .posicao("Atacante")
                .numeroDaCamisa(7)
                .clube(clubeJogador2)
                .build();
        jogadorService.save(jogador2);

        all = jogadorService.findAll();
        assertEquals(todoOsJogadoresInicial + 2, all.size());

        returnedValue = jogadorService.findById(jogador2.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Griezmann", returnedValue.getNome());
        assertEquals("Atacante", returnedValue.getPosicao());
        assertEquals(7, returnedValue.getNumeroDaCamisa());
        assertEquals(jogador2.getClube().getId(), returnedValue.getClube().getId());
    }

    @Test
    @DisplayName("Deve ataualizar o jogador por id")
    void deveAtualizarJogador(){

        Clube clubeJogador = clubesService.findById(2L).get();
        assertNotNull(jogadorService);

        Jogador jogador = jogadorService.findById(2L).get();
        jogador.setNome("Suarez");
        jogador.setPosicao("Atacante");
        jogador.setNumeroDaCamisa(9);
        jogador.setClube(clubeJogador);
        jogadorService.save(jogador);

        Jogador jogadorAtualizado = jogadorService.findById(2L).get();

        assertNotNull(jogadorAtualizado);
        assertEquals("Suarez", jogadorAtualizado.getNome());
        assertEquals("Atacante", jogadorAtualizado.getPosicao());
        assertEquals(9, jogadorAtualizado.getNumeroDaCamisa());
        assertEquals(jogador.getClube().getId(), jogadorAtualizado.getClube().getId());
    }

    @Test
    @DisplayName("Deve remover um jogador pelo ID")
    void deveRemoverJogadorPorId(){
        List<Jogador> jogadores = jogadorService.findAll();
        jogadorService.deleteById(16L);
        int tamanhoPosExclusao = jogadores.size();
        assertEquals(tamanhoPosExclusao, jogadores.size());
    }
}
