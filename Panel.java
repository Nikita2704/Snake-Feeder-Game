import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Panel extends JPanel implements KeyListener, ActionListener 
{
//Adding images to the game
    ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    ImageIcon rightmouth= new ImageIcon(getClass().getResource("rightmouth.png"));
    ImageIcon snakeimage= new ImageIcon(getClass().getResource("snakeimage.png"));
    ImageIcon upmouth= new ImageIcon(getClass().getResource("upmouth.png"));
    ImageIcon downmouth= new ImageIcon(getClass().getResource("downmouth.png"));
    ImageIcon leftmouth= new ImageIcon(getClass().getResource("leftmouth.png"));
    ImageIcon food= new ImageIcon(getClass().getResource("enemy.png"));
    boolean isUp=false;
    boolean isDown =false;
    boolean isRight =true;
    boolean isLeft= false;
    int score;

    int xpos[]={25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850}; //position of food from x 
    int ypos[]={75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525,550,575,600,625};//array for position of food from Y

    Random random =new Random();
    int foodx=150;
    int foody=150;

    int[] snakeX= new int[750];
    int[] snakeY=new int[750];
    int move=0;
    int lengthOfSnake=3;
    Timer time;
    boolean GameOver =false;

    Panel()
    {
       addKeyListener(this);
       setFocusable(true);
       time=new Timer(150,this);//timer function to increase and decrease the speed of snake
       time.start();
    }

    @Override
    public void paint(Graphics g) 
    {
    //paint funtion 
        super.paint(g);
        g.setColor(Color.white);
        g.drawRect(24,10,851,55);//draw first rectangle
        g.fillRect(24,74,851,576);//fill rectangle
        snaketitle.paintIcon(this,g,25,11);//use image
        g.setColor(Color.black);
        g.fillRect(25,75,850,575);

        if(move==0)
        {
            snakeX[0]=100;
            snakeX[1]=75;
            snakeX[2]=50;

            snakeY[0]=100;
            snakeY[1]=100;
            snakeY[2]=100;

        }
        if(isRight)
            rightmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isDown)
            downmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isUp)
            upmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        if(isLeft)
            leftmouth.paintIcon(this,g,snakeX[0],snakeY[0]);
        for(int i=1;i<lengthOfSnake;i++){
            snakeimage.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        food.paintIcon(this,g,foodx,foody);
        if(GameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
            g.drawString("Game Over",300 ,300);
            g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,10));
            g.drawString("Press The Space Key TO Restart",330,360);

        }
        g.setColor(Color.white);
        g.setFont(new Font("ITALIC",Font.PLAIN,15));
        g.drawString("Score "+score,750,30);
        g.drawString("Length :"+lengthOfSnake,750,50);
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
    //funtions for all the keypressed from the keyboard
        if(e.getKeyCode()==KeyEvent.VK_SPACE && GameOver){
            restart();
        }

        //if key pressed is equal to right key then move
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!isLeft))
        {
            isUp=false;
            isLeft=false;
            isDown=false;
            isRight =true;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!isRight))
        {
            isUp=false;
            isLeft=true;
            isDown=false;
            isRight =false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!isDown))
        {
            isUp=true;
            isLeft=false;
            isDown=false;
            isRight =false;
            move++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!isUp))
        {
            isUp=false;
            isLeft=false;
            isDown=true;
            isRight =false;
            move++;
        }

    }
    private void restart()
    {
    //function to restart the game
        GameOver=false;
        move=0;
        score=0;
        lengthOfSnake=3;
        isLeft=false;
        isRight=true;
        isUp=false;
        isDown=false;
        time.start();
        newfood();
        repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=lengthOfSnake-1;i>0;i--)//snake body is moving forward
        {
            snakeX[i]=snakeX[i-1];//here snakeX[1] move to the position of snakeX[0]
            snakeY[i]=snakeY[i-1];
        }

        if(isLeft)
            snakeX[0]=snakeX[0]-25;
        if(isRight)
            snakeX[0]=snakeX[0]+25;
        if(isUp)
            snakeY[0]=snakeY[0]-25;
        if(isDown)
            snakeY[0]=snakeY[0]+25;

        if(snakeX[0]>850)//if the snakeX[0] went more then 850 then start the snake from beginning
        {
            snakeX[0]=25;
        }
        if(snakeX[0]<25){
            snakeX[0]=850;
        }
        if(snakeY[0]>625)
        {
            snakeY[0]=75;
        }
        if(snakeY[0]<75)
        {
            snakeY[0]=625;
        }
        CollisionWithFood();
        CollisionWithBody();

        repaint();
    }
    private void CollisionWithBody()
    {//funtion when the head of snake colloid with body
        for(int i=lengthOfSnake-1;i>0;i--)
        {
            if(snakeX[i]==snakeX[0] && snakeY[i]== snakeY[0])
            {
                time.stop();
                GameOver=true;
            }
        }
    }
    private void CollisionWithFood()
    {//function when snake colloid with food
        if(snakeX[0]==foodx && snakeY[0]==foody)
        {
            newfood();
            score++;
            lengthOfSnake++;
            snakeX[lengthOfSnake+1]=snakeX[lengthOfSnake-2];
            snakeY[lengthOfSnake+1]=snakeY[lengthOfSnake-2];
        }
    }
    private void newfood()
    {//function for new food 
        foodx=xpos[random.nextInt(xpos.length-1)];
        foody=ypos[random.nextInt(ypos.length-1)];
        for(int i=lengthOfSnake-1;i>=0;i--){
            if(snakeX[i]==foodx && snakeY[i]==foody){
                newfood();
            }
        }
    }
}
