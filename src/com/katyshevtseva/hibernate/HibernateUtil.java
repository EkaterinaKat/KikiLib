package com.katyshevtseva.hibernate;

import com.katyshevtseva.config.ConfigConstants;
import com.katyshevtseva.config.ConfigUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import java.io.File;

class HibernateUtil {
    private static Session session;

    private static Session buildSession() {
        String configPath = new ConfigUtil().getConfigOrThrowExeption(ConfigConstants.HIBERNATE_CONFIG_FILE_PATH);
        try {
            File file = new File(configPath);
            SessionFactory factory = new AnnotationConfiguration().configure(file).buildSessionFactory();
            return factory.openSession();
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    static Session getSession() {
        if (session == null) {
            session = buildSession();
        }
        return session;
    }
}
