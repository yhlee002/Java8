package P07_Annotation;

import java.util.Arrays;

/**
 * 특정 클래스에 등록된 어노테이션 '목록' 조회
 * <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationClass)
 *
 * 특정 타입의 클래스의 어노테이션 조회
 * <A extends Annotation> A getAnnotation(Class<A> annotationClass)
 * Cf. Repeatable한 어노테이션을 컨테이너로 감싸서 가져오기 위해서는 해당 메서드를 사용하되 인자로 컨테이너 클래스를 전달할 수 있다.
 *
 */
@Chicken2("마늘")
@Chicken2("양념")
public class App_AnnotationDuplicateUse {
    public static void main(String[] args) {
        Chicken2[] chickens = App_AnnotationDuplicateUse.class.getAnnotationsByType(Chicken2.class);
        Arrays.stream(chickens).forEach(c -> {
            System.out.println(c.value());
        });

        Chicken2 chickens2 = App_AnnotationDuplicateUse.class.getAnnotation(Chicken2.class);
        System.out.println(chickens2.value());

        ChickenContainer container = App_AnnotationDuplicateUse.class.getAnnotation(ChickenContainer.class);
        Arrays.stream(container.value()).forEach(c -> {
            System.out.println(c.value());
        });
    }
}
