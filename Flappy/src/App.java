import javax.swing.*;


public class App {
    public static void main(String[] args) throws Exception {
            int backwidth=1920;
            int backheight=1080;


            JFrame frame= new JFrame("Flappy Bird");
            // frame.setVisible(true );
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(backwidth, backheight);
            frame.setLocationRelativeTo(null);
            FlappyBird flappyBird= new FlappyBird();
            frame.add(flappyBird);
            frame.pack();
            flappyBird.requestFocus();
            frame.setVisible(true); 
    }
}
