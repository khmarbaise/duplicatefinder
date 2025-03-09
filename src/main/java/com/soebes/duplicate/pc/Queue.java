package com.soebes.duplicate.pc;

import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Queue {
  public static final Path TERMINATION = Path.of("");

  private final BlockingQueue<Path> queue;

  public Queue() {
    this.queue = new ArrayBlockingQueue<>(5);
  }

  void add(Path path) {
    try {
      var offer = queue.offer(path, 200, TimeUnit.MILLISECONDS);
      System.out.println("offer = " + offer);
    } catch (InterruptedException e) {
      System.out.println("e = " + e);
    }
  }

}
