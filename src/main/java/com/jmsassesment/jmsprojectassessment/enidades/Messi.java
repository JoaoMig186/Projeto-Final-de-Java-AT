package com.jmsassesment.jmsprojectassessment.enidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class Messi {
    private Long id;
    private String date;
    private String competition;
    private String home;
    private String result;
    private String away;
    private String minute;
    private String Score;
    private String what;
    private String how;

}
