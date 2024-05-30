package com.appsdeveloperblog.keycloak.RemoteUserStorageProvider;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;


//RESTEasy Client

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
public interface UserApiService {

    @GET
    @Path("/{username}")
    User getUserDetails(@PathParam("username") String username);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{username}/verify-password")
    VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password);
}
