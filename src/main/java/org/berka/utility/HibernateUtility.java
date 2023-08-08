package org.berka.utility;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {

    private static SessionFactory SESSION_FACTORY;

    static { //sinif ayaga kalktigi an calisir static oldugu icin.


        try {
            SESSION_FACTORY=new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            e.printStackTrace();
        }


    }

    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
}
