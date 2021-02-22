# kaka

## 프로젝트 요약
### 개발기간
2018/12/10 ~ 2019/01/17

### 참여인원
3명

### 개요
소켓 통신을 이용한 실시간 채팅 사이트입니다.
웹소켓과 Stomp 프로토콜을 이용하여 구현하였으며 여러명의 친구를 초대하여 실시간 채팅을 이용할 수 있습니다. 

### 주요기능
- 아이디를 이용해 친구 검색 및 친구 추가
- 등록된 친구들 중에서 대화할 상대를 선택하여 대화방 개설
- 개설된 대화방에서 여러명의 친구들과의 실시간 채팅 기능
- 채팅방목록에서 채팅방들의 가장 최근 대화내용 확인 및 최근에 채팅이 이루어진 순서대로 채팅방 목록 실시간 변화

## 개발환경
![찐개발환경](https://user-images.githubusercontent.com/45163261/108684598-72d24a00-7536-11eb-896f-4486da6bae90.PNG)

## 내가 구현한 기능

## 프로젝트 상세화면

## 사용된 기술
### 웹 소켓을 이용한 채팅방 구현
웹 소켓을 이용하여 채팅방을 구현하는데 있어 Stomp 프로토콜을 이용했습니다.
Stomp 프로토콜은 웹상에서 텍스트를 송수신하는 방식을 규약한 것으로 pub/sub 모델로 되어 있어 채팅방과 같은 실시간 양방향 통신을 구현하는데 유용합니다.
pub/sub 구조는 클라이언트가 특정 주제(topic)에 대해 구독(sub)을 하면 해당 주제에 대해 발행(pub)되는 정보에 대해 받아볼 수 있게됩니다.
이때 구독, 발행의 작업은 중간 브로커(broker)를 통해 이루어지게 됩니다.
![Stomp](https://user-images.githubusercontent.com/45163261/108693610-60114280-7541-11eb-9f3f-ca55efa7d625.PNG)


### JPA을 통한 RDBMS와 Redis을 이용한 채팅 데이터
계정 정보, 친구 관계, 프로필 사진과 같은 자주 변동되지 않고 형태가 일정한 데이터는 JPA 엔티티 매핑을 통해 mysql에 저장하였습니다.
채팅방 정보, 채팅 내역 등 DB와 io가 자주 일어나고 변동이 잦은 데이터들은 인메모리 방식인 redis에 저장함으로써 성능 향상을 도모하였습니다.
jpa maria redis

```java
Service
@NoArgsConstructor
public class RedisService {

    private static final String ROOMS = "ROOMS";
    private static final String USER_IN_ROOMS = "USER_IN_ROOMS";
    private static final String ROOM_IN_USERS = "ROOM_IN_USERS";
    private static final String ROOM_MESSAGES = "ROOM_MESSAGES";
    private static final String USER_MESSAGES = "USER_MESSAGES";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Room> rooms;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Set<Long>> userInRooms;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, List<Message>> roomMessages;

    public Room findRoom(Long roomId) {
        return rooms.get(ROOMS, roomId);
    }

    public void saveRoom(Room room) { rooms.put(ROOMS, room.getId(), room); }
    ...
```

spring security token
### Spring Security와 JWT를 이용한 인가, 인증
Spring Security와 JWT을 이용하여 일부 리소스를 인증된 회원만이 접근 가능하게 하였습니다.
또한 사용자의 권한을 확인하여 일부 리소스를 인가된 회원만이 접근 가능하게 하였습니다.

CICD
### 지속적인 통합(CI), 지속적인 배포(CD)
Travis, AWS s3를 통해 지속적인 통합(CI) 환경을 구축하였고
AWS CodeDeploy를 통해 AWS ec2 서버에 지속적인 배포(CD) 환경을 구축하였습니다.
