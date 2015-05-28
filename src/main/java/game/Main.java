package game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{
    static boolean finished = false;
    static List<Character> used  = new ArrayList<Character>();
    public static void main(String [] args){
        runConsole();

    }
    public static void runConsole(){
        used.clear();
        Scanner in = new Scanner(System.in);
        print("Choose language (russian or english):");
        String lang = in.next();
        String word = getRandomWord(lang);
        int tries = 0;
        int maxtries = word.length() + 5;
        char[] chars = word.toCharArray();
        char[] result = new char[chars.length];
        print("There is your word: " + toWord(result));
        boolean canPlay = true;
        while(canPlay){
            char c = ask(in);
            if(searchAndReplace(chars, c, result) == 0){
                print("There isn't any characters like this! You've got " + (maxtries - tries) + " tries.");
                print(toWord(result));
                tries ++;
            }else{
                print("There is " + searchAndReplace(chars, c, result)  + " characters like this in word. Keep guessing!");
                print("There is all you guessed: " + toWord(result));
            }
            printUsed();
            if(tries == maxtries) canPlay = false;
            if(compare(chars, result)) canPlay = false;
        }
        if(tries == maxtries){
            print("");
            print("You lose! The word was " + word);
        }
        if(compare(chars, result)){
            print("");
            print("You win! The word was \"" + word+"\"");
        }
        print("Do you want to play again?");
        String replay = in.next();
        if(replay.toLowerCase().equals("yes")) runConsole();
        in.close();
        finished = false;
    }
    static char ask(Scanner in){
        char c = '~';
        print("Guess a character:");
        String s = in.next();
        c = s.toCharArray()[0];
        if(used.contains(c)){
            print("You've already tried this character!");
            c  = ask(in);
        }
        if(!used.contains(c))used.add(c);
        return c;
    }
    public static void print(Object o){
        System.out.println(o);
    }
    public static String toWord(char[] chars){
        String s = "";
        for(int i = 0; i< chars.length; i++){
            if(chars[i] == 0) s += '_';
            else s += chars[i];
        }
        return s;
    }
    static int searchAndReplace(char[] srch, char obj, char[] target){
        int count = 0;
        for(int i = 0; i < srch.length; i++){
            if(srch[i] == obj){
                target[i] = obj;
                count ++;
            }
        }
        return count;
    }
    static boolean compare(char[] fst, char[] snd){
        return Arrays.equals(fst, snd);
    }

    static void printUsed(){
        String s = "This is characters you've already used: ";
        StringBuilder sb = new StringBuilder(s);

        for(char c: used){
            sb.append(c + ",");
        }
        print(sb.toString());
    }
    public static String getRandomWord(String lang ){
        try {
            String string = choose(new File("/words_" + lang +".txt"));
            return string;
        } catch (FileNotFoundException e) {
        }
        return "UnhandledException";
    }
    public static String choose(File f) throws FileNotFoundException {
        int n = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if(new Random().nextInt(n) == 0) {
                sc.close();
                return line;
            }
        }
        return "";
    }
}
