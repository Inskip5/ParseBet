package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Bet implements Serializable {
    private String betName;
    private final HashMap<String, Double> possibleOutcomes = new HashMap<>(); //Исходы и коэффициенты на них
    private final Date dateNow;
    private final SimpleDateFormat formatForDateNow;
    @Serial
    private static final long serialVersionUID = 1L;

    public Bet() {
        dateNow = new Date();
        formatForDateNow = new SimpleDateFormat("E yyyy.MM.dd \n'Время: ' k:mm:ss");
    }

    public void setName(String name) {
        betName = name;
    }

    public void addOutcome(String name, Double coefficient) {
        possibleOutcomes.put(name, coefficient);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(betName).append("\n\n");
        result.append("Дата создания: ").append(formatForDateNow.format(dateNow)).append("\n\n");
        for (HashMap.Entry<String, Double> item : possibleOutcomes.entrySet()) {
            result.append(item.getKey()).append(": ").append(item.getValue()).append('\n');
        }
        return result.toString();
    }
}
