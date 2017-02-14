package org.apache.tomee.datanucleus.setup;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.logging.Logger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Arquillian.class)
public class ApparatusTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(PingServlet.class);
    }

    @Test
    public void shouldSayHiFromContainer() throws Exception {
        Logger.getLogger(getClass().getName())
                .info("Hello from test running in " + Thread.currentThread().getContextClassLoader());
    }

    @RunAsClient @Test
    public void shouldRespondToPing(@ArquillianResource URI webappUri) throws Exception {
        URL pingUrl = webappUri.resolve("ping").toURL();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(pingUrl.openStream()))) {
            assertThat(reader.readLine(), is(equalTo("here")));
        }
        catch (FileNotFoundException e) {
            fail("Server returned a 404 response for the following URL: " + pingUrl);
        }
    }
}
