package org.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class UtilHibernate {
    public SessionFactory createSessionFactory(Class classCreator){
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(classCreator);

        StandardServiceRegistryBuilder sBuilder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());

        SessionFactory sessionFactory = configuration.buildSessionFactory(sBuilder.build());

        return sessionFactory;
    }
}
