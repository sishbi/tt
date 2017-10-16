package sishbi.tt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
  /**
   * Get list of results (e.g. A = B * C).
   * @param byList the multipliers.
   * @param from   the minimum number to calculate to.
   * @param to     the maximum number to calculate to.
   * @param op     the operators (*, /)
   * @return the list of calculation results.
   */
  public List<CalcResponse> getResults(List<Double> byList, int from, int to, String op) {
    LOG.debug("Calculating results: by={}, from={}, to={}", byList, from, to);
    final List<CalcResponse> calculations = new ArrayList<>();
    for (final Double by : byList) {
      //calculate set of times tables 'times' * 'by' until 'to'
      for (int times = from; times <= to; times++) {
        final double answer = times * by;
        if (op.contains("*")) {
          // answer = times * by
          calculations.add(new CalcResponse(answer, times, by, "*"));
        }
        if (op.contains("/")) {
          // times = answer / by
          calculations.add(new CalcResponse(times, answer, by, "/"));
        }
      }
    }
    Collections.shuffle(calculations);
    LOG.debug("Calculated {} results", calculations.size());
    return calculations;
  }
}
