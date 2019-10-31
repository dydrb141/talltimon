# Strategy Pattern

행위를 클래스로 캡슐화해 동적으로 행위를 자유롭게 함.

같은 문제를 해결하는 여러 알고리즘이 클래스별로 캡슐화 되어있고 필요할때 교체되도록

### 소스 설명

슛돌이는 슛을 연마하여 각 상황에 맞게 총알슛 독수리슛 도깨비슛을 구사할 수 있다

Soccer.java에서는 슈팅을 할때 상황에 맞게 슛을 쏠수 있도록 구현함. 내부 슈팅 구현은 어뗗게 구현 되어있는지 알 수 없음.



{% code-tabs %}
{% code-tabs-item title="Shoot.java" %}
```java
public interface Shoot {
	String shoot();
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="Soccer.java" %}
```java
public class Soccer {
	private Shoot shoot;

	public void setShoot(Shoot shoot) {
		this.shoot = shoot;
	}

	public void shooting() {
		System.out.println(shoot.shoot());
	}
}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="DoggabiShoot.java" %}
```java
public class DoggabiShoot implements Shoot {
	@Override
	public String shoot() {
		return "도깨비 슛";
	}
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="DocksuriShoot.java" %}
```java
public class DocksuriShoot implements Shoot {
	@Override
	public String shoot() {
		return "독수리 슛";
	}
}
```
{% endcode-tabs-item %}
{% endcode-tabs %}

{% code-tabs %}
{% code-tabs-item title="ChongalShoot.java" %}
```java
public class ChongalShoot implements Shoot {
	@Override
	public String shoot() {
		return "총알 슛";
	}
}

```
{% endcode-tabs-item %}
{% endcode-tabs %}

