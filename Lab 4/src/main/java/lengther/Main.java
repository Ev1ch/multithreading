package lengther;

import common.Folder;
import common.Statistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    var folder = Folder.fromDirectory(new File("D:\\University\\Parralel programming\\Labs\\Lab 4\\texts"));

    var firstStart = System.currentTimeMillis();
    var task = new TextAnalyzeTask();
    var result = task.analyze(folder);
    var firstEnd = System.currentTimeMillis();
    var firstTime = firstEnd - firstStart;
    printStatistic(result);
    System.out.println("Parallel algorithm time: " + firstTime);

    System.out.println();

    var secondStart = System.currentTimeMillis();
    var secondResult = analyze(folder.getPath());
    var secondEnd = System.currentTimeMillis();
    var secondTime = secondEnd - secondStart;
    printStatistic(secondResult);
    System.out.println("Sequential algorithm time: " + secondTime);
  }

  public static void printStatistic(Map<Integer, Integer> sampling) {
    var statistic = new Statistic(sampling);
    System.out.println("Expected value: " + statistic.getExpectedValue());
    System.out.println("Dispersion: " + statistic.getDispersion());
    System.out.println("Standart error: " + statistic.getStandartError());
  }

  public static Map<Integer, Integer> analyze(String path) throws IOException {
    var fileArrayDeque = new ArrayDeque<File>();
    fileArrayDeque.add(new File(path));
    var lengths = new HashMap<Integer, Integer>();

    File currentFile;
    while ((currentFile = fileArrayDeque.poll()) != null) {
      if (currentFile.isDirectory()) {
        var subFiles = currentFile.listFiles();
        assert subFiles != null;
        fileArrayDeque.addAll(Arrays.asList(subFiles));
      } else {
        var lines = new ArrayList<String>();

        try (var reader = new BufferedReader(new FileReader(currentFile))) {
          String line = reader.readLine();

          while (line != null) {
            lines.add(line);
            line = reader.readLine();
          }
        }

        for (var line : lines) {
          var words = line.trim().split("(\\s|\\p{Punct})+");

          for (var word : words) {
            if (lengths.containsKey(word.length())) {
              lengths.put(word.length(), lengths.get(word.length()) + 1);
            } else {
              lengths.put(word.length(), 1);
            }
          }
        }
      }
    }

    return lengths;
  }
}
