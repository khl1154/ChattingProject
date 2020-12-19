package com.clone.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class ChatApplication {

	@Value("${spring.application.name}")
	String applicationName;

	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ChatApplication.class);
		builder.build().addListeners(new ApplicationPidFileWriter());
		builder.run(args);
	}

	@EventListener
	public void applicationStartedEvent(ApplicationStartedEvent applicationStartedEvent) {
		log.debug("applicationStartedEvent done!!");
	}

	@PostConstruct
	public void onStart() {
		log.info("START {}", applicationName);
	}

	@PreDestroy
	public void onExit() {
		log.info("EXIT {}", applicationName);
	}
}
