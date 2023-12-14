package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import com.jmsassesment.jmsprojectassessment.enidades.Jogador;
import com.jmsassesment.jmsprojectassessment.repositorios.ClubesRepository;
import com.jmsassesment.jmsprojectassessment.repositorios.JogadoresRepository;
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
public class JogadorControllerTestMemory {
    @Autowired
    JogadoresRepository jogadorService;
    @Autowired
    ClubesRepository clubesService;

    @Test
    @DisplayName("Deve Inserir um jogador")
    @BeforeEach
    void deveCriarUmJogador(){
        List<Jogador> listaInicial = jogadorService.findAll();
        int todoOsJogadoresInicial = listaInicial.size(); // Tamanho inicial da lista


        Jogador jogador1 = Jogador.builder()
                .nome("Haaland")
                .posicao("Atacante")
                .numeroDaCamisa(9)
                .build();
        jogadorService.save(jogador1);

        List<Jogador> all = jogadorService.findAll();
        assertEquals(todoOsJogadoresInicial + 1, all.size());

        Jogador returnedValue = jogadorService.findById(jogador1.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Haaland", returnedValue.getNome());
        assertEquals("Atacante", returnedValue.getPosicao());
        assertEquals(9, returnedValue.getNumeroDaCamisa());

        Jogador jogador2 = Jogador.builder()
                .nome("Griezmann")
                .posicao("Atacante")
                .numeroDaCamisa(7)
                .build();
        jogadorService.save(jogador2);

        all = jogadorService.findAll();
        assertEquals(todoOsJogadoresInicial + 2, all.size());

        returnedValue = jogadorService.findById(jogador2.getId()).orElse(null);
        assertNotNull(returnedValue);
        assertEquals("Griezmann", returnedValue.getNome());
        assertEquals("Atacante", returnedValue.getPosicao());
        assertEquals(7, returnedValue.getNumeroDaCamisa());
    }

    @Test
    @DisplayName("Deve retornar todos os jogadores")
    void deveTestarGetAllJogadores(){
        List<Jogador> jogadores =  jogadorService.findAll();
        assertEquals(2, jogadores.size());
    }

    @Test
    @DisplayName("Deve retornar o jogador por Id")
    void deveRetornarJogadorPeloId(){
        Jogador jogador = jogadorService.findById(1L).get();

        assertEquals(jogador.getId(), 1L);
        assertEquals(jogador.getNome(), "Haaland");
        assertEquals(jogador.getPosicao(), "Atacante");
        assertEquals(jogador.getNumeroDaCamisa(), 9);

        assertThrows(NoSuchElementException.class, ()->{
            jogadorService.findById(-1L).get();
        });
    }

    @Test
    @DisplayName("Deve retornar o jogador pelo nome")
    void deveRetornarJogadorPeloNome(){
        List<Jogador> jogadores = jogadorService.findByNome("Haaland");

        assertEquals(jogadores.get(0).getId(), 1L);
        assertEquals(jogadores.get(0).getNome(), "Haaland");
        assertEquals(jogadores.get(0).getPosicao(), "Atacante");
        assertEquals(jogadores.get(0).getNumeroDaCamisa(), 9);
    }

    @Test
    @DisplayName("Deve ataualizar o jogador por id")
    void deveAtualizarJogador(){

        assertNotNull(jogadorService);

        Jogador jogador = jogadorService.findById(2L).get();
        jogador.setNome("Sommer");
        jogador.setPosicao("Goleiro");
        jogador.setNumeroDaCamisa(1);
        jogadorService.save(jogador);

        Jogador jogadorAtualizado = jogadorService.findById(2L).get();

        assertNotNull(jogadorAtualizado);
        assertEquals("Sommer", jogadorAtualizado.getNome());
        assertEquals("Goleiro", jogadorAtualizado.getPosicao());
        assertEquals(1, jogadorAtualizado.getNumeroDaCamisa());
    }

    @Test
    @DisplayName("Deve remover um jogador pelo ID")
    void deveRemoverJogadorPorId(){
        List<Jogador> jogadores = jogadorService.findAll();
        jogadorService.deleteById(1L);
        int tamanhoPosExclusao = jogadores.size();
        assertEquals(tamanhoPosExclusao, jogadores.size());
    }
}
