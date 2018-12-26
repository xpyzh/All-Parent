package leetcode;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 804. Unique Morse Code Words
 * International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.
 *
 * For convenience, the full table for the 26 letters of the English alphabet is given below:
 *
 * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
 * Now, given a list of words, each word can be written as a concatenation of the Morse code of each letter. For example, "cba" can be written as "-.-..--...", (which is the concatenation "-.-." + "-..." + ".-"). We'll call such a concatenation, the transformation of a word.
 *
 * Return the number of different transformations among all words we have.
 *
 * Example:
 * Input: words = ["gin", "zen", "gig", "msg"]
 * Output: 2
 * Explanation:
 * The transformation of each word is:
 * "gin" -> "--...-."
 * "zen" -> "--...-."
 * "gig" -> "--...--."
 * "msg" -> "--...--."
 *
 * There are 2 different transformations, "--...-." and "--...--.".
 * Note:
 *
 * The length of words will be at most 100.
 * Each words[i] will have length in range [1, 12].
 * words[i] will only consist of lowercase letters.
 */
public class UniqueMorseCodeWords {
    public static void main(String[] args) {
        String[] inputList = new String[]{"gin", "zen", "gig", "msg"};
        System.out.println(uniqueMorseRepresentations1(inputList));
        System.out.println(uniqueMorseRepresentations2(inputList));


    }

    public static int uniqueMorseRepresentations1(String[] words) {
        //init
        String[] morseCodeList = new String[]{".-",
                "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        HashMap<Character, String> decodingTable = new HashMap<>();
        for (int i = 0; i < morseCodeList.length; i++) {
            Character key = (char) (i + 'a');
            decodingTable.put(key, morseCodeList[i]);
        }
        //decode
        HashSet<String> resultSet = new HashSet<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            for (char letter : word.toCharArray()) {
                sb.append(decodingTable.get(letter));
            }
            resultSet.add(sb.toString());
        }
        return resultSet.size();
    }
    //更优
    public static int uniqueMorseRepresentations2(String[] words) {
        //init
        String[] morseCodeList = new String[]{".-",
                "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        HashSet<String> resultSet = new HashSet<>();
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            for (char letter : word.toCharArray()) {
                sb.append(morseCodeList[letter-'a']);//因为都是小写字母,减到'a'，就能和莫斯码的index对齐
            }
            resultSet.add(sb.toString());
        }
        return resultSet.size();
    }

}
