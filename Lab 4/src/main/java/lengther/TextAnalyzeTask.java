package lengther;

import common.Folder;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class TextAnalyzeTask {
  private final ForkJoinPool forkJoinPool = new ForkJoinPool();

  public Map<Integer, Integer> analyze(Folder folder) {
    return forkJoinPool.invoke(new FolderAnalyzeTask(folder));
  }
}