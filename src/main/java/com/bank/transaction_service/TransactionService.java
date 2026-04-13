package com.bank.transaction_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // ---------------------------
    // DEBIT LEDGER ENTRY
    // ---------------------------
    public Transaction createDebitEntry(Long accountId, Double amount) {

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setType(TransactionType.DEBIT);
        tx.setAmount(amount);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setStatus("SUCCESS");

        return transactionRepository.save(tx);
    }

    // ---------------------------
    // CREDIT LEDGER ENTRY
    // ---------------------------
    public Transaction createCreditEntry(Long accountId, Double amount) {

        Transaction tx = new Transaction();
        tx.setAccountId(accountId);
        tx.setType(TransactionType.CREDIT);
        tx.setAmount(amount);
        tx.setCreatedAt(LocalDateTime.now());
        tx.setStatus("SUCCESS");

        return transactionRepository.save(tx);
    }

    // ---------------------------
    // TRANSFER LEDGER ENTRY (DOUBLE ENTRY)
    // ---------------------------
    public String createTransferLedger(Long fromAccountId, Long toAccountId, Double amount) {

        // 1. Debit entry
        Transaction debitTx = new Transaction();
        debitTx.setAccountId(fromAccountId);
        debitTx.setType(TransactionType.DEBIT);
        debitTx.setAmount(amount);
        debitTx.setCreatedAt(LocalDateTime.now());
        debitTx.setStatus("SUCCESS");
        transactionRepository.save(debitTx);

        // 2. Credit entry
        Transaction creditTx = new Transaction();
        creditTx.setAccountId(toAccountId);
        creditTx.setType(TransactionType.CREDIT);
        creditTx.setAmount(amount);
        creditTx.setCreatedAt(LocalDateTime.now());
        creditTx.setStatus("SUCCESS");
        transactionRepository.save(creditTx);

        return "TRANSFER_LEDGER_RECORDED";
    }

    // ---------------------------
    // FETCH SINGLE TRANSACTION
    // ---------------------------
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
    }

    // ---------------------------
    // FETCH ALL TRANSACTIONS FOR AN ACCOUNT
    // ---------------------------
    public List<Transaction> getTransactionsForAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
