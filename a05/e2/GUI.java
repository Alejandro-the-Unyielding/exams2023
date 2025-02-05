package e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> cells = new HashMap<>();
    private final Logic logic;

    
    public GUI(int width) {
        this.logic = new LogicImpl(width);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(70*width, 70*width);
        
        JPanel panel = new JPanel(new GridLayout(width,width));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            logic.hit(this.cells.get(jb));

            if(logic.isOver()){
                this.cells.keySet().stream().forEach(button -> button.setEnabled(false));
            }
            else{
                redraw();
            }
        	
        };
                
        for (int i=0; i<width; i++){
            for (int j=0; j<width; j++){
                final JButton jb = new JButton();
                this.cells.put(jb, new Position(i, j));
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        redraw();
        this.setVisible(true);
    }

    private void redraw(){

        Position player = logic.getPlayer(); Position enemy = logic.getEnemy();

        this.cells.keySet().stream()
        .filter(key -> key.getText().equals("P") || key.getText().equals("E"))
        .forEach(button -> button.setText(""));

        this.cells.entrySet().forEach(entry -> {
            if(entry.getValue().equals(player)){
                entry.getKey().setText("P");
            }
            else if(entry.getValue().equals(enemy)){
                entry.getKey().setText("E");
            }
        });

            
            
        }
        
    }
    

