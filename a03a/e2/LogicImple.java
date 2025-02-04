package e2;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class LogicImple implements Logic{
    private final int width;
    private final int height;
    private final Set<Position> marked = new HashSet<>();
    private final Random random = new Random();
    private Position BullsEye;


    LogicImple(final int width, final int height){
        this.width = Objects.requireNonNull(width);
        this.height = Objects.requireNonNull(height);
        this.resetMarks();
        this.BullsEye = new Position(this.width-1, random.nextInt(this.height));
    }

    private void resetMarks() {
        this.marked.clear();
    }

    @Override
    public void hit(Position pos) {

        this.resetMarks();

        this.marked.add(pos);

        var arrow = pos;
        var yDir = -1;
        for(int x = pos.x() + 1 ; x<this.width; x++){
            arrow = new Position(x, arrow.y() + yDir);
            this.marked.add(arrow);
            if(arrow.y() == 0 || arrow.y() == this.height -1){
                yDir *= -1;
            }
        }

    }

    @Override
    public boolean isMarked(Position position) {
        return this.marked.contains(position);
    }

    @Override
    public boolean isOver() {
        return this.marked.contains(this.BullsEye);
    }

    @Override
    public Position getGoal() {
        return this.BullsEye;
    }

}
