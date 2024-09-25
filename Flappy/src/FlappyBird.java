import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class FlappyBird extends JPanel implements ActionListener, KeyListener{
    int backwidth=1920;
    int backheight=1080;

    //Background Images
    Image backImg;
    Image birdImg;
    Image UpImg;
    Image DownImg;


    //bird
    int birdX = backwidth/8;
    int birdY = backheight/2;

    int birdWidth=48;
    int birdHeight=34;

    class Bird{
        
        int x=birdX;
        int y=birdY;
        int width=birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img = img;
        }

    }

    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;


        Pipe(Image img){
            this.img= img;
        }
    }

    //Pipe
    int pipeX=backwidth;
    int pipeY=0;
    int pipeWidth=88;
    int pipeHeight=600;



    //game logic
    Bird bird; 
    int velocityX=-7;
    int velocityY=-14;
    int gravity=1       ;


    ArrayList<Pipe> pipes;
    Random random= new Random();

    //Timers

    Timer gameloop;
    Timer placepipesTimer;
    boolean gameover=false;
    double score = 0 ;





    FlappyBird(){
        setPreferredSize(new Dimension(backwidth, backheight));
        // setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        //load images
        backImg = new ImageIcon(getClass().getResource("./Back.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        UpImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        DownImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        //bird
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        //place Pipe timer

        placepipesTimer = new  Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                placedPipe();
            } 
        }); //1.5 sec

        //pipe timer
        placepipesTimer.start();
        
		//game timer
		gameloop = new Timer(1000/60, this); //how long it takes to start timer, milliseconds gone between frames 
        gameloop.start();


    }


    //pipe

    public void placedPipe(){


        // randomheights of pipes
        int RanY=(int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int open=backheight/4;



        Pipe top = new Pipe(UpImg);
        top.y= RanY;
        pipes.add(top);

        Pipe down = new Pipe(DownImg);
        down.y = top.y + pipeHeight + open;
        pipes.add(down);
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
        //background
        g.drawImage(backImg,0,0,backwidth,backheight, null);


        //bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);

        //pipes
        for(int i = 0;i< pipes.size();i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width,  pipe.height, null);   
        }

        //score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 60));

        if(gameover){
            g.drawString("Game Over = "+ String.valueOf((int)score),520,350);
            g.drawString("Press SpaceBar to Restart",400 , 420);
        }
        else {
            g.drawString(String.valueOf((int) score), 20, 50);
        }
    }

    public void move(){
        //bird
        velocityY+=gravity;
        bird.y +=velocityY;
        bird.y=Math.max(bird.y,0);


        // pipes
        for(int i = 0; i < pipes.size() ;i++){
            Pipe pipe =  pipes.get(i);
            pipe.x += velocityX;

            if(!pipe.passed && bird.x > pipe.x + pipe.width){
                pipe.passed = true;    
                score += 0.5;
            }

            if(collision(bird, pipe)){
                gameover = true;
            }

        }


        //gameOver
        if(bird.y > backheight){
            gameover = true;
        }
    }


    public boolean collision(Bird a, Pipe b){
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height >b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if(gameover){
            placepipesTimer.stop();
            gameloop.stop();
        }
    }

    

    @Override
    public void keyPressed(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_SPACE){
       // System.out.println("Chidiya");
        velocityY=-16;

        if (gameover) {
            //restart game by resetting conditions
            bird.y = birdY;
            velocityY = 0;
            pipes.clear();
            gameover = false;
            score = 0;
            gameloop.start();
            placepipesTimer.start();
        }
    }
    }


    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    

    
}