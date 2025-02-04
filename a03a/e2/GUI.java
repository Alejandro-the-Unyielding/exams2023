package e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final int DONE = 1;
    private final int random = new Random().nextInt(HEIGHT);
    private Logic logic;

    
    public GUI(int width, int height) {
        this.logic = new LogicImple(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*height);
        
        JPanel panel = new JPanel(new GridLayout(width,height));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
        	var pos = this.cells.get(jb);
            var hit = logic.hit(pos);
            if(hit){
                System.exit(DONE);
            }
            else{
                redraw();
            }

        };
                
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
            	var pos = new Position(i, j);
                final JButton jb = new JButton();
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }

        this.cells.entrySet().stream()
        .filter(entry -> entry.getValue()
        .equals(new Position(2, 2)))
        .findFirst()
        .get()
        .getKey()
        .setText("o");

        
        logic.getMark(this.cells.entrySet().stream()
        .filter(entry -> entry.getKey().getText().equals("o"))
        .findFirst()
        .get()
        .getValue());


        this.setVisible(true);

    }

        private void redraw(){

            var trajectory = logic.Trajectory();
            trajectory.forEach(pos ->{
                this.cells.entrySet().stream().filter(entry -> entry.getValue().equals(pos)).findFirst().get().getKey().setText("o");
            });

        
    }

}
