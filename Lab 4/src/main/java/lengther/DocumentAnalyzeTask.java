package lengther;

import common.Document;
import common.Words;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class DocumentAnalyzeTask extends RecursiveTask<Map<Integer, Integer>> {
  private final Document document;

  public DocumentAnalyzeTask(Document document) {
    super();
    this.document = document;
  }

  @Override
  protected Map<Integer, Integer> compute() {
    var words = Words.getWordsFromDocument(document);
    var lengths = new HashMap<Integer, Integer>();

    for (var word : words) {
      if (lengths.containsKey(word.length())) {
        lengths.put(word.length(), lengths.get(word.length()) + 1);
      } else {
        lengths.put(word.length(), 1);
      }

    }

    return lengths;
  }
}