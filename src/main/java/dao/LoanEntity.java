package dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "loan")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "loan_id")
    private String loanId;
    @Column(name = "customer_id")
    private String customerId;
    @Column(name = "lenderId")
    private String lenderId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "remaining_amount")
    private double remainingAmount;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    @Column(name = "interest_per_day")
    private int interestPerDay;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "penalty_per_day")
    private double penaltyPerDay;

    public boolean isValidPaymentDate() {
        return paymentDate.compareTo(dueDate) <= 0;
    }

    public boolean isPastDueDate() {
        return LocalDate.now().isAfter(dueDate);
    }
}
