import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Naruto implements ActionListener, KeyListener {

    public static Naruto nin;
    private Renderer renderer;
    private final int WIDTH = 800, HEIGHT = 600;
    private ArrayList<Object> obstacle;
    private Random random;
    private int moment=0,fall=0;
    private boolean Over,Play;
    private int score=0;
    private int highScore=0;
    private Image background;
    private int which=0;
    private int action=0;
    private Object ninja;
    private int fl=0;
    private int nrSpace=0;
    String[] imag= {"/Users/yacinedjeddi/Desktop/Naruto/1.png",
            "/Users/yacinedjeddi/Desktop/Naruto/2.png",
            "/Users/yacinedjeddi/Desktop/Naruto/3.png",
            "/Users/yacinedjeddi/Desktop/Naruto/4.png"
    };

    private Clip clip;

    private Naruto() {

        JFrame Window = new JFrame();
        renderer = new Renderer();
        Window.add(renderer);
        random = new Random();
        ninja=new Object(100,HEIGHT-125,90,90,imag[which]);
        obstacle = new ArrayList<Object>();
        obstacle.add(new Object(-100, HEIGHT - 110, 70, 70,"/Users/yacinedjeddi/Desktop/Naruto/5.png"));
        background=new ImageIcon("/Users/yacinedjeddi/Desktop/Naruto/pict1.png").getImage();
        Window.setTitle("Sonario");
        Timer timer = new Timer(20, this);
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Window.setSize(WIDTH, HEIGHT);
        Window.addKeyListener(this);
        Window.setResizable(false);
        Window.setVisible(true);
        timer.start();

        addObstacle(true);
        addObstacle(true);

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("/Users/yacinedjeddi/Desktop/Naruto/music.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    private void addObstacle(boolean start) {
        int obs=random.nextInt(60);
        int widthC = 30 + random.nextInt(60); 
        int difObs=450+random.nextInt(650);
        if(obs%7!=0)
        if(start)
            obstacle.add(new Object(WIDTH + widthC + obstacle.size() * difObs, HEIGHT - 110, widthC, 70,"/Users/yacinedjeddi/Desktop/Naruto/5.png"));
            
        else {
            obstacle.add(new Object(obstacle.get(obstacle.size()-1).x+difObs, HEIGHT - 110, widthC,70,"/Users/yacinedjeddi/Desktop/Naruto/5.png"));
        }
        else
        if(start)
            obstacle.add(new Object(WIDTH + widthC + obstacle.size() * difObs, HEIGHT - 175, 90, 70,"/Users/yacinedjeddi/Desktop/Naruto/6.png"));
            
        else {
            obstacle.add(new Object(obstacle.get(obstacle.size()-1).x+difObs, HEIGHT - 175 , 90, 70,"/Users/yacinedjeddi/Desktop/Naruto/6.png"));
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(Play) {
            int speed = 15; 
            for (int i = 0; i < obstacle.size(); i++) {
                Object aux = obstacle.get(i);
                aux.x -= speed;  
            }
            for (int i = 0; i < obstacle.size() && !Over; i++) {
                Object aux = obstacle.get(i);
                if (aux.x + aux.widt < 0) {
                    obstacle.remove(aux);
                    addObstacle(false); 
                }
            }
            ninja = new Object(100, HEIGHT - 125, 90, 90, imag[which]); 
            moment++;
            if(moment%3==0)
                score=score+1;          
            if (((moment % 2) == 0) && (fall < 18)) {
                fall = fall + 25; 
            }
            if (fall< 18) {
                if (action == 0) {
                    ninja.y += fall;
                } else {
                    ninja.y -= fall;
                }
            } else {
                ninja.y = HEIGHT - 125;
            }
            if (ninja.y == HEIGHT - 125) {
                action = 0;
                if(which==0 || which==1) {    
                    which++;
                    if (which > 1) {
                        which = 0;
                    }
                }
                else
                if(which==2 || which==3){          
                    ninja.y = HEIGHT - 96;
                    which++;
                    if (which > 3) {
                        which = 2;
                    }
                }
            }
        }
            for(Object colum: obstacle){
                if(colum.intersects(ninja) ){
                    Over=true;
                    Play=false;    
                 
                }
            }
        renderer.repaint();
        }

    public void repaint(Graphics g) {
        g.drawImage(background,0,0,null);
        g.setColor(Color.gray);
        g.fillRect(0, HEIGHT-45, WIDTH, 5);    
        if(!Over) {
            g.drawImage(ninja.imj,ninja.x,ninja.y,null);      
        }

        for (Object colum : obstacle) {
                g.drawImage(colum.imj,colum.x,colum.y,colum.widt,colum.heigh,null);     

        g.setColor(Color.blue);
        g.setFont(new Font("Courier",1,25));
        if(!Play){
            g.drawString("Get ready for an epic adventure!",280,100);
           
        }
        g.setColor(Color.green.brighter());
        g.setFont(new Font("Courier",1,20));
        if(!Over && Play){
            g.drawString("High Score:"+String.valueOf(highScore)+" * "+"Running Score:"+String.valueOf(score),WIDTH-390,40);  

        }
        g.setColor(Color.red.darker());
        g.setFont(new Font("Courier",1,60));
        if(Over) {
            g.drawString("End of the road", 160, HEIGHT / 2 - 50);     

        }
    }
}

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN && ninja.y==HEIGHT-125){
            which=2;
         }

        if(e.getKeyCode()==KeyEvent.VK_SPACE && ninja.y==HEIGHT-125 &&!Over &&(which==0 || which==1)){
            jump();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        if(e.getKeyCode()==KeyEvent.VK_DOWN ){
            which=1;
        }

        if(e.getKeyCode()==KeyEvent.VK_SPACE && Over){
            nrSpace++;
            action=1;
            if(nrSpace==2) {  

                jump();
            }
        }
    }
    private void jump() {
        if(Over) {
            nrSpace=0;
            Over = false;
            ninja = new Object(100, HEIGHT-125, 90, 90,imag[which]);
            obstacle.clear(); 
            addObstacle(true);
            addObstacle(true);
            if(score>highScore)
            highScore=score; 
            score=0;
            fall=0;
        }
        if(!Play)
            Play=true;
        else if(!Over){
            if(fall>0){
                fall=0;
            }
            fall-=fl;
            if(fl<100) {
                fl+=30;
                jump();
            }
            else{
               fl=0;
            }
        }
    }
    public static void main(String[] args) {
        nin = new Naruto();
    }
}


