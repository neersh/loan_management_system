package service;

import dao.LoanEntity;

import java.util.List;
import java.util.Map;

public interface LoanService {
    LoanEntity addLoan(LoanEntity loanEntity);

    Map<String, List<LoanEntity>> getLoansGroupedByLenderId();

    Map<Integer, List<LoanEntity>> getLoansGroupedByInterestPerDay();

    Map<String, List<LoanEntity>> getLoansGroupedByCustomerId();

    List<LoanEntity> getLoansPastDueDate();

    void validateLoans();
}
