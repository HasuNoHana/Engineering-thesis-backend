package common.commonbackend;

import common.commonbackend.entities.Task;
import common.commonbackend.repositories.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CommonBackendApplicationTest {

	@Autowired
	private TaskRepository taskRepository;

	@Test
	void shouldAddTaskToDatabase() {
		//given
		taskRepository.deleteAll(); //remove tasks from different tests
		Task task = new Task("name");

		//when
		taskRepository.save(task);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
	}

	@Test
	void shouldAddAndDeleteTask() {
		//given
		taskRepository.deleteAll(); //remove tasks from different tests
		Task task = new Task("name");

		//when
		taskRepository.save(task);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
		taskRepository.delete(task);
		assertThat(taskRepository.count()).isZero();
	}

	@Test
	void shouldSaveOnAlreadyExistingTask() {
		//given
		taskRepository.deleteAll(); //remove tasks from different tests
		Task task = new Task("name");
		taskRepository.save(task);

		//when
		Task task1 = new Task(task.getId(), "name1");
		taskRepository.save(task1);

		//then
		assertThat(taskRepository.count()).isEqualTo(1);
		assertThat(taskRepository.getTaskById(task.getId())).isEqualTo(task1);
	}
}
