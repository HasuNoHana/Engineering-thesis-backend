package common.commonbackend.repositories;

import common.commonbackend.entities.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    Room getRoomById(Long id);

    @Override
    default Iterable<Room> findAll() {
        //TODO change dummy implementation when initializing database //NOSONAR
        return List.of(
                new Room("Sypialnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg"),
                new Room("Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg"),
                new Room("Łazienka", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Bathroom_with_tub_and_fireplace_%28Pleasure_Point_Roadhouse%2C_Monterey_Bay%2C_California_-_30_September%2C_2002%29.jpg")
        );
    }
}
