package common.commonbackend.users.house_buddy;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseBuddyRepository extends CrudRepository<HouseBuddy, Long> {

    HouseBuddy save(HouseBuddy houseBuddy);

    HouseBuddy getHouseBuddyById(Long id);
}
