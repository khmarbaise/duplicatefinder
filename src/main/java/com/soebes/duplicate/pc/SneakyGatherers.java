package com.soebes.duplicate.pc;

import java.util.function.Function;
import java.util.stream.Gatherer;

public class SneakyGatherers {

  static <T,R> Gatherer<T, ?, R> sneakyPredicate(Function<? super T, ? extends R> mapper) {
    //
    Gatherer.Integrator.Greedy<Void, T, R> integrator = (_, element, downstream) -> {
      try {
        return downstream.push(mapper.apply(element));
      } catch (Exception e) {
        throw new RuntimeException("gathererMapper", e);
      }
    };
    //
    return Gatherer.ofSequential(integrator);
  }

}
