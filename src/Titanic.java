import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

class Titanic extends JFrame {


    public static void main(String[] args)  {
        new Titanic();

    }

    public Titanic() {
        this.setTitle("Titanic Passengers Data");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(new ManageScreen(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        this.setVisible(true);

    }




}