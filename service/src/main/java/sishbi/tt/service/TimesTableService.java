/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */

package sishbi.tt.service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sishbi.tt.model.CalcResponse;

/**
 * Times Table Service.
 */
@Stateless
public class TimesTableService {
  private static final Logger LOG = LogManager.getLogger();
  private static final DecimalFormat df = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
  static {
    df.setRoundingMode(RoundingMode.HALF_UP);
  }

  /**
   * Get list of results (e.g. A = B * C).
   * @param byList the multipliers.
   * @param op     the operators (*, /)
   * @return the list of calculation results.
   */
  public List<CalcResponse> getResults(List<String> byList, String op) {
    LOG.debug("Calculating results: by={}", byList);
    final List<CalcResponse> calculations = new ArrayList<>();
    for (final String byToStr : byList) {
      final String[] byTo = byToStr.split(",");
      final int from = 1;
      final double by = Double.parseDouble(byTo[0]);
      final int to = Integer.parseInt(byTo[1]);
      boolean odd = false;
      if (byTo.length > 2) {
        odd = "Odd".equalsIgnoreCase(byTo[2]);
        LOG.debug("including Odd numbers");
      }
      //calculate set of times tables 'times' * 'by' until 'to'
      if (odd) {
        calcResult(op, calculations, by, 0.5);
      }
      for (int times = from; times <= to; times++) {
        calcResult(op, calculations, by, times);
        if (odd) {
          calcResult(op, calculations, by, times + 0.5);
        }
      }
    }
    Collections.shuffle(calculations);
    LOG.debug("Calculated {} results", calculations.size());
    return calculations;
  }

  /**
   * Calculate an answer.
   * @param op the operator (* or /)
   * @param calculations the list of calculations
   * @param by times by
   * @param times the number to multiply
   */
  private void calcResult(String op, List<CalcResponse> calculations, double by, double times) {
    final String answer = df.format(times * by);
    LOG.debug("Calculated: {} * {} = {}", times, df.format(by), answer);
    if (op.contains("*")) {
      // answer = times * by
      calculations.add(new CalcResponse(answer, df.format(times), df.format(by), "*"));
    }
    if (op.contains("/")) {
      // times = answer / by
      calculations.add(new CalcResponse(df.format(times), answer, df.format(by), "/"));
    }
  }
}
