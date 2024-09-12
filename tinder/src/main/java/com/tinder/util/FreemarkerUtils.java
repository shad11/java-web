package com.tinder.util;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerUtils {
    private static final Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

    static {
        cfg.setClassForTemplateLoading(FreemarkerUtils.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public static void render(HttpServletResponse response, String template, Map<String, Object> data) {
        try {
            Template temp = cfg.getTemplate(template);
            Writer out = response.getWriter();

            response.setContentType("text/html");
            temp.process(data, out);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void render(HttpServletResponse response, String template) {
        render(response, template, new HashMap<>());
    }
}
