package org.hibernate.bugs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;

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
    public void testEntityManagerFind() throws Exception {
        System.out.println("ENTITYMANAGER FIND START");
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        User expectedUser = this.persistUser(entityManager);
        expectedUser.setName("changedInSession");

        // returns instance from session context
        User actualUser = entityManager.find(User.class, expectedUser.getId());

        this.assertUser(actualUser, expectedUser);

        entityManager.close();
        System.out.println("ENTITYMANAGER FIND STOP");
    }

    @Test
    public void testJPQL() throws Exception {
        System.out.println("JPQL FIND START");
        EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        User expectedUser = this.persistUser(entityManager);
        expectedUser.setName("changedInSession");

        // returns instance from session context, but also queries the
        // database (see console output)
        User actualUser = entityManager.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class).setParameter("id", expectedUser.getId())
                .getSingleResult();

        this.assertUser(actualUser, expectedUser);

        entityManager.close();
        System.out.println("JPQL FIND STOP");
    }

    private void assertUser(final User actualUser, final User expectedUser) {
        assertThat(actualUser.getName(), CoreMatchers.is(expectedUser.getName()));
        assertThat(actualUser.getVersion(), CoreMatchers.is(expectedUser.getVersion()));
    }

    private User persistUser(final EntityManager entityManager) {
        User user = new User();
        user.setName("original");
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(user);
        transaction.commit();
        return user;
    }
}
