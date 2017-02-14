package org.apache.tomee.datanucleus.mapping;

import org.apache.tomee.datanucleus.domain.Agent;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(Arquillian.class)
public class EntityrakerTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsWebInfResource("test-persistence.xml", "persistence.xml")
                .addAsWebInfResource("test-resources.xml", "resources.xml")
                .addClasses(Entityraker.class, Agent.class);
    }

    @Inject Entityraker raker;

    @Test
    public void shouldBeInFlight() {
        assertThat(raker.isInFlight(), is(true));
    }

    @Test
    public void shouldRespondToPing() {
        assertThat(raker.isResponsive(), is(true));
    }

    @Test
    public void shouldBeShielded() {
        assertThat(raker.isShielded(), is(true));
    }

    @Test
    public void shouldDiscoverMappedEntities() {
        assertThat(raker.entityRecon(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void shouldBeRakedIn() {
        assertThat(raker.isRakedIn(Agent.class), is(true));
    }

    @Test
    public void shouldNotLocateAgent() {
        assertThat(raker.locateAgent(), is(false));
    }
}