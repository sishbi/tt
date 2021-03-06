/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */

package sishbi.tt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sishbi.tt.model.CalcRequest;
import sishbi.tt.model.CalcResponse;
import sishbi.tt.service.TimesTableService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Times Tables resource.
 */
@Path("/times")
public class TimesTableResource {
  private static final Logger LOG = LogManager.getLogger();
  @Inject
  private TimesTableService service;

  /**
   * Initialise the JAX-RS bean.
   */
  @PostConstruct
  public void init() {
    LOG.info("Starting TT");
  }

  /**
   * Handle a POST request to calculate the times tables.
   * @param request the calculation request.
   * @return a list of calculation results.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<CalcResponse> getTimes(CalcRequest request) {
    LOG.debug("Calculation request: {}", request);
    if (request == null) {
      LOG.info("Invalid request - returning empty response");
      return null;
    }
    //calculate set of times tables times 'by' until 'to'
    final List<CalcResponse> results = service.getResults(request.getBy(), request.getOp());
    LOG.debug("Calculation results: {}", results);
    return results;
  }
}
