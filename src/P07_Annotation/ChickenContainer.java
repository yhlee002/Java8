package P07_Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Repeatable 대상 클래스
 * 어노테이션 컨테이너로 사용된다.
 * 주의할 점은 자기 자신이 감쌀 어노테이션의 리텐션 범위와 타겟 범위보다 커야 한다는 점이다.
 */
@Retention(RetentionPolicy.RUNTIME) // Retention
@Target(ElementType.TYPE_USE)
public @interface ChickenContainer {

    // 자신이 감싸고 있는 어노테이션을 배열로 가지고 있어야 함(value라는 이름의 변수로)
    Chicken2[] value();
}
