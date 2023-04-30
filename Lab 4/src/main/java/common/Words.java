package common;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Words {
  public static String[] getWordsFromLine(String line) {
    return line.trim().split("(\\s|\\p{Punct})+");
  }

  public static boolean isNumber(String word) {
    return word.matches("\\d+");
  }

  public static String[] getWordsFromDocument(Document document) {
    return document.getLines()
      .stream()
      .flatMap(line -> Stream.of(getWordsFromLine(line)))
      .filter(word -> word.length() > 1 && !isNumber(word))
      .map(word -> word.toLowerCase())
      .toArray(String[]::new);
  }

  public static Set<String> getUniqueWordsFromDocument(Document document) {
    var uniqueWords = new HashSet<String>();

    for (var line : document.getLines()) {
      var words = getWordsFromLine(line);

      for (var word : words) {
        if (word.length() > 1 && !isNumber(word)) {
          uniqueWords.add(word.toLowerCase());
        }
      }
    }

    return uniqueWords;
  }
}
