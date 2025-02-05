package e2;

public interface Logic {

    void hit(Position p);

    boolean isOver();

    Position getPlayer();

    Position getEnemy();

    boolean isNear(Position player, Position enemy);

}
