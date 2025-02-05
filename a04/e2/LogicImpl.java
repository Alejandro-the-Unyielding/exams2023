package e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicImpl implements Logic {

    private int width;
    private Position start;
    private final Random random = new Random();
    private Set<Position> hits = new HashSet<>();

    
    LogicImpl(final int width){
        this.width = width;
        this.start = new Position(random.nextInt(width), width);
        this.hits.add(start);
        };

    @Override
    public boolean isOver() {




    }

    @Override
    public void hit(Position p) {
        if(!p.equals(start) || !this.hits.contains(p) || p.y() != 0){
            this.hits.add(p);
        }

    }

}
