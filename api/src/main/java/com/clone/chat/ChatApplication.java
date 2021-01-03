package com.clone.chat;

import com.clone.chat.domain.File;
import com.clone.chat.domain.Friend;
import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.FriendRepository;
import com.clone.chat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;
import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
@Slf4j
public class ChatApplication {

    @Value("${spring.application.name}")
    String applicationName;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ChatApplication.class);
        builder.build().addListeners(new ApplicationPidFileWriter());
        builder.run(args);
    }

    @EventListener
    public void applicationStartedEvent(ApplicationStartedEvent applicationStartedEvent) {
        log.debug("applicationStartedEvent done!! -> " + activeProfile);
        if ("dev".equals(activeProfile)) {
            File file = File.builder()
                    .id(1L)
                    .fileName("5db96de3e32fa818dc0d0aecfe82869f")
                    .filePath("https://dt8upu5cibhp1.cloudfront.net/5db96de3e32fa818dc0d0aecfe82869f")
                    .fileSize(18912L)
                    .originalFileName("기본이미지.jpg")
                    .build();
            fileRepository.save(file);

            List<User> users = Arrays.asList(
                    UserBase.builder().id("user1").password(passwordEncoder.encode("1234")).nickName("user1-nick").build().map(User.class),
                    UserBase.builder().id("user2").password(passwordEncoder.encode("1234")).nickName("user2-nick").build().map(User.class),
                    UserBase.builder().id("user3").password(passwordEncoder.encode("1234")).nickName("user3-nick").build().map(User.class),
                    UserBase.builder().id("user4").password(passwordEncoder.encode("1234")).nickName("user4-nick").build().map(User.class),
                    UserBase.builder().id("user5").password(passwordEncoder.encode("1234")).nickName("user5-nick").build().map(User.class),
                    UserBase.builder().id("user6").password(passwordEncoder.encode("1234")).nickName("user6-nick").build().map(User.class)
            );
            for (User user : users) {
                user.setFile(file);
            }
            userRepository.saveAll(users);


            List<Friend> friends = Arrays.asList(
                    Friend.builder().userId("user1").userFriend(userRepository.findById("user2").get()).build(),
                    Friend.builder().userId("user1").userFriend(userRepository.findById("user3").get()).build(),
                    Friend.builder().userId("user2").userFriend(userRepository.findById("user4").get()).build(),
                    Friend.builder().userId("user2").userFriend(userRepository.findById("user5").get()).build()
            );
            friendRepository.saveAll(friends);


        }
    }


//    @Bean
//	@Profile("dev")
//	public void test() {
//		log.info("----");
//	}


    @PostConstruct
    public void onStart() {
        log.info("START {}", applicationName);
    }

    @PreDestroy
    public void onExit() {
        log.info("EXIT {}", applicationName);
    }

}
