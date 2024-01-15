
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    static Scanner scan1 = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException {
        List<String> listWithWords = loadingWordsFromFile(Hangman.PATH);
        while (true) {
            startGame(listWithWords);
            exitFromMainLoop();
        }
    }

    public static void startGame(List<String> listWithWords) {
        exitFromMainLoop();
        StringBuffer randomWord = new StringBuffer(getRandomWord(listWithWords));
        System.out.println(randomWord);
        StringBuffer maskWord = getMaskWord(randomWord);
        int countMistakes = 0;
        gameLoop(countMistakes, randomWord, maskWord);
    }

    public static void gameLoop(int countMistakes, StringBuffer randomWord, StringBuffer maskWord) {
        String constWord = randomWord.toString();
        while (!isVictory(maskWord, constWord)) {
            System.out.println(Hangman.ARRAY_ALL_STATES[countMistakes]);
            System.out.println(Arrays.toString(maskWord.toString().toCharArray()));
            String input = validateIncomingSymbol();

            if (randomWord.indexOf(input) != -1) {
                while (randomWord.indexOf(input) != -1) {
                    int index = randomWord.indexOf(input);
                    maskWord.replace(index, index + 1, input);
                    randomWord.replace(index, index + 1, "_");
                }
            } else {
                System.out.println("Такой буквы в загаданном слове нет");
                countMistakes++;
                if (countMistakes == 6) {
                    System.out.println(Hangman.ARRAY_ALL_STATES[countMistakes]);
                    System.out.println("Вы проиграли");
                    break;
                }
            }
        }


    }

    public static String validateIncomingSymbol() {
        while (true) {
            System.out.println("Введите букву русского алфавита");
            Scanner scan = scan1;
            char input = scan.nextLine().charAt(0);
            if (input >= 'А' && input <= 'я') {
                return Character.toString(Character.toLowerCase(input));
            } else {
                System.out.println("Вы ввели неверный символ, повторите ввод");
            }
        }
    }

    public static boolean isVictory(StringBuffer maskWord, String constWord) {
        if (maskWord.toString().equals(constWord)){
            System.out.println("Victory!!!!!");
            return maskWord.toString().equals(constWord);
        }
        return false;
    }

    public static void exitFromMainLoop() {
        System.out.println("Введите 0 чтобы выйти с игры, или любую другую клавищу чтобы продолжить");
        Scanner scan = scan1;
        String input = scan.nextLine();
        if (input.equals("0")) {

            System.exit(0);
        }
    }

    public static String getRandomWord(List<String> list) {
        Random random = new Random();
        int randomIndex = random.nextInt(list.size());
        String randomWord = list.get(randomIndex);
        list.remove(randomIndex);
        return randomWord;
    }

    public static StringBuffer getMaskWord(StringBuffer randomWord) {
        StringBuffer maskWord = new StringBuffer(randomWord.length());
        maskWord.append("_".repeat(randomWord.length()));
        return maskWord;
    }

    public static List<String> loadingWordsFromFile(String path) throws FileNotFoundException {
        List<String> listWithWords = new ArrayList<>();
        File file = new File(path);
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String word = scan.nextLine();
            if (word.length() < 6) {
                listWithWords.add(word);
            }
        }
        scan.close();
        return listWithWords;
    }
}