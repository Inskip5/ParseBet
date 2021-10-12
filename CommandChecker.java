package com.company;
//Класс для проверки команд пользователя
 /*
 Существующие команды:
 1) @new *название ставки*
 2) @add *название исхода* *коэффициент*
 3) @end //Заканчивает формирование ставки
 4) @quit //Выход из программы
 5) @showBets //Выводит на экран пользователя записанные ставки
 */
public class CommandChecker {
    public StringBuilder check(String sentence) {
        StringBuilder str = new StringBuilder();
        String[] subStr;
        String delimiter = " ";

        if (sentence.indexOf("new ") == 0) {
            str.append('N');
            subStr = sentence.split("new ");
            str.append(subStr[1]);
        } else if (sentence.indexOf("add ") == 0) {
            str.append('A');
            subStr = sentence.split(delimiter);
            str.append(subStr[1]).append(' ').append(subStr[2]);
        } else if (sentence.indexOf("end") == 0) {
            str.append('E');
        } else if (sentence.indexOf("showBets") == 0) {
            str.append('S');
        } else if (sentence.indexOf("quit") == 0) {
            str.append('Q');
            return str;
        } else if (sentence.indexOf("-help") == 0) {
            str.append('H');
            return str;
        } else {
            str.append('X');
        }
        return str;
    }
}
