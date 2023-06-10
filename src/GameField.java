import javax.print.attribute.standard.Media;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 768;
    private final int DOT_SIZE = 16;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = false;
    private int a = 0;
    private int b = 0;



    public GameField(){
        setBackground(Color.darkGray);
        loadImages();
        initLabel();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
        addKeyListener(new restart());

    }
    private void initLabel() {
        JLabel label = new JLabel("Welcome");
        add (label, BorderLayout.NORTH);
    }

    public void initGame(){
        inGame = false;
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 512 - i * DOT_SIZE;
            y[i] = 384;
        }
        timer = new Timer(150,this);
        timer.start();
        createApple();
    }

    public void createApple(){
        for (int i = 0; i < dots; i++) {
            if (appleX != x[i] || appleY != y[i] || appleX != x[0] || appleY != y[0]) {
                    appleX = new Random().nextInt(48)* DOT_SIZE;
                    appleY = new Random().nextInt(48)* DOT_SIZE;
            }
        }
    }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("apple2.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot2.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
                g.setColor(Color.WHITE);

                String score = "Your score: ";
                g.drawString(score, 20, 20);
                g.drawString(String.valueOf(a), 110, 20);
            }
        } else{

            //g.d("My button", Font.BOLD | Font.BOLD, 20));



            String str2 = "SNAKE";
            g.setFont(new Font ("TimesRoman", Font.BOLD | Font.BOLD, 50));
            g.setColor(Color.white);
            g.drawString(str2,320,310);
            String str4 = " by jojo_panamo";
            String str1 = "PRESS ENTER KEY TO START";
            String str3 = "Your score is: ";
            String str5 = "Best score: ";
            g.setFont(new Font ("TimesRoman", Font.BOLD | Font.BOLD, 20));
            g.setColor(Color.white);
            g.drawString(str4,335,330);
            g.drawString(str3,340,410);
            g.drawString(str5,340,430);
            g.drawString(String.valueOf(a),480,410);
            g.drawString(String.valueOf(b),480,430);
            g.drawString(str1,250,500);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {

        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
            a++;
            if (a >= b) {
                b = a;
            }else{

            }

        }
    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }

            if(key == KeyEvent.VK_UP && !down){
                right = false;
                up = true;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                right = false;
                down = true;
                left = false;
            }
            //if (key == KeyEvent.VK_P) {
            //}
        }
    }
    public class restart extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key1 = e.getKeyCode();
            if (inGame == false && key1 == KeyEvent.VK_ENTER){
                inGame = true;
                repaint();
                a = 0;
                dots = 3;
                for (int i = 0; i < dots; i++) {
                    x[i] = 512 - i * DOT_SIZE;
                    y[i] = 384;
                    }
                }
            }
        }
    }