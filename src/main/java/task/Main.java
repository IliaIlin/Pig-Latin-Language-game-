package task;

public class Main {

    public static void main(String[] args) {
        PigLatinTranslator pigLatinTranslator = new PigLatinTranslator();
        System.out.println("Your text:                    " + args[0]);
        System.out.println("Text translated to Pig Latin: " + pigLatinTranslator.translateText(args[0]));
    }
}
