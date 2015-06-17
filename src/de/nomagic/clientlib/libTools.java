package de.nomagic.clientlib;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class libTools
{

    private libTools()
    {
        // TODO Auto-generated constructor stub
    }

    public static void setLogLevel(String LogLevel)
    {
        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        try
        {
            final JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            final String logCfg =
            "<configuration>" +
              "<appender name='STDOUT' class='ch.qos.logback.core.ConsoleAppender'>" +
                "<encoder>" +
                  "<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>" +
                "</encoder>" +
              "</appender>" +
              "<root level='" + LogLevel + "'>" +
                "<appender-ref ref='STDOUT' />" +
              "</root>" +
            "</configuration>";
            ByteArrayInputStream bin;
            try
            {
                bin = new ByteArrayInputStream(logCfg.getBytes("UTF-8"));
                configurator.doConfigure(bin);
            }
            catch(UnsupportedEncodingException e)
            {
                // A system without UTF-8 ? - No chance to do anything !
                e.printStackTrace();
                System.exit(1);
            }
        }
        catch (JoranException je)
        {
          // StatusPrinter will handle this
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
    }

}
