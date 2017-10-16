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
  private final List<Double> by;
  private final int from;
  private final int to;
  private final String op;

  /**
   * Default constructor.
   */
  public CalcRequest() {
    by = new ArrayList<>();
    from = 0;
    to = 0;
    op = "";
  }

  /**
   * Create a calculation request.
   * @param by   list of times tables to include.
   * @param from min times (e.g. X * from)
   * @param to   max times (e.g. Y * to)
   */
  public CalcRequest(List<Double> by, int from, int to, String op) {
    this.by = by;
    this.from = from;
    this.to = to;
    this.op = op;
  }

  public List<Double> getBy() {
    return by;
  }

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public String getOp() {
    return op;
  }

  @Override
  public String toString() {
    return "CalcRequest{"
      + "by=" + by
      + ", from=" + from
      + ", to=" + to
      + ", op=" + op
      + '}';
  }
}
