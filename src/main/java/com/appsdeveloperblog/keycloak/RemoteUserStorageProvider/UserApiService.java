package com.appsdeveloperblog.keycloak.RemoteUserStorageProvider;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.awt.*;


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
