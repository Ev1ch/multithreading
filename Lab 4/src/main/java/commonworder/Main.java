package commonworder;

import java.io.File;
import java.io.IOException;
import common.Folder;

public class Main {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("D:\\University\\Parralel programming\\Labs\\Lab 4\\texts"));

        var commonWordsSearchTask = new CommonWordsSearchTask();
        var commonWords = commonWordsSearchTask.getCommonWords(folder);

        System.out.println(commonWords);
    }
}