package keywords;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

import common.Document;
import common.Words;

class DocumentSearchTask extends RecursiveTask<Map<String, Map<String, Long>>> {
    private final Document document;
    private final Set<String> keywords;

    DocumentSearchTask(Document document, Set<String> keywords) {
        super();
        this.document = document;
        this.keywords = keywords;
    }

    @Override
    protected Map<String, Map<String, Long>> compute() {
        var map = new HashMap<String, Long>();
        var words = Words.getWordsFromDocument(document);

        for (var word : words) {
            if (!keywords.contains(word)) {
               continue;
            }

            if(map.containsKey(word)) {
                map.put(word, map.get(word) + 1);
            } else {
                map.put(word, 1l);
            }
        }

        var result = new HashMap<String, Map<String, Long>>();
        result.put(document.getPath(), map);

        return result;
    }
}