package com.jmsassesment.jmsprojectassessment.controles;

import com.jmsassesment.jmsprojectassessment.enidades.Messi;
import com.jmsassesment.jmsprojectassessment.util.ApiMessi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ApiMessiTest {
    @Autowired
    ApiMessi apiMessi;

    @Test
    @DisplayName("Deve retornar os dados de todos os gols do messi")
    void deveRetornarTodosOsGolsDoMessi(){
        List<Messi> messiGols = apiMessi.GetAll();
        assertEquals(805, messiGols.size());
    }

    @Test
    @DisplayName("Deve retornar os dados de um gol do Messi por id")
    void deveRetornarUmGolDoMessi(){
        Messi objetoDadosMessi = apiMessi.getById(1);

        assertEquals("01-05-2005", objetoDadosMessi.getDate());
        assertEquals("La Liga", objetoDadosMessi.getCompetition());
        assertEquals("FC Barcelona", objetoDadosMessi.getHome());
        assertEquals("2-0", objetoDadosMessi.getResult());
        assertEquals("Albacete", objetoDadosMessi.getAway());
        assertEquals("90", objetoDadosMessi.getMinute());
        assertEquals("2-0", objetoDadosMessi.getScore());
        assertEquals("Field goal", objetoDadosMessi.getWhat());
        assertEquals("Left foot", objetoDadosMessi.getHow());

        assertThrows(RuntimeException.class, () -> {
            apiMessi.getById(-1);
        });
    }
}
