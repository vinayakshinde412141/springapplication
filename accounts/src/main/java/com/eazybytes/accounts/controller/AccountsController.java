/**
 *
 */
package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.config.AccountsServiceConfig;
import com.eazybytes.accounts.model.*;
import com.eazybytes.accounts.service.client.CardsFeignClient;
import com.eazybytes.accounts.service.client.LoansFeignClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.eazybytes.accounts.repository.AccountsRepository;

import java.util.List;

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

    @Autowired
    LoansFeignClient loansFeignClient;

    @Autowired
    CardsFeignClient cardsFeignClient;

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

    @RequestMapping("/myCustomerDetails")
    public CustomerDetails getCustomerDetails(@RequestBody Customer customer){

        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loans=loansFeignClient.getLoanDetails(customer);
        List<Cards> cards=cardsFeignClient.getCardDetails(customer);

        CustomerDetails customerDetails=new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setLoans(loans);
        customerDetails.setCards(cards);

        return customerDetails;
    }

}