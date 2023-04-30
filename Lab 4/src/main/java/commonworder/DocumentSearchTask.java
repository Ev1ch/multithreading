package commonworder;

import java.util.Set;
import java.util.concurrent.RecursiveTask;

import common.Document;
import common.Words;

class DocumentSearchTask extends RecursiveTask<Set<String>> {
    private final Document document;

    DocumentSearchTask(Document document) {
        super();
        this.document = document;
    }

    @Override
    protected Set<String> compute() {
        return Words.getUniqueWordsFromDocument(document);
    }
}