package org.apache.tomee.datanucleus.mapping;

import org.apache.tomee.datanucleus.domain.Agent;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

@Stateless
public class Entityraker {
    private
    @PersistenceContext
    EntityManager em;

    public boolean isInFlight() {
        return em.isOpen();
    }

    // NOTE this method is intended to fail on any database except HSQLDB (so we know we're using the correct DataSource)
    public boolean isResponsive() {
        String result = em.createNativeQuery("select 1 from INFORMATION_SCHEMA.SYSTEM_USERS").getSingleResult().toString();
        return "1".equals(result);
    }

    public boolean isShielded() {
        // NOTE isJoinedToTransaction() always returns true in TomEE 7.0.2 (fixed in TomEE 7.0.3)
        //return em.isJoinedToTransaction();
        try {
            // this call will trigger an exception if there's no active transaction
            em.joinTransaction();
            return true;
        } catch (TransactionRequiredException e) {
            return false;
        }
    }

    public int entityRecon() {
        return em.getMetamodel().getEntities().size();
    }

    public boolean isRakedIn(Class<?> entityClass) {
        return em.getMetamodel().getEntities().stream()
                .anyMatch(t -> t.getJavaType().equals(entityClass));
    }

    public boolean locateAgent() {
        return em.find(Agent.class, -1L) != null;
    }
}