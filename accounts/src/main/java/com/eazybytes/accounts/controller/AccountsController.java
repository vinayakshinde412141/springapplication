/**
 *
 */
package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.config.AccountsServiceConfig;
import com.eazybytes.accounts.model.Properties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eazybytes.accounts.model.Accounts;
import com.eazybytes.accounts.model.Customer;
import com.eazybytes.accounts.repository.AccountsRepository;

/**
 * @author Eazy Bytes
 *
 */

@RestController
public class AccountsController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    AccountsServiceConfig accountsServiceConfig;

    @PostMapping("/myAccount")
    public Accounts getAccountDetails(@RequestBody Customer customer) {

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId());
        if (accounts != null) {
            return accounts;
        } else {
            return null;
        }

    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

        Properties properties = new Properties(accountsServiceConfig.getMsg(), accountsServiceConfig.getBuildVersion()
                , accountsServiceConfig.getMailDetails(), accountsServiceConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }
}