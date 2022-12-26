package P07_Annotation;

import java.lang.annotation.*;

/**
 * @Repeatable : 이 어노테이션을 사용할 곳 정의
 */
@Retention(RetentionPolicy.RUNTIME) // Retention 전략(얼마나 유지할지)
@Target(ElementType.TYPE_USE) // TYPE_PARAMETER 사용 시 타입 매개변수에만 사용 가능
@Repeatable(ChickenContainer.class) // 컨테이너 어노테이션 타입을 선언해주어야 함
public @interface Chicken2 {
    String value();
}
