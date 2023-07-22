package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.LoanDTO;
import dao.LoanEntity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.LoanService;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class LoanController {
    final ModelMapper modelMapper;

    final LoanService loanService;

    final ObjectMapper objectMapper;

    public LoanController(ModelMapper modelMapper, LoanService loanService, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.loanService = loanService;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/addLoan")
    public ResponseEntity<LoanEntity> addLoan(@RequestBody LoanDTO loan) {
        LoanEntity savedLoan = null;
        try {
            log.info("add loan request is : {} " + objectMapper.writeValueAsString(loan));
            LoanEntity loanEntity = modelMapper.map(loan, LoanEntity.class);
            savedLoan = loanService.addLoan(loanEntity);
            log.info("New loan with ID: " + savedLoan.getLoanId() + " has been added.");
        } catch (Exception e) {
            log.error("UnExcepted Error" + e.getMessage());

        }

        return new ResponseEntity<>(savedLoan, HttpStatus.CREATED);

    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", value = "/groupedByLenderId")
    public ResponseEntity<Map<String, List<LoanEntity>>> getLoansGroupedByLenderId() {
        log.info("Received a request to get loans grouped by Lender ID.");
        Map<String, List<LoanEntity>> loansByLenderId = loanService.getLoansGroupedByLenderId();
        return new ResponseEntity<>(loansByLenderId, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", value = "/groupedByInterestPerDay")
    public ResponseEntity<Map<Integer, List<LoanEntity>>> getLoansGroupedByInterestPerDay() {
        log.info("Received a request to get loans grouped by Interest Per Day.");
        Map<Integer, List<LoanEntity>> loansByInterestPerDay = loanService.getLoansGroupedByInterestPerDay();
        return new ResponseEntity<>(loansByInterestPerDay, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", value = "/groupedByCustomerId")
    public ResponseEntity<Map<String, List<LoanEntity>>> getLoansGroupedByCustomerId() {
        log.info("Received a request to get loans grouped by Customer ID.");
        Map<String, List<LoanEntity>> loansByCustomerId = loanService.getLoansGroupedByCustomerId();
        return new ResponseEntity<>(loansByCustomerId, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, consumes = "application/json", value = "/pastDueDate")
    public ResponseEntity<List<LoanEntity>> getLoansPastDueDate() {
        log.info("Received a request to get loans past the due date.");
        List<LoanEntity> pastDueLoans = loanService.getLoansPastDueDate();
        return new ResponseEntity<>(pastDueLoans, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", value = "/validate")
    public ResponseEntity<String> validateLoans() {
        try {
            log.info(" Received a request to validate loans.");
            loanService.validateLoans();
            return new ResponseEntity<>("All loans are valid.", HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
