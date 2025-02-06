package e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ZipCombinerFactoryImpl implements ZipCombinerFactory{

    @Override
    public <X, Y> ZipCombiner<X, Y, Pair<X, Y>> classical() {

        return new ZipCombiner<X,Y,Pair<X,Y>>() {

            private List<Pair<X,Y>> result = new ArrayList<>();
            @Override
            public List<Pair<X, Y>> zipCombine(List<X> l1, List<Y> l2) {
                int size = Math.min(l1.size(), l2.size());
                for(int i = 0; i < size; i++){
                    result.add(new Pair<X,Y>(l1.get(i), l2.get(i)));
                }
                return result;

            }
            
            
        };
    }

    @Override
    public <X, Y, Z> ZipCombiner<X, Y, Z> mapFilter(Predicate<X> predicate, Function<Y, Z> mapper) {
        return new ZipCombiner<X,Y,Z>() {
            private List<Z> result = new ArrayList<>();
            @Override
            public List<Z> zipCombine(List<X> l1, List<Y> l2) {
                for(int i = 0; i < l1.size(); i++){
                    if(predicate.test(l1.get(i))){
                        result.add(mapper.apply(l2.get(i)));
                    }
                }
                return result;

            }  
        };
    }

    @Override
    public <Y> ZipCombiner<Integer, Y, List<Y>> taker() {
        return new ZipCombiner<Integer,Y,List<Y>>() {
            private List<List<Y>> result = new ArrayList<>();
            private int count = 0;
            @Override
            public List<List<Y>> zipCombine(List<Integer> l1, List<Y> l2) {

                l1.forEach(entry1 -> {

                    if(entry1.equals(0)){
                        count++;
                        result.add(new ArrayList<>());
                    } 
                    else{
                        int j = 0;
                        List<Y> tmp = new ArrayList<>();
                        for(int i = count; j < entry1; i++){
                            tmp.add(l2.get(i));
                            j++;
                        }
                        result.add(tmp);
                    }

                });

                return result;

            }      
        };
    }

    @Override
    public <X> ZipCombiner<X, Integer, Pair<X, Integer>> countUntilZero() {
        return new ZipCombiner<X,Integer,Pair<X,Integer>>() {
            private List<Pair<X,Integer>> result = new ArrayList<>();
            private int index = 0;

            @Override
            public List<Pair<X, Integer>> zipCombine(List<X> l1, List<Integer> l2) {
                l1.forEach(entry1 -> {
                    Integer count = 0;
                    for(int i = index; i < l2.size(); i++){
                        if(l2.get(i) == 0){
                            index = i+1;
                            break;
                        }
                        count++;
                    }
                    result.add(new Pair<>(entry1, count));

                });

                return result;


            }
            
        };

    }

}
