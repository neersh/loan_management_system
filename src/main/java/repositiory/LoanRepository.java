package repositiory;

import dao.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}
