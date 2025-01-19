package e2;

import java.util.HashSet;
import java.util.Set;

public class LogicImpl implements Logic {
    @SuppressWarnings("unused")
    private final int size;
    private Integer count = 1;
    private Set<Pair<Integer,Integer>> selected = new HashSet<>();

    public LogicImpl(final int size ){
        this.size = size;
    }

    @Override
public HitType hit(int x, int y) {
    var p = new Pair<>(x, y);

    if (isAround(p)) {
        return HitType.IS_A; // Already around
    }
    
    if (!selected.contains(p)) {
        selected.add(p);
        return HitType.NOT_A; // New selection
    }

    return HitType.IS_A;
}

    @Override
     public boolean isAround(final Pair<Integer,Integer> index){
        for (var pair : selected) {
            for(int i = (pair.getX()-1) ; i <= (pair.getX()+1) ; i++ ){
                for(int j = (pair.getY()-1) ; j <= (pair.getY()+1) ; j++ ){
                    var tmp = new Pair<Integer,Integer>(i,j);
                    if(index.equals(tmp)){
                        return true;
                    }
                    
                } 
            }
            
        }
        return false;
    }

    @Override
    public String getTurn() {
        String turn = Integer.toString(count++);
        return turn;
    }

    @Override
    public void isOver() {
    }

}
