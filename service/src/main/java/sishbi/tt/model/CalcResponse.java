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
  private String times;
  private String by;
  private String answer;
  private String op;

  /**
   * Default constructor.
   */
  public CalcResponse() {
    times = "";
    by = "";
    answer = "";
    op = "";
  }

  /**
   * Create a calculation  (e.g. Answer = Times * By).
   * @param answer the answer.
   * @param times  times.
   * @param by by.
   */
  public CalcResponse(String answer, String times, String by, String op) {
    this.times = times;
    this.by = by;
    this.answer = answer;
    this.op = op;
  }

  public String getTimes() {
    return times;
  }

  public String getBy() {
    return by;
  }

  public String getAnswer() {
    return answer;
  }

  public void setTimes(String times) {
    this.times = times;
  }

  public void setBy(String by) {
    this.by = by;
  }

  public void setAnswer(String answer) {
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
      times.equals(calcResponse.times) &&
      op.equals(calcResponse.op) &&
      by.equals(calcResponse.by) &&
      answer.equals(calcResponse.answer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(times, op, by, answer);
  }
}
