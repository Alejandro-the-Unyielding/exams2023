package e2;

import java.util.ArrayList;
import java.util.Objects;

public class LogicImple implements Logic{
    private final int width;
    private final int height;
    private ArrayList<Position> marks = new ArrayList<>();
    private Position BullsEye;


    LogicImple(final int width, final int height){
        this.width = Objects.requireNonNull(width);
        this.height = Objects.requireNonNull(height);
    }

    @Override
    public boolean hit(Position pos) {

        var arrow = pos;

        while(arrow.x() != width - 1){
            if(arrow.y() == height - 1){
                this.marks.add(arrow);
                arrow = new Position(arrow.x() + 1, arrow.y() - 1);
            }
            else if(arrow.y() == 0){
                this.marks.add(arrow);
                arrow = new Position(arrow.x() + 1, arrow.y() + 1);
            }
        }

        return this.marks.contains(BullsEye);


    }

    @Override
    public void getMark(Position pos) {
        this.BullsEye = pos;

    }

    @Override
    public ArrayList<Position> Trajectory() {
        return this.marks;
    }

}
