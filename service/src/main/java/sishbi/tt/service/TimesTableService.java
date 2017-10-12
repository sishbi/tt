package sishbi.tt.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sishbi.tt.model.CalcResult;

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
   * @return the list of calculation results.
   */
  public List<CalcResult> getResults(List<Integer> byList, int from, int to) {
    LOG.debug("Calculating results: by={}, from={}, to={}", byList, from, to);
    final List<CalcResult> calculations = new ArrayList<>();
    for (final Integer by : byList) {
      //calculate set of times tables 'times' * 'by' until 'to'
      for (int times = from; times <= to; times++) {
        final int answer = times * by;
        calculations.add(new CalcResult(answer, times, by));
      }
    }
    LOG.debug("Calculated {} results", calculations.size());
    return calculations;
  }
}
