package com.soebes.performance.streams;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchmarkStreamConcat {


  @Benchmark
  public List<Element> with_new_arraylist_constructor(Container content) {
    return content.getFancyStuffs().stream().flatMap(item -> {
      ArrayList<Element> objects = new ArrayList<>(item.getElements());
      objects.add(item.getElement());
      return objects.stream();
    }).collect(Collectors.toList());
  }

  @Benchmark
  public List<Element> with_new_arraylist_constructor_size(Container content) {
    return content.getFancyStuffs().stream().flatMap(item -> {
      ArrayList<Element> objects = new ArrayList<>(item.getElements().size() + 1);
      objects.add(item.getElement());
      objects.addAll(item.getElements());
      return objects.stream();
    }).collect(Collectors.toList());
  }

  @Benchmark
  public List<Element> with_new_arraylist(Container content) {
    return content.getFancyStuffs().stream().flatMap(item -> {
      ArrayList<Element> objects = new ArrayList<>();
      objects.add(item.getElement());
      objects.addAll(item.getElements());
      return objects.stream();
    }).collect(Collectors.toList());
  }

  @Benchmark
  public List<Element> with_stream_concat(Container content) {
    return content.getFancyStuffs()
      .stream()
      .flatMap(fs -> Stream.concat(Stream.of(fs.getElement()), fs.getElements().stream()))
      .collect(Collectors.toList());
  }

  @State(Scope.Thread)
  public static class Container {
    @Param({"10", "20", "50", "100", "200", "500", "1000"})
    int count;

    @Param({"10", "20", "50", "100", "200", "500", "1000"})
    int elementCount;

    private List<FancyStuff> fancyStuffs;

    public Container() {
      this.fancyStuffs = Collections.emptyList();
    }

    @Setup(Level.Trial)
    public void setup() {
      fancyStuffs = IntStream.rangeClosed(1, count)
        .mapToObj(i -> new FancyStuff(new Element(i + 1), createList(i)))
        .collect(Collectors.toList());
    }

    @TearDown(Level.Trial)
    public void tearDown() {
      this.fancyStuffs = Collections.emptyList();
    }

    private List<Element> createList(int factor) {
      return IntStream.rangeClosed(2, elementCount)
        .mapToObj(i -> new Element(i * factor))
        .collect(Collectors.toList());
    }

    public List<FancyStuff> getFancyStuffs() {
      return fancyStuffs;
    }
  }

}
