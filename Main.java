package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.ListIterator;


public class Main {
    private static int countOfSavedBets;
    private static ArrayList<Integer> marks = new ArrayList<>();
    private static final String savedDataPath = "C:\\Users\\79095\\IdeaProjects\\ParseBet\\SavedBets.dat";
    private static final String savedMarksPath = "C:\\Users\\79095\\IdeaProjects\\ParseBet\\SavedMarks.txt";
    private static final String savedBetsPath = "C:\\Users\\79095\\IdeaProjects\\ParseBet\\CountOfSavedBets.txt";


    public static void automaticSaved(ObjectOutputStream oos, Bet bet) throws IOException {
        oos.writeObject(bet);
    }

    public static void loadBets(FileInputStream fis, ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ObjectInputStream newOis = ois;
        ListIterator<Integer> it;
        for (int i = 0; i < countOfSavedBets; i++) {
            it = marks.listIterator();
            if (!marks.isEmpty()){
                while (it.hasNext()) {
                   if (i == it.next()){
                       newOis = new ObjectInputStream(fis);
                   }
                }
            }
            Bet bet = (Bet) newOis.readObject();
            System.out.println(bet.toString());
        }
    }

    public static void saveCountOfSavedBets() {
        try(FileWriter writer = new FileWriter(savedBetsPath, false)) {
            writer.write(String.valueOf(countOfSavedBets));
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void saveMarks() {
        ListIterator<Integer> it = marks.listIterator();
        try(FileWriter writer = new FileWriter(savedMarksPath, false)) {
            while (it.hasNext()) {
                writer.write(String.valueOf(it.next()));
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void loadMarks() {
        StringBuilder str = new StringBuilder();
        try(FileReader reader = new FileReader(savedMarksPath)) {
            int c;
            while((c = reader.read()) != -1){
                str.append((char)c);
                if (!str.isEmpty()) {
                    marks.add(Integer.parseInt(str.toString()));
                }
                str.delete(0, 1);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void loadCountOfSavedBets() {
        StringBuilder str = new StringBuilder();
        try(FileReader reader = new FileReader(savedBetsPath))
        {
            int c;
            while((c = reader.read()) != -1){
               str.append((char)c);
            }
            countOfSavedBets = Integer.parseInt(str.toString());
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void printHelpInfo() {
        System.out.println("""
                Commands:
                1. new *name of bet*
                2. add *name of outcome* *coefficient*
                3. end - finish creating a bet
                4. quit - quit from program""");
        System.out.println("""
                -------------------------
                Instructions:
                First step: Create a bet using the 'new' command and enter the name of the bet
                Second step: Add outcomes using the 'add' command"
                Final step: If you have finished adding outcomes, type the command 'end'""");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        BufferedReader fromUser;
        Bet bet = null;
        String sentence;
        CommandChecker checker = new CommandChecker();
        String[] subStr;
        String delimiter = " ";

        FileOutputStream fileOutputStream = new FileOutputStream(savedDataPath, true);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        FileInputStream fileInputStream = new FileInputStream(savedDataPath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        System.out.println("\nHello, everything is ready to go, you can start typing!\nIf you need help, type '-help'\n");

        loadCountOfSavedBets();
        loadMarks();
        //Цикл крутится, ожидая команд от пользователя, команда пользователя скармливается в CommandChecker
        //После этого свитч заканчивает обработку команды и дает команду на выполнение нужных действий
        while (true) {
            StringBuilder str;
            boolean quitFlag = false;
            fromUser = new BufferedReader(new InputStreamReader(System.in));
            sentence = fromUser.readLine();
            str = checker.check(sentence);

            switch (str.charAt(0)) {
                case ('N') -> {
                    str.delete(0, 1);
                    bet = new Bet();
                    bet.setName(str.toString());
                }
                case ('A') -> {
                    double coefficient;
                    str.delete(0, 1);
                    subStr = str.toString().split(delimiter);
                    coefficient = Double.parseDouble(subStr[1]);
                    if (bet != null) {
                        bet.addOutcome(subStr[0], coefficient);
                    }
                }
                case ('E') -> {
                    automaticSaved(objectOutputStream, bet);
                    countOfSavedBets++;
                }
                case ('Q') -> {
                    saveCountOfSavedBets();
                    if (countOfSavedBets != 0){
                        marks.add(countOfSavedBets);
                        saveMarks();
                    }
                    quitFlag = true;
                }
                case ('S') -> loadBets(fileInputStream, objectInputStream);
                case ('H') -> printHelpInfo();
                case ('X') -> System.out.println("Отсутствует служебная команда, попробуйте снова");
            }
            if (quitFlag) {
                break;
            }
        }

        fileOutputStream.close();
        objectOutputStream.close();
        fileInputStream.close();
        objectInputStream.close();
    }
}
