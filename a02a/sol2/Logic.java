package sol2;

public interface Logic {

    static enum CellType {
        STATIC, DYNAMIC, EMPTY
    }

    boolean hit(Position position);

    CellType getMark(Position position);

    boolean isOver();
}
