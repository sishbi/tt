package sishbi.tt.service;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import sishbi.tt.model.CalcResult;

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
  public void testGetTimes(int by, int from, int to, int[] answers) {
//    System.out.println("by=" + by + ", from=" + from + ", to="+ to + ", answers=" + Arrays.toString(answers));
    final TimesTableService cut = new TimesTableService();
    final List<CalcResult> results = cut.getResults(Arrays.asList(by), from, to);
    assertThat("IsList", results, is(iterableWithSize(answers.length)));
    final List<CalcResult> expected = new ArrayList<>();
    for (int i = 0; i < answers.length; i++) {
//      System.out.println("i=" + i + ", times=" + (from + i));
      expected.add(new CalcResult(answers[i], (from + i), by));
    }
    assertThat("Results", results, containsInAnyOrder(expected.toArray()));
  }

  static Stream<Arguments> getAnswers() {
    return Stream.of(Arguments.of(2, 10, 12, new int[] {20, 22, 24}),
                     Arguments.of(3, 3, 6,   new int[] {9, 12, 15, 18}),
                     Arguments.of(4, 1, 4,   new int[] {4, 8, 12, 16}),
                     Arguments.of(5, 5, 7,   new int[] {25, 30, 35}),
                     Arguments.of(6, 8, 12,  new int[] {48, 54, 60, 66, 72}),
                     Arguments.of(7, 2, 4,   new int[] {14, 21, 28}),
                     Arguments.of(8, 9, 11,  new int[] {72, 80, 88}),
                     Arguments.of(9, 7, 10,  new int[] {63, 72, 81, 90}),
                     Arguments.of(10, 5, 6,  new int[] {50, 60}),
                     Arguments.of(11, 1, 3,  new int[] {11, 22, 33}),
                     Arguments.of(12, 4, 7,  new int[] {48, 60, 72, 84}));
  }

}
