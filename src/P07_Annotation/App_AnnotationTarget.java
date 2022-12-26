package P07_Annotation;

import java.util.Arrays;
import java.util.List;
@Chicken
public class App_AnnotationTarget {
    public static void main(@Chicken String[] args) throws @Chicken RuntimeException {
        List<@Chicken String> names = Arrays.asList("yhlee");
    }

    static class FeelsLikeChicken<@Chicken T> {
        public static <C> void print(C c) { // <C>의 C는 '타입 파라미터', C는 '타입'(둘은 다름)
            System.out.println(c);
        }
    }
}
