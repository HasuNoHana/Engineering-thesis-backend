package common.commonbackend.house;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends CrudRepository<HouseEntity, Long> {
    HouseEntity findByJoinCode(String joinCode);
}
