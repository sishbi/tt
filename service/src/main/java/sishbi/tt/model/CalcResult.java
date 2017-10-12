package sishbi.tt.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Calculation Result.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CalcResult {
  private int times;
  private int by;
  private int answer;

  /**
   * Default constructor.
   */
  public CalcResult() {
    times = 0;
    by = 0;
    answer = 0;
  }

  /**
   * Create a calculation  (e.g. Answer = Times * By).
   * @param answer the answer.
   * @param times  times.
   * @param by by.
   */
  public CalcResult(int answer, int times, int by) {
    this.times = times;
    this.by = by;
    this.answer = answer;
  }

  public int getTimes() {
    return times;
  }

  public int getBy() {
    return by;
  }

  public int getAnswer() {
    return answer;
  }

  public void setTimes(int times) {
    this.times = times;
  }

  public void setBy(int by) {
    this.by = by;
  }

  public void setAnswer(int answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    return "Calc{"
      + times
      + " * " + by
      + " = " + answer
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
    final CalcResult calcResult = (CalcResult) o;
    return
      times == calcResult.times &&
      by == calcResult.by &&
      answer == calcResult.answer;
  }

  @Override
  public int hashCode() {
    return Objects.hash(times, by, answer);
  }
}
