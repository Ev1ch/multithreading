package commonworder;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

import common.Folder;

class FolderSearchTask extends RecursiveTask<Set<String>> {
    private final Folder folder;

    FolderSearchTask(Folder folder) {
        super();
        this.folder = folder;
    }

    @Override
    protected Set<String> compute() {
        List<RecursiveTask<Set<String>>> forks = new LinkedList<>();

        for (var subFolder : folder.getSubFolders()) {
            FolderSearchTask task = new FolderSearchTask(subFolder);
            forks.add(task);
            task.fork();
        }

        for (var document : folder.getDocuments()) {
            DocumentSearchTask task = new DocumentSearchTask(document);
            forks.add(task);
            task.fork();
        }

        var commonWords = forks.get(0).join();
        for (int i = 1; i < forks.size(); i++) {
            var taskUniqueWords = forks.get(i).join();

            commonWords.retainAll(taskUniqueWords);
        }

        return commonWords;
    }
}