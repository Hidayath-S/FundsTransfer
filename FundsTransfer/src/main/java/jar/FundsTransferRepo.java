package jar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FundsTransferRepo extends JpaRepository<FundTransferRes, Integer> {
	

}
