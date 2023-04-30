package commonworder;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import common.Folder;

public class CommonWordsSearchTask {
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Set<String> getCommonWords(Folder folder) {
        return forkJoinPool.invoke(new FolderSearchTask(folder));
    }
}