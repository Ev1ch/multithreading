package lengther;

import common.Folder;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class FolderAnalyzeTask extends RecursiveTask<Map<Integer, Integer>> {
  private final Folder folder;

  public FolderAnalyzeTask(Folder folder) {
    super();
    this.folder = folder;
  }

  @Override
  protected Map<Integer, Integer> compute() {
    var forks = new LinkedList<RecursiveTask<Map<Integer, Integer>>>();

    for (var subFolder : folder.getSubFolders()) {
      var task = new FolderAnalyzeTask(subFolder);
      forks.add(task);
      task.fork();
    }

    for (var document : folder.getDocuments()) {
      var task = new DocumentAnalyzeTask(document);
      forks.add(task);
      task.fork();
    }

    var result = new HashMap<Integer, Integer>();
    for (var fork : forks) {
      var forkResult = fork.join();

      forkResult.forEach((key, value) -> {
        if (result.containsKey(key)) {
          result.put(key, result.get(key) + value);
        } else {
          result.put(key, value);
        }
      });
    }

    return result;
  }
}