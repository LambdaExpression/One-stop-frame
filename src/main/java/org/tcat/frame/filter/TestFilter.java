package org.tcat.frame.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "testFilter", urlPatterns = "/*")
@Order(1)
public class TestFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig config) throws ServletException {
        log.info("  =========================  TestFilter...init..........");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("  =========================  TestFilter...do Something..........");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("  =========================  TestFilter...destroy..........");
    }

}