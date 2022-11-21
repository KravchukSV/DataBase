package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class UtilHibernate {
    private Configuration configuration = new Configuration().configure();
    protected SessionFactory createSessionFactory(){
        StandardServiceRegistryBuilder sBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());

        SessionFactory sessionFactory = configuration.buildSessionFactory(sBuilder.build());

        return sessionFactory;
    }

    protected void addAnnotated(Class classAnnotated){
        configuration.addAnnotatedClass(classAnnotated);
    }
}
