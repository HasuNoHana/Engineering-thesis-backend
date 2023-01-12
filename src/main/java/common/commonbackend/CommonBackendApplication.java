package common.commonbackend;

import common.commonbackend.houses.HouseEntity;
import common.commonbackend.houses.HouseRepository;
import common.commonbackend.images.Image;
import common.commonbackend.images.ImageService;
import common.commonbackend.images.ImageType;
import common.commonbackend.rooms.Room;
import common.commonbackend.rooms.RoomRepository;
import common.commonbackend.tasks.Task;
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


		Room room1 = new Room("Sypialnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg",houseEntity);
		Room room2 = new Room("Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg",houseEntity);
		Room room3 = new Room("Łazienka", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Bathroom_with_tub_and_fireplace_%28Pleasure_Point_Roadhouse%2C_Monterey_Bay%2C_California_-_30_September%2C_2002%29.jpg",houseEntity);
		this.roomRepository.save(room1);
		this.roomRepository.save(room2);
		this.roomRepository.save(room3);

		Room szatanskaKuchnia = new Room("Kuchnia piekła", "https://upload.wikimedia.org/wikipedia/pt/0/03/HKSIC.png",obcyHouse);
		this.roomRepository.save(szatanskaKuchnia);


		Task task1 = new Task("task1", 10, false, room1);
		Task task2 = new Task("task2", 20, false, room1);
		Task task3 = new Task("task3", 30, true, room1);
		Task task4 = new Task("task4", 40, false, room2);
		Task task5 = new Task("task5", 50, true, room2);
		this.taskRepository.save(task1);
		this.taskRepository.save(task2);
		this.taskRepository.save(task3);
		this.taskRepository.save(task4);
		this.taskRepository.save(task5);

		Task task6 = new Task("złożyć ofiarę z martwych niemowląt", 10, false, szatanskaKuchnia);
		Task task66 = new Task("przygotować pentagram", 20, true, szatanskaKuchnia);

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

		avatarImages.forEach(image -> imageService.saveImage(new Image(image, ImageType.AVATAR)));
	}

}
