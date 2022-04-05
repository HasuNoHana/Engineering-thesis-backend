package common.commonbackend;

import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommonBackendApplicationTests {

	@Autowired
	private TaskRepository taskRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void shouldAddTaskToDatabase() {
		//given
		Task task = new Task(1L, "name");

		//when
		taskRepository.save(task);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
//		assertThat(taskRepository.getTaskById(1L));
	}

	@Test
	void shouldAddAndDeleteTask() {
		//given
		Task task = new Task(1L, "name");

		//when
		taskRepository.save(task);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
		taskRepository.delete(task);
		assertThat(taskRepository.count()).isEqualTo(0);
	}

	@Test
	void shouldSaveOnAlreadyExistingTask() {
		//given
		Task task = new Task(1L, "name");
		taskRepository.save(task);

		//when
		Task task1 = new Task(1L, "name1");
		taskRepository.save(task1);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
		assertThat(taskRepository.getTaskById(1L)).isEqualTo(task1);
	}

}
