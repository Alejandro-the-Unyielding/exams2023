package e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class ListExtractorFactoryImpl implements ListExtractorFactory{

    @Override
    public <X> ListExtractor<X, Optional<X>> head() {
        return new ListExtractor<X,Optional<X>>() {

            @Override
            public Optional<X> extract(List<X> list) {
                Optional<X> v = (list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)));
                return v;

            }
            
        };

    }

    @Override
    public <X, Y> ListExtractor<X, List<Y>> collectUntil(Function<X, Y> mapper, Predicate<X> stopCondition) {
        return new ListExtractor<X,List<Y>>() {

            private List<Y> dif = new ArrayList<>();

            @Override
            public List<Y> extract(List<X> list) {

                dif.clear();
                for (X x : list) {

                    if(stopCondition.test(x)){
                        break;
                    }
                    else{
                        dif.add(mapper.apply(x));
                    }
                    
                }

                return dif;
            }
            
        };

    }

    @Override
public <X> ListExtractor<X, List<List<X>>> scanFrom(Predicate<X> startCondition) {
    return new ListExtractor<X, List<List<X>>>() {
        @Override
        public List<List<X>> extract(List<X> list) {
            List<List<X>> resultLists = new ArrayList<>();
            List<X> currentList = new ArrayList<>();
            boolean found = false;

            for (X value : list) {
                // Start processing when the condition is met
                if (found || startCondition.test(value)) {
                    found = true;
                    currentList.add(value);
                    resultLists.add(new ArrayList<>(currentList)); // Create a new list for each step
                }
            }
            return resultLists;
        }
    };
}


    @Override
    public <X> ListExtractor<X, Integer> countConsecutive(X x) {
        return new ListExtractor<X, Integer>() {
            @Override
            public Integer extract(List<X> list) {
                int count = 0;
                boolean started = false;
    
                for (X item : list) {
                    if (item.equals(x)) {
                        started = true;
                        count++;
                    } else if (started) {
                        break; // Stop counting when a different element appears
                    }
                }
                return count;
            }
        };
    }
    
}
