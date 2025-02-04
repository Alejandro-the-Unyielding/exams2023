package e1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class WindowingFactoryImpl implements WindowingFactory{

    
    
    
    @Override
    public <X> Windowing<X, X> trivial() {
        return new Windowing<X,X>() {

            @Override
            public Optional<X> process(X x) {
                X result = x;
                return Optional.ofNullable(result);
            }
            
        };

    }

    @Override
    public <X> Windowing<X, Pair<X, X>> pairing() {
        return new Windowing<X,Pair<X,X>>() {

            private X last = null;

            @Override
            public Optional<Pair<X, X>> process(X x) {
                if(last == null){
                    last = x;
                    return Optional.empty();
                }
                else{

                    Pair<X,X> result = new Pair<X,X>(this.last, x);
                    this.last = x;
                    return Optional.of(result);
                }

            }
            
        };

    }

@Override
public Windowing<Integer, Integer> sumLastFour() {
    return new Windowing<Integer, Integer>() {
        private Queue<Integer> lastFour = new LinkedList<>();
        private int sum = 0;

        @Override
        public Optional<Integer> process(Integer x) {
            lastFour.add(x);
            sum += x; // Add the new element to sum

            if (lastFour.size() > 4) {
                sum -= lastFour.poll(); // Remove oldest element from sum
            }

            return lastFour.size() == 4 ? Optional.of(sum) : Optional.empty();
        }
    };
}


    @Override
    public <X> Windowing<X, List<X>> lastN(int n) {
        return new Windowing<X,List<X>>() {

            private Queue<X> lastN = new LinkedList<>();
            private List<X> result = new ArrayList<>();

            @Override
            public Optional<List<X>> process(X x) {
                lastN.add(x);
                result.add(x);
                if(lastN.size() > n){
                    result.remove(0);
                    lastN.poll();
                }

                return lastN.size() == n ? Optional.of(result) : Optional.empty();

 
            }
            
        };

    }

    @Override
    public Windowing<Integer, List<Integer>> lastWhoseSumIsAtLeast(int n) {
        return new Windowing<Integer, List<Integer>>() {
            private List<Integer> elements = new ArrayList<>();
            private int sum = 0;
    
            @Override
            public Optional<List<Integer>> process(Integer x) {
                elements.add(x);
                sum += x; // Add new number to sum
    
                // Remove unnecessary elements from the front while keeping the sum at least `n`
                while (!elements.isEmpty() && sum - elements.get(0) >= n) {
                    sum -= elements.remove(0);
                }
    
                return sum >= n ? Optional.of(new ArrayList<>(elements)) : Optional.empty();
            }
        };
    }
    
    }


