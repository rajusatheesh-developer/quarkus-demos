package com.appkodar.demos.quarkus.mappers;

import io.vertx.core.json.JsonObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {

        int code = 500;
        if (exception instanceof WebApplicationException) {
            code = ((WebApplicationException) exception).getResponse().getStatus();
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("exceptionType", exception.getClass().getName());
        jsonObject.put("code", code);

        if (exception.getMessage() != null) {
            jsonObject.put("error", exception.getMessage());
        }

        return Response.status(code)
                .entity(jsonObject)
                .build();
    }
}