package task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Character.*;

public class PigLatinTranslator {

    private final List<Character> vowels = List.of('a', 'e', 'i', 'o', 'u', 'y', 'A', 'I', 'E', 'O', 'U', 'Y');
    private final List<Character> terminals = List.of('.', ',', '?', '!', ';', ':');
    private final String HYPHEN = "-";
    private final String APOSTROPHE = "\'";
    private final String SPACE = " ";

    private final String VOWEL_CASE_ENDING = "way";
    private final String CONSONANT_CASE_ENDING = "ay";
    private final String NO_CHANGE_ENDING = "way";

    public String translateText(final String textToBeTranslated) {
        return Stream.of(textToBeTranslated.split(SPACE))
                .map(wordToBeTranslated ->
                        Stream.of(wordToBeTranslated.split(HYPHEN))
                                .map(this::translateWord)
                                .collect(Collectors.joining(HYPHEN))
                )
                .collect(Collectors.joining(SPACE));
    }

    private String translateWord(final String wordToBeTranslated) {
        int indexOfLastCharacter = wordToBeTranslated.length() - 1;
        Character lastCharacter = wordToBeTranslated.charAt(indexOfLastCharacter);
        return terminals.contains(lastCharacter) ?
                translateWithoutTerminals(wordToBeTranslated.substring(0, indexOfLastCharacter)) + lastCharacter
                : translateWithoutTerminals(wordToBeTranslated);
    }

    private String translateWithoutTerminals(final String wordToBeTranslated) {
        if (wordToBeTranslated.endsWith(NO_CHANGE_ENDING)) {
            return wordToBeTranslated;
        }
        StringBuilder stringBuilder = new StringBuilder(wordToBeTranslated.toLowerCase());
        if (vowels.contains(wordToBeTranslated.charAt(0))) {
            transform(stringBuilder, wordToBeTranslated, transformVowelCase);
        } else {
            transform(stringBuilder, wordToBeTranslated, transformConsonantCase);
        }
        return stringBuilder.toString();
    }

    private final Consumer<StringBuilder> transformConsonantCase = (final StringBuilder stringBuilder) -> {
        Character firstCharacter = stringBuilder.charAt(0);
        stringBuilder.deleteCharAt(0)
                .append(toLowerCase(firstCharacter))
                .append(CONSONANT_CASE_ENDING);
    };

    private final Consumer<StringBuilder> transformVowelCase = (final StringBuilder stringBuilder) ->
            stringBuilder.append(VOWEL_CASE_ENDING);

    private void transform(final StringBuilder stringBuilder,
                           final String wordToBeTranslated,
                           final Consumer<StringBuilder> transformer) {
        final boolean containsApostrophe = wordToBeTranslated.contains(APOSTROPHE);
        final List<Integer> indexesOfApostrophe = getIndexesOfApostrophe(wordToBeTranslated);
        if (containsApostrophe) {
            removeApostrophes(stringBuilder, indexesOfApostrophe);
        }
        transformer.accept(stringBuilder);
        if (containsApostrophe) {
            insertApostrophes(stringBuilder, wordToBeTranslated, indexesOfApostrophe);
        }
        List<Integer> indexesOfUpperCaseLetters = getIndexesOfUpperCaseLetters(wordToBeTranslated);
        for (int index : indexesOfUpperCaseLetters) {
            stringBuilder.setCharAt(index, toUpperCase(stringBuilder.charAt(index)));
        }
    }

    private List<Integer> getIndexesOfUpperCaseLetters(final String wordToBeTranslated) {
        List<Integer> upperCaseIndexes = new ArrayList<>();
        for (int i = 0; i < wordToBeTranslated.length(); i++) {
            if (isUpperCase(wordToBeTranslated.charAt(i))) {
                upperCaseIndexes.add(i);
            }
        }
        return upperCaseIndexes;
    }

    private List<Integer> getIndexesOfApostrophe(final String wordToBeTranslated) {
        List<Integer> indexesOfApostrophe = new ArrayList<>();
        char apostropheChar = APOSTROPHE.charAt(0);
        for (int i = 0; i < wordToBeTranslated.length(); i++) {
            if (wordToBeTranslated.charAt(i) == apostropheChar) {
                indexesOfApostrophe.add(i);
            }
        }
        return indexesOfApostrophe;
    }

    private void removeApostrophes(final StringBuilder stringBuilder,
                                   final List<Integer> indexesOfApostrophe) {
        for (int index : indexesOfApostrophe) {
            stringBuilder.deleteCharAt(index);
        }
    }

    private void insertApostrophes(final StringBuilder stringBuilder,
                                   final String wordToBeTranslated,
                                   final List<Integer> indexesOfApostrophe) {
        for (int index : indexesOfApostrophe) {
            int indexRelativeToTheEnd = stringBuilder.length() - (wordToBeTranslated.length() - 1 - index);
            stringBuilder.insert(indexRelativeToTheEnd, APOSTROPHE);
        }
    }
}
