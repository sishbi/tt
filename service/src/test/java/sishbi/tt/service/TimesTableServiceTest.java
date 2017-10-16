package sishbi.tt.service;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sishbi.tt.model.CalcResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.hamcrest.core.Is.is;

/**
 * tt.
 */
public class TimesTableServiceTest { //NOPMD
  /**
   * Unit test for getResults.
   */
  @ParameterizedTest //NOPMD
  @MethodSource("getAnswers")
  public void testGetTimes(String op, double by, int from, int to, double[] answers) {
//    System.out.println("by=" + by + ", from=" + from + ", to="+ to + ", answers=" + Arrays.toString(answers));
    final TimesTableService cut = new TimesTableService();
    final List<CalcResponse> results = cut.getResults(Arrays.asList(by), from, to, op);
    assertThat("IsList", results, is(iterableWithSize(answers.length)));
    final List<CalcResponse> expected = new ArrayList<>();
    for (int i = 0; i < answers.length; i++) {
//      System.out.println("i=" + i + ", times=" + (from + i));
      if ("*".equals(op)) {
        expected.add(new CalcResponse(answers[i], (from + i), by, "*"));
      }
      if ("/".equals(op)) {
        expected.add(new CalcResponse((from + i), answers[i], by, "/"));
      }
    }
    assertThat("Results", results, containsInAnyOrder(expected.toArray()));
  }

  static Stream<Arguments> getAnswers() {
    return Stream.of(Arguments.of("*", (double) 2, 10, 12, new double[] {20, 22, 24}),
                     Arguments.of("*", (double) 3, 3, 6,   new double[] {9, 12, 15, 18}),
                     Arguments.of("*", (double) 4, 1, 4,   new double[] {4, 8, 12, 16}),
                     Arguments.of("*", (double) 5, 5, 7,   new double[] {25, 30, 35}),
                     Arguments.of("*", (double) 6, 8, 12,  new double[] {48, 54, 60, 66, 72}),
                     Arguments.of("*", (double) 7, 2, 4,   new double[] {14, 21, 28}),
                     Arguments.of("*", (double) 8, 9, 11,  new double[] {72, 80, 88}),
                     Arguments.of("*", (double) 9, 7, 10,  new double[] {63, 72, 81, 90}),
                     Arguments.of("*", (double) 10, 5, 6,  new double[] {50, 60}),
                     Arguments.of("*", (double) 11, 1, 3,  new double[] {11, 22, 33}),
                     Arguments.of("*", (double) 12, 4, 7,  new double[] {48, 60, 72, 84}),
                     Arguments.of("/", (double) 2, 2, 3,   new double[] {4, 6}));
  }

}
