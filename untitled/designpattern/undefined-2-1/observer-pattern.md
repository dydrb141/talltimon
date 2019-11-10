# Observer Pattern

한 객체의 상태 변화에 따라 다른 객체의 상태도 연동되도록 일대다 객체 의존 관계를 구성하는 패턴

데이터의 변경이 발생했을 경우 다른 클래스 객체에 의존하지 않으며 데이터 변경을 통보하고자 할때

### 소스설명

축구팀을 운영하기 위해서는 축구 선수가 있어야 한다.

각 구단에서는 축구 선수를 영입할 때 선수 연봉 정보를 가지고 있다.

각 팀에서 선수를 영입하게 되면 선수 정보와 연봉이 갱신 되며  선수가 영입될때 마다 팀의 Top3연봉을 갱신해서 보여준다.

{% tabs %}
{% tab title="FootballPlayerObserver.java" %}
```java
public interface FootballPlayerObserver {
	void footballPlayerUpdate();
}
```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="TopThreePlayerFootballPlayer.java" %}
```java
public class TopThreePlayerFootballPlayer implements FootballPlayerObserver {
	private FootballPlayer footballPlayer;

	public TopThreePlayerFootballPlayer(FootballPlayer footballPlayer) {
		this.footballPlayer = footballPlayer;
	}

	@Override
	public void footballPlayerUpdate() {
		printMyTeamTopPlayer(footballPlayer.getTopThreeFootballPlayer());
	}

	public void printMyTeamTopPlayer(Map<String, BigDecimal> myTeamTopPlayers) {
		System.out.println("--------------------------------------------");
		myTeamTopPlayers.keySet().forEach(System.out::println);
	}
}
```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="FootballPlayerManager.java" %}
```java
public abstract class FootballPlayerManager {
	List<FootballPlayerObserver> observers = new ArrayList<>();
	
	public void attach(FootballPlayerObserver observer) {
		observers.add(observer);
	}
	
	public void detach(FootballPlayerObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers() {
		for (FootballPlayerObserver observer : observers) {
			observer.footballPlayerUpdate();
		}
	}
}
```
{% endtab %}
{% endtabs %}

```java
public class FootballPlayer extends FootballPlayerManager {
	ConcurrentHashMap<String, BigDecimal> map = new ConcurrentHashMap<>();

	public void addPlayer(String name, BigDecimal salary) {
		map.put(name, salary);
		notifyObservers();
	}

	public Map<String, BigDecimal> getTopThreeFootballPlayer() {
		return map
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.limit(3)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		        //collection 만들어 줄때 순서를 보장하는 LinkedHashMapd을 사용해줘야 원하는 순서를 보장해서 넘겨줌
		        //HashSet으로 받으면 오더링 하는 이유가 없이 순서를 보장하지 않음
	}


	public BigDecimal getFootballPlaySalary(String name) {
		if (StringUtils.isEmpty(name)) {
			return new BigDecimal(0);
		}

		return Objects.isNull(map.get(name)) ? new BigDecimal(0) : map.get(name);
	}
}
```

{% tabs %}
{% tab title="Main.java" %}
```java
public class Main {
	public static void main(String[] args) {
		FootballPlayer footballPlayer = new FootballPlayer();

		TopThreePlayerFootballPlayer observer = new TopThreePlayerFootballPlayer(footballPlayer);
		footballPlayer.attach(observer);

		footballPlayer.addPlayer("한용규",  new BigDecimal(100000000));
		footballPlayer.addPlayer("심준보",  new BigDecimal(2000000000));
		footballPlayer.addPlayer("호날두",  new BigDecimal(new BigInteger("100000000000")));
		footballPlayer.addPlayer("메시",  new BigDecimal(new BigInteger("200000000000")));
		footballPlayer.addPlayer("김밥",  new BigDecimal(new BigInteger("100")));
	}
}
```
{% endtab %}
{% endtabs %}



