package common.commonbackend.dto;

import common.commonbackend.house.HouseEntity;
import lombok.Data;

@Data
public class RoomDTO {
    String name;
    String image;
    HouseEntity house;
}
