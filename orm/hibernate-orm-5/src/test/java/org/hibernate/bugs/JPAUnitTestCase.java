package org.hibernate.bugs;

import java.time.Instant;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM,
 * using the Java Persistence API.
 */
public class JPAUnitTestCase {

    private EntityManagerFactory entityManagerFactory;

    @After
    public void destroy() {
        this.entityManagerFactory.close();
    }

    @Before
    public void init() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("templatePU");
    }

    @Test
    public void testInstantMax() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User();
        user.setInstant(Instant.MAX);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void testInstantMin() throws Exception {
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = new User();
        user.setInstant(Instant.MIN);
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
