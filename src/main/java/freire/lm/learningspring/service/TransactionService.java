package freire.lm.learningspring.service;

import freire.lm.learningspring.dto.CreateTransactionDto;
import freire.lm.learningspring.model.Transaction;


public interface TransactionService {

    Transaction createTransaction(final CreateTransactionDto transactionData);

    Transaction retrieveTransaction(final long id);
}
