import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
      static String calc(String input) {
        int res = 0;

        Pattern isIncorrect = Pattern.compile("[^IVXLCDMZ\\d-*/+\\s]", Pattern.CASE_INSENSITIVE);
        Matcher matcherIncorrect = isIncorrect.matcher(input);
        if(matcherIncorrect.find()){
            throw new RuntimeException("Введены некорректные символы.");
        }

        Pattern containsRoman = Pattern.compile("[IVXLCDMZ]");
        Pattern containsArabic = Pattern.compile("\\d");

        Matcher matcherRoman = containsRoman.matcher(input);
        Matcher matcherArabic = containsArabic.matcher(input);
        boolean isRomanFound = matcherRoman.find();
        if(isRomanFound && matcherArabic.find()){
            throw new RuntimeException("Калькулятор может обрабатывать либо арабские, либо римские числа одновременно.");
        }

        String [] splitString = input.split(" ");
        if(splitString.length != 3){
            throw new RuntimeException("Введено некорректное количество операндов.");
        }

        String operator = splitString[1];
        int firstOperand  = isRomanFound ? romanToInt(splitString[0]) : Integer.parseInt(splitString[0]);
        int secondOperand = isRomanFound ? romanToInt(splitString[2]) : Integer.parseInt(splitString[2]);

        if(firstOperand > 10 || firstOperand < 0 || secondOperand > 10 || secondOperand < 0){
            throw new RuntimeException("Вводимые числа должны лежать в диапазоне от 1 до 10.");
        }

        switch (operator) {
            case "+":
                res = firstOperand + secondOperand;
                break;
            case "-":
                res = firstOperand - secondOperand;
                break;
            case "*":
                res = firstOperand * secondOperand;
                break;
            case "/":
                res = firstOperand / secondOperand;
                break;
        }
        if(isRomanFound && res < 1){
            throw new RuntimeException("Возвращаемое значение операции с римскими числами может быть только положительным числом больше 0.");
        }
        return isRomanFound ? intToRoman(res) : Integer.toString(res);
    }

    static String intToRoman(int num)
    {
        String [] keys = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        Integer [] vals = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while(ind < keys.length)
        {
            while(num >= vals[ind])
            {
                var d = num / vals[ind];
                num = num % vals[ind];
                for(int i=0; i<d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }

        return ret.toString();
    }

    static int romanToInt(String s) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int result = 0;
        int i = 0;

        for (int j = 0; j < numerals.length; j++) {
            while (s.startsWith(numerals[j], i)) {
                i += numerals[j].length();
                result += values[j];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Введите выражение:");

        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String input = scanner.nextLine();
            String res = calc(input);
            System.out.println(res);
        }
    }
}