package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Clube;
import com.jmsassesment.jmsprojectassessment.repositorios.ClubesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clubes")
public class ClubeController {
    private static final Logger logger = LoggerFactory.getLogger(JogadorController.class);
    @Autowired
    private ClubesRepository repository;
    @GetMapping
    public List<Clube> findAll(){
        try {
            List<Clube> result = repository.findAll();
            logger.info("Lista de clubes buscada com sucesso.");
            return result;
        } catch (Exception e) {
            logger.error("Erro ao buscar lista de clubes.", e);
            return List.of();
        }
    }

    @GetMapping(value = "/{id}")
    public Clube findById(@PathVariable Long id){
        try {
            Clube result = repository.findById(id).get();
            logger.info("Clube procurado pelo id " + id + " encontrado com sucesso!");
            return result;
        } catch (Exception e){
            logger.info("Nenhum clube encontrado com o id " + id);
            return (Clube) List.of();
        }
    }
    @GetMapping("/search")
    public List<Clube> findByNome(@RequestParam String nome) {
        try{
            List<Clube> clubes = repository.findByNome(nome);
            if (!clubes.isEmpty()) {
                logger.info("Clube procurado pelo nome '" + nome + "' encontrado com sucesso!");
                return clubes;
            } else {
                logger.info("Nenhum clube encontrado com o nome '" + nome + "'.");
                return List.of();
            }
        } catch (Exception e) {
            logger.error("Erro ao procurar o clube pelo nome.", e);
            return List.of();
        }
    }


    @PostMapping
    public Clube create(@RequestBody Clube clube){
        try {
            Clube result = repository.save(clube);
            logger.info("O Clube de nome '" + result.getNome() + "' foi criado com sucesso: " + result);
            return result;
        } catch (Exception e) {
            logger.error("Erro ao criar o jogador de nome '" + clube.getNome() + "'.", e);
            return (Clube) List.of();
        }
    }

    @PutMapping(value = "/{id}")
    public Clube update(@PathVariable Long id, @RequestBody Clube clube) {
        try {
            Clube clubeToUpdate = repository.findById(id).orElse(null);

            if (clubeToUpdate != null) {
                clubeToUpdate.setNome(clube.getNome());
                logger.info("O clube de nome '" +  clubeToUpdate.getNome() + "' foi atualizado com sucesso!");
                return repository.save(clubeToUpdate);
            }
            else {
                logger.info("Não foi possível encontrar nenhum clube com o Id: "+ id);
                return (Clube) List.of();
            }
        }catch (Exception e) {
            logger.error("Erro ao atualizar o clube: " + clube.getNome(), e);
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            logger.info("Clube excluído com sucesso. ID: " + id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Tentativa de excluir clube inexistente. ID: " + id);
        } catch (Exception e) {
            logger.error("Erro ao excluir o clube. ID: " + id, e);
        }
    }
}

