package common.commonbackend.users.houseBuddy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseBuddyRepository extends CrudRepository<HouseBuddy, Long> {
}
