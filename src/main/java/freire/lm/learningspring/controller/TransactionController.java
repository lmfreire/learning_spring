package freire.lm.learningspring.controller;

import freire.lm.learningspring.dto.CreateTransactionDto;
import freire.lm.learningspring.model.Transaction;
import freire.lm.learningspring.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("transactions")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody final CreateTransactionDto transactionData){

        final Transaction transaction = transactionService.createTransaction(transactionData);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Transaction> retrieveTransaction(@PathVariable final String id) {
        final Transaction transaction = transactionService.retrieveTransaction(Long.parseLong(id));

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
