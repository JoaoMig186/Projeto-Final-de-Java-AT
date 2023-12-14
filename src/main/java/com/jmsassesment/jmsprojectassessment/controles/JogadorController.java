package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Jogador;
import com.jmsassesment.jmsprojectassessment.repositorios.JogadoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping(value = "/jogadores")
public class JogadorController {
    private static final Logger logger = LoggerFactory.getLogger(JogadorController.class);

    @Autowired
    private JogadoresRepository repository;
    @GetMapping
    public List<Jogador> findAll(){
        try {
            List<Jogador> result = repository.findAll();
            logger.info("Lista de jogadores buscada com sucesso.");
            return result;
        } catch (Exception e) {
            logger.error("Erro ao buscar lista de jogadores.", e);
            return List.of();
        }
    }

    @GetMapping(value = "/{id}")
    public Jogador findById(@PathVariable Long id){
        try{
            Jogador result = repository.findById(id).get();
            logger.info("Jogador procurado pelo id " + id + " encontrado com sucesso!");
            return result;
        } catch (Exception e){
            logger.info("Nenhum jogador encontrado com o id " + id);
            return (Jogador) List.of();
        }
    }

    @GetMapping("/search")
    public List<Jogador> findByNome(@RequestParam String nome) {
        try {
            List<Jogador> jogadores = repository.findByNome(nome);

            if (!jogadores.isEmpty()) {
                logger.info("Jogador procurado pelo nome '" + nome + "' encontrado com sucesso!");
                return jogadores;
            } else {
                logger.info("Nenhum jogador encontrado com o nome '" + nome + "'.");
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Erro ao procurar o jogador pelo nome.", e);
            return List.of();
        }
    }

    @GetMapping("/searchClubAndNum")
    public List<Jogador> findByNumeroDaCamisaAndClubeNome(@RequestParam int numeroDaCamisa, @RequestParam String nomeClube)
    {
        try{
            List<Jogador> jogadores = repository.findByNumeroDaCamisaAndClubeNome(numeroDaCamisa, nomeClube);
            if (!jogadores.isEmpty()) {
                logger.info("Jogador camisa " + numeroDaCamisa + " do clube '" + nomeClube + "' foi encontrado com sucesso.");
                return jogadores;
            } else {
                logger.info("Nenhum jogador camisa " + numeroDaCamisa + " do clube '" + nomeClube + "' foi encontrado");
                return List.of();
            }

        } catch (Exception e) {
            logger.error("Erro ao procurar o jogador pelo nome e pelo número.", e);
            return List.of();
        }

    }

    @PostMapping
    public Jogador create(@RequestBody Jogador jogador){
        try {
            Jogador result = repository.save(jogador);
            logger.info("O Jogador de nome '" + result.getNome() + "' foi criado com sucesso: " + result);
            return result;
        } catch (Exception e) {
            logger.error("Erro ao criar o jogador de nome '" + jogador.getNome() + "'.", e);
            return (Jogador) List.of();
        }
    }

    @PutMapping(value = "/{id}")
    public Jogador update(@PathVariable Long id, @RequestBody Jogador jogador) {
        try {
            Jogador jogadorToUpdate = repository.findById(id).orElse(null);

            if (jogadorToUpdate != null) {
                jogadorToUpdate.setNome(jogador.getNome());
                jogadorToUpdate.setPosicao(jogador.getPosicao());
                jogadorToUpdate.setNumeroDaCamisa(jogador.getNumeroDaCamisa());
                jogadorToUpdate.setClube(jogador.getClube());

                logger.info("O jogador de nome '" + jogadorToUpdate.getNome() + "' foi atualizado com sucesso!");
                return repository.save(jogadorToUpdate);
            } else {
                logger.info("Não foi possível encontrar nenhum jogador com o Id: "+ id);
                return (Jogador) List.of();
            }
        } catch (Exception e) {
            logger.error("Erro ao atualizar o jogador: " + jogador.getNome(), e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            logger.info("Jogador excluído com sucesso. ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Tentativa de excluir jogador inexistente. ID: " + id);
        } catch (Exception e) {
            logger.error("Erro ao excluir o jogador. ID: " + id, e);
        }
    }
}
