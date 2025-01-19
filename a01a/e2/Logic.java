package e2;

public interface Logic {

    enum HitType{
        NOT_A,
        IS_A;
    }

    HitType hit(final int x, final int y);

    String getTurn();

    boolean isAround(final Pair<Integer,Integer> index);

    void isOver();

}
