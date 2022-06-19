import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class gamepanel extends JPanel implements ActionListener {
    static final  int screenwidth = 700;
    static final  int screenheight = 700;
    static final  int unitsize = 25;
    static final  int gameunits = (screenwidth*screenheight)/unitsize;
    static final int delay = 80;
    final int[] x =new int[gameunits];
    final int[] y = new int[gameunits];
    int bodyparts = 5;
    int appleseaten = 0;
    int applex;
    int appley;
    char direction ='D';
    boolean running = false;
    Timer timer;
    Random random;
    gamepanel(){
     random = new Random();
     this.setPreferredSize(new Dimension(screenwidth,screenheight));
     this.setBackground(Color.black);
     this.setFocusable(true);
     this.addKeyListener(new keyadapter() );
     startgame();

    }
    public void startgame(){
     newapple();
     running= true;
     timer = new Timer(delay,this);
     timer.start();
    }
    public void paintComponent(Graphics g){
     super.paintComponent(g);
     draw(g);
    }
    public void draw(Graphics g){
        if(running) {


            g.setColor(Color.white);
            g.fillOval(applex, appley, unitsize, unitsize);
            for (int i = 0; i < bodyparts; i++) {
                if (i == 0) {
                    g.setColor(Color.orange);
                    g.fillRect(x[i], y[i], unitsize, unitsize);
                } else {
                    g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], unitsize, unitsize);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free",Font.BOLD,35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" +appleseaten,(screenwidth - metrics.stringWidth("Score:"))/2,g.getFont().getSize());
        }
        else{
            gameover(g);
        }
    }
    public void newapple(){
     applex = random.nextInt(screenwidth/unitsize)*unitsize;
     appley = random.nextInt(screenheight/unitsize)*unitsize;
    }
    public void move(){
     for(int i=bodyparts;i>0;i--){
         x[i]=x[i-1];
         y[i]=y[i-1];
     }
        switch (direction) {
            case 'U' -> y[0] = y[0] - unitsize;
            case 'D' -> y[0] = y[0] + unitsize;
            case 'L' -> x[0] = x[0] - unitsize;
            case 'R' -> x[0] = x[0] + unitsize;
        }
    }
    public void checkegg(){
      if((x[0]==applex) && (y[0]==appley)){
          bodyparts++;
          appleseaten++;
          newapple();
      }
    }
    public void checkcollisions(){
    for(int i=bodyparts;i>0;i--){
        if ((x[0] == x[i]) && (y[0] == y[i])) {
            running = false;
            break;
        }
    }
    if(x[0]<0){
        running = false;
    }
    if(x[0]>screenwidth){
            running = false;
        }
    if(y[0]>screenwidth){
        running = false;
    }
    if(y[0]<0){
        running = false;
    }
    if(!running){
        timer.stop();
    }
    }
    public void gameover(Graphics g){
        if(!running) {


            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" +appleseaten,(screenwidth - metrics.stringWidth("Score:"))/2,unitsize*3);
            g.drawString("Game Over", (screenwidth - metrics.stringWidth("Game Over")) / 2, screenheight / 2);
            g.setColor(Color.RED);
            g.setFont(new Font("Ink Free", Font.BOLD, 30));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("1-> to start the game press enter", (screenwidth - metrics1.stringWidth("to start the game press enter")) / 2, screenheight / 2 + 100);
            g.drawString("2-> to exit press any key", (screenwidth - metrics1.stringWidth("to exit press any keyr")) / 2, screenheight / 2 + 150);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
     if(running){
         move();
         checkegg();
         checkcollisions();

     }
     repaint();
    }
    public class keyadapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(!running){
              if(e.getKeyCode() == KeyEvent.VK_ENTER){
                  new gameframe();
              }
              else{
                  System.exit(0);
              }
            }

         switch (e.getKeyCode()){
             case KeyEvent.VK_LEFT:
                 if(direction != 'R'){
                     direction = 'L';
                 }
                 break;
             case KeyEvent.VK_RIGHT:
                 if(direction != 'L'){
                     direction = 'R';
                 }
                 break;
             case KeyEvent.VK_UP:
                 if(direction != 'D'){
                     direction = 'U';
                 }
                 break;
             case KeyEvent.VK_DOWN:
                 if(direction != 'U'){
                     direction = 'D';
                 }
                 break;
         }
        }
    }
}
