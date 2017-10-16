package sishbi.tt.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Calculation Response.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalcResponse {
  private double times;
  private double by;
  private double answer;
  private String op;

  /**
   * Default constructor.
   */
  public CalcResponse() {
    times = 0;
    by = 0;
    answer = 0;
    op = "";
  }

  /**
   * Create a calculation  (e.g. Answer = Times * By).
   * @param answer the answer.
   * @param times  times.
   * @param by by.
   */
  public CalcResponse(double answer, double times, double by, String op) {
    this.times = times;
    this.by = by;
    this.answer = answer;
    this.op = op;
  }

  public double getTimes() {
    return times;
  }

  public double getBy() {
    return by;
  }

  public double getAnswer() {
    return answer;
  }

  public void setTimes(double times) {
    this.times = times;
  }

  public void setBy(double by) {
    this.by = by;
  }

  public void setAnswer(double answer) {
    this.answer = answer;
  }

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  @Override
  public String toString() {
    return "Calc{"
      + times + " "
      + op + " "
      + by + " = "
      + answer
      + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final CalcResponse calcResponse = (CalcResponse) o;
    return
      times == calcResponse.times &&
      op.equals(calcResponse.op) &&
      by == calcResponse.by &&
      answer == calcResponse.answer;
  }

  @Override
  public int hashCode() {
    return Objects.hash(times, op, by, answer);
  }
}
