package br.com.elifazio.demoschedule;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.redis.spring.RedisLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@SpringBootApplication
@EnableScheduling // Habilita o agendamento de tarefas
@EnableSchedulerLock(defaultLockAtMostFor = "59s", defaultLockAtLeastFor = "59s") // Habilita o bloqueio de tarefas agendadas
public class DemoScheduleApplication {

	@Value("${server.port}")
	String port;

	public static void main(String[] args) {
		SpringApplication.run(DemoScheduleApplication.class, args);
	}

	@Scheduled(cron = "0 0/1 * * * ?") // Executa a cada 1 minuto
	@SchedulerLock(name = "scheduledTaskName") // Nome da tarefa agendada
	public void scheduledTask0() throws InterruptedException {
		// LockAssert.assertLocked();
		LoggerFactory.getLogger(DemoScheduleApplication.class).info("Scheduled task 0 running... {}", this.port);
		Thread.sleep(5000);
	}

	@Scheduled(cron = "0 0/1 * * * ?") // Executa a cada 1 minuto
	@SchedulerLock(name = "scheduledTaskName") // Nome da tarefa agendada
	public void scheduledTask1() throws InterruptedException {
		// LockAssert.assertLocked();
		LoggerFactory.getLogger(DemoScheduleApplication.class).info("Scheduled task 1 running... {}", this.port);
		Thread.sleep(5000);
	}

	@Scheduled(cron = "0 0/1 * * * ?") // Executa a cada 1 minuto
	@SchedulerLock(name = "scheduledTaskName") // Nome da tarefa agendada
	public void scheduledTask2() throws InterruptedException {
		LoggerFactory.getLogger(DemoScheduleApplication.class).info("Scheduled task 2 running... {}", this.port);
		Thread.sleep(5000);
	}

	@Bean
	public LockProvider lockProvider(RedisConnectionFactory connectionFactory) {
		return new RedisLockProvider(connectionFactory, DemoScheduleApplication.class.getSimpleName());
	}

}
