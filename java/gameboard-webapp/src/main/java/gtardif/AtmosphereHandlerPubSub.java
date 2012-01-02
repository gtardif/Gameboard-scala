package gtardif;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.HeaderConfig;
import org.atmosphere.handler.AbstractReflectorAtmosphereHandler;
import org.atmosphere.websocket.WebSocketEventListenerAdapter;

/**
 * Simple PubSub resource that demonstrate many functionality supported by
 * Atmosphere JQuery Plugin and AtmosphereHandler extension.  You can compare that implementation
 * with the MeteorPubSub and the JQueryPubsub sample
 *
 * This sample support out of the box WebSocket, Long-Polling and Streaming
 *
 * @author Jeanfrancois Arcand
 */
public class AtmosphereHandlerPubSub extends AbstractReflectorAtmosphereHandler {

    @Override
    public void onRequest(AtmosphereResource<HttpServletRequest, HttpServletResponse> r) throws IOException {

        HttpServletRequest req = r.getRequest();
        HttpServletResponse res = r.getResponse();
        String method = req.getMethod();

        // Suspend the response.
        if ("GET".equalsIgnoreCase(method)) {
            // Log all events on the console, including WebSocket events.
            r.addEventListener(new WebSocketEventListenerAdapter());

            res.setContentType("text/html;charset=ISO-8859-1");

            Broadcaster b = lookupBroadcaster(req.getPathInfo());
            r.setBroadcaster(b);

            if (req.getHeader(HeaderConfig.X_ATMOSPHERE_TRANSPORT).equalsIgnoreCase(HeaderConfig.LONG_POLLING_TRANSPORT)) {
                req.setAttribute(ApplicationConfig.RESUME_ON_BROADCAST, Boolean.TRUE);
                r.suspend(-1, false);
            } else {
                r.suspend(-1);
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            Broadcaster b = lookupBroadcaster(req.getPathInfo());

            String message = req.getReader().readLine();

            if ((message != null) && (message.indexOf("message") != -1)) {
                b.broadcast(message.substring("message=".length()));
            }
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * Retrieve the {@link Broadcaster} based on the request's path info.
     *
     * @param pathInfo
     * @return the {@link Broadcaster} based on the request's path info.
     */
    Broadcaster lookupBroadcaster(String pathInfo) {
        String[] decodedPath = pathInfo.split("/");
        Broadcaster b = BroadcasterFactory.getDefault().lookup(decodedPath[decodedPath.length - 1], true);
        return b;
    }

}
