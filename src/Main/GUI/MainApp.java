package Main.GUI;
import javax.swing.*;

// Main class
class MainApp {

    // Main driver method
    public static void main(String[] args)
    {
        int posX = 50;
        int posY = 50;
        // Creating instance of JFrame
        JFrame frame = new JFrame();

        // Creating instance of JButton
        JButton button = new JButton(" GFG WebSite Click");

        button.setBounds(posX, posY, 220, 50);
        button.addActionListener(e->{
            button.setLocation(posX +50, posY);

        });

        // adding button in JFrame
        frame.add(button);

        // 400 width and 500 height
        frame.setSize(500, 600);

        // using no layout managers
        frame.setLayout(null);

        // making the frame visible
        frame.setVisible(true);
    }
}
