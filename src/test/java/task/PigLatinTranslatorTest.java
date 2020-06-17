package task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PigLatinTranslatorTest {

    private static PigLatinTranslator translator;

    @BeforeAll
    static void beforeAll() {
        translator = new PigLatinTranslator();
    }

    /*
    Words that start with a consonant have their first letter moved to the end of the word and the
    letters “ay” added to the end.
    ▪ Hello becomes Ellohay
    */
    @Test
    void translate_wordsThatStartWithConsonant() {
        assertEquals("Ellohay", translator.translateText("Hello"));
    }

    /*
    Words that start with a vowel have the letters “way” added to the end.
    ▪ apple becomes appleway
    */
    @Test
    void translate_wordsThatStartWithVowel() {
        assertEquals("appleway", translator.translateText("apple"));
    }

    /*
    Words that end in “way” are not modified.
    ▪ stairway stays as stairway
    */
    @Test
    void translate_wordsThatEndInWay() {
        assertEquals("stairway", translator.translateText("stairway"));
    }

    /*
     Punctuation must remain in the same relative place from the end of the word.
     ▪ can’t becomes antca’y
     ▪ end. becomes endway.
    */
    @Test
    void translate_punctuationStaysTheSame() {
        assertEquals("antca'y", translator.translateText("can't"));
        assertEquals("endway.", translator.translateText("end."));
    }

    /*
    Hyphens are treated as two words
    ▪ this-thing becomes histay-hingtay
    */
    @Test
    void translate_hyphensSeparateWordsProcessing() {
        assertEquals("histay-hingtay", translator.translateText("this-thing"));
    }

    /*
    Capitalization must remain in the same place.
    ▪ Beach becomes Eachbay
    ▪ McCloud becomes CcLoudmay
    */
    @Test
    void translate_capitalizationRemains() {
        assertEquals("Eachbay", translator.translateText("Beach"));
        assertEquals("CcLoudmay", translator.translateText("McCloud"));
    }

    @Test
    void translate_wholeSentence() {
        assertEquals("Ymay stairway isntwa'y inway away oodgay tatesay althoughway ustjay away ittlelay-usedway.",
                translator.translateText("My stairway isn't in a good state although just a little-used."));
    }
}