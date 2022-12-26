package P07_Annotation;

import java.lang.annotation.*;

/**
 * @Target : 이 어노테이션을 사용할 곳 정의
 * Java 8에 크게 두 가지 타입이 추가되었다.
 *  1) ElementType.TYPE_PARAMETER: 해당 클래스를 Type Parameter에만 사용 가능해진다.
 *  2) ElementType.TYPE_USE: 해당 클래스를 Type에 모두 사용 가능해진다.
 */
@Retention(RetentionPolicy.RUNTIME) // Retention 전략(얼마나 유지할지)
@Target(ElementType.TYPE_USE) // TYPE_PARAMETER 사용 시 타입 매개변수에만 사용 가능
public @interface Chicken {
}
