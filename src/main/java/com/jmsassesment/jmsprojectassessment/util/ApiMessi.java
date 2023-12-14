package com.jmsassesment.jmsprojectassessment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jmsassesment.jmsprojectassessment.controles.ExcecoesController;
import com.jmsassesment.jmsprojectassessment.enidades.Messi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Component
public class ApiMessi {
    private static final Logger logger = LoggerFactory.getLogger(ExcecoesController.class);
    private int ultimoStatusCode;
    public Messi getById(int id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://api-ankara-messi-jfmt35emc-joaomig186.vercel.app/goals/" + id))
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("# Status Code da API do Messi: {} #", response.statusCode());
            if(response.statusCode() == 404){
                throw new RuntimeException("Não encontrado. Verifique a URL da API ou o parâmetro da requisição.");
            }

            ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
            Messi messi = objectMapper.readValue(response.body(), Messi.class);
            return messi;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Messi> GetAll() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .version(HttpClient.Version.HTTP_2)
                    .uri(new URI("https://api-ankara-messi-jfmt35emc-joaomig186.vercel.app/goals"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("# Status Code da API do Messi: {} #", response.statusCode());
            if (response.statusCode() == 404) {
                throw new RuntimeException("Não encontrado. Verifique a URL da API ou o parâmetro da requisição.");
            }

            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            List<Messi> messiList = objectMapper.readValue(response.body(), objectMapper.getTypeFactory().constructCollectionType(List.class, Messi.class));

            return messiList;

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
