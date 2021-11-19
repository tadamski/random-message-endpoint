package org.greetings.messages;

import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("/Sample")
public class SampleEndPoint {

    @GET
    @Path("/message-unsecured")
    public String helloworld(@Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        String caller = principal == null ? "anonymous" : principal.getName();

        return "Merry Christmas!";
    }

    @Inject
    JsonWebToken jwt;

    @GET()
    @Path("/message")
    @RolesAllowed({"Subscriber"})
    public String helloRolesAllowed(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String name = caller == null ? "anonymous" : caller.getName();
        boolean hasJWT = jwt.getClaimNames() != null;
        String helloReply = String.format("hello + %s, hasJWT: %s", name, hasJWT);
        return helloReply;
    }

}