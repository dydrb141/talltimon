# HTTP의 이해

HTTP\(HyperText Transfer Protocol\)는 WWW\(World Wide Web\) 상태에서 데이터를 주고 받을수 있는 프로토콜 현재 화면과 서버 간의 통신이 가장 많이 사용되는 프로토콜

## 프로토콜 구조

HTTP는 클라이언트\(브라우저\) 와 서버\(웹 서버\) 간에 요청/읍답 구조를 가진 프로토콜

## HTTP 요청

요청은 헤더와 본문으로 나뉨 헤더는 요청라인 과 MIME헤더로 나뉨

### 요청 라인

요청 라인에는 요청 대상\(URI\) 정보가 들어있다.

```text
POST(요청방식) /board/news.jps(URI) HTTP/1.1 (HTTP버전)
```

### 요청 방식 8가지

* GET - 본문이 없음, 처리에 필요한 정보가 있다면 URI에 붙여서 보냄 
* POST - 입력 폼이나 파일 멀티파트 XML JSOn 같은 데이터를 본문에 보냄 
* HEAD - 헤더 정보만 보냄 
* PUT - 본문에 전송된 데이터를 URI에 저장된 위치에 저장
* DELETE - 요청 URI에 지정된 자원을 서버에서 삭제
* TRACE - 클라이언트가 요청한 자원에 도달할때 까지 경로를 기록하는 루프백 검사용
* OPTIONS - 지정된 URI에 대해 서버가 어떤 기능과 옵션을 제공하는지 조회할 때 
* CONNECT - SSL과 같이 터널링을 시작할 때

### URI

URI\(Uniform Resource Identifier\)는 웹서버에서 대상을 유일하게 인식할 수 있느느 주소로 컨텐츠와 위치의 명칭을 가리킨다.

### MIME 헤더

MIME\(Multipurpose Internet Mail Extensions\) 원래 전자우편의 인코딩 방식을 설명하는 표준 포맷

_**MIME 헤더 공통**_ **Cache-Control**: 캐시가 동작하는 조건을 지정하는 지시문 Connection: 클라이언트와 웹 서버간 네트워크 연결 시에 대한 Keepalive 여부 지정 Date:현재 날짜 표시

_**MIME 요청 전용 헤더 \(중요한것만\)**_ **Accept**: 클라이언트\(브라우저\)가 응답으로 받을 수 있는 MIME타입을 지정 Accept-Encoding:클라이언트가 받을 수 있는 본문 인코딩

**Authorization**: URI에 클라이언트가 데이터에 접근할 수 있는 권한을 제공. HTTP가 일반적으로 사용하는 권한 계획은 BASIC이다.

**Cookie**: 서버에서 설정한 데이터가 사용자 PC에 저장되어 요청 간에도 해당 데이터를 서버 측면에서 공유 Host: 호스트명과 URI포트 번호로 구성

**User-Agent**: 클라이언트 브라우저에 대한 식별 정보 전송

## HTTP응답

응답은 헤더와 본문으로 나뉨 헤더는 응답 라인과 MIME 헤더로 나뉨

### 응답라인

```text
HTTP/1.1(HTTP버전) 200 OK(수행 결과 응답 코드 및 코드명)
```

_**응답 코드 목록**_

* 1XX\(Informational\): 정보 교환
* 2XX\(Success\): 데이터가 성공적으로 이뤄졌거나 이해됫거나 수락됫다
* 3xx\(Redirection\): 문서의 위차가 바뀌었다
* 4xx\(Client Error\): 요청 실패, 분법상 오류가 있어서 서버가 요청사항을 이해하지 못함. 
* 5xx\(Internal Server Error\):서버 측의 오류로 올바른 요청을 처리할 수 없다.

_**응답전용 MIME 헤더**_ _\*\*_

* Content-Encoding: 본문을 전송할 때 사용할 인코딩 체계를 지정 
* Content-Language: 전송될 본문에서 사용하는 언어를 지정 
* Content-Type: 본문의 미디어 형식을 설명, 웹 서버는 클라이언트가 Accept 헤더로 보내온 지원하는 포맷 양식에 따르는 미디어 유형 반환

## 데이터 송수신

본문이 없는 경우: \[CRLF\]가 2번 연속 반복할때까지 전문을 읽음 본문이 있는 경우: Context-Length나 Chunked 방식에 따라 본문 크기를 확인하고 데이터를 읽음

### Content-Length 방식

Context-Length에 본문의 크기를 바이트 단위로 기록해서 수신측에서 본문에 크기를 알 수 있게 함. 정적 컨텐츠와 같이 HTTP헤더 정보를 만드는 시점에 본문 전체 크기를 알 수 있을때 사용

### Chunked방식

HTTP헤더인 Transfer-Encoding이 chunked값을 가지고 있음 본문의 크기를 알수 없을때 사용 JSP같은 경우 애플리케이션이 수행되기 시작하면서 HTTP응답이 생성됨

송신 버퍼가 8K가 채워지면 네트워크에 응답을 보내기 시작하여 본문이 어느정도 크기인지 알수 없음 이럴때 chunk방식 사용

본문은 크게 서브 블록으로 나뉘어져 있고 블록의 크기가 0일때까지 반복한다.

