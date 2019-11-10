# Template Pattern

어떤 작업을 처리하는 일부분을 서브 클래스로 캡슐화

일을 수행하는 구조는 바뀌지 않으면서 특정 단계에서 수행하는 내역을 바꾸는 패턴

전체적으로 동일하면서 부분적으로 다른 구분으로 구성된 메서드의 코드 중복을 최소화 할때 유용

### 소스설명

슛돌이는 슛을 연마하여 여러가지 슛을 쏠 수 있는데 슛을 하기 위해 패스를 받고 트래핑 하는 공통적인 작업은 상위 클래스에 구현하고 연마한 슛은 각 상황에 맞게 슛을 사용한다.



{% tabs %}
{% tab title="Soccer.java" %}
```java
public abstract class Soccer {
	public String longPass() {
		return "LongPass";
	}

	public String shortPass() {
		return "ShortPass";
	}

	public String kneeTrapping() {
		return "kneeTrapping";
	}

	public String chestTrapping() {
		return "chestTrapping";
	}

	public void longPassAndKneeTrappingAfterShoot() {
		System.out.println(this.longPass());
		System.out.println(this.kneeTrapping());
		System.out.println(this.shoot());
	}

	public void shortPassAndchestTrappingAfterShoot() {
		System.out.println(this.shortPass());
		System.out.println(this.chestTrapping());
		System.out.println(this.shoot());
	}

	abstract String shoot();
}

```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="DocksuriShoot.java" %}
```java
public class DocksuriShoot extends Soccer {
	@Override String shoot() {
		return "DocksuriSoooooot";
	}
}
```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="ChongalShoot.java" %}
```java
public class ChongalShoot extends Soccer {
	@Override String shoot() {
		return "chongalShoooooot";
	}
}

```
{% endtab %}
{% endtabs %}

{% tabs %}
{% tab title="Main.java" %}
```java
public class Main {
	public static void main(String[] args) {
		Soccer chong = new ChongalShoot();
		Soccer dock = new DocksuriShoot();

		chong.longPassAndKneeTrappingAfterShoot();
		dock.shortPassAndchestTrappingAfterShoot();
	}
}

```
{% endtab %}
{% endtabs %}

