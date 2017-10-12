package sishbi.tt.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sishbi.tt.model.CalcRequest;
import sishbi.tt.model.CalcResult;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * tt.
 */
@Path("/times")
public class TimesTableResource {
  private static final Logger LOG = LogManager.getLogger();
  @Inject
  private sishbi.tt.service.TimesTableService service;

  @PostConstruct
  public void init() {
    LOG.info("Starting TT");
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public List<CalcResult> getTimes(CalcRequest request) {
    LOG.debug("Received request: {}", request);
    if (request == null) {
      LOG.info("Invalid request - returning empty response");
      return null;
    }
    //calculate set of times tables times 'by' until 'to'
    final List<CalcResult> results = service.getResults(request.getBy(), request.getFrom(), request.getTo());
    LOG.debug("Calculation results: {}", results);
    return results;
  }
}
