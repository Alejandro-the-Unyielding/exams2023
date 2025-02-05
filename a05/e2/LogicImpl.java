package e2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LogicImpl implements Logic{

    private final int size;
    private Position player = new Position(0, 0);
    private Position enemy = new Position(0, 1);
    private Random random = new Random();


    LogicImpl(final int size){
        this.size = size;
        while(isNear(player, enemy)){
            this.player = new Position(random.nextInt(size), random.nextInt(size));
            this.enemy = new Position(random.nextInt(size), random.nextInt(size));
        }
    }

    @Override
    public void hit(Position p) {
        

        List<Position> possibleMoves = new ArrayList<>();

        for(int x = -1; x <= 1 ; x++){
            for(int y = -1 ; y <= 1 ; y++){
                if(x == 0&& y == 0) continue;
                possibleMoves.add(new Position(player.x() + x, player.y() + y));
            }
        }

        if(possibleMoves.contains(p))
        {
            this.player = p;
            if(!isNear(player, enemy)){
                List<Position> possibleEnemyMoves = new ArrayList<>();
                for(int i = -1; i <= 1; i++){
                    for(int j = -1; j<=1 ; j++){
                        if(j == 0 && i == 0) continue;
                        Position newPosition = new Position(enemy.x() + i, enemy.y() + j);
                        if(newPosition.x() >= 0 
                                && newPosition.x() <= size-1 
                                    && newPosition.y() >= 0 && newPosition.y() <= size-1 
                                        && !newPosition.equals(player) 
                                            && !isNear(p, newPosition)){                        
                            possibleEnemyMoves.add(newPosition);
                        }
                    }
                }
                    enemy = possibleEnemyMoves.get(random.nextInt(possibleEnemyMoves.size()));
                
            }
        }


    }



    @Override
    public boolean isOver() {
        return player.equals(enemy);
    }

    @Override
    public Position getPlayer() {
        return this.player;
    }

    @Override
    public Position getEnemy() {
        return this.enemy;
    }

    @Override
    public boolean isNear(Position player, Position enemy) {
        return Math.abs(player.x() - enemy.x()) <= 1 && Math.abs(player.y() - enemy.y()) <= 1;
    }

}
