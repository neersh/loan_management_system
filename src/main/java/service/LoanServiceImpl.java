package service;

import dao.LoanEntity;
import org.springframework.beans.factory.annotation.Autowired;
import repositiory.LoanRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoanServiceImpl implements LoanService {


    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // Method to add a loan
    public LoanEntity addLoan(LoanEntity loan) {
        return loanRepository.save(loan);
    }

    // Method to get loans grouped by Lender ID
    public Map<String, List<LoanEntity>> getLoansGroupedByLenderId() {
        List<LoanEntity> loans = loanRepository.findAll();
        return loans.stream().collect(Collectors.groupingBy(LoanEntity::getLenderId));
    }

    // Method to get loans grouped by Interest Per Day
    public Map<Integer, List<LoanEntity>> getLoansGroupedByInterestPerDay() {
        List<LoanEntity> loans = loanRepository.findAll();
        return loans.stream().collect(Collectors.groupingBy(LoanEntity::getInterestPerDay));
    }

    // Method to get loans grouped by Customer ID
    public Map<String, List<LoanEntity>> getLoansGroupedByCustomerId() {
        List<LoanEntity> loans = loanRepository.findAll();
        return loans.stream().collect(Collectors.groupingBy(LoanEntity::getCustomerId));
    }

    // Method to get loans that have crossed the due date
    public List<LoanEntity> getLoansPastDueDate() {
        List<LoanEntity> loans = loanRepository.findAll();
        return loans.stream().filter(LoanEntity::isPastDueDate).collect(Collectors.toList());
    }

    // Method to validate loans and throw an exception if payment date is greater than due date
    public void validateLoans() {
        List<LoanEntity> loans = loanRepository.findAll();
        for (LoanEntity loan : loans) {
            if (!loan.isValidPaymentDate()) {
                throw new RuntimeException("Payment date cannot be greater than the due date for Loan: " + loan.getLoanId());
            }
        }
    }

}
