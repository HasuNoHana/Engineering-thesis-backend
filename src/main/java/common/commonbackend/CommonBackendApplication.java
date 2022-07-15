package common.commonbackend;

import common.commonbackend.entities.Room;
import common.commonbackend.entities.Task;
import common.commonbackend.entities.User;
import common.commonbackend.repositories.RoomRepository;
import common.commonbackend.repositories.TaskRepository;
import common.commonbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class CommonBackendApplication {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CommonBackendApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void addFirstDataAfterStartup() {
//		User user1 = new User("zuza", "haslo");
//		User user2 = new User("filip", "password");
//		this.userRepository.save(user1);
//		this.userRepository.save(user2);
//
//		Room room1 = new Room("Sypialnia", "https://upload.wikimedia.org/wikipedia/commons/3/31/White_paper.jpg");
//		Room room2 = new Room("Kuchnia", "https://upload.wikimedia.org/wikipedia/commons/b/b8/L_K%C3%BCche_2015.jpg");
//		Room room3 = new Room("Łazienka", "https://upload.wikimedia.org/wikipedia/commons/8/8a/Bathroom_with_tub_and_fireplace_%28Pleasure_Point_Roadhouse%2C_Monterey_Bay%2C_California_-_30_September%2C_2002%29.jpg");
//		this.roomRepository.save(room1);
//		this.roomRepository.save(room2);
//		this.roomRepository.save(room3);
//
//		Task task1 = new Task("task1", 10, false, room1);
//		Task task2 = new Task("task2", 20, false, room1);
//		Task task3 = new Task("task3", 30, true, room1);
//		Task task4 = new Task("task4", 40, false, room2);
//		Task task5 = new Task("task5", 50, true, room2);
//		this.taskRepository.save(task1);
//		this.taskRepository.save(task2);
//		this.taskRepository.save(task3);
//		this.taskRepository.save(task4);
//		this.taskRepository.save(task5);
	}

}
