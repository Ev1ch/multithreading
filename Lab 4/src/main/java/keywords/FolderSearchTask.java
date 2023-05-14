package keywords;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

import common.Folder;

class FolderSearchTask extends RecursiveTask<Map<String, Map<String, Long>>> {
    private final Folder folder;
    private final Set<String> keywords;

    FolderSearchTask(Folder folder, Set<String> keywords) {
        super();
        this.folder = folder;
        this.keywords = keywords;
    }

    @Override
    protected Map<String, Map<String, Long>> compute() {
        List<RecursiveTask<Map<String, Map<String, Long>>>> forks = new LinkedList<>();

        for (var subFolder : folder.getSubFolders()) {
            var task = new FolderSearchTask(subFolder, this.keywords);
            forks.add(task);
            task.fork();
        }

        for (var document : folder.getDocuments()) {
            var task = new DocumentSearchTask(document, this.keywords);
            forks.add(task);
            task.fork();
        }

        var foundKeywords = new HashMap<String, Map<String, Long>>();
        for (var fork : forks) {
            foundKeywords.putAll(fork.join());
        }

        return foundKeywords;
    }
}