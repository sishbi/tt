/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */

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
  private final List<String> by;
  private final String op;

  /**
   * Default constructor.
   */
  public CalcRequest() {
    by = new ArrayList<>();
    op = "";
  }

  /**
   * Create a calculation request.
   * @param by   list of times tables to include.
   */
  public CalcRequest(List<String> by, String op) {
    this.by = by;
    this.op = op;
  }

  public List<String> getBy() {
    return by;
  }

  public String getOp() {
    return op;
  }

  @Override
  public String toString() {
    return "CalcRequest{"
      + "by=" + by
      + ", op=" + op
      + '}';
  }
}
