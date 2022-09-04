package com.appkodar.demos.quarkus.validators;


import com.appkodar.demos.quarkus.Account;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AccountValidator implements Validator<Account> {

    @Override
    public boolean validate(Account obj) {
        if (obj == null)
            return false;
        return obj.getAccountNumber() != null;
    }
}
