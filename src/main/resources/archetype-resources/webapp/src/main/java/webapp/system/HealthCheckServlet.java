package com.github.chenjianjx.srb4jfullsample.webapp.system;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * HttpCode = 200 means healthy
 * Created by chenjianjx@gmail.com on 11/11/18.
 */
public class HealthCheckServlet extends HttpServlet {


    private static final Logger logger = LoggerFactory.getLogger(HealthCheckServlet.class);


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {
        WebApplicationContext ctx = WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());

        HealthStatus status = new HealthStatus();
        status.setDataSourceConnection(checkDataSource(ctx));
        status.setSmtpServerConnection(checkMailServer(ctx));

        resp.setContentType("application/json;charset=utf-8");
        resp.setStatus(status.isOverall() ? 200 : 500);
        resp.getWriter().write(status.toString());
        resp.getWriter().flush();
    }

    private boolean checkDataSource(WebApplicationContext ctx) {

        try {
            DataSource dataSource = (DataSource) ctx.getBean("dataSource");
            Connection conn = dataSource.getConnection();
            conn.close();
        } catch (SQLException e) {
            logger.error("Data source unhealthy", e);
            return false;
        }
        return true;
    }


    private boolean checkMailServer(WebApplicationContext ctx) {

        try {
            WebAppEnvProp webAppEnvProp = (WebAppEnvProp) ctx.getBean(WebAppEnvProp.class);
            Socket socket = new Socket(webAppEnvProp.getSmtpHost(), webAppEnvProp.getSmtpPort());
            socket.close();
        } catch (IOException e) {
            logger.error("Smtp connection unhealthy", e);
            return false;
        }
        return true;
    }

    private static final class HealthStatus {
        private boolean dataSourceConnection;
        private boolean smtpServerConnection;


        public boolean isDataSourceConnection() {
            return dataSourceConnection;
        }

        public void setDataSourceConnection(boolean dataSourceConnection) {
            this.dataSourceConnection = dataSourceConnection;
        }

        public boolean isOverall() {
            return dataSourceConnection;
        }

        public boolean isSmtpServerConnection() {
            return smtpServerConnection;
        }

        public void setSmtpServerConnection(boolean smtpServerConnection) {
            this.smtpServerConnection = smtpServerConnection;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
        }
    }

}
