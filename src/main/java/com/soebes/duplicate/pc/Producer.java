package com.soebes.duplicate.pc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;

public class Producer {

  private static final Predicate<Path> IS_REGULAR_FILE = Files::isRegularFile;
  private static final Predicate<Path> IS_READABLE = Files::isReadable;
  private static final Predicate<Path> IS_VALID_FILE = IS_REGULAR_FILE.and(IS_READABLE);


  static final Queue queue = new Queue();

  static class CollectingThread implements Runnable {
    private final Path startLocation;
    public CollectingThread(Path startLocation) {
      this.startLocation = startLocation;
    }

    @Override
    public void run() {
      try (var pathStream = Files.walk(this.startLocation)) {
        pathStream.filter(IS_VALID_FILE)
            .forEach(entry -> {
              System.out.println("entry = " + entry);
              queue.add(entry);
            });
        queue.add(Queue.TERMINATION);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  static class CalculatingThread implements Runnable {

    @Override
    public void run() {

    }
  }
  public static void main(String[] args) {
    Path searchPath = Paths.get(args[0]);
    var collectingThread = new CollectingThread(searchPath);
    var start = Thread.ofPlatform().start(collectingThread);
    try {
      start.join();
    } catch (InterruptedException e) {
      start.interrupt();
    }
  }

}
