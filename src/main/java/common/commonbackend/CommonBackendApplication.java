package common.commonbackend;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.houses.HouseRepository;
import common.commonbackend.images.Image;
import common.commonbackend.images.ImageService;
import common.commonbackend.images.ImageType;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.TaskEntity;
import common.commonbackend.tasks.TaskRepository;
import common.commonbackend.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
public class CommonBackendApplication {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;

	public static void main(String[] args) {
		SpringApplication.run(CommonBackendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void addFirstDataAfterStartup() {
		HouseEntity houseEntity = new HouseEntity();
		houseEntity.setJoinCode("1234");

		HouseEntity obcyHouse = new HouseEntity();
		obcyHouse.setJoinCode("6666");

		this.houseRepository.save(houseEntity);
		this.houseRepository.save(obcyHouse);

		userService.createUser("zuza", "haslo", "1234");
		userService.createUser("filip", "password", "1234");

		userService.createUser("obcy", "666", "6666");


		Room room1 = new Room("Sypialnia", "https://upload.wikimedia.org/wikipedia/commons/2/2d/Simbavati4.jpg", houseEntity);
		Room room2 = new Room("Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg", houseEntity);
		Room room3 = new Room("Łazienka", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Bathroom_with_tub_and_fireplace_%28Pleasure_Point_Roadhouse%2C_Monterey_Bay%2C_California_-_30_September%2C_2002%29.jpg", houseEntity);
		this.roomRepository.save(room1);
		this.roomRepository.save(room2);
		this.roomRepository.save(room3);

		Room szatanskaKuchnia = new Room("Kuchnia piekła", "https://upload.wikimedia.org/wikipedia/pt/0/03/HKSIC.png", obcyHouse);
		this.roomRepository.save(szatanskaKuchnia);


		TaskEntity task1 = new TaskEntity("task1", 10, false, room1);
		TaskEntity task2 = new TaskEntity("task2", 20, false, room1);
		TaskEntity task3 = new TaskEntity("task3", 30, true, room1);
		TaskEntity task4 = new TaskEntity("task4", 40, false, room2);
		TaskEntity task5 = new TaskEntity("task5", 50, true, room2);
		this.taskRepository.save(task1);
		this.taskRepository.save(task2);
		this.taskRepository.save(task3);
		this.taskRepository.save(task4);
		this.taskRepository.save(task5);

		TaskEntity task6 = new TaskEntity("złożyć ofiarę z martwych niemowląt", 10, false, szatanskaKuchnia);
		TaskEntity task66 = new TaskEntity("przygotować pentagram", 20, true, szatanskaKuchnia);

		this.taskRepository.save(task6);
		this.taskRepository.save(task66);

		List<String> avatarImages = List.of("https://upload.wikimedia.org/wikipedia/commons/2/25/Simple_gold_crown.svg",
				"https://upload.wikimedia.org/wikipedia/commons/3/35/Red-simple-heart-symbol-only.png",
				"https://upload.wikimedia.org/wikipedia/commons/2/2b/Black_Cat_Vector.svg",
				"https://upload.wikimedia.org/wikipedia/commons/4/47/Anchor_pictogram_grey.svg",
				"https://upload.wikimedia.org/wikipedia/commons/0/03/Oxygen480-apps-preferences-web-browser-cookies.svg",
				"https://upload.wikimedia.org/wikipedia/commons/6/6f/Lotus_flower_animation.svg",
				"https://upload.wikimedia.org/wikipedia/commons/f/f5/Circle-icons-flower.svg",
				"https://upload.wikimedia.org/wikipedia/commons/3/3c/Dog_%28134522%29_-_The_Noun_Project.svg");

		List<String> roomImages = List.of("https://upload.wikimedia.org/wikipedia/commons/5/59/Kitchen_life_in_the_18nt_cen.JPG",
				"https://upload.wikimedia.org/wikipedia/commons/d/dc/Kitchen_Renovation_Marlton_New_Jersey.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/9/9c/Set_Design-_Something%27s_Gotta_Give-Sony-Pictures-2003.png",
				"https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg",

				"https://upload.wikimedia.org/wikipedia/commons/2/2d/Simbavati4.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/0/0d/Bedroom_Mitcham.jpg",
				"https://upload.wikimedia.org/wikipedia/commons/b/b5/Beautiful_fitted_bedroom_design_lodnon.gif",
				"https://upload.wikimedia.org/wikipedia/commons/c/cf/Regency_Suite_King_-_Bedroom.jpg",

				"https://upload.wikimedia.org/wikipedia/commons/f/f9/Youngest_child_bedroom_-_second_floor_-_Tinsley_Living_Farm_-_Museum_of_the_Rockies_-_2013-07-08.jpg",

				"https://upload.wikimedia.org/wikipedia/commons/0/03/Edison_NJ_The_Coffee_House_beautiful_bathroom_decor.JPG",
				"https://upload.wikimedia.org/wikipedia/commons/b/b1/CasaMila-Bany.jpg");


		avatarImages.forEach(image -> imageService.saveImage(new Image(image, ImageType.AVATAR)));
		roomImages.forEach(image -> imageService.saveImage(new Image(image, ImageType.ROOM)));
	}

}
