# kaka

## 개발기간
2020/12/20 ~ 2021/02/22

## 참여인원
3명

## 개요
**소켓 통신을 이용한 실시간 채팅 사이트**입니다.  
**웹소켓과 Stomp 프로토콜**을 이용하여 구현하였으며 여러명의 친구를 초대하여 실시간 채팅을 이용할 수 있습니다. 

## 주요기능
- 아이디를 이용해 친구 검색 및 친구 추가  
- 친구 목록에서 대화할 상대 클릭을 통한 1대1 대화 기능
- 등록된 친구들 중에서 대화할 상대를 선택하여 대화방 개설  
- 개설된 대화방에서 여러명의 친구들과의 실시간 채팅 기능  
- 채팅방목록에서 채팅방들의 가장 최근 대화내용 확인 및 최근에 채팅이 이루어진 순서대로 채팅방 목록 실시간 변화  

## 프로젝트 이용
-  http://13.124.86.222:9000/
- 직접 회원가입을 통해 이용해 보실 수 있습니다.  
- __같은 계정으로 여러개의 브라우저 또는 여러개의 탭에서 동시에 이용할 경우 정상적으로 동작하지 않을 수 있습니다.__  
>  __사용가능한 계정__  
> id : sero &nbsp;&nbsp;pw : 123  
> id : son  &nbsp;&nbsp;&nbsp;pw : 123  

## 개발환경
![찐개발환경](https://user-images.githubusercontent.com/45163261/108697007-a49edd00-7545-11eb-84f5-1f578d003e13.PNG)

## 프로젝트 상세화면
![친구리스트](https://user-images.githubusercontent.com/45163261/108715375-23a00f80-755e-11eb-8c62-187091af15ca.PNG)
![친구검색](https://user-images.githubusercontent.com/45163261/108715378-24d13c80-755e-11eb-934f-6e9541da0b20.PNG)
![채팅리스트](https://user-images.githubusercontent.com/45163261/108715379-269b0000-755e-11eb-9518-1532848f5d20.PNG)
![방생성](https://user-images.githubusercontent.com/45163261/108715389-2864c380-755e-11eb-941c-e94ddcbdec3d.PNG)
![채팅](https://user-images.githubusercontent.com/45163261/108715393-2995f080-755e-11eb-8dea-9edfc6ebd32f.PNG)


## 사용된 기술
### 웹 소켓을 이용한 채팅방 구현
웹 소켓을 이용하여 채팅방을 구현하는데 있어 **Stomp 프로토콜**을 이용했습니다. Stomp 프로토콜은 웹상에서 텍스트를 송수신하는 방식을 규약한 것으로 **pub/sub 모델**로 되어 있어 채팅방과 같은 실시간 양방향 통신을 구현하는데 유용합니다.  
pub/sub 구조는 클라이언트가 특정 주제(topic)에 대해 구독(sub)을 하면 해당 주제에 대해 발행(pub)되는 정보에 대해 받아볼 수 있게됩니다. 이때 구독, 발행의 작업은 중간 브로커(broker)를 통해 이루어지게 됩니다.
![Stomp](https://user-images.githubusercontent.com/45163261/108693610-60114280-7541-11eb-9f3f-ca55efa7d625.PNG)


### JPA을 통한 ORM와 Redis을 이용한 채팅 데이터
계정 정보, 친구 관계, 프로필 사진과 같은 자주 변동되지 않고 형태가 일정한 데이터는 JPA 엔티티 매핑을 통해 **MariaDB**에 저장했습니다.  
채팅방 정보, 채팅 내역 등 DB와 io가 자주 일어나고 변동이 잦은 데이터들은 인메모리 방식인 **Redis**에 저장함으로써 성능 향상을 도모했습니다.

#### redis 적용 코드
```java
@Service
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
    //...이하생략
```

### SpringSecurity와 JWT를 이용한 인가, 인증
**SpringSecurity**와 **JWT**을 이용하여 일부 리소스를 인증된 회원만이 접근 가능하게 했습니다. 또한 사용자의 권한을 확인하여 일부 리소스를 인가된 회원만이 접근 가능하게 했습니다.

### 지속적인 통합(CI), 지속적인 배포(CD)
Travis, AWS s3를 통해 **지속적인 통합(CI)** 환경을 구축했고 AWS CodeDeploy를 통해 AWS ec2 서버에 **지속적인 배포(CD)** 환경을 구축했습니다.
![CICD](https://user-images.githubusercontent.com/45163261/108695469-ac5d8200-7543-11eb-989d-eed462410b2a.PNG)

## 성능 향상
최초 개발 시에는 모든 데이터를 RDBMS인 MariaDB에 저장하고 관리했습니다. 그러나 채팅의 특성상 매번 채팅이 이루어질때 마다 채팅 데이터를 DB에 insert하는 작업이 수행되어야 했고 
채팅방 리스트 역시 유저가 속해있는 채팅방에서 채팅이 이루어질 때 마다 채팅방 리스트 역시 최근 채팅이 이루어진 순서대로 재배열되어야 했기에 DB에서 채팅방 리스트를 가져오는 select 작업이 수행되어야 했습니다.
그래서 **DB와의 잦은 io가 발생하는 채팅 기능에 대한 성능 향상**을 위해 디스크 기반 DB 대신 **인메모리 기반의 redis에 채팅데이터를 저장,관리** 하는 것으로 변경하였습니다.

### DB 전환 과정
- 채팅 데이터의 DB 전환을 위하여 기존의 JPA ORM을 위해 만들었던 Entity들을 모두 Redis에 적용하기 위한 형태로 변환했습니다.
- 기존에는 JPARepository 인터페이스를 상속받아 DB에 접근했던 것을 **RedisTemplate에 Key-value** 형태로 접근하도록 했습니다.

### 성능 비교
- 채팅 데이터를 MariaDB에서 관리 했을 때와 Redis에서 관리 했을 때 성능 비교를 **Apache-jmeter**를 통해 수행했습니다.
- 총 10000건의 채팅을 반복하여 전송하는 과정을 수행했습니다. 
- 채팅이 1번 전송 될때마다 DB에 해당 채팅데이터를 insert해서 해당 채팅방에 속해있는 유저들에게 전송해주고 해당 채팅방에 속해 있는 유저들이 참여중인 채팅리스트를 select해서 각 유저들에게 전송해주게됩니다.
#### MariaDB
- 1번의 메시지 전송이 수행될 때 평균적으로 **0.03초**의 시간이 소모되었습니다.
- 총 10000번의 메시지 전송을 수행하는데 소모되는 시간은 **5분 3초** 였습니다.
![maria(10000)](https://user-images.githubusercontent.com/45163261/108707317-29dcbe80-7553-11eb-8550-e03b3f828973.PNG)
#### RedisDB
- 1번의 메시지 전송이 수행될 때 평균적으로 **0.022초**의 시간이 소모되었습니다.
- 총 10000번의 메시지 전송을 수행하는데 소모되는 시간은 **3분 47초** 였습니다.
![redis(10000)](https://user-images.githubusercontent.com/45163261/108707307-26e1ce00-7553-11eb-9fb7-4730742a1916.PNG)
### 결론
- 10000건의 데이터로 비교해봤을 때 인메모리 방식의 redis를 이용했을 때 응답시간이 약 **36%** 감소했습니다.
- 디스크에서의 작업 속도보다 메모리에서의 작업속도가 빠르다 보니 채팅과 같이 **DB와의 io가 자주 발생하는 작업의 경우에는 인메모리 DB를 활용하는 것이 성능에서 더 효율적이다.**
- 만약 RDB의 관계가 더 복잡해 join이 자주 발생하는 작업일 경우에는 인메모리 방식의 DB를 활용하면 더 큰 성능 개선을 이룰 수 있다.
