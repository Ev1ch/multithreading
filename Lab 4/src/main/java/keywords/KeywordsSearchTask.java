package keywords;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import common.Folder;

public class KeywordsSearchTask {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Map<String, Map<String, Long>> getKeywords(Folder folder, Set<String> keywords) {
        return forkJoinPool.invoke(new FolderSearchTask(folder, keywords));
    }
}