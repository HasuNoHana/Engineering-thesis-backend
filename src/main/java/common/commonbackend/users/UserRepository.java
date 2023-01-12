package common.commonbackend.users;

import common.commonbackend.houses.HouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findById(long id);

    List<User> findByHouseBuddy_House(HouseEntity myHouse);
}
