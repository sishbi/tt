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
      //calculate set of times tables 'times' * 'by' until 'to'
      for (int times = from; times <= to; times++) {
        final String answer = df.format(times * by);
        LOG.debug("Calculated: {} * {} = {}", times, df.format(by), answer);
        if (op.contains("*")) {
          // answer = times * by
          calculations.add(new CalcResponse(answer, Integer.toString(times), df.format(by), "*"));
        }
        if (op.contains("/")) {
          // times = answer / by
          calculations.add(new CalcResponse(Integer.toString(times), answer, df.format(by), "/"));
        }
      }
    }
    Collections.shuffle(calculations);
    LOG.debug("Calculated {} results", calculations.size());
    return calculations;
  }
}
