package dao;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoanDTO {
    private String longId;
    private String customerId;
    private String lenderId;
    private double amount;
    private double remainingAmount;
    private LocalDate paymentDate;
    private int interestPerDay;
    private LocalDate dueDate;
    private double penaltyPerDay;
}
