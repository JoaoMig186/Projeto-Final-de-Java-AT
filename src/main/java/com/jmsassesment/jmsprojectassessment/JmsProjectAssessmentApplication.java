package com.jmsassesment.jmsprojectassessment;

import com.jmsassesment.jmsprojectassessment.controles.ExcecoesController;
import com.jmsassesment.jmsprojectassessment.enidades.Messi;
import com.jmsassesment.jmsprojectassessment.util.ApiMessi;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Random;
import java.util.Scanner;

@SpringBootApplication
public class JmsProjectAssessmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(JmsProjectAssessmentApplication.class, args);
	}
	private static final Logger logger = LoggerFactory.getLogger(ExcecoesController.class);
	@PostConstruct
	public void GetMessiData(){

		Random random = new Random();
		int idAleatorio = random.nextInt(805) + 1;

		ApiMessi apiMessi = new ApiMessi();
		Messi messi = apiMessi.getById(idAleatorio);
		String textoMessi = "\nEste foi o gol de número " + messi.getId() + " na carreira de Lionel Messi. Ele foi marcado no jogo " +
				messi.getHome() + " " + messi.getResult() + " " + messi.getAway() + ", no dia " + messi.getDate() + " em jogo válido pela " + messi.getCompetition() +
				" aos " + messi.getMinute() + " minutos. " +
				"A forma que ele marcou o gol foi de " + messi.getHow() + ".";
		logger.info(textoMessi);
	}
}