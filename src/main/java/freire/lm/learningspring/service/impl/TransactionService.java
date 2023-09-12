package freire.lm.learningspring.service.impl;

import freire.lm.learningspring.dto.CreateTransactionDto;
import freire.lm.learningspring.exception.AppException;
import freire.lm.learningspring.model.Transaction;
import freire.lm.learningspring.model.User;
import freire.lm.learningspring.repository.TransactionRepository;
import freire.lm.learningspring.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionService implements freire.lm.learningspring.service.TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(final CreateTransactionDto transactionData){
        final User foundPayer = userRepository.findById(transactionData.getPayer_id()).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));

        if(Objects.equals(foundPayer.getType(), "SELLER")) {
          throw new AppException("SELLER type users cannot send Money", HttpStatus.FORBIDDEN);
        }

        final float transactionValue = transactionData.getValue();

        final float payerCurrentBalance = foundPayer.getBalance();

        if(payerCurrentBalance < transactionValue){
            throw new AppException("Payer Balance not sufficient", HttpStatus.FORBIDDEN);
        }

        foundPayer.setBalance(payerCurrentBalance - transactionValue);

        final User foundPayee = userRepository.findById(transactionData.getPayee_id()).orElseThrow(() -> new AppException("User Not Found", HttpStatus.NOT_FOUND));

        final float payeeCurrentBalance = foundPayee.getBalance();

        foundPayee.setBalance(payeeCurrentBalance + transactionValue);

        final Transaction newTransaction = new Transaction(foundPayer, foundPayee, transactionValue);

        return transactionRepository.save(newTransaction);

    }

    public Transaction retrieveTransaction(final long id) {

        return transactionRepository.findById(id).orElseThrow(() -> new AppException("Transaction Not Found", HttpStatus.NOT_FOUND));

    }
}
