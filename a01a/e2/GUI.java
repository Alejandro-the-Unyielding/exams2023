package e2;

import javax.swing.*;
import java.util.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Pair<Integer, Integer>> cells = new HashMap<>();

    public GUI(final int size) {
        Logic logics = new LogicImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(panel);
        
        ActionListener al = e -> {
            var jb = (JButton)e.getSource();
            var index = this.cells.get(jb);
            var v = logics.hit(index.getX(), index.getY());
            switch (v) {
                case NOT_A: jb.setText(logics.getTurn()); break;
            
                case IS_A: 
        
                //if(logics.isOver()){
                  //  System.exit(0);
                //};
                
                translateUpRight(); break;
                
            }

        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
            	var pos = new Pair<>(j,i);
                final JButton jb = new JButton("");
                this.cells.put(jb, pos);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void translateUpRight() {
        // Temporary map for updated positions
        Map<JButton, Pair<Integer, Integer>> updatedPositions = new HashMap<>();
        int gridSize = (int) Math.sqrt(cells.size()); // Assume square grid
    
        // Iterate through all buttons and their positions
        for (Map.Entry<JButton, Pair<Integer, Integer>> entry : this.cells.entrySet()) {
            JButton button = entry.getKey();
            Pair<Integer, Integer> position = entry.getValue();
    
            // Check if the button is selected (e.g., has a number on it)
            if (!button.getText().isEmpty() && button.getText().matches("\\d+")) {
                int currentRow = position.getX();
                int currentCol = position.getY();
    
                // Calculate the new position (up-right by 1)
                int newRow = currentRow - 1; // Move up
                int newCol = currentCol + 1; // Move right
    
                // Ensure the new position is within bounds
                if (newRow >= 0 && newCol < gridSize) {
                    // Find the button at the new position
                    for (Map.Entry<JButton, Pair<Integer, Integer>> innerEntry : this.cells.entrySet()) {
                        if (innerEntry.getValue().equals(new Pair<>(newRow, newCol))) {
                            JButton targetButton = innerEntry.getKey();
                            targetButton.setText(button.getText()); // Transfer the number
                            break;
                        }
                    }
                }
    
                // Clear the current button's text
                button.setText("");
    
                // Update the button's position in the map
                updatedPositions.put(button, new Pair<>(newRow, newCol));
            } else {
                // Keep unselected buttons in their current positions
                updatedPositions.put(button, position);
            }
        }
    
        // Update the grid map with new positions
        this.cells.clear();
        this.cells.putAll(updatedPositions);
    }
    
    
        
    }
    

