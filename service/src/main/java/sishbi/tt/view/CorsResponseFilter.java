package sishbi.tt.view;

import org.apache.logging.log4j.LogManager;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * CORS Response Filter.
 */
@Provider
@PreMatching
public class CorsResponseFilter implements ContainerResponseFilter {
  private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger();

  @Override
  public void filter( ContainerRequestContext requestCtx, ContainerResponseContext responseCtx ) throws IOException {
    LOG.info( "Executing REST response filter" );

    responseCtx.getHeaders().add( "Access-Control-Allow-Origin", "*" );
    responseCtx.getHeaders().add( "Access-Control-Allow-Headers", "Content-Type" );
    responseCtx.getHeaders().add( "Access-Control-Allow-Credentials", "true" );
    responseCtx.getHeaders().add( "Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
  }
}