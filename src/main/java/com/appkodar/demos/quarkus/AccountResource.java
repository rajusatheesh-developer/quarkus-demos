package com.appkodar.demos.quarkus;

import com.appkodar.demos.quarkus.validators.AccountValidator;
import com.appkodar.demos.quarkus.validators.Validator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.*;


@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    @Inject
    Validator<Account> validator;
    Set<Account> accounts = new HashSet<>();

    @GET
    public Set<Account> allAccounts() {
        return accounts;
    }

    @GET
    @Path("/{accountNumber}")
    public Account getAccountByAccountNumber(@PathParam("accountNumber") String accountNumber) {
        Optional<Account> account = accounts.stream()
                .filter(d -> Objects.equals(d.getAccountNumber(), accountNumber))
                .findFirst();
        return account.orElseThrow(() -> new WebApplicationException("Account with id of " + accountNumber + " does not exist.", 404));
    }

    @POST
    @Path("/create")
    public Response createAccount(Account account) {
        if (validator.validate(account)) {
            accounts.add(account);
            return Response.status(201).entity(account).build();
        }
        throw new WebApplicationException("Account number cannot be null.", 400);
    }


    @PostConstruct
    void setUp() {
        accounts.add(new Account(UUID.randomUUID().toString(),
                UUID.randomUUID().toString(), "George Baird", new BigDecimal("354.23")));
        accounts.add(new Account(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Mary Taylor", new BigDecimal("560.03")));
        accounts.add(new Account(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "Diana Rigg", new BigDecimal("422.00")));
    }


}
