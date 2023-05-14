package keywords;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import common.Folder;

public class Main {
    public static void main(String[] args) throws IOException {
        var folder = Folder.fromDirectory(new File("D:\\University\\Parralel programming\\Labs\\Lab 4\\texts"));

        var keywords = new HashSet<String>(List.of("java"));

        var keywordsSearchTask = new KeywordsSearchTask();
        var foundKeywords = keywordsSearchTask.getKeywords(folder, keywords);

        foundKeywords.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }
}