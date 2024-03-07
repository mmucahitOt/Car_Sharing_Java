import java.util.*;
import java.util.function.*;
import java.util.stream.*;


class MinMax {

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {

        // your implementation here
        List<? extends T> list = stream.toList();
        if (list.stream().findAny().isEmpty()) {
            minMaxConsumer.accept(null, null);
            return;
        }
        minMaxConsumer.accept(list.stream().min(order).get(), list.stream().max(order).get());
    }
}