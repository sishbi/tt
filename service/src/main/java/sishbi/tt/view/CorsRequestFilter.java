/*
 * Copyright (c) 2018 Simon Billingsley. All rights reserved.
 */

package sishbi.tt.view;

import org.apache.logging.log4j.LogManager;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * CORS Request Filter (for Pre-Flight OPTIONS request).
 */
@Provider
@PreMatching
public class CorsRequestFilter implements ContainerRequestFilter {
  private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger();

  @Override
  public void filter( ContainerRequestContext requestCtx ) throws IOException {
    LOG.info( "Executing REST request filter" );

    // When HttpMethod comes as OPTIONS, just acknowledge that it accepts...
    if ( requestCtx.getRequest().getMethod().equals( "OPTIONS" ) ) {
      LOG.info( "HTTP Method (OPTIONS) - Detected!" );

      // Just send a OK signal back to the browser
      requestCtx.abortWith( Response.status( Response.Status.OK ).build() );
    }
  }
}