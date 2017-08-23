package org.tcat.frame.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.tcat.frame.util.PropertiesUtil;
import org.tcat.frame.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Lin on 2017/8/23.
 */
@WebFilter(filterName = "managementFilter", urlPatterns = {
        "/autoconfig", "/beans", "/configprops", "/dump", "/env"
        , "/health", "/info", "/loggers", "/mappings", "/metrics"
        , "/trace", "/auditevents", "/logfile", "/docs"})
@Order(1)
public class ManagementFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ManagementFilter.class);
    private static Properties properties = PropertiesUtil.loadPropertiesFromResourceFile("config.properties");
    private static String managementToken;

    static {
        managementToken = properties.getProperty("management.token");
        if (StringUtils.isEmptyByTrim(managementToken)) {
            logger.error("config.properties management.token configuration is empty, the system security may be hidden!!!");
            logger.error("config.properties 中 management.token 配置为空，系统安全可能存在隐患!!!");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("  =========================  ManagementFilter...init..........");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.info("  =========================  ManagementFilter...do Something..........");
        if (StringUtils.isEmptyByTrim(managementToken) || !managementToken.equals(request.getParameter("key"))) {
            ((HttpServletResponse) response).sendRedirect(properties.getProperty("domain"));
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("  =========================  ManagementFilter...destroy..........");
    }

}
