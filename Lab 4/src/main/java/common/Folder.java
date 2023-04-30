package common;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Folder {
  private final List<Folder> subFolders;
  private final List<Document> documents;
  private final String path;

  public Folder(String path, List<Folder> subFolders, List<Document> documents) {
    this.subFolders = subFolders;
    this.documents = documents;
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  public List<Folder> getSubFolders() {
    return new LinkedList<>(subFolders);
  }

  public List<Document> getDocuments() {
    return new LinkedList<>(documents);
  }

  public static Folder fromDirectory(File dir) throws IOException {
    List<Document> documents = new LinkedList<>();
    List<Folder> subFolders = new LinkedList<>();

    for (File entry : dir.listFiles()) {
      if (entry.isDirectory()) {
        subFolders.add(Folder.fromDirectory(entry));
      } else {
        documents.add(Document.fromFile(entry));
      }
    }

    return new Folder(dir.getPath(), subFolders, documents);
  }
}
