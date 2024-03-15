package com.epam.labtaskspringcore.global;

import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Tracer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class TraceFilter implements Filter {

    private static final String TRACE_ID_HEADER_NAME = "traceparent";
    public static final String DEFAULT = "00";
    private final Tracer tracer;

    public TraceFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (!response.getHeaderNames().contains(TRACE_ID_HEADER_NAME)) {
            if (Optional.of(tracer).map(Tracer::currentTraceContext).map(CurrentTraceContext::context).isEmpty()) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            var context = tracer.currentTraceContext().context();
            var traceId = context.traceId();
            var parentId = context.spanId();
            var traceparent = DEFAULT + "-" + traceId + "-" + parentId + "-" + DEFAULT;
            response.setHeader(TRACE_ID_HEADER_NAME, traceparent);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
