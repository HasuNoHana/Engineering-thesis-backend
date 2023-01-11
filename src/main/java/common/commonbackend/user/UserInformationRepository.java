package common.commonbackend.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends CrudRepository<UserInformation, Long> {
}