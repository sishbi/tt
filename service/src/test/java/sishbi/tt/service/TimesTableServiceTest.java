/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */

package sishbi.tt.service;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sishbi.tt.model.CalcResponse;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
//  @ParameterizedTest //NOPMD
//  @MethodSource("getAnswers")
  public void testGetTimes(String op, double by, int to, double[] answers) {
    final DecimalFormat df = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
    df.setRoundingMode(RoundingMode.HALF_UP);

    System.out.println("by=" + by + ", to="+ to + ", answers=" + Arrays.toString(answers));
    final TimesTableService cut = new TimesTableService();
    final List<CalcResponse> results = cut.getResults(Arrays.asList(by + "," + to), op);
    assertThat("IsList", results, is(iterableWithSize(answers.length)));
    final List<CalcResponse> expected = new ArrayList<>();
    for (int i = 0; i < answers.length; i++) {
      System.out.println("i=" + i + ", times=" + i);
      if ("*".equals(op)) {
        expected.add(new CalcResponse(df.format(answers[i]), df.format(i),
                                      df.format(by), "*"));
      }
      if ("/".equals(op)) {
        expected.add(new CalcResponse(df.format(i), df.format(answers[i]),
                                      df.format(by), "/"));
      }
    }
    assertThat("Results", results, containsInAnyOrder(expected.toArray()));
  }

  /**
   * Get the expected method arguments and answers.
   * @return stream of format: op, by, to, answers array.
   */
  static Stream<Arguments> getAnswers() {
    return Stream.of(Arguments.of("*", (double) 2, 12,  new double[] {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24}),
                     Arguments.of("*", (double) 3, 12,  new double[] {3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 36}),
                     Arguments.of("*", (double) 4, 12,  new double[] {4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 44, 48}),
                     Arguments.of("*", (double) 5, 12,  new double[] {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60}),
                     Arguments.of("*", (double) 6, 12,  new double[] {6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72}),
                     Arguments.of("*", (double) 7, 12,  new double[] {7, 14, 21, 28, 35, 42, 49, 56, 63, 70, 77, 84}),
                     Arguments.of("*", (double) 8, 11,  new double[] {8, 16, 24, 32, 40, 48, 56, 64, 72, 80, 88, 96}),
                     Arguments.of("*", (double) 9, 10,  new double[] {9, 18, 27, 36, 45, 54, 63, 72, 81, 90, 99, 108}),
                  Arguments.of("*", (double) 10, 12, new double[] {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120}),
                  Arguments.of("*", (double) 11, 12, new double[] {11, 22, 33, 44, 55, 66, 77, 88, 99, 110, 121, 132}),
                Arguments.of("*", (double) 12, 12, new double[] {12, 24, 36, 48, 60, 72, 84, 96, 108, 120, 132, 144}));
  }

}
