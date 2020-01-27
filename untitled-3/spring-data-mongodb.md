# spring-data-mongodb 연동시 설정 옵션 정보

Spirng Boot 2.2.4를 사용할때 Spring-data-mongodb를 사용하게 되는데 2.2.4 RELEASE 버전을 사용하게 된다.  
여기서 사용하는 자바 설정 파일은 AbstractMongoClientConfiguration을 상속 받아서 설정 할 수 있고 기존에 사용했던 AbstractMongoConfiguration랑은 설정과 옵션이 조금 변경된것 같다.  
찾아보니 xml로 쉽게 mongodb연동이 가능하고 연동할때 제공해주는 옵션이 있는데 찾아보니 잘 안나와서 직접 소스 까서 번역\(구글번역, 파파고\) 하였다.

* min-connections-per-host :  호스트당 최소 연결 수
* connections-per-host :호스트당 연결 가능한 수, 연결이 부족하면 차단됨 디폴트 100
* threads-allowed-to-block-for-connection-multiplier:  차단 할수 있는 connectionsPerHost 에 승수, 기본값은 5이다. connectionsPerHost 가 10이고 threadsAllowedToBlockForConnectionMultiplier 5라고 했을때 50보다 많은 스레드가 요청한다면 익셉션이 발생함.
* max-wait-time : 연결에 대한 블로킹 스레드의 최대 대기시간. 디폴트 120000ms 2분
* max-connection-idle-time :  풀링에 연결의 최대 유휴시간 
* max-connection-life-time : 풀링에 연결된 최대 수명
* connect-timeout : 연결시간 초과. 0은 기본이며 무한대임
* socket-timeout: 소켓 타임아웃. 0은 기본이며 무한대임
* socket-keep-alive : 연결유지 플래그는 소켓 연결 유지 시간 종료 여부를 제어함. 기본은 false
* server-selection-timeout : 예외를 던지기 전에 서버 선택이 성공할 때까지 얼마나 오래 기다릴지 정의하는 밀리초 단위의 서버 선택 시간 초과입니다. 기본 시간은 30초
* read-preference :읽기 환경 설정
* readPreferenceEnumeration : MongoDbFactory에 DB 개체를 요청할 때 사용되는 기본값이 될 WriteConcern
* writeConcernEnumeration : 드라이버가 클러스터에있는 각 서버의 현재 상태를 확인하려고 시도하는 빈도.
* min-heartbeat-frequency : 드라이버가 서버의 가용성을 자주 재확인해야하는 경우, 이전 점검 이후 노력을 낭비하지 않기 위해 최소한이 시간 동안 기다림
* heartbeat-connect-timeout : 클러스터 하트비트에 사용되는 연결에 대한 연결 시간 초과.
* heartbeat-socket-timeout : 클러스터 하트비트에 사용되는 소켓 연결시간 초과
* ssl : 드라이버가 ssl연결을 해야 하는지 여부 디폴트는 false
* ssl-socket-factory-ref : SSL 연결에 사용할 SSLSocketFactory. 구성되지 않은 경우 SSLSocketFactory\#getDefault\(\)가 사용된다. 

writeConcern이란 : [https://bluese05.tistory.com/74](https://bluese05.tistory.com/74)

