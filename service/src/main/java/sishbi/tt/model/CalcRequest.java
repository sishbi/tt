package sishbi.tt.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Calculation Request.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalcRequest {
  final List<Integer> by;
  final int from;
  final int to;

  /**
   * Default constructor.
   */
  public CalcRequest() {
    by = new ArrayList<>();
    from = 0;
    to = 0;
  }

  /**
   * Create a calculation request.
   * @param by   list of times tables to include.
   * @param from min times (e.g. X * from)
   * @param to   max times (e.g. Y * to)
   */
  public CalcRequest(List<Integer> by, int from, int to) {
    this.by = by;
    this.from = from;
    this.to = to;
  }

  public List<Integer> getBy() {
    return by;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  @Override
  public String toString() {
    return "CalcRequest{"
      + "by=" + by
      + ", from=" + from
      + ", to=" + to
      + '}';
  }
}
