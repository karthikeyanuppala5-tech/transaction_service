package com.bank.transaction_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionRepository transactionRepository;

    // ---------------------------
    // CREATE DEBIT LEDGER ENTRY
    // ---------------------------
    @PostMapping("/debit")
    public Transaction createDebitEntry(
            @RequestParam Long accountId,
            @RequestParam Double amount
    ) {
        return transactionService.createDebitEntry(accountId, amount);
    }

    // ---------------------------
    // CREATE CREDIT LEDGER ENTRY
    // ---------------------------
    @PostMapping("/credit")
    public Transaction createCreditEntry(
            @RequestParam Long accountId,
            @RequestParam Double amount
    ) {
        return transactionService.createCreditEntry(accountId, amount);
    }

    // ---------------------------
    // CREATE TRANSFER LEDGER ENTRY (DOUBLE ENTRY)
    // ---------------------------
    @PostMapping("/transfer")
    public void createTransferLedger(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam Double amount
    ) {
         transactionService.createTransferLedger(fromAccountId, toAccountId, amount);
    }

    // ---------------------------
    // GET SINGLE TRANSACTION
    // ---------------------------
    @GetMapping("/{id}")
    public Transaction getTransaction(@PathVariable Long id) {
        return transactionService.getTransaction(id);
    }

    // ---------------------------
    // GET ALL TRANSACTIONS FOR AN ACCOUNT
    // ---------------------------
    @GetMapping("/account/{accountId}")
    public List<Transaction> getTransactionsForAccount(@PathVariable Long accountId) {
        return transactionService.getTransactionsForAccount(accountId);
    }

    // ---------------------------
    // GET ALL TRANSACTIONS
    // ---------------------------
    @GetMapping("/all")
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
