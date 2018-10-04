import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random; //class To pick Numbers Randomly
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.LineUnavailableException;
public class ColorBlocks extends JFrame implements ActionListener{

    private JButton []btn;
    private JButton restartBtn;
    private JButton undoBtn;
    private JButton redoBtn;
    private JLabel timerLabel2 = new JLabel("00:00");
    private JLabel scoreLabel2 = new JLabel("225");
    private GridLayout gridLayout = new GridLayout(20,10);
    private JPanel []pane = new JPanel[(gridLayout.getRows() * gridLayout.getColumns())];
    private Color gy = new Color(236, 236, 236);
    private JRadioButton soundRadioButton = new JRadioButton("Sound");
    private JRadioButton radioBtn9 = new JRadioButton(" 9");
    private JRadioButton radioButton8 = new JRadioButton(" 8");
    private JRadioButton radioButton7 = new JRadioButton(" 7");
    private JRadioButton radioButton6 = new JRadioButton(" 6");
    private int []randomArray;
    private int []undoArray;
    private int []redoArray;
    private Timer sec;
    private int counter=0;
    private int count =0;
    private int init;
    private int bound1, sound1;
    private ColorBlocks()  {
        JFrame frame1 = new JFrame("COLOR CLICKS");
        ImageIcon undo = new ImageIcon("undoIcon.icon.png","undo");
        ImageIcon redo = new ImageIcon("redoIcon.icon","redo");
        ImageIcon restart = new ImageIcon("restart.png","restart");
        ImageIcon settings = new ImageIcon("settings.png","settings");
        frame1.setSize(350,500);
        frame1.setVisible(true);
        frame1.setLocation(500,150);
        frame1.setResizable(false);
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GridLayout gridLayout5 = new GridLayout(1,1);
        JButton exitBtn = new JButton("EXIT");
        JButton newGameBtn = new JButton("NEW GAME");
        JButton highScoresBtn = new JButton("HIGH SCORES");
        restartBtn = new JButton(restart);
        restartBtn.setBorderPainted(false);
        restartBtn.setBackground(gy);
        restartBtn.setToolTipText("Restart this Game");
        JButton settingsBtn = new JButton(settings);
        settingsBtn.setBorderPainted(false);
        settingsBtn.setBackground(gy);
        settingsBtn.setToolTipText("Settings");
        undoBtn = new JButton(undo);
        undoBtn.setBorderPainted(false);
        undoBtn.setBackground(gy);
        undoBtn.setToolTipText("Un-Do \n you can only undo once");
        redoBtn = new JButton(redo);
        redoBtn.setBorderPainted(false);
        redoBtn.setBackground(gy);
        redoBtn.setToolTipText("Re-Do");
        Scanner input = null;
        try {
            input = new Scanner(new FileInputStream("settings.bin"));
        }catch(FileNotFoundException ieo){
            System.out.println("FILE settings.bin WAS NOT FOUND!!!");
        }
        try{
            if(input!=null){
                sound1=Integer.parseInt(input.nextLine());
                bound1=Integer.parseInt(input.nextLine());
            }
        }catch ( NullPointerException npe){
            System.out.println("exception null pointer");
        }
        BorderLayout BdrLyt = new BorderLayout(0,0); //BORDER LAYOUT
        GridLayout gridLayout2 = new GridLayout(1,3,5,2);
        GridLayout gridLayout3 = new GridLayout(1,3,50,5);
        GridLayout gridLayout4 = new GridLayout(2,2);
        GridLayout gridLayout6 = new GridLayout(1,2);
        JPanel pane2 = new JPanel(gridLayout);
        JPanel pane1 = new JPanel(gridLayout2);
        JPanel pane3 = new JPanel(gridLayout3);
        JPanel pane4 = new JPanel(gridLayout4);
        JPanel pane5 = new JPanel(gridLayout6);
        JPanel pane6 = new JPanel(gridLayout6);
        Font forte = new Font("forte",Font.BOLD,11);
        JLabel timerLabel1 = new JLabel("Time: ");
        timerLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        timerLabel1.setFont(forte   );
        JLabel scoreLabel1 = new JLabel("Score: ");
        scoreLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
        scoreLabel1.setFont(forte);
        pane1.add(highScoresBtn);
        pane1.add(newGameBtn);
        pane1.add(exitBtn);
        pane4.add(timerLabel1);
        pane4.add(timerLabel2);
        pane4.add(scoreLabel1);
        pane4.add(scoreLabel2);
        pane5.add(settingsBtn);
        pane5.add(restartBtn);
        pane6.add(undoBtn);
        pane6.add(redoBtn);
        pane3.add(pane6);
        pane3.add(pane4);
        pane3.add(pane5);
        frame1.setLayout(BdrLyt);
        frame1.add(pane3, BorderLayout.NORTH);
        frame1.add(pane2, BorderLayout.CENTER);
        frame1.add(pane1, BorderLayout.SOUTH);
        for (int i = 0; i < (gridLayout.getRows() * gridLayout.getColumns()); i++) {
            pane[i] = new JPanel();
            pane[i].setLayout(gridLayout5);
            pane2.add(pane[i]);
        }
        randomButton();
        setScore();
        timer();
        highScoresBtn.addActionListener(new HighScores());
        newGameBtn.addActionListener(new NewGame());
        exitBtn.addActionListener(new Exit());
        restartBtn.addActionListener(new ReStart());
        settingsBtn.addActionListener(e -> settings() );
        undoBtn.addActionListener(e-> undo());
        redoBtn.addActionListener(e-> redo());
        soundRadioButton.setSelected(true);
        undoBtn.setEnabled(false);
        redoBtn.setEnabled(false);
    }
    private class ReStart implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i=0; i<200;i++){
                pane[i].removeAll();
                colorButton(i,randomArray[i]);
            }
            count=0;
            counter=0;
            scoreLabel2.setText("225");
            init = 0;
        }
    }
    private class NewGame implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for (int i=0; i<200;i++) {
                pane[i].removeAll();
            }
            randomButton();
            count=0;
            counter=0;
            scoreLabel2.setText("225");
            restartBtn.setEnabled(true);
            undoBtn.setEnabled(false);
            redoBtn.setEnabled(false);
            init =0;
            if (!restartBtn.isEnabled()){
                timer();
            }
        }
    }
    private class Exit implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class HighScores implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new CalcHighScore();
        }
    }
    private void randomButton() {

        Random rn = new Random();
        btn= new JButton[(gridLayout.getRows() * gridLayout.getColumns())];
        randomArray = new int [200];
        for (int i = 0; i < (gridLayout.getRows() * gridLayout.getColumns()); i++) {
            int j = rn.nextInt(bound1);
            randomArray[i] = j;
            colorButton(i,j);
        }
    }
    private void colorButton(int i,int j){
        if (j == 1) {
            Color C = new Color(255, 0, 0); //red
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 2) {
            Color C = new Color(0, 255, 0); //green
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 3) {
            Color C = new Color(0, 0, 255); //blue
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 4) {
            Color C = new Color(0, 0, 0); //black
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 5) {
            Color C = new Color(255, 0, 255); //purple
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 6) {
            Color C = new Color(0, 255, 255); //cyan
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 7) {
            Color C = new Color(255, 255, 0); //yellow
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 8) {
            Color C = new Color(127, 127, 127);
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if (j == 0) {
            Color C = new Color(139, 69, 19);
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(C);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        } else if(j==9) {
            System.out.println(j);
            btn[i] = new JButton();
            btn[i].setBackground(gy);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        }else{
            btn[i] = new JButton();
            btn[i].setBackground(gy);
            btn[i].setBorderPainted(false);
            btn[i].addActionListener(e -> choice(btn,i));
            pane[i].add(btn[i]);
        }
        System.out.println(i);
    }
    private void button0(){
        Color color = pane[0].getComponent(0).getBackground();
        if (pane[1].getComponent(0) != null&&color.equals(pane[1].getComponent(0).getBackground())){
                searchModuleRight(0,9,color);
        }
        else if (pane[10].getComponent(0) != null&&color.equals(pane[10].getComponent(0).getBackground())){
                searchModuleDown(0,color);
        }
    }
    private void button1(){
        Color color = pane[1].getComponent(0).getBackground();
        if (pane[2].getComponent(0) != null&&color.equals(pane[2].getComponent(0).getBackground())) {
                searchModuleRight(1,9,color);
        } else if (pane[0].getComponent(0) != null&&color.equals(pane[0].getComponent(0).getBackground())) {
                searchModuleLeft(1,0,color);
        } else if (pane[11].getComponent(0) != null&&color.equals(pane[11].getComponent(0).getBackground())) {
                searchModuleDown(1,color);
        }
    }
    private void button2(){
        Color color = pane[2].getComponent(0).getBackground();
        if (pane[3].getComponent(0) != null&&color.equals(pane[3].getComponent(0).getBackground())) {
                searchModuleRight(2,9,color);
        } else if (pane[1].getComponent(0) != null&&color.equals(pane[1].getComponent(0).getBackground())) {
                searchModuleLeft(2,0,color);
        } else if (pane[12].getComponent(0) != null&&color.equals(pane[12].getComponent(0).getBackground())) {
                searchModuleDown(2,color);
        }
    }
    private void button3(){
        Color color = pane[3].getComponent(0).getBackground();
        if (pane[4].getComponent(0) != null&&color.equals(pane[4].getComponent(0).getBackground())) {
                searchModuleRight(3,9,color);
        } else if (pane[2].getComponent(0) != null&&color.equals(pane[2].getComponent(0).getBackground())) {
                searchModuleLeft(3,0,color);
        } else if (pane[13].getComponent(0) != null&&color.equals(pane[13].getComponent(0).getBackground())) {
                searchModuleDown(3,color);
        }
    }
    private void button4(){
        Color color = pane[4].getComponent(0).getBackground();
        if (pane[5].getComponent(0) != null&&color.equals(pane[5].getComponent(0).getBackground())) {
                searchModuleRight(4,9,color);
        } else if (pane[3].getComponent(0) != null&&color.equals(pane[3].getComponent(0).getBackground())) {
                searchModuleLeft(4,0,color);
        } else if (pane[14].getComponent(0) != null&&color.equals(pane[14].getComponent(0).getBackground())) {
                searchModuleDown(4,color);
        }
    }
    private void button5(){
        Color color = pane[5].getComponent(0).getBackground();
        if (pane[6].getComponent(0) != null&&color.equals(pane[6].getComponent(0).getBackground())) {
                searchModuleRight(5,9,color);
        } else if (pane[4].getComponent(0) != null&&color.equals(pane[4].getComponent(0).getBackground())) {
                searchModuleLeft(5,0,color);
        } else if (pane[15].getComponent(0) != null&&color.equals(pane[15].getComponent(0).getBackground())) {
                searchModuleDown(5,color);
        }
    }
    private void button6(){
        Color color = pane[6].getComponent(0).getBackground();
        if (pane[7].getComponent(0) != null&&color.equals(pane[7].getComponent(0).getBackground())) {
                searchModuleRight(6,9,color);
        } else if (pane[5].getComponent(0) != null&&color.equals(pane[5].getComponent(0).getBackground())) {
                searchModuleLeft(6,0,color);
        } else if (pane[16].getComponent(0) != null&&color.equals(pane[16].getComponent(0).getBackground())) {
                searchModuleDown(6,color);
        }
    }
    private void button7(){
        Color color = pane[7].getComponent(0).getBackground();
        if (pane[8].getComponent(0) != null&&color.equals(pane[8].getComponent(0).getBackground())) {
                searchModuleRight(7,9,color);
        } else if (pane[6].getComponent(0) != null&&color.equals(pane[6].getComponent(0).getBackground())) {
                searchModuleLeft(7,0,color);
        } else if (pane[17].getComponent(0) != null&&color.equals(pane[17].getComponent(0).getBackground())) {
                searchModuleDown(7,color);
        }
    }
    private void button8(){
        Color color = pane[8].getComponent(0).getBackground();
        if (pane[9].getComponent(0) != null&&color.equals(pane[9].getComponent(0).getBackground())) {
                searchModuleRight(8,9,color);
        } else if (pane[7].getComponent(0) != null&&color.equals(pane[7].getComponent(0).getBackground())) {
                searchModuleLeft(8,0,color);
        } else if (pane[18].getComponent(0) != null&&color.equals(pane[18].getComponent(0).getBackground())) {
                searchModuleDown(8,color);
        }
    }
    private void button9(){
        Color color = pane[9].getComponent(0).getBackground();
        if (pane[8].getComponent(0) != null&&color.equals(pane[8].getComponent(0).getBackground())) {
                searchModuleLeft(9,0,color);
        } else if (pane[19].getComponent(0) != null&&color.equals(pane[19].getComponent(0).getBackground())) {
                searchModuleDown(9,color);
        }
    }
    private void button10(){
        Color color = pane[10].getComponent(0).getBackground();
        if (pane[11].getComponent(0) != null&&color.equals(pane[11].getComponent(0).getBackground())) {
                searchModuleRight(10,19,color);
        } else if (pane[20].getComponent(0) != null&&color.equals(pane[20].getComponent(0).getBackground())) {
                searchModuleDown(10,color);
        } else if (pane[0].getComponent(0) != null&&color.equals(pane[0].getComponent(0).getBackground())) {
                searchModuleUp(10,color);
        }
    }
    private void button11(){
        Color color = pane[11].getComponent(0).getBackground();
        if (pane[12].getComponent(0) != null&&color.equals(pane[12].getComponent(0).getBackground())) {
            searchModuleRight(11,19,color);
        } else if (pane[10].getComponent(0) != null&&color.equals(pane[10].getComponent(0).getBackground())) {
            searchModuleLeft(11,10,color);
        } else if (pane[21].getComponent(0) != null&&color.equals(pane[21].getComponent(0).getBackground())) {
            searchModuleDown(11,color);
        } else if (pane[1].getComponent(0) != null&&color.equals(pane[1].getComponent(0).getBackground())) {
            searchModuleUp(11,color);
        }
    }
    private void button12(){
        Color color = pane[12].getComponent(0).getBackground();
        if (pane[13].getComponent(0) != null&&color.equals(pane[13].getComponent(0).getBackground())) {
            searchModuleRight(12,19,color);
        } else if (pane[11].getComponent(0) != null&&color.equals(pane[11].getComponent(0).getBackground())) {
            searchModuleLeft(12,10,color);
        } else if (pane[22].getComponent(0) != null&&color.equals(pane[22].getComponent(0).getBackground())) {
            searchModuleDown(12,color);
        } else if (pane[2].getComponent(0) != null&&color.equals(pane[2].getComponent(0).getBackground())) {
            searchModuleUp(12,color);
        }
    }
    private void button13(){
        Color color = pane[13].getComponent(0).getBackground();
        if (pane[14].getComponent(0) != null&&color.equals(pane[14].getComponent(0).getBackground())) {
            searchModuleRight(13,19,color);
        } else if (pane[12].getComponent(0) != null&&color.equals(pane[12].getComponent(0).getBackground())) {
            searchModuleLeft(13,10,color);
        } else if (pane[23].getComponent(0) != null&&color.equals(pane[23].getComponent(0).getBackground())) {
            searchModuleDown(13,color);
        } else if (pane[3].getComponent(0) != null&&color.equals(pane[3].getComponent(0).getBackground())) {
            searchModuleUp(13,color);
        }
    }
    private void button14(){
        Color color = pane[14].getComponent(0).getBackground();
        if (pane[15].getComponent(0) != null&&color.equals(pane[15].getComponent(0).getBackground())) {
            searchModuleRight(14,19,color);
        } else if (pane[13].getComponent(0) != null&&color.equals(pane[13].getComponent(0).getBackground())) {
            searchModuleLeft(14,10,color);
        } else if (pane[24].getComponent(0) != null&&color.equals(pane[24].getComponent(0).getBackground())) {
            searchModuleDown(14,color);
        } else if (pane[4].getComponent(0) != null&&color.equals(pane[4].getComponent(0).getBackground())) {
            searchModuleUp(14,color);
        }
    }
    private void button15(){
        Color color = pane[15].getComponent(0).getBackground();
        if (pane[16].getComponent(0) != null&&color.equals(pane[16].getComponent(0).getBackground())) {
            searchModuleRight(15,19,color);
        } else if (pane[14].getComponent(0) != null&&color.equals(pane[14].getComponent(0).getBackground())) {
            searchModuleLeft(15,10,color);
        } else if (pane[25].getComponent(0) != null&&color.equals(pane[25].getComponent(0).getBackground())) {
            searchModuleDown(15,color);
        } else if (pane[5].getComponent(0) != null&&color.equals(pane[5].getComponent(0).getBackground())) {
            searchModuleUp(15,color);
        }
    }
    private void button16(){
        Color color = pane[16].getComponent(0).getBackground();
        if (pane[17].getComponent(0) != null&&color.equals(pane[17].getComponent(0).getBackground())) {
            searchModuleRight(16,19,color);
        } else if (pane[15].getComponent(0) != null&&color.equals(pane[15].getComponent(0).getBackground())) {
            searchModuleLeft(16,10,color);
        } else if (pane[26].getComponent(0) != null&&color.equals(pane[26].getComponent(0).getBackground())) {
            searchModuleDown(16,color);
        } else if (pane[6].getComponent(0) != null&&color.equals(pane[6].getComponent(0).getBackground())) {
            searchModuleUp(16,color);
        }
    }
    private void button17(){
        Color color = pane[17].getComponent(0).getBackground();
        if (pane[18].getComponent(0) != null&&color.equals(pane[18].getComponent(0).getBackground())) {
            searchModuleRight(17,19,color);
        } else if (pane[16].getComponent(0) != null&&color.equals(pane[16].getComponent(0).getBackground())) {
            searchModuleLeft(17,10,color);
        } else if (pane[27].getComponent(0) != null&&color.equals(pane[27].getComponent(0).getBackground())) {
            searchModuleDown(17,color);
        } else if (pane[7].getComponent(0) != null&&color.equals(pane[7].getComponent(0).getBackground())) {
            searchModuleUp(17,color);
        }
    }
    private void button18(){
        Color color = pane[18].getComponent(0).getBackground();
        if (pane[19].getComponent(0) != null&&color.equals(pane[19].getComponent(0).getBackground())) {
            searchModuleRight(18,19,color);
        } else if (pane[17].getComponent(0) != null&&color.equals(pane[17].getComponent(0).getBackground())) {
            searchModuleLeft(18,10,color);
        } else if (pane[28].getComponent(0) != null&&color.equals(pane[28].getComponent(0).getBackground())) {
            searchModuleDown(18,color);
        } else if (pane[8].getComponent(0) != null&&color.equals(pane[8].getComponent(0).getBackground())) {
            searchModuleUp(18,color);
        }
    }
    private void button19(){
        Color color = pane[19].getComponent(0).getBackground();
        if (pane[18].getComponent(0) != null&&color.equals(pane[18].getComponent(0).getBackground())) {
            searchModuleLeft(19,10,color);
        } else if (pane[29].getComponent(0) != null&&color.equals(pane[29].getComponent(0).getBackground())) {
            searchModuleDown(19,color);
        } else if (pane[9].getComponent(0) != null&&color.equals(pane[9].getComponent(0).getBackground())) {
            searchModuleUp(19,color);
        }
    }
    private void button20(){
        Color color = pane[20].getComponent(0).getBackground();
        if (pane[21].getComponent(0) != null&&color.equals(pane[21].getComponent(0).getBackground())) {
            searchModuleRight(20,29,color);
        } else if (pane[30].getComponent(0) != null&&color.equals(pane[30].getComponent(0).getBackground())) {
            searchModuleDown(20,color);
        } else if (pane[10].getComponent(0) != null&&color.equals(pane[10].getComponent(0).getBackground())) {
            searchModuleUp(20,color);
        }
    }
    private void button21(){
        Color color = pane[21].getComponent(0).getBackground();
        if (pane[22].getComponent(0) != null&&color.equals(pane[22].getComponent(0).getBackground())) {
            searchModuleRight(21,29,color);
        } else if (pane[20].getComponent(0) != null&&color.equals(pane[20].getComponent(0).getBackground())) {
            searchModuleLeft(21,20,color);
        } else if (pane[31].getComponent(0) != null&&color.equals(pane[31].getComponent(0).getBackground())) {
            searchModuleDown(21,color);
        } else if (pane[11].getComponent(0) != null&&color.equals(pane[11].getComponent(0).getBackground())) {
            searchModuleUp(21,color);
        }
    }
    private void button22(){
        Color color = pane[22].getComponent(0).getBackground();
        if (pane[23].getComponent(0) != null&&color.equals(pane[23].getComponent(0).getBackground())) {
            searchModuleRight(22,29,color);
        } else if (pane[21].getComponent(0) != null&&color.equals(pane[21].getComponent(0).getBackground())) {
            searchModuleLeft(22,20,color);
        } else if (pane[32].getComponent(0) != null&&color.equals(pane[32].getComponent(0).getBackground())) {
            searchModuleDown(22,color);
        } else if (pane[12].getComponent(0) != null&&color.equals(pane[12].getComponent(0).getBackground())) {
            searchModuleUp(22,color);
        }
    }
    private void button23(){
        Color color = pane[23].getComponent(0).getBackground();
        if (pane[24].getComponent(0) != null&&color.equals(pane[24].getComponent(0).getBackground())) {
            searchModuleRight(23,29,color);
        } else if (pane[22].getComponent(0) != null&&color.equals(pane[22].getComponent(0).getBackground())) {
            searchModuleLeft(23,20,color);
        } else if (pane[33].getComponent(0) != null&&color.equals(pane[33].getComponent(0).getBackground())) {
            searchModuleDown(23,color);
        } else if (pane[13].getComponent(0) != null&&color.equals(pane[13].getComponent(0).getBackground())) {
            searchModuleUp(23,color);
        }
    }
    private void button24(){
        Color color = pane[24].getComponent(0).getBackground();
        if (pane[25].getComponent(0) != null&&color.equals(pane[25].getComponent(0).getBackground())) {
            searchModuleRight(24,29,color);
        } else if (pane[23].getComponent(0) != null&&color.equals(pane[23].getComponent(0).getBackground())) {
            searchModuleLeft(24,20,color);
        } else if (pane[34].getComponent(0) != null&&color.equals(pane[34].getComponent(0).getBackground())) {
            searchModuleDown(24,color);
        } else if (pane[14].getComponent(0) != null&&color.equals(pane[14].getComponent(0).getBackground())) {
            searchModuleUp(24,color);
        }
    }
    private void button25(){
        Color color = pane[25].getComponent(0).getBackground();
        if (pane[26].getComponent(0) != null&&color.equals(pane[26].getComponent(0).getBackground())) {
            searchModuleRight(25,29,color);
        } else if (pane[24].getComponent(0) != null&&color.equals(pane[24].getComponent(0).getBackground())) {
            searchModuleLeft(25,20,color);
        } else if (pane[35].getComponent(0) != null&&color.equals(pane[35].getComponent(0).getBackground())) {
            searchModuleDown(25,color);
        } else if (pane[15].getComponent(0) != null&&color.equals(pane[15].getComponent(0).getBackground())) {
            searchModuleUp(25,color);
        }
    }
    private void button26(){
        Color color = pane[26].getComponent(0).getBackground();
        if (pane[27].getComponent(0) != null&&color.equals(pane[27].getComponent(0).getBackground())) {
            searchModuleRight(26,29,color);
        } else if (pane[25].getComponent(0) != null&&color.equals(pane[25].getComponent(0).getBackground())) {
            searchModuleLeft(26,20,color);
        } else if (pane[36].getComponent(0) != null&&color.equals(pane[36].getComponent(0).getBackground())) {
            searchModuleDown(26,color);
        } else if (pane[16].getComponent(0) != null&&color.equals(pane[16].getComponent(0).getBackground())) {
            searchModuleUp(26,color);
        }
    }
    private void button27(){
        Color color = pane[27].getComponent(0).getBackground();
        if (pane[28].getComponent(0) != null&&color.equals(pane[28].getComponent(0).getBackground())) {
            searchModuleRight(27,29,color);
        } else if (pane[26].getComponent(0) != null&&color.equals(pane[26].getComponent(0).getBackground())) {
            searchModuleLeft(27,20,color);
        } else if (pane[37].getComponent(0) != null&&color.equals(pane[37].getComponent(0).getBackground())) {
            searchModuleDown(27,color);
        } else if (pane[17].getComponent(0) != null&&color.equals(pane[17].getComponent(0).getBackground())) {
            searchModuleUp(27,color);
        }
    }
    private void button28(){
        Color color = pane[28].getComponent(0).getBackground();
        if (pane[29].getComponent(0) != null&&color.equals(pane[29].getComponent(0).getBackground())) {
            searchModuleRight(28,29,color);
        } else if (pane[27].getComponent(0) != null&&color.equals(pane[27].getComponent(0).getBackground())) {
            searchModuleLeft(28,20,color);
        } else if (pane[38].getComponent(0) != null&&color.equals(pane[38].getComponent(0).getBackground())) {
            searchModuleDown(28,color);
        } else if (pane[18].getComponent(0) != null&&color.equals(pane[18].getComponent(0).getBackground())) {
            searchModuleUp(28,color);
        }
    }
    private void button29(){
        Color color = pane[29].getComponent(0).getBackground();
        if (pane[28].getComponent(0) != null&&color.equals(pane[28].getComponent(0).getBackground())) {
            searchModuleLeft(29,20,color);
        } else if (pane[39].getComponent(0) != null&&color.equals(pane[39].getComponent(0).getBackground())) {
            searchModuleDown(29,color);
        } else if (pane[19].getComponent(0) != null&&color.equals(pane[19].getComponent(0).getBackground())) {
            searchModuleUp(29,color);
        }
    }
    private void button30(){
        Color color = pane[30].getComponent(0).getBackground();
        if (pane[31].getComponent(0) != null&&color.equals(pane[31].getComponent(0).getBackground())) {
            searchModuleRight(30,39,color);
        } else if (pane[40].getComponent(0) != null&&color.equals(pane[40].getComponent(0).getBackground())) {
            searchModuleDown(30,color);
        } else if (pane[20].getComponent(0) != null&&color.equals(pane[20].getComponent(0).getBackground())) {
            searchModuleUp(30,color);
        }
    }
    private void button31(){
        Color color = pane[31].getComponent(0).getBackground();
        if (pane[32].getComponent(0) != null&&color.equals(pane[32].getComponent(0).getBackground())) {
            searchModuleRight(31,39,color);
        } else if (pane[30].getComponent(0) != null&&color.equals(pane[30].getComponent(0).getBackground())) {
            searchModuleLeft(31,30,color);
        } else if (pane[41].getComponent(0) != null&&color.equals(pane[41].getComponent(0).getBackground())) {
            searchModuleDown(31,color);
        } else if (pane[21].getComponent(0) != null&&color.equals(pane[21].getComponent(0).getBackground())) {
            searchModuleUp(31,color);
        }
    }
    private void button32(){
        Color color = pane[32].getComponent(0).getBackground();
        if (pane[33].getComponent(0) != null&&color.equals(pane[33].getComponent(0).getBackground())) {
            searchModuleRight(32,39,color);
        } else if (pane[31].getComponent(0) != null&&color.equals(pane[31].getComponent(0).getBackground())) {
            searchModuleLeft(32,30,color);
        } else if (pane[42].getComponent(0) != null&&color.equals(pane[42].getComponent(0).getBackground())) {
            searchModuleDown(32,color);
        } else if (pane[22].getComponent(0) != null&&color.equals(pane[22].getComponent(0).getBackground())) {
            searchModuleUp(32,color);
        }
    }
    private void button33(){
        Color color = pane[33].getComponent(0).getBackground();
        if (pane[34].getComponent(0) != null&&color.equals(pane[34].getComponent(0).getBackground())) {
            searchModuleRight(33,39,color);
        } else if (pane[32].getComponent(0) != null&&color.equals(pane[32].getComponent(0).getBackground())) {
            searchModuleLeft(33,30,color);
        } else if (pane[43].getComponent(0) != null&&color.equals(pane[43].getComponent(0).getBackground())) {
            searchModuleDown(33,color);
        } else if (pane[23].getComponent(0) != null&&color.equals(pane[23].getComponent(0).getBackground())) {
            searchModuleUp(33,color);
        }
    }
    private void button34(){
        Color color = pane[34].getComponent(0).getBackground();
        if (pane[35].getComponent(0) != null&&color.equals(pane[35].getComponent(0).getBackground())) {
            searchModuleRight(34,39,color);
        } else if (pane[33].getComponent(0) != null&&color.equals(pane[33].getComponent(0).getBackground())) {
            searchModuleLeft(34,30,color);
        } else if (pane[44].getComponent(0) != null&&color.equals(pane[44].getComponent(0).getBackground())) {
            searchModuleDown(34,color);
        } else if (pane[24].getComponent(0) != null&&color.equals(pane[24].getComponent(0).getBackground())) {
            searchModuleUp(34,color);
        }
    }
    private void button35(){
        Color color = pane[35].getComponent(0).getBackground();
        if (pane[36].getComponent(0) != null&&color.equals(pane[36].getComponent(0).getBackground())) {
            searchModuleRight(35,39,color);
        } else if (pane[34].getComponent(0) != null&&color.equals(pane[34].getComponent(0).getBackground())) {
            searchModuleLeft(35,30,color);
        } else if (pane[45].getComponent(0) != null&&color.equals(pane[45].getComponent(0).getBackground())) {
            searchModuleDown(35,color);
        } else if (pane[25].getComponent(0) != null&&color.equals(pane[25].getComponent(0).getBackground())) {
            searchModuleUp(35,color);
        }
    }
    private void button36(){
        Color color = pane[36].getComponent(0).getBackground();
        if (pane[37].getComponent(0) != null&&color.equals(pane[37].getComponent(0).getBackground())) {
            searchModuleRight(36,39,color);
        } else if (pane[35].getComponent(0) != null&&color.equals(pane[35].getComponent(0).getBackground())) {
            searchModuleLeft(36,30,color);
        } else if (pane[46].getComponent(0) != null&&color.equals(pane[46].getComponent(0).getBackground())) {
            searchModuleDown(36,color);
        } else if (pane[26].getComponent(0) != null&&color.equals(pane[26].getComponent(0).getBackground())) {
            searchModuleUp(36,color);
        }
    }
    private void button37(){
        Color color = pane[37].getComponent(0).getBackground();
        if (pane[38].getComponent(0) != null&&color.equals(pane[38].getComponent(0).getBackground())) {
            searchModuleRight(37,39,color);
        } else if (pane[36].getComponent(0) != null&&color.equals(pane[36].getComponent(0).getBackground())) {
            searchModuleLeft(37,30,color);
        } else if (pane[47].getComponent(0) != null&&color.equals(pane[47].getComponent(0).getBackground())) {
            searchModuleDown(37,color);
        } else if (pane[27].getComponent(0) != null&&color.equals(pane[27].getComponent(0).getBackground())) {
            searchModuleUp(37,color);
        }
    }
    private void button38(){
        Color color = pane[38].getComponent(0).getBackground();
        if (pane[39].getComponent(0) != null&&color.equals(pane[39].getComponent(0).getBackground())) {
            searchModuleRight(38,39,color);
        } else if (pane[37].getComponent(0) != null&&color.equals(pane[37].getComponent(0).getBackground())) {
            searchModuleLeft(38,30,color);
        } else if (pane[48].getComponent(0) != null&&color.equals(pane[48].getComponent(0).getBackground())) {
            searchModuleDown(38,color);
        } else if (pane[28].getComponent(0) != null&&color.equals(pane[28].getComponent(0).getBackground())) {
            searchModuleUp(38,color);
        }
    }
    private void button39(){
        Color color = pane[39].getComponent(0).getBackground();
        if (pane[38].getComponent(0) != null&&color.equals(pane[38].getComponent(0).getBackground())) {
            searchModuleLeft(39,30,color);
        } else if (pane[49].getComponent(0) != null&&color.equals(pane[49].getComponent(0).getBackground())) {
            searchModuleDown(39,color);
        } else if (pane[29].getComponent(0) != null&&color.equals(pane[29].getComponent(0).getBackground())) {
            searchModuleUp(39,color);
        }
    }
    private void button40(){
        Color color = pane[40].getComponent(0).getBackground();
        if (pane[41].getComponent(0) != null&&color.equals(pane[41].getComponent(0).getBackground())) {
            searchModuleRight(40,49,color);
        } else if (pane[50].getComponent(0) != null&&color.equals(pane[50].getComponent(0).getBackground())) {
            searchModuleDown(40,color);
        } else if (pane[30].getComponent(0) != null&&color.equals(pane[30].getComponent(0).getBackground())) {
            searchModuleUp(40,color);
        }
    }
    private void button41(){
        Color color = pane[41].getComponent(0).getBackground();
        if (pane[42].getComponent(0) != null&&color.equals(pane[42].getComponent(0).getBackground())) {
            searchModuleRight(41,49,color);
        } else if (pane[40].getComponent(0) != null&&color.equals(pane[40].getComponent(0).getBackground())) {
            searchModuleLeft(41,40,color);
        } else if (pane[51].getComponent(0) != null&&color.equals(pane[51].getComponent(0).getBackground())) {
            searchModuleDown(41,color);
        } else if (pane[31].getComponent(0) != null&&color.equals(pane[31].getComponent(0).getBackground())) {
            searchModuleUp(41,color);
        }
    }
    private void button42(){
        Color color = pane[42].getComponent(0).getBackground();
        if (pane[43].getComponent(0) != null&&color.equals(pane[43].getComponent(0).getBackground())) {
            searchModuleRight(42,49,color);
        } else if (pane[41].getComponent(0) != null&&color.equals(pane[41].getComponent(0).getBackground())) {
            searchModuleLeft(42,40,color);
        } else if (pane[52].getComponent(0) != null&&color.equals(pane[52].getComponent(0).getBackground())) {
            searchModuleDown(42,color);
        } else if (pane[32].getComponent(0) != null&&color.equals(pane[32].getComponent(0).getBackground())) {
            searchModuleUp(42,color);
        }
    }
    private void button43(){
        Color color = pane[43].getComponent(0).getBackground();
        if (pane[44].getComponent(0) != null&&color.equals(pane[44].getComponent(0).getBackground())) {
            searchModuleRight(43,49,color);
        } else if (pane[42].getComponent(0) != null&&color.equals(pane[42].getComponent(0).getBackground())) {
            searchModuleLeft(43,40,color);
        } else if (pane[53].getComponent(0) != null&&color.equals(pane[53].getComponent(0).getBackground())) {
            searchModuleDown(43,color);
        } else if (pane[33].getComponent(0) != null&&color.equals(pane[33].getComponent(0).getBackground())) {
            searchModuleUp(43,color);
        }
    }
    private void button44(){
        Color color = pane[44].getComponent(0).getBackground();
        if (pane[45].getComponent(0) != null&&color.equals(pane[45].getComponent(0).getBackground())) {
            searchModuleRight(44,49,color);
        } else if (pane[43].getComponent(0) != null&&color.equals(pane[43].getComponent(0).getBackground())) {
            searchModuleLeft(44,40,color);
        } else if (pane[54].getComponent(0) != null&&color.equals(pane[54].getComponent(0).getBackground())) {
            searchModuleDown(44,color);
        } else if (pane[34].getComponent(0) != null&&color.equals(pane[34].getComponent(0).getBackground())) {
            searchModuleUp(44,color);
        }
    }
    private void button45(){
        Color color = pane[45].getComponent(0).getBackground();
        if (pane[46].getComponent(0) != null&&color.equals(pane[46].getComponent(0).getBackground())) {
            searchModuleRight(45,49,color);
        } else if (pane[44].getComponent(0) != null&&color.equals(pane[44].getComponent(0).getBackground())) {
            searchModuleLeft(45,40,color);
        } else if (pane[55].getComponent(0) != null&&color.equals(pane[55].getComponent(0).getBackground())) {
            searchModuleDown(45,color);
        } else if (pane[35].getComponent(0) != null&&color.equals(pane[35].getComponent(0).getBackground())) {
            searchModuleUp(45,color);
        }
    }
    private void button46(){
        Color color = pane[46].getComponent(0).getBackground();
        if (pane[47].getComponent(0) != null&&color.equals(pane[47].getComponent(0).getBackground())) {
            searchModuleRight(46,49,color);
        } else if (pane[45].getComponent(0) != null&&color.equals(pane[45].getComponent(0).getBackground())) {
            searchModuleLeft(46,40,color);
        } else if (pane[56].getComponent(0) != null&&color.equals(pane[56].getComponent(0).getBackground())) {
            searchModuleDown(46,color);
        } else if (pane[36].getComponent(0) != null&&color.equals(pane[36].getComponent(0).getBackground())) {
            searchModuleUp(46,color);
        }
    }
    private void button47(){
        Color color = pane[47].getComponent(0).getBackground();
        if (pane[48].getComponent(0) != null&&color.equals(pane[48].getComponent(0).getBackground())) {
            searchModuleRight(47,49,color);
        } else if (pane[46].getComponent(0) != null&&color.equals(pane[46].getComponent(0).getBackground())) {
            searchModuleLeft(47,40,color);
        } else if (pane[57].getComponent(0) != null&&color.equals(pane[57].getComponent(0).getBackground())) {
            searchModuleDown(47,color);
        } else if (pane[37].getComponent(0) != null&&color.equals(pane[37].getComponent(0).getBackground())) {
            searchModuleUp(47,color);
        }
    }
    private void button48(){
        Color color = pane[48].getComponent(0).getBackground();
        if (pane[49].getComponent(0) != null&&color.equals(pane[49].getComponent(0).getBackground())) {
            searchModuleRight(48,49,color);
        } else if (pane[47].getComponent(0) != null&&color.equals(pane[47].getComponent(0).getBackground())) {
            searchModuleLeft(48,40,color);
        } else if (pane[58].getComponent(0) != null&&color.equals(pane[58].getComponent(0).getBackground())) {
            searchModuleDown(48,color);
        } else if (pane[38].getComponent(0) != null&&color.equals(pane[38].getComponent(0).getBackground())) {
            searchModuleUp(48,color);
        }
    }
    private void button49(){
        Color color = pane[49].getComponent(0).getBackground();
        if (pane[48].getComponent(0) != null&&color.equals(pane[48].getComponent(0).getBackground())) {
            searchModuleLeft(49,40,color);
        } else if (pane[59].getComponent(0) != null&&color.equals(pane[59].getComponent(0).getBackground())) {
            searchModuleDown(49,color);
        } else if (pane[39].getComponent(0) != null&&color.equals(pane[39].getComponent(0).getBackground())) {
            searchModuleUp(49,color);
        }
    }
    private void button50(){
        Color color = pane[50].getComponent(0).getBackground();
        if (pane[51].getComponent(0) != null&&color.equals(pane[51].getComponent(0).getBackground())) {
            searchModuleRight(50,59,color);
        } else if (pane[60].getComponent(0) != null&&color.equals(pane[60].getComponent(0).getBackground())) {
            searchModuleDown(50,color);
        } else if (pane[40].getComponent(0) != null&&color.equals(pane[40].getComponent(0).getBackground())) {
            searchModuleUp(50,color);
        }
    }
    private void button51(){
        Color color = pane[51].getComponent(0).getBackground();
        if (pane[52].getComponent(0) != null&&color.equals(pane[52].getComponent(0).getBackground())) {
            searchModuleRight(51,59,color);
        } else if (pane[50].getComponent(0) != null&&color.equals(pane[50].getComponent(0).getBackground())) {
            searchModuleLeft(51,50,color);
        } else if (pane[61].getComponent(0) != null&&color.equals(pane[61].getComponent(0).getBackground())) {
            searchModuleDown(51,color);
        } else if (pane[41].getComponent(0) != null&&color.equals(pane[41].getComponent(0).getBackground())) {
            searchModuleUp(51,color);
        }
    }
    private void button52(){
        Color color = pane[52].getComponent(0).getBackground();
        if (pane[53].getComponent(0) != null&&color.equals(pane[53].getComponent(0).getBackground())) {
            searchModuleRight(52,59,color);
        } else if (pane[51].getComponent(0) != null&&color.equals(pane[51].getComponent(0).getBackground())) {
            searchModuleLeft(52,50,color);
        } else if (pane[62].getComponent(0) != null&&color.equals(pane[62].getComponent(0).getBackground())) {
            searchModuleDown(52,color);
        } else if (pane[42].getComponent(0) != null&&color.equals(pane[42].getComponent(0).getBackground())) {
            searchModuleUp(52,color);
        }
    }
    private void button53(){
        Color color = pane[53].getComponent(0).getBackground();
        if (pane[54].getComponent(0) != null&&color.equals(pane[54].getComponent(0).getBackground())) {
            searchModuleRight(53,59,color);
        } else if (pane[52].getComponent(0) != null&&color.equals(pane[52].getComponent(0).getBackground())) {
            searchModuleLeft(53,50,color);
        } else if (pane[63].getComponent(0) != null&&color.equals(pane[63].getComponent(0).getBackground())) {
            searchModuleDown(53,color);
        } else if (pane[43].getComponent(0) != null&&color.equals(pane[43].getComponent(0).getBackground())) {
            searchModuleUp(53,color);
        }
    }
    private void button54(){
        Color color = pane[54].getComponent(0).getBackground();
        if (pane[55].getComponent(0) != null&&color.equals(pane[55].getComponent(0).getBackground())) {
            searchModuleRight(54,59,color);
        } else if (pane[53].getComponent(0) != null&&color.equals(pane[53].getComponent(0).getBackground())) {
            searchModuleLeft(54,50,color);
        } else if (pane[64].getComponent(0) != null&&color.equals(pane[64].getComponent(0).getBackground())) {
            searchModuleDown(54,color);
        } else if (pane[44].getComponent(0) != null&&color.equals(pane[44].getComponent(0).getBackground())) {
            searchModuleUp(54,color);
        }
    }
    private void button55(){
        Color color = pane[55].getComponent(0).getBackground();
        if (pane[56].getComponent(0) != null&&color.equals(pane[56].getComponent(0).getBackground())) {
            searchModuleRight(55,59,color);
        } else if (pane[54].getComponent(0) != null&&color.equals(pane[54].getComponent(0).getBackground())) {
            searchModuleLeft(55,50,color);
        } else if (pane[65].getComponent(0) != null&&color.equals(pane[65].getComponent(0).getBackground())) {
            searchModuleDown(55,color);
        } else if (pane[45].getComponent(0) != null&&color.equals(pane[45].getComponent(0).getBackground())) {
            searchModuleUp(55,color);
        }
    }
    private void button56(){
        Color color = pane[56].getComponent(0).getBackground();
        if (pane[57].getComponent(0) != null&&color.equals(pane[57].getComponent(0).getBackground())) {
            searchModuleRight(56,59,color);
        } else if (pane[55].getComponent(0) != null&&color.equals(pane[55].getComponent(0).getBackground())) {
            searchModuleLeft(56,50,color);
        } else if (pane[66].getComponent(0) != null&&color.equals(pane[66].getComponent(0).getBackground())) {
            searchModuleDown(56,color);
        } else if (pane[46].getComponent(0) != null&&color.equals(pane[46].getComponent(0).getBackground())) {
            searchModuleUp(56,color);
        }
    }
    private void button57(){
        Color color = pane[57].getComponent(0).getBackground();
        if (pane[58].getComponent(0) != null&&color.equals(pane[58].getComponent(0).getBackground())) {
            searchModuleRight(57,59,color);
        } else if (pane[56].getComponent(0) != null&&color.equals(pane[56].getComponent(0).getBackground())) {
            searchModuleLeft(57,50,color);
        } else if (pane[67].getComponent(0) != null&&color.equals(pane[67].getComponent(0).getBackground())) {
            searchModuleDown(57,color);
        } else if (pane[47].getComponent(0) != null&&color.equals(pane[47].getComponent(0).getBackground())) {
            searchModuleUp(57,color);
        }
    }
    private void button58(){
        Color color = pane[58].getComponent(0).getBackground();
        if (pane[59].getComponent(0) != null&&color.equals(pane[59].getComponent(0).getBackground())) {
            searchModuleRight(58,59,color);
        } else if (pane[57].getComponent(0) != null&&color.equals(pane[57].getComponent(0).getBackground())) {
            searchModuleLeft(58,50,color);
        } else if (pane[68].getComponent(0) != null&&color.equals(pane[68].getComponent(0).getBackground())) {
            searchModuleDown(58,color);
        } else if (pane[48].getComponent(0) != null&&color.equals(pane[48].getComponent(0).getBackground())) {
            searchModuleUp(58,color);
        }
    }
    private void button59(){
        Color color = pane[59].getComponent(0).getBackground();
        if (pane[58].getComponent(0) != null&&color.equals(pane[58].getComponent(0).getBackground())) {
            searchModuleLeft(59,50,color);
        } else if (pane[69].getComponent(0) != null&&color.equals(pane[69].getComponent(0).getBackground())) {
            searchModuleDown(59,color);
        } else if (pane[49].getComponent(0) != null&&color.equals(pane[49].getComponent(0).getBackground())) {
            searchModuleUp(59,color);
        }
    }
    private void button60(){
        Color color = pane[60].getComponent(0).getBackground();
        if (pane[61].getComponent(0) != null&&color.equals(pane[61].getComponent(0).getBackground())) {
            searchModuleRight(60,69,color);
        } else if (pane[70].getComponent(0) != null&&color.equals(pane[70].getComponent(0).getBackground())) {
            searchModuleDown(60,color);
        } else if (pane[50].getComponent(0) != null&&color.equals(pane[50].getComponent(0).getBackground())) {
            searchModuleUp(60,color);
        }
    }
    private void button61(){
        Color color = pane[61].getComponent(0).getBackground();
        if (pane[62].getComponent(0) != null&&color.equals(pane[62].getComponent(0).getBackground())) {
            searchModuleRight(61,69,color);
        } else if (pane[60].getComponent(0) != null&&color.equals(pane[60].getComponent(0).getBackground())) {
            searchModuleLeft(61,60,color);
        } else if (pane[71].getComponent(0) != null&&color.equals(pane[71].getComponent(0).getBackground())) {
            searchModuleDown(61,color);
        } else if (pane[51].getComponent(0) != null&&color.equals(pane[51].getComponent(0).getBackground())) {
            searchModuleUp(61,color);
        }
    }
    private void button62(){
        Color color = pane[62].getComponent(0).getBackground();
        if (pane[63].getComponent(0) != null&&color.equals(pane[63].getComponent(0).getBackground())) {
            searchModuleRight(62,69,color);
        } else if (pane[61].getComponent(0) != null&&color.equals(pane[61].getComponent(0).getBackground())) {
            searchModuleLeft(62,60,color);
        } else if (pane[72].getComponent(0) != null&&color.equals(pane[72].getComponent(0).getBackground())) {
            searchModuleDown(62,color);
        } else if (pane[52].getComponent(0) != null&&color.equals(pane[52].getComponent(0).getBackground())) {
            searchModuleUp(62,color);
        }
    }
    private void button63(){
        Color color = pane[63].getComponent(0).getBackground();
        if (pane[64].getComponent(0) != null&&color.equals(pane[64].getComponent(0).getBackground())) {
            searchModuleRight(63,69,color);
        } else if (pane[62].getComponent(0) != null&&color.equals(pane[62].getComponent(0).getBackground())) {
            searchModuleLeft(63,60,color);
        } else if (pane[73].getComponent(0) != null&&color.equals(pane[73].getComponent(0).getBackground())) {
            searchModuleDown(63,color);
        } else if (pane[53].getComponent(0) != null&&color.equals(pane[53].getComponent(0).getBackground())) {
            searchModuleUp(63,color);
        }
    }
    private void button64(){
        Color color = pane[64].getComponent(0).getBackground();
        if (pane[65].getComponent(0) != null&&color.equals(pane[65].getComponent(0).getBackground())) {
            searchModuleRight(64,69,color);
        } else if (pane[63].getComponent(0) != null&&color.equals(pane[63].getComponent(0).getBackground())) {
            searchModuleLeft(64,60,color);
        } else if (pane[74].getComponent(0) != null&&color.equals(pane[74].getComponent(0).getBackground())) {
            searchModuleDown(64,color);
        } else if (pane[54].getComponent(0) != null&&color.equals(pane[54].getComponent(0).getBackground())) {
            searchModuleUp(64,color);
        }
    }
    private void button65(){
        Color color = pane[65].getComponent(0).getBackground();
        if (pane[66].getComponent(0) != null&&color.equals(pane[66].getComponent(0).getBackground())) {
            searchModuleRight(65,69,color);
        } else if (pane[64].getComponent(0) != null&&color.equals(pane[64].getComponent(0).getBackground())) {
            searchModuleLeft(65,60,color);
        } else if (pane[75].getComponent(0) != null&&color.equals(pane[75].getComponent(0).getBackground())) {
            searchModuleDown(65,color);
        } else if (pane[55].getComponent(0) != null&&color.equals(pane[55].getComponent(0).getBackground())) {
            searchModuleUp(65,color);
        }
    }
    private void button66(){
        Color color = pane[66].getComponent(0).getBackground();
        if (pane[67].getComponent(0) != null&&color.equals(pane[67].getComponent(0).getBackground())) {
            searchModuleRight(66,69,color);
        } else if (pane[65].getComponent(0) != null&&color.equals(pane[65].getComponent(0).getBackground())) {
            searchModuleLeft(66,60,color);
        } else if (pane[76].getComponent(0) != null&&color.equals(pane[76].getComponent(0).getBackground())) {
            searchModuleDown(66,color);
        } else if (pane[56].getComponent(0) != null&&color.equals(pane[56].getComponent(0).getBackground())) {
            searchModuleUp(66,color);
        }
    }
    private void button67(){
        Color color = pane[67].getComponent(0).getBackground();
        if (pane[68].getComponent(0) != null&&color.equals(pane[68].getComponent(0).getBackground())) {
            searchModuleRight(67,69,color);
        } else if (pane[66].getComponent(0) != null&&color.equals(pane[66].getComponent(0).getBackground())) {
            searchModuleLeft(67,60,color);
        } else if (pane[77].getComponent(0) != null&&color.equals(pane[77].getComponent(0).getBackground())) {
            searchModuleDown(67,color);
        } else if (pane[57].getComponent(0) != null&&color.equals(pane[57].getComponent(0).getBackground())) {
            searchModuleUp(67,color);
        }
    }
    private void button68(){
        Color color = pane[68].getComponent(0).getBackground();
        if (pane[69].getComponent(0) != null&&color.equals(pane[69].getComponent(0).getBackground())) {
            searchModuleRight(68,69,color);
        } else if (pane[67].getComponent(0) != null&&color.equals(pane[67].getComponent(0).getBackground())) {
            searchModuleLeft(68,60,color);
        } else if (pane[78].getComponent(0) != null&&color.equals(pane[78].getComponent(0).getBackground())) {
            searchModuleDown(68,color);
        } else if (pane[58].getComponent(0) != null&&color.equals(pane[58].getComponent(0).getBackground())) {
            searchModuleUp(68,color);
        }
    }
    private void button69(){
        Color color = pane[69].getComponent(0).getBackground();
        if (pane[68].getComponent(0) != null&&color.equals(pane[68].getComponent(0).getBackground())) {
            searchModuleLeft(69,60,color);
        } else if (pane[79].getComponent(0) != null&&color.equals(pane[79].getComponent(0).getBackground())) {
            searchModuleDown(69,color);
        } else if (pane[59].getComponent(0) != null&&color.equals(pane[59].getComponent(0).getBackground())) {
            searchModuleUp(69,color);
        }
    }
    private void button70(){
        Color color = pane[70].getComponent(0).getBackground();
        if (pane[71].getComponent(0) != null&&color.equals(pane[71].getComponent(0).getBackground())) {
            searchModuleRight(70,79,color);
        } else if (pane[80].getComponent(0) != null&&color.equals(pane[80].getComponent(0).getBackground())) {
            searchModuleDown(70,color);
        } else if (pane[60].getComponent(0) != null&&color.equals(pane[60].getComponent(0).getBackground())) {
            searchModuleUp(70,color);
        }
    }
    private void button71(){
        Color color = pane[71].getComponent(0).getBackground();
        if (pane[72].getComponent(0) != null&&color.equals(pane[72].getComponent(0).getBackground())) {
            searchModuleRight(71,79,color);
        } else if (pane[70].getComponent(0) != null&&color.equals(pane[70].getComponent(0).getBackground())) {
            searchModuleLeft(71,70,color);
        } else if (pane[81].getComponent(0) != null&&color.equals(pane[81].getComponent(0).getBackground())) {
            searchModuleDown(71,color);
        } else if (pane[61].getComponent(0) != null&&color.equals(pane[61].getComponent(0).getBackground())) {
            searchModuleUp(71,color);
        }
    }
    private void button72(){
        Color color = pane[72].getComponent(0).getBackground();
        if (pane[73].getComponent(0) != null&&color.equals(pane[73].getComponent(0).getBackground())) {
            searchModuleRight(72,79,color);
        } else if (pane[71].getComponent(0) != null&&color.equals(pane[71].getComponent(0).getBackground())) {
            searchModuleLeft(72,70,color);
        } else if (pane[82].getComponent(0) != null&&color.equals(pane[82].getComponent(0).getBackground())) {
            searchModuleDown(72,color);
        } else if (pane[62].getComponent(0) != null&&color.equals(pane[62].getComponent(0).getBackground())) {
            searchModuleUp(72,color);
        }
    }
    private void button73(){
        Color color = pane[73].getComponent(0).getBackground();
        if (pane[74].getComponent(0) != null&&color.equals(pane[74].getComponent(0).getBackground())) {
            searchModuleRight(73,79,color);
        } else if (pane[72].getComponent(0) != null&&color.equals(pane[72].getComponent(0).getBackground())) {
            searchModuleLeft(73,70,color);
        } else if (pane[83].getComponent(0) != null&&color.equals(pane[83].getComponent(0).getBackground())) {
            searchModuleDown(73,color);
        } else if (pane[63].getComponent(0) != null&&color.equals(pane[63].getComponent(0).getBackground())) {
            searchModuleUp(73,color);
        }
    }
    private void button74(){
        Color color = pane[74].getComponent(0).getBackground();
        if (pane[75].getComponent(0) != null&&color.equals(pane[75].getComponent(0).getBackground())) {
            searchModuleRight(74,79,color);
        } else if (pane[73].getComponent(0) != null&&color.equals(pane[73].getComponent(0).getBackground())) {
            searchModuleLeft(74,70,color);
        } else if (pane[84].getComponent(0) != null&&color.equals(pane[84].getComponent(0).getBackground())) {
            searchModuleDown(74,color);
        } else if (pane[64].getComponent(0) != null&&color.equals(pane[64].getComponent(0).getBackground())) {
            searchModuleUp(74,color);
        }
    }
    private void button75(){
        Color color = pane[75].getComponent(0).getBackground();
        if (pane[76].getComponent(0) != null&&color.equals(pane[76].getComponent(0).getBackground())) {
            searchModuleRight(75,79,color);
        } else if (pane[74].getComponent(0) != null&&color.equals(pane[74].getComponent(0).getBackground())) {
            searchModuleLeft(75,70,color);
        } else if (pane[85].getComponent(0) != null&&color.equals(pane[85].getComponent(0).getBackground())) {
            searchModuleDown(75,color);
        } else if (pane[65].getComponent(0) != null&&color.equals(pane[65].getComponent(0).getBackground())) {
            searchModuleUp(75,color);
        }
    }
    private void button76(){
        Color color = pane[76].getComponent(0).getBackground();
        if (pane[77].getComponent(0) != null&&color.equals(pane[77].getComponent(0).getBackground())) {
            searchModuleRight(76,79,color);
        } else if (pane[75].getComponent(0) != null&&color.equals(pane[75].getComponent(0).getBackground())) {
            searchModuleLeft(76,70,color);
        } else if (pane[86].getComponent(0) != null&&color.equals(pane[86].getComponent(0).getBackground())) {
            searchModuleDown(76,color);
        } else if (pane[66].getComponent(0) != null&&color.equals(pane[66].getComponent(0).getBackground())) {
            searchModuleUp(76,color);
        }
    }
    private void button77(){
        Color color = pane[77].getComponent(0).getBackground();
        if (pane[78].getComponent(0) != null&&color.equals(pane[78].getComponent(0).getBackground())) {
            searchModuleRight(77,79,color);
        } else if (pane[76].getComponent(0) != null&&color.equals(pane[76].getComponent(0).getBackground())) {
            searchModuleLeft(77,70,color);
        } else if (pane[87].getComponent(0) != null&&color.equals(pane[87].getComponent(0).getBackground())) {
            searchModuleDown(77,color);
        } else if (pane[67].getComponent(0) != null&&color.equals(pane[67].getComponent(0).getBackground())) {
            searchModuleUp(77,color);
        }
    }
    private void button78(){
        Color color = pane[78].getComponent(0).getBackground();
        if (pane[79].getComponent(0) != null&&color.equals(pane[79].getComponent(0).getBackground())) {
            searchModuleRight(78,79,color);
        } else if (pane[77].getComponent(0) != null&&color.equals(pane[77].getComponent(0).getBackground())) {
            searchModuleLeft(78,70,color);
        } else if (pane[88].getComponent(0) != null&&color.equals(pane[88].getComponent(0).getBackground())) {
            searchModuleDown(78,color);
        } else if (pane[68].getComponent(0) != null&&color.equals(pane[68].getComponent(0).getBackground())) {
            searchModuleUp(78,color);
        }
    }
    private void button79(){
        Color color = pane[79].getComponent(0).getBackground();
        if (pane[78].getComponent(0) != null&&color.equals(pane[78].getComponent(0).getBackground())) {
            searchModuleLeft(79,70,color);
        } else if (pane[89].getComponent(0) != null&&color.equals(pane[89].getComponent(0).getBackground())) {
            searchModuleDown(79,color);
        } else if (pane[69].getComponent(0) != null&&color.equals(pane[69].getComponent(0).getBackground())) {
            searchModuleUp(79,color);
        }
    }
    private void button80(){
        Color color = pane[80].getComponent(0).getBackground();
        if (pane[81].getComponent(0) != null&&color.equals(pane[81].getComponent(0).getBackground())) {
            searchModuleRight(80,89,color);
        } else if (pane[90].getComponent(0) != null&&color.equals(pane[90].getComponent(0).getBackground())) {
            searchModuleDown(80,color);
        } else if (pane[70].getComponent(0) != null&&color.equals(pane[70].getComponent(0).getBackground())) {
            searchModuleUp(80,color);
        }
    }
    private void button81(){
        Color color = pane[81].getComponent(0).getBackground();
        if (pane[82].getComponent(0) != null&&color.equals(pane[82].getComponent(0).getBackground())) {
            searchModuleRight(81,89,color);
        } else if (pane[80].getComponent(0) != null&&color.equals(pane[80].getComponent(0).getBackground())) {
            searchModuleLeft(81,80,color);
        } else if (pane[91].getComponent(0) != null&&color.equals(pane[91].getComponent(0).getBackground())) {
            searchModuleDown(81,color);
        } else if (pane[71].getComponent(0) != null&&color.equals(pane[71].getComponent(0).getBackground())) {
            searchModuleUp(81,color);
        }
    }
    private void button82(){
        Color color = pane[82].getComponent(0).getBackground();
        if (pane[83].getComponent(0) != null&&color.equals(pane[83].getComponent(0).getBackground())) {
            searchModuleRight(82,89,color);
        } else if (pane[81].getComponent(0) != null&&color.equals(pane[81].getComponent(0).getBackground())) {
            searchModuleLeft(82,80,color);
        } else if (pane[92].getComponent(0) != null&&color.equals(pane[92].getComponent(0).getBackground())) {
            searchModuleDown(82,color);
        } else if (pane[72].getComponent(0) != null&&color.equals(pane[72].getComponent(0).getBackground())) {
            searchModuleUp(82,color);
        }
    }
    private void button83(){
        Color color = pane[83].getComponent(0).getBackground();
        if (pane[84].getComponent(0) != null&&color.equals(pane[84].getComponent(0).getBackground())) {
            searchModuleRight(83,89,color);
        } else if (pane[82].getComponent(0) != null&&color.equals(pane[82].getComponent(0).getBackground())) {
            searchModuleLeft(83,80,color);
        } else if (pane[93].getComponent(0) != null&&color.equals(pane[93].getComponent(0).getBackground())) {
            searchModuleDown(83,color);
        } else if (pane[73].getComponent(0) != null&&color.equals(pane[73].getComponent(0).getBackground())) {
            searchModuleUp(83,color);
        }
    }
    private void button84(){
        Color color = pane[84].getComponent(0).getBackground();
        if (pane[85].getComponent(0) != null&&color.equals(pane[85].getComponent(0).getBackground())) {
            searchModuleRight(84,89,color);
        } else if (pane[83].getComponent(0) != null&&color.equals(pane[83].getComponent(0).getBackground())) {
            searchModuleLeft(84,80,color);
        } else if (pane[94].getComponent(0) != null&&color.equals(pane[94].getComponent(0).getBackground())) {
            searchModuleDown(84,color);
        } else if (pane[74].getComponent(0) != null&&color.equals(pane[74].getComponent(0).getBackground())) {
            searchModuleUp(84,color);
        }
    }
    private void button85(){
        Color color = pane[85].getComponent(0).getBackground();
        if (pane[86].getComponent(0) != null&&color.equals(pane[86].getComponent(0).getBackground())) {
            searchModuleRight(85,89,color);
        } else if (pane[84].getComponent(0) != null&&color.equals(pane[84].getComponent(0).getBackground())) {
            searchModuleLeft(85,80,color);
        } else if (pane[95].getComponent(0) != null&&color.equals(pane[95].getComponent(0).getBackground())) {
            searchModuleDown(85,color);
        } else if (pane[75].getComponent(0) != null&&color.equals(pane[75].getComponent(0).getBackground())) {
            searchModuleUp(85,color);
        }
    }
    private void button86(){
        Color color = pane[86].getComponent(0).getBackground();
        if (pane[87].getComponent(0) != null&&color.equals(pane[87].getComponent(0).getBackground())) {
            searchModuleRight(86,89,color);
        } else if (pane[85].getComponent(0) != null&&color.equals(pane[85].getComponent(0).getBackground())) {
            searchModuleLeft(86,80,color);
        } else if (pane[96].getComponent(0) != null&&color.equals(pane[96].getComponent(0).getBackground())) {
            searchModuleDown(86,color);
        } else if (pane[76].getComponent(0) != null&&color.equals(pane[76].getComponent(0).getBackground())) {
            searchModuleUp(86,color);
        }
    }
    private void button87(){
        Color color = pane[87].getComponent(0).getBackground();
        if (pane[88].getComponent(0) != null&&color.equals(pane[88].getComponent(0).getBackground())) {
            searchModuleRight(87,89,color);
        } else if (pane[86].getComponent(0) != null&&color.equals(pane[86].getComponent(0).getBackground())) {
            searchModuleLeft(87,80,color);
        } else if (pane[97].getComponent(0) != null&&color.equals(pane[97].getComponent(0).getBackground())) {
            searchModuleDown(87,color);
        } else if (pane[77].getComponent(0) != null&&color.equals(pane[77].getComponent(0).getBackground())) {
            searchModuleUp(87,color);
        }
    }
    private void button88(){
        Color color = pane[88].getComponent(0).getBackground();
        if (pane[89].getComponent(0) != null&&color.equals(pane[89].getComponent(0).getBackground())) {
            searchModuleRight(88,89,color);
        } else if (pane[87].getComponent(0) != null&&color.equals(pane[87].getComponent(0).getBackground())) {
            searchModuleLeft(88,80,color);
        } else if (pane[98].getComponent(0) != null&&color.equals(pane[98].getComponent(0).getBackground())) {
            searchModuleDown(88,color);
        } else if (pane[78].getComponent(0) != null&&color.equals(pane[78].getComponent(0).getBackground())) {
            searchModuleUp(88,color);
        }
    }
    private void button89(){
        Color color = pane[89].getComponent(0).getBackground();
        if (pane[88].getComponent(0) != null&&color.equals(pane[88].getComponent(0).getBackground())) {
            searchModuleLeft(89,80,color);
        } else if (pane[99].getComponent(0) != null&&color.equals(pane[99].getComponent(0).getBackground())) {
            searchModuleDown(89,color);
        } else if (pane[79].getComponent(0) != null&&color.equals(pane[79].getComponent(0).getBackground())) {
            searchModuleUp(89,color);
        }
    }
    private void button90(){
        Color color = pane[90].getComponent(0).getBackground();
        if (pane[91].getComponent(0) != null&&color.equals(pane[91].getComponent(0).getBackground())) {
            searchModuleRight(90,99,color);
        } else if (pane[100].getComponent(0) != null&&color.equals(pane[100].getComponent(0).getBackground())) {
            searchModuleDown(90,color);
        } else if (pane[80].getComponent(0) != null&&color.equals(pane[80].getComponent(0).getBackground())) {
            searchModuleUp(90,color);
        }
    }
    private void button91(){
        Color color = pane[91].getComponent(0).getBackground();
        if (pane[92].getComponent(0) != null&&color.equals(pane[92].getComponent(0).getBackground())) {
            searchModuleRight(91,99,color);
        } else if (pane[90].getComponent(0) != null&&color.equals(pane[90].getComponent(0).getBackground())) {
            searchModuleLeft(91,90,color);
        } else if (pane[101].getComponent(0) != null&&color.equals(pane[101].getComponent(0).getBackground())) {
            searchModuleDown(91,color);
        } else if (pane[81].getComponent(0) != null&&color.equals(pane[81].getComponent(0).getBackground())) {
            searchModuleUp(91,color);
        }
    }
    private void button92(){
        Color color = pane[92].getComponent(0).getBackground();
        if (pane[93].getComponent(0) != null&&color.equals(pane[93].getComponent(0).getBackground())) {
            searchModuleRight(92,99,color);
        } else if (pane[91].getComponent(0) != null&&color.equals(pane[91].getComponent(0).getBackground())) {
            searchModuleLeft(92,90,color);
        } else if (pane[102].getComponent(0) != null&&color.equals(pane[102].getComponent(0).getBackground())) {
            searchModuleDown(92,color);
        } else if (pane[82].getComponent(0) != null&&color.equals(pane[82].getComponent(0).getBackground())) {
            searchModuleUp(92,color);
        }
    }
    private void button93(){
        Color color = pane[93].getComponent(0).getBackground();
        if (pane[94].getComponent(0) != null&&color.equals(pane[94].getComponent(0).getBackground())) {
            searchModuleRight(93,99,color);
        } else if (pane[92].getComponent(0) != null&&color.equals(pane[92].getComponent(0).getBackground())) {
            searchModuleLeft(93,90,color);
        } else if (pane[103].getComponent(0) != null&&color.equals(pane[103].getComponent(0).getBackground())) {
            searchModuleDown(93,color);
        } else if (pane[83].getComponent(0) != null&&color.equals(pane[83].getComponent(0).getBackground())) {
            searchModuleUp(93,color);
        }
    }
    private void button94(){
        Color color = pane[94].getComponent(0).getBackground();
        if (pane[95].getComponent(0) != null&&color.equals(pane[95].getComponent(0).getBackground())) {
            searchModuleRight(94,99,color);
        } else if (pane[93].getComponent(0) != null&&color.equals(pane[93].getComponent(0).getBackground())) {
            searchModuleLeft(94,90,color);
        } else if (pane[104].getComponent(0) != null&&color.equals(pane[104].getComponent(0).getBackground())) {
            searchModuleDown(94,color);
        } else if (pane[84].getComponent(0) != null&&color.equals(pane[84].getComponent(0).getBackground())) {
            searchModuleUp(94,color);
        }
    }
    private void button95(){
        Color color = pane[95].getComponent(0).getBackground();
        if (pane[96].getComponent(0) != null&&color.equals(pane[96].getComponent(0).getBackground())) {
            searchModuleRight(95,99,color);
        } else if (pane[94].getComponent(0) != null&&color.equals(pane[94].getComponent(0).getBackground())) {
            searchModuleLeft(95,90,color);
        } else if (pane[105].getComponent(0) != null&&color.equals(pane[105].getComponent(0).getBackground())) {
            searchModuleDown(95,color);
        } else if (pane[85].getComponent(0) != null&&color.equals(pane[85].getComponent(0).getBackground())) {
            searchModuleUp(95,color);
        }
    }
    private void button96(){
        Color color = pane[96].getComponent(0).getBackground();
        if (pane[97].getComponent(0) != null&&color.equals(pane[97].getComponent(0).getBackground())) {
            searchModuleRight(96,99,color);
        } else if (pane[95].getComponent(0) != null&&color.equals(pane[95].getComponent(0).getBackground())) {
            searchModuleLeft(96,90,color);
        } else if (pane[106].getComponent(0) != null&&color.equals(pane[106].getComponent(0).getBackground())) {
            searchModuleDown(96,color);
        } else if (pane[86].getComponent(0) != null&&color.equals(pane[86].getComponent(0).getBackground())) {
            searchModuleUp(96,color);
        }
    }
    private void button97(){
        Color color = pane[97].getComponent(0).getBackground();
        if (pane[98].getComponent(0) != null&&color.equals(pane[98].getComponent(0).getBackground())) {
            searchModuleRight(97,99,color);
        } else if (pane[96].getComponent(0) != null&&color.equals(pane[96].getComponent(0).getBackground())) {
            searchModuleLeft(97,90,color);
        } else if (pane[107].getComponent(0) != null&&color.equals(pane[107].getComponent(0).getBackground())) {
            searchModuleDown(97,color);
        } else if (pane[87].getComponent(0) != null&&color.equals(pane[87].getComponent(0).getBackground())) {
            searchModuleUp(97,color);
        }
    }
    private void button98(){
        Color color = pane[98].getComponent(0).getBackground();
        if (pane[99].getComponent(0) != null&&color.equals(pane[99].getComponent(0).getBackground())) {
            searchModuleRight(98,99,color);
        } else if (pane[97].getComponent(0) != null&&color.equals(pane[97].getComponent(0).getBackground())) {
            searchModuleLeft(98,90,color);
        } else if (pane[108].getComponent(0) != null&&color.equals(pane[108].getComponent(0).getBackground())) {
            searchModuleDown(98,color);
        } else if (pane[88].getComponent(0) != null&&color.equals(pane[88].getComponent(0).getBackground())) {
            searchModuleUp(98,color);
        }
    }
    private void button99(){
        Color color = pane[99].getComponent(0).getBackground();
        if (pane[98].getComponent(0) != null&&color.equals(pane[98].getComponent(0).getBackground())) {
            searchModuleLeft(99,90,color);
        } else if (pane[109].getComponent(0) != null&&color.equals(pane[109].getComponent(0).getBackground())) {
            searchModuleDown(99,color);
        } else if (pane[89].getComponent(0) != null&&color.equals(pane[89].getComponent(0).getBackground())) {
            searchModuleUp(99,color);
        }
    }
    private void button100(){
        Color color = pane[100].getComponent(0).getBackground();
        if (pane[101].getComponent(0) != null&&color.equals(pane[101].getComponent(0).getBackground())){
            searchModuleRight(100,109,color);
        } else if (pane[110].getComponent(0) != null&&color.equals(pane[110].getComponent(0).getBackground())){
            searchModuleDown(100,color);
        }else if (pane[90].getComponent(0) != null&&color.equals(pane[90].getComponent(0).getBackground())) {
            searchModuleUp(100,color);
        }
    }
    private void button101(){
        Color color = pane[101].getComponent(0).getBackground();
        if (pane[102].getComponent(0) != null&&color.equals(pane[102].getComponent(0).getBackground())) {
            searchModuleRight(101,109,color);
        } else if (pane[100].getComponent(0) != null&&color.equals(pane[100].getComponent(0).getBackground())) {
            searchModuleLeft(101,100,color);
        } else if (pane[111].getComponent(0) != null&&color.equals(pane[111].getComponent(0).getBackground())) {
            searchModuleDown(101,color);
        }else if (pane[91].getComponent(0) != null&&color.equals(pane[91].getComponent(0).getBackground())) {
            searchModuleUp(101,color);
        }
    }
    private void button102(){
        Color color = pane[102].getComponent(0).getBackground();
        if (pane[103].getComponent(0) != null&&color.equals(pane[103].getComponent(0).getBackground())) {
            searchModuleRight(102,109,color);
        } else if (pane[101].getComponent(0) != null&&color.equals(pane[101].getComponent(0).getBackground())) {
            searchModuleLeft(102,100,color);
        } else if (pane[112].getComponent(0) != null&&color.equals(pane[112].getComponent(0).getBackground())) {
            searchModuleDown(102,color);
        }else if (pane[92].getComponent(0) != null&&color.equals(pane[92].getComponent(0).getBackground())) {
            searchModuleUp(102,color);
        }
    }
    private void button103(){
        Color color = pane[103].getComponent(0).getBackground();
        if (pane[104].getComponent(0) != null&&color.equals(pane[104].getComponent(0).getBackground())) {
            searchModuleRight(103,109,color);
        } else if (pane[102].getComponent(0) != null&&color.equals(pane[102].getComponent(0).getBackground())) {
            searchModuleLeft(103,100,color);
        } else if (pane[113].getComponent(0) != null&&color.equals(pane[113].getComponent(0).getBackground())) {
            searchModuleDown(103,color);
        }else if (pane[93].getComponent(0) != null&&color.equals(pane[93].getComponent(0).getBackground())) {
            searchModuleUp(103,color);
        }
    }
    private void button104(){
        Color color = pane[104].getComponent(0).getBackground();
        if (pane[105].getComponent(0) != null&&color.equals(pane[105].getComponent(0).getBackground())) {
            searchModuleRight(104,109,color);
        } else if (pane[103].getComponent(0) != null&&color.equals(pane[103].getComponent(0).getBackground())) {
            searchModuleLeft(104,100,color);
        } else if (pane[114].getComponent(0) != null&&color.equals(pane[114].getComponent(0).getBackground())) {
            searchModuleDown(104,color);
        }else if (pane[94].getComponent(0) != null&&color.equals(pane[94].getComponent(0).getBackground())) {
            searchModuleUp(104,color);
        }
    }
    private void button105(){
        Color color = pane[105].getComponent(0).getBackground();
        if (pane[106].getComponent(0) != null&&color.equals(pane[106].getComponent(0).getBackground())) {
            searchModuleRight(105,109,color);
        } else if (pane[104].getComponent(0) != null&&color.equals(pane[104].getComponent(0).getBackground())) {
            searchModuleLeft(105,100,color);
        } else if (pane[115].getComponent(0) != null&&color.equals(pane[115].getComponent(0).getBackground())) {
            searchModuleDown(105,color);
        }else if (pane[95].getComponent(0) != null&&color.equals(pane[95].getComponent(0).getBackground())) {
            searchModuleUp(105,color);
        }
    }
    private void button106(){
        Color color = pane[106].getComponent(0).getBackground();
        if (pane[107].getComponent(0) != null&&color.equals(pane[107].getComponent(0).getBackground())) {
            searchModuleRight(106,109,color);
        } else if (pane[105].getComponent(0) != null&&color.equals(pane[105].getComponent(0).getBackground())) {
            searchModuleLeft(106,100,color);
        } else if (pane[116].getComponent(0) != null&&color.equals(pane[116].getComponent(0).getBackground())) {
            searchModuleDown(106,color);
        }else if (pane[96].getComponent(0) != null&&color.equals(pane[96].getComponent(0).getBackground())) {
            searchModuleUp(106,color);
        }
    }
    private void button107(){
        Color color = pane[107].getComponent(0).getBackground();
        if (pane[108].getComponent(0) != null&&color.equals(pane[108].getComponent(0).getBackground())) {
            searchModuleRight(107,109,color);
        } else if (pane[106].getComponent(0) != null&&color.equals(pane[106].getComponent(0).getBackground())) {
            searchModuleLeft(107,100,color);
        } else if (pane[117].getComponent(0) != null&&color.equals(pane[117].getComponent(0).getBackground())) {
            searchModuleDown(107,color);
        }else if (pane[97].getComponent(0) != null&&color.equals(pane[97].getComponent(0).getBackground())) {
            searchModuleUp(107,color);
        }
    }
    private void button108(){
        Color color = pane[108].getComponent(0).getBackground();
        if (pane[109].getComponent(0) != null&&color.equals(pane[109].getComponent(0).getBackground())) {
            searchModuleRight(108,109,color);
        } else if (pane[107].getComponent(0) != null&&color.equals(pane[107].getComponent(0).getBackground())) {
            searchModuleLeft(108,100,color);
        } else if (pane[118].getComponent(0) != null&&color.equals(pane[118].getComponent(0).getBackground())) {
            searchModuleDown(108,color);
        }else if (pane[98].getComponent(0) != null&&color.equals(pane[98].getComponent(0).getBackground())) {
            searchModuleUp(108,color);
        }
    }
    private void button109(){
        Color color = pane[109].getComponent(0).getBackground();
        if (pane[108].getComponent(0) != null&&color.equals(pane[108].getComponent(0).getBackground())) {
            searchModuleLeft(109,100,color);
        } else if (pane[119].getComponent(0) != null&&color.equals(pane[119].getComponent(0).getBackground())) {
            searchModuleDown(109,color);
        }else if (pane[99].getComponent(0) != null&&color.equals(pane[99].getComponent(0).getBackground())) {
            searchModuleUp(109,color);
        }
    }
    private void button110(){
        Color color = pane[110].getComponent(0).getBackground();
        if (pane[111].getComponent(0) != null&&color.equals(pane[111].getComponent(0).getBackground())) {
            searchModuleRight(110,119,color);
        } else if (pane[120].getComponent(0) != null&&color.equals(pane[120].getComponent(0).getBackground())) {
            searchModuleDown(110,color);
        } else if (pane[100].getComponent(0) != null&&color.equals(pane[100].getComponent(0).getBackground())) {
            searchModuleUp(110,color);
        }
    }
    private void button111(){
        Color color = pane[111].getComponent(0).getBackground();
        if (pane[112].getComponent(0) != null&&color.equals(pane[112].getComponent(0).getBackground())) {
            searchModuleRight(111,119,color);
        } else if (pane[110].getComponent(0) != null&&color.equals(pane[110].getComponent(0).getBackground())) {
            searchModuleLeft(111,110,color);
        } else if (pane[121].getComponent(0) != null&&color.equals(pane[121].getComponent(0).getBackground())) {
            searchModuleDown(111,color);
        } else if (pane[101].getComponent(0) != null&&color.equals(pane[101].getComponent(0).getBackground())) {
            searchModuleUp(111,color);
        }
    }
    private void button112(){
        Color color = pane[112].getComponent(0).getBackground();
        if (pane[113].getComponent(0) != null&&color.equals(pane[113].getComponent(0).getBackground())) {
            searchModuleRight(112,119,color);
        } else if (pane[111].getComponent(0) != null&&color.equals(pane[111].getComponent(0).getBackground())) {
            searchModuleLeft(112,110,color);
        } else if (pane[122].getComponent(0) != null&&color.equals(pane[122].getComponent(0).getBackground())) {
            searchModuleDown(112,color);
        } else if (pane[102].getComponent(0) != null&&color.equals(pane[102].getComponent(0).getBackground())) {
            searchModuleUp(112,color);
        }
    }
    private void button113(){
        Color color = pane[113].getComponent(0).getBackground();
        if (pane[114].getComponent(0) != null&&color.equals(pane[114].getComponent(0).getBackground())) {
            searchModuleRight(113,119,color);
        } else if (pane[112].getComponent(0) != null&&color.equals(pane[112].getComponent(0).getBackground())) {
            searchModuleLeft(113,110,color);
        } else if (pane[123].getComponent(0) != null&&color.equals(pane[123].getComponent(0).getBackground())) {
            searchModuleDown(113,color);
        } else if (pane[103].getComponent(0) != null&&color.equals(pane[103].getComponent(0).getBackground())) {
            searchModuleUp(113,color);
        }
    }
    private void button114(){
        Color color = pane[114].getComponent(0).getBackground();
        if (pane[115].getComponent(0) != null&&color.equals(pane[115].getComponent(0).getBackground())) {
            searchModuleRight(114,119,color);
        } else if (pane[113].getComponent(0) != null&&color.equals(pane[113].getComponent(0).getBackground())) {
            searchModuleLeft(114,110,color);
        } else if (pane[124].getComponent(0) != null&&color.equals(pane[124].getComponent(0).getBackground())) {
            searchModuleDown(114,color);
        } else if (pane[104].getComponent(0) != null&&color.equals(pane[104].getComponent(0).getBackground())) {
            searchModuleUp(114,color);
        }
    }
    private void button115(){
        Color color = pane[115].getComponent(0).getBackground();
        if (pane[116].getComponent(0) != null&&color.equals(pane[116].getComponent(0).getBackground())) {
            searchModuleRight(115,119,color);
        } else if (pane[114].getComponent(0) != null&&color.equals(pane[114].getComponent(0).getBackground())) {
            searchModuleLeft(115,110,color);
        } else if (pane[125].getComponent(0) != null&&color.equals(pane[125].getComponent(0).getBackground())) {
            searchModuleDown(115,color);
        } else if (pane[105].getComponent(0) != null&&color.equals(pane[105].getComponent(0).getBackground())) {
            searchModuleUp(115,color);
        }
    }
    private void button116(){
        Color color = pane[116].getComponent(0).getBackground();
        if (pane[117].getComponent(0) != null&&color.equals(pane[117].getComponent(0).getBackground())) {
            searchModuleRight(116,119,color);
        } else if (pane[115].getComponent(0) != null&&color.equals(pane[115].getComponent(0).getBackground())) {
            searchModuleLeft(116,110,color);
        } else if (pane[126].getComponent(0) != null&&color.equals(pane[126].getComponent(0).getBackground())) {
            searchModuleDown(116,color);
        } else if (pane[106].getComponent(0) != null&&color.equals(pane[106].getComponent(0).getBackground())) {
            searchModuleUp(116,color);
        }
    }
    private void button117(){
        Color color = pane[117].getComponent(0).getBackground();
        if (pane[118].getComponent(0) != null&&color.equals(pane[118].getComponent(0).getBackground())) {
            searchModuleRight(117,119,color);
        } else if (pane[116].getComponent(0) != null&&color.equals(pane[116].getComponent(0).getBackground())) {
            searchModuleLeft(117,110,color);
        } else if (pane[127].getComponent(0) != null&&color.equals(pane[127].getComponent(0).getBackground())) {
            searchModuleDown(117,color);
        } else if (pane[107].getComponent(0) != null&&color.equals(pane[107].getComponent(0).getBackground())) {
            searchModuleUp(117,color);
        }
    }
    private void button118(){
        Color color = pane[118].getComponent(0).getBackground();
        if (pane[119].getComponent(0) != null&&color.equals(pane[119].getComponent(0).getBackground())) {
            searchModuleRight(118,119,color);
        } else if (pane[117].getComponent(0) != null&&color.equals(pane[117].getComponent(0).getBackground())) {
            searchModuleLeft(118,110,color);
        } else if (pane[128].getComponent(0) != null&&color.equals(pane[128].getComponent(0).getBackground())) {
            searchModuleDown(118,color);
        } else if (pane[108].getComponent(0) != null&&color.equals(pane[108].getComponent(0).getBackground())) {
            searchModuleUp(118,color);
        }
    }
    private void button119(){
        Color color = pane[119].getComponent(0).getBackground();
        if (pane[118].getComponent(0) != null&&color.equals(pane[118].getComponent(0).getBackground())) {
            searchModuleLeft(119,110,color);
        } else if (pane[129].getComponent(0) != null&&color.equals(pane[129].getComponent(0).getBackground())) {
            searchModuleDown(119,color);
        } else if (pane[109].getComponent(0) != null&&color.equals(pane[109].getComponent(0).getBackground())) {
            searchModuleUp(119,color);
        }
    }
    private void button120(){
        Color color = pane[120].getComponent(0).getBackground();
        if (pane[121].getComponent(0) != null&&color.equals(pane[121].getComponent(0).getBackground())) {
            searchModuleRight(120,129,color);
        } else if (pane[130].getComponent(0) != null&&color.equals(pane[130].getComponent(0).getBackground())) {
            searchModuleDown(120,color);
        } else if (pane[110].getComponent(0) != null&&color.equals(pane[110].getComponent(0).getBackground())) {
            searchModuleUp(120,color);
        }
    }
    private void button121(){
        Color color = pane[121].getComponent(0).getBackground();
        if (pane[122].getComponent(0) != null&&color.equals(pane[122].getComponent(0).getBackground())) {
            searchModuleRight(121,129,color);
        } else if (pane[120].getComponent(0) != null&&color.equals(pane[120].getComponent(0).getBackground())) {
            searchModuleLeft(121,120,color);
        } else if (pane[131].getComponent(0) != null&&color.equals(pane[131].getComponent(0).getBackground())) {
            searchModuleDown(121,color);
        } else if (pane[111].getComponent(0) != null&&color.equals(pane[111].getComponent(0).getBackground())) {
            searchModuleUp(121,color);
        }
    }
    private void button122(){
        Color color = pane[122].getComponent(0).getBackground();
        if (pane[123].getComponent(0) != null&&color.equals(pane[123].getComponent(0).getBackground())) {
            searchModuleRight(122,129,color);
        } else if (pane[121].getComponent(0) != null&&color.equals(pane[121].getComponent(0).getBackground())) {
            searchModuleLeft(122,120,color);
        } else if (pane[132].getComponent(0) != null&&color.equals(pane[132].getComponent(0).getBackground())) {
            searchModuleDown(122,color);
        } else if (pane[112].getComponent(0) != null&&color.equals(pane[112].getComponent(0).getBackground())) {
            searchModuleUp(122,color);
        }
    }
    private void button123(){
        Color color = pane[123].getComponent(0).getBackground();
        if (pane[124].getComponent(0) != null&&color.equals(pane[124].getComponent(0).getBackground())) {
            searchModuleRight(123,129,color);
        } else if (pane[122].getComponent(0) != null&&color.equals(pane[122].getComponent(0).getBackground())) {
            searchModuleLeft(123,120,color);
        } else if (pane[133].getComponent(0) != null&&color.equals(pane[133].getComponent(0).getBackground())) {
            searchModuleDown(123,color);
        } else if (pane[113].getComponent(0) != null&&color.equals(pane[113].getComponent(0).getBackground())) {
            searchModuleUp(123,color);
        }
    }
    private void button124(){
        Color color = pane[124].getComponent(0).getBackground();
        if (pane[125].getComponent(0) != null&&color.equals(pane[125].getComponent(0).getBackground())) {
            searchModuleRight(124,129,color);
        } else if (pane[123].getComponent(0) != null&&color.equals(pane[123].getComponent(0).getBackground())) {
            searchModuleLeft(124,120,color);
        } else if (pane[134].getComponent(0) != null&&color.equals(pane[134].getComponent(0).getBackground())) {
            searchModuleDown(124,color);
        } else if (pane[114].getComponent(0) != null&&color.equals(pane[114].getComponent(0).getBackground())) {
            searchModuleUp(124,color);
        }
    }
    private void button125(){
        Color color = pane[125].getComponent(0).getBackground();
        if (pane[126].getComponent(0) != null&&color.equals(pane[126].getComponent(0).getBackground())) {
            searchModuleRight(125,129,color);
        } else if (pane[124].getComponent(0) != null&&color.equals(pane[124].getComponent(0).getBackground())) {
            searchModuleLeft(125,120,color);
        } else if (pane[135].getComponent(0) != null&&color.equals(pane[135].getComponent(0).getBackground())) {
            searchModuleDown(125,color);
        } else if (pane[115].getComponent(0) != null&&color.equals(pane[115].getComponent(0).getBackground())) {
            searchModuleUp(125,color);
        }
    }
    private void button126(){
        Color color = pane[126].getComponent(0).getBackground();
        if (pane[127].getComponent(0) != null&&color.equals(pane[127].getComponent(0).getBackground())) {
            searchModuleRight(126,129,color);
        } else if (pane[125].getComponent(0) != null&&color.equals(pane[125].getComponent(0).getBackground())) {
            searchModuleLeft(126,120,color);
        } else if (pane[136].getComponent(0) != null&&color.equals(pane[136].getComponent(0).getBackground())) {
            searchModuleDown(126,color);
        } else if (pane[116].getComponent(0) != null&&color.equals(pane[116].getComponent(0).getBackground())) {
            searchModuleUp(126,color);
        }
    }
    private void button127(){
        Color color = pane[127].getComponent(0).getBackground();
        if (pane[128].getComponent(0) != null&&color.equals(pane[128].getComponent(0).getBackground())) {
            searchModuleRight(127,129,color);
        } else if (pane[126].getComponent(0) != null&&color.equals(pane[126].getComponent(0).getBackground())) {
            searchModuleLeft(127,120,color);
        } else if (pane[137].getComponent(0) != null&&color.equals(pane[137].getComponent(0).getBackground())) {
            searchModuleDown(127,color);
        } else if (pane[117].getComponent(0) != null&&color.equals(pane[117].getComponent(0).getBackground())) {
            searchModuleUp(127,color);
        }
    }
    private void button128(){
        Color color = pane[128].getComponent(0).getBackground();
        if (pane[129].getComponent(0) != null&&color.equals(pane[129].getComponent(0).getBackground())) {
            searchModuleRight(128,129,color);
        } else if (pane[127].getComponent(0) != null&&color.equals(pane[127].getComponent(0).getBackground())) {
            searchModuleLeft(128,120,color);
        } else if (pane[138].getComponent(0) != null&&color.equals(pane[138].getComponent(0).getBackground())) {
            searchModuleDown(128,color);
        } else if (pane[118].getComponent(0) != null&&color.equals(pane[118].getComponent(0).getBackground())) {
            searchModuleUp(128,color);
        }
    }
    private void button129(){
        Color color = pane[129].getComponent(0).getBackground();
        if (pane[128].getComponent(0) != null&&color.equals(pane[128].getComponent(0).getBackground())) {
            searchModuleLeft(129,120,color);
        } else if (pane[139].getComponent(0) != null&&color.equals(pane[139].getComponent(0).getBackground())) {
            searchModuleDown(129,color);
        } else if (pane[119].getComponent(0) != null&&color.equals(pane[119].getComponent(0).getBackground())) {
            searchModuleUp(129,color);
        }
    }
    private void button130(){
        Color color = pane[130].getComponent(0).getBackground();
        if (pane[131].getComponent(0) != null&&color.equals(pane[131].getComponent(0).getBackground())) {
            searchModuleRight(130,139,color);
        } else if (pane[140].getComponent(0) != null&&color.equals(pane[140].getComponent(0).getBackground())) {
            searchModuleDown(130,color);
        } else if (pane[120].getComponent(0) != null&&color.equals(pane[120].getComponent(0).getBackground())) {
            searchModuleUp(130,color);
        }
    }
    private void button131(){
        Color color = pane[131].getComponent(0).getBackground();
        if (pane[132].getComponent(0) != null&&color.equals(pane[132].getComponent(0).getBackground())) {
            searchModuleRight(131,139,color);
        } else if (pane[130].getComponent(0) != null&&color.equals(pane[130].getComponent(0).getBackground())) {
            searchModuleLeft(131,130,color);
        } else if (pane[141].getComponent(0) != null&&color.equals(pane[141].getComponent(0).getBackground())) {
            searchModuleDown(131,color);
        } else if (pane[121].getComponent(0) != null&&color.equals(pane[121].getComponent(0).getBackground())) {
            searchModuleUp(131,color);
        }
    }
    private void button132(){
        Color color = pane[132].getComponent(0).getBackground();
        if (pane[133].getComponent(0) != null&&color.equals(pane[133].getComponent(0).getBackground())) {
            searchModuleRight(132,139,color);
        } else if (pane[131].getComponent(0) != null&&color.equals(pane[131].getComponent(0).getBackground())) {
            searchModuleLeft(132,130,color);
        } else if (pane[142].getComponent(0) != null&&color.equals(pane[142].getComponent(0).getBackground())) {
            searchModuleDown(132,color);
        } else if (pane[122].getComponent(0) != null&&color.equals(pane[122].getComponent(0).getBackground())) {
            searchModuleUp(132,color);
        }
    }
    private void button133(){
        Color color = pane[133].getComponent(0).getBackground();
        if (pane[134].getComponent(0) != null&&color.equals(pane[134].getComponent(0).getBackground())) {
            searchModuleRight(133,139,color);
        } else if (pane[132].getComponent(0) != null&&color.equals(pane[132].getComponent(0).getBackground())) {
            searchModuleLeft(133,130,color);
        } else if (pane[143].getComponent(0) != null&&color.equals(pane[143].getComponent(0).getBackground())) {
            searchModuleDown(133,color);
        } else if (pane[123].getComponent(0) != null&&color.equals(pane[123].getComponent(0).getBackground())) {
            searchModuleUp(133,color);
        }
    }
    private void button134(){
        Color color = pane[134].getComponent(0).getBackground();
        if (pane[135].getComponent(0) != null&&color.equals(pane[135].getComponent(0).getBackground())) {
            searchModuleRight(134,139,color);
        } else if (pane[133].getComponent(0) != null&&color.equals(pane[133].getComponent(0).getBackground())) {
            searchModuleLeft(134,130,color);
        } else if (pane[144].getComponent(0) != null&&color.equals(pane[144].getComponent(0).getBackground())) {
            searchModuleDown(134,color);
        } else if (pane[124].getComponent(0) != null&&color.equals(pane[124].getComponent(0).getBackground())) {
            searchModuleUp(134,color);
        }
    }
    private void button135(){
        Color color = pane[135].getComponent(0).getBackground();
        if (pane[136].getComponent(0) != null&&color.equals(pane[136].getComponent(0).getBackground())) {
            searchModuleRight(135,139,color);
        } else if (pane[134].getComponent(0) != null&&color.equals(pane[134].getComponent(0).getBackground())) {
            searchModuleLeft(135,130,color);
        } else if (pane[145].getComponent(0) != null&&color.equals(pane[145].getComponent(0).getBackground())) {
            searchModuleDown(135,color);
        } else if (pane[125].getComponent(0) != null&&color.equals(pane[125].getComponent(0).getBackground())) {
            searchModuleUp(135,color);
        }
    }
    private void button136(){
        Color color = pane[136].getComponent(0).getBackground();
        if (pane[137].getComponent(0) != null&&color.equals(pane[137].getComponent(0).getBackground())) {
            searchModuleRight(136,139,color);
        } else if (pane[135].getComponent(0) != null&&color.equals(pane[135].getComponent(0).getBackground())) {
            searchModuleLeft(136,130,color);
        } else if (pane[146].getComponent(0) != null&&color.equals(pane[146].getComponent(0).getBackground())) {
            searchModuleDown(136,color);
        } else if (pane[126].getComponent(0) != null&&color.equals(pane[126].getComponent(0).getBackground())) {
            searchModuleUp(136,color);
        }
    }
    private void button137(){
        Color color = pane[137].getComponent(0).getBackground();
        if (pane[138].getComponent(0) != null&&color.equals(pane[138].getComponent(0).getBackground())) {
            searchModuleRight(137,139,color);
        } else if (pane[136].getComponent(0) != null&&color.equals(pane[136].getComponent(0).getBackground())) {
            searchModuleLeft(137,130,color);
        } else if (pane[147].getComponent(0) != null&&color.equals(pane[147].getComponent(0).getBackground())) {
            searchModuleDown(137,color);
        } else if (pane[127].getComponent(0) != null&&color.equals(pane[127].getComponent(0).getBackground())) {
            searchModuleUp(137,color);
        }
    }
    private void button138(){
        Color color = pane[138].getComponent(0).getBackground();
        if (pane[139].getComponent(0) != null&&color.equals(pane[139].getComponent(0).getBackground())) {
            searchModuleRight(138,139,color);
        } else if (pane[137].getComponent(0) != null&&color.equals(pane[137].getComponent(0).getBackground())) {
            searchModuleLeft(138,130,color);
        } else if (pane[148].getComponent(0) != null&&color.equals(pane[148].getComponent(0).getBackground())) {
            searchModuleDown(138,color);
        } else if (pane[128].getComponent(0) != null&&color.equals(pane[128].getComponent(0).getBackground())) {
            searchModuleUp(138,color);
        }
    }
    private void button139(){
        Color color = pane[139].getComponent(0).getBackground();
        if (pane[138].getComponent(0) != null&&color.equals(pane[138].getComponent(0).getBackground())) {
            searchModuleLeft(139,130,color);
        } else if (pane[149].getComponent(0) != null&&color.equals(pane[149].getComponent(0).getBackground())) {
            searchModuleDown(139,color);
        } else if (pane[129].getComponent(0) != null&&color.equals(pane[129].getComponent(0).getBackground())) {
            searchModuleUp(139,color);
        }
    }
    private void button140(){
        Color color = pane[140].getComponent(0).getBackground();
        if (pane[141].getComponent(0) != null&&color.equals(pane[141].getComponent(0).getBackground())) {
            searchModuleRight(140,149,color);
        } else if (pane[150].getComponent(0) != null&&color.equals(pane[150].getComponent(0).getBackground())) {
            searchModuleDown(140,color);
        } else if (pane[130].getComponent(0) != null&&color.equals(pane[130].getComponent(0).getBackground())) {
            searchModuleUp(140,color);
        }
    }
    private void button141(){
        Color color = pane[141].getComponent(0).getBackground();
        if (pane[142].getComponent(0) != null&&color.equals(pane[142].getComponent(0).getBackground())) {
            searchModuleRight(141,149,color);
        } else if (pane[140].getComponent(0) != null&&color.equals(pane[140].getComponent(0).getBackground())) {
            searchModuleLeft(141,140,color);
        } else if (pane[151].getComponent(0) != null&&color.equals(pane[151].getComponent(0).getBackground())) {
            searchModuleDown(141,color);
        } else if (pane[131].getComponent(0) != null&&color.equals(pane[131].getComponent(0).getBackground())) {
            searchModuleUp(141,color);
        }
    }
    private void button142(){
        Color color = pane[142].getComponent(0).getBackground();
        if (pane[143].getComponent(0) != null&&color.equals(pane[143].getComponent(0).getBackground())) {
            searchModuleRight(142,149,color);
        } else if (pane[141].getComponent(0) != null&&color.equals(pane[141].getComponent(0).getBackground())) {
            searchModuleLeft(142,140,color);
        } else if (pane[152].getComponent(0) != null&&color.equals(pane[152].getComponent(0).getBackground())) {
            searchModuleDown(142,color);
        } else if (pane[132].getComponent(0) != null&&color.equals(pane[132].getComponent(0).getBackground())) {
            searchModuleUp(142,color);
        }
    }
    private void button143(){
        Color color = pane[143].getComponent(0).getBackground();
        if (pane[144].getComponent(0) != null&&color.equals(pane[144].getComponent(0).getBackground())) {
            searchModuleRight(143,149,color);
        } else if (pane[142].getComponent(0) != null&&color.equals(pane[142].getComponent(0).getBackground())) {
            searchModuleLeft(143,140,color);
        } else if (pane[153].getComponent(0) != null&&color.equals(pane[153].getComponent(0).getBackground())) {
            searchModuleDown(143,color);
        } else if (pane[133].getComponent(0) != null&&color.equals(pane[133].getComponent(0).getBackground())) {
            searchModuleUp(143,color);
        }
    }
    private void button144(){
        Color color = pane[144].getComponent(0).getBackground();
        if (pane[145].getComponent(0) != null&&color.equals(pane[145].getComponent(0).getBackground())) {
            searchModuleRight(144,149,color);
        } else if (pane[143].getComponent(0) != null&&color.equals(pane[143].getComponent(0).getBackground())) {
            searchModuleLeft(144,140,color);
        } else if (pane[154].getComponent(0) != null&&color.equals(pane[154].getComponent(0).getBackground())) {
            searchModuleDown(144,color);
        } else if (pane[134].getComponent(0) != null&&color.equals(pane[134].getComponent(0).getBackground())) {
            searchModuleUp(144,color);
        }
    }
    private void button145(){
        Color color = pane[145].getComponent(0).getBackground();
        if (pane[146].getComponent(0) != null&&color.equals(pane[146].getComponent(0).getBackground())) {
            searchModuleRight(145,149,color);
        } else if (pane[144].getComponent(0) != null&&color.equals(pane[144].getComponent(0).getBackground())) {
            searchModuleLeft(145,140,color);
        } else if (pane[155].getComponent(0) != null&&color.equals(pane[155].getComponent(0).getBackground())) {
            searchModuleDown(145,color);
        } else if (pane[135].getComponent(0) != null&&color.equals(pane[135].getComponent(0).getBackground())) {
            searchModuleUp(145,color);
        }
    }
    private void button146(){
        Color color = pane[146].getComponent(0).getBackground();
        if (pane[147].getComponent(0) != null&&color.equals(pane[147].getComponent(0).getBackground())) {
            searchModuleRight(146,149,color);
        } else if (pane[145].getComponent(0) != null&&color.equals(pane[145].getComponent(0).getBackground())) {
            searchModuleLeft(146,140,color);
        } else if (pane[156].getComponent(0) != null&&color.equals(pane[156].getComponent(0).getBackground())) {
            searchModuleDown(146,color);
        } else if (pane[136].getComponent(0) != null&&color.equals(pane[136].getComponent(0).getBackground())) {
            searchModuleUp(146,color);
        }
    }
    private void button147(){
        Color color = pane[147].getComponent(0).getBackground();
        if (pane[148].getComponent(0) != null&&color.equals(pane[148].getComponent(0).getBackground())) {
            searchModuleRight(147,149,color);
        } else if (pane[146].getComponent(0) != null&&color.equals(pane[146].getComponent(0).getBackground())) {
            searchModuleLeft(147,140,color);
        } else if (pane[157].getComponent(0) != null&&color.equals(pane[157].getComponent(0).getBackground())) {
            searchModuleDown(147,color);
        } else if (pane[137].getComponent(0) != null&&color.equals(pane[137].getComponent(0).getBackground())) {
            searchModuleUp(147,color);
        }
    }
    private void button148(){
        Color color = pane[148].getComponent(0).getBackground();
        if (pane[149].getComponent(0) != null&&color.equals(pane[149].getComponent(0).getBackground())) {
            searchModuleRight(148,149,color);
        } else if (pane[147].getComponent(0) != null&&color.equals(pane[147].getComponent(0).getBackground())) {
            searchModuleLeft(148,140,color);
        } else if (pane[158].getComponent(0) != null&&color.equals(pane[158].getComponent(0).getBackground())) {
            searchModuleDown(148,color);
        } else if (pane[138].getComponent(0) != null&&color.equals(pane[138].getComponent(0).getBackground())) {
            searchModuleUp(148,color);
        }
    }
    private void button149(){
        Color color = pane[149].getComponent(0).getBackground();
        if (pane[148].getComponent(0) != null&&color.equals(pane[148].getComponent(0).getBackground())) {
            searchModuleLeft(149,140,color);
        } else if (pane[159].getComponent(0) != null&&color.equals(pane[159].getComponent(0).getBackground())) {
            searchModuleDown(149,color);
        } else if (pane[139].getComponent(0) != null&&color.equals(pane[139].getComponent(0).getBackground())) {
            searchModuleUp(149,color);
        }
    }
    private void button150(){
        Color color = pane[150].getComponent(0).getBackground();
        if (pane[151].getComponent(0) != null&&color.equals(pane[151].getComponent(0).getBackground())) {
            searchModuleRight(150,159,color);
        } else if (pane[160].getComponent(0) != null&&color.equals(pane[160].getComponent(0).getBackground())) {
            searchModuleDown(150,color);
        } else if (pane[140].getComponent(0) != null&&color.equals(pane[140].getComponent(0).getBackground())) {
            searchModuleUp(150,color);
        }
    }
    private void button151(){
        Color color = pane[151].getComponent(0).getBackground();
        if (pane[152].getComponent(0) != null&&color.equals(pane[152].getComponent(0).getBackground())) {
            searchModuleRight(151,159,color);
        } else if (pane[150].getComponent(0) != null&&color.equals(pane[150].getComponent(0).getBackground())) {
            searchModuleLeft(151,150,color);
        } else if (pane[161].getComponent(0) != null&&color.equals(pane[161].getComponent(0).getBackground())) {
            searchModuleDown(151,color);
        } else if (pane[141].getComponent(0) != null&&color.equals(pane[141].getComponent(0).getBackground())) {
            searchModuleUp(151,color);
        }
    }
    private void button152(){
        Color color = pane[152].getComponent(0).getBackground();
        if (pane[153].getComponent(0) != null&&color.equals(pane[153].getComponent(0).getBackground())) {
            searchModuleRight(152,159,color);
        } else if (pane[151].getComponent(0) != null&&color.equals(pane[151].getComponent(0).getBackground())) {
            searchModuleLeft(152,150,color);
        } else if (pane[162].getComponent(0) != null&&color.equals(pane[162].getComponent(0).getBackground())) {
            searchModuleDown(152,color);
        } else if (pane[142].getComponent(0) != null&&color.equals(pane[142].getComponent(0).getBackground())) {
            searchModuleUp(152,color);
        }
    }
    private void button153(){
        Color color = pane[153].getComponent(0).getBackground();
        if (pane[154].getComponent(0) != null&&color.equals(pane[154].getComponent(0).getBackground())) {
            searchModuleRight(153,159,color);
        } else if (pane[152].getComponent(0) != null&&color.equals(pane[152].getComponent(0).getBackground())) {
            searchModuleLeft(153,150,color);
        } else if (pane[163].getComponent(0) != null&&color.equals(pane[163].getComponent(0).getBackground())) {
            searchModuleDown(153,color);
        } else if (pane[143].getComponent(0) != null&&color.equals(pane[143].getComponent(0).getBackground())) {
            searchModuleUp(153,color);
        }
    }
    private void button154(){
        Color color = pane[154].getComponent(0).getBackground();
        if (pane[155].getComponent(0) != null&&color.equals(pane[155].getComponent(0).getBackground())) {
            searchModuleRight(154,159,color);
        } else if (pane[153].getComponent(0) != null&&color.equals(pane[153].getComponent(0).getBackground())) {
            searchModuleLeft(154,150,color);
        } else if (pane[164].getComponent(0) != null&&color.equals(pane[164].getComponent(0).getBackground())) {
            searchModuleDown(154,color);
        } else if (pane[144].getComponent(0) != null&&color.equals(pane[144].getComponent(0).getBackground())) {
            searchModuleUp(154,color);
        }
    }
    private void button155(){
        Color color = pane[155].getComponent(0).getBackground();
        if (pane[156].getComponent(0) != null&&color.equals(pane[156].getComponent(0).getBackground())) {
            searchModuleRight(155,159,color);
        } else if (pane[154].getComponent(0) != null&&color.equals(pane[154].getComponent(0).getBackground())) {
            searchModuleLeft(155,150,color);
        } else if (pane[165].getComponent(0) != null&&color.equals(pane[165].getComponent(0).getBackground())) {
            searchModuleDown(155,color);
        } else if (pane[145].getComponent(0) != null&&color.equals(pane[145].getComponent(0).getBackground())) {
            searchModuleUp(155,color);
        }
    }
    private void button156(){
        Color color = pane[156].getComponent(0).getBackground();
        if (pane[157].getComponent(0) != null&&color.equals(pane[157].getComponent(0).getBackground())) {
            searchModuleRight(156,159,color);
        } else if (pane[155].getComponent(0) != null&&color.equals(pane[155].getComponent(0).getBackground())) {
            searchModuleLeft(156,150,color);
        } else if (pane[166].getComponent(0) != null&&color.equals(pane[166].getComponent(0).getBackground())) {
            searchModuleDown(156,color);
        } else if (pane[146].getComponent(0) != null&&color.equals(pane[146].getComponent(0).getBackground())) {
            searchModuleUp(156,color);
        }
    }
    private void button157(){
        Color color = pane[157].getComponent(0).getBackground();
        if (pane[158].getComponent(0) != null&&color.equals(pane[158].getComponent(0).getBackground())) {
            searchModuleRight(157,159,color);
        } else if (pane[156].getComponent(0) != null&&color.equals(pane[156].getComponent(0).getBackground())) {
            searchModuleLeft(157,150,color);
        } else if (pane[167].getComponent(0) != null&&color.equals(pane[167].getComponent(0).getBackground())) {
            searchModuleDown(157,color);
        } else if (pane[147].getComponent(0) != null&&color.equals(pane[147].getComponent(0).getBackground())) {
            searchModuleUp(157,color);
        }
    }
    private void button158(){
        Color color = pane[158].getComponent(0).getBackground();
        if (pane[159].getComponent(0) != null&&color.equals(pane[159].getComponent(0).getBackground())) {
            searchModuleRight(158,159,color);
        } else if (pane[157].getComponent(0) != null&&color.equals(pane[157].getComponent(0).getBackground())) {
            searchModuleLeft(158,150,color);
        } else if (pane[168].getComponent(0) != null&&color.equals(pane[168].getComponent(0).getBackground())) {
            searchModuleDown(158,color);
        } else if (pane[148].getComponent(0) != null&&color.equals(pane[148].getComponent(0).getBackground())) {
            searchModuleUp(158,color);
        }
    }
    private void button159(){
        Color color = pane[159].getComponent(0).getBackground();
        if (pane[158].getComponent(0) != null&&color.equals(pane[158].getComponent(0).getBackground())) {
            searchModuleLeft(159,150,color);
        } else if (pane[169].getComponent(0) != null&&color.equals(pane[169].getComponent(0).getBackground())) {
            searchModuleDown(159,color);
        } else if (pane[149].getComponent(0) != null&&color.equals(pane[149].getComponent(0).getBackground())) {
            searchModuleUp(159,color);
        }
    }
    private void button160(){
        Color color = pane[160].getComponent(0).getBackground();
        if (pane[161].getComponent(0) != null&&color.equals(pane[161].getComponent(0).getBackground())) {
            searchModuleRight(160,169,color);
        } else if (pane[170].getComponent(0) != null&&color.equals(pane[170].getComponent(0).getBackground())) {
            searchModuleDown(160,color);
        } else if (pane[150].getComponent(0) != null&&color.equals(pane[150].getComponent(0).getBackground())) {
            searchModuleUp(160,color);
        }
    }
    private void button161(){
        Color color = pane[161].getComponent(0).getBackground();
        if (pane[162].getComponent(0) != null&&color.equals(pane[162].getComponent(0).getBackground())) {
            searchModuleRight(161,169,color);
        } else if (pane[160].getComponent(0) != null&&color.equals(pane[160].getComponent(0).getBackground())) {
            searchModuleLeft(161,160,color);
        } else if (pane[171].getComponent(0) != null&&color.equals(pane[171].getComponent(0).getBackground())) {
            searchModuleDown(161,color);
        } else if (pane[151].getComponent(0) != null&&color.equals(pane[151].getComponent(0).getBackground())) {
            searchModuleUp(161,color);
        }
    }
    private void button162(){
        Color color = pane[162].getComponent(0).getBackground();
        if (pane[163].getComponent(0) != null&&color.equals(pane[163].getComponent(0).getBackground())) {
            searchModuleRight(162,169,color);
        } else if (pane[161].getComponent(0) != null&&color.equals(pane[161].getComponent(0).getBackground())) {
            searchModuleLeft(162,160,color);
        } else if (pane[172].getComponent(0) != null&&color.equals(pane[172].getComponent(0).getBackground())) {
            searchModuleDown(162,color);
        } else if (pane[152].getComponent(0) != null&&color.equals(pane[152].getComponent(0).getBackground())) {
            searchModuleUp(162,color);
        }
    }
    private void button163(){
        Color color = pane[163].getComponent(0).getBackground();
        if (pane[164].getComponent(0) != null&&color.equals(pane[164].getComponent(0).getBackground())) {
            searchModuleRight(163,169,color);
        } else if (pane[162].getComponent(0) != null&&color.equals(pane[162].getComponent(0).getBackground())) {
            searchModuleLeft(163,160,color);
        } else if (pane[173].getComponent(0) != null&&color.equals(pane[173].getComponent(0).getBackground())) {
            searchModuleDown(163,color);
        } else if (pane[153].getComponent(0) != null&&color.equals(pane[153].getComponent(0).getBackground())) {
            searchModuleUp(163,color);
        }
    }
    private void button164(){
        Color color = pane[164].getComponent(0).getBackground();
        if (pane[165].getComponent(0) != null&&color.equals(pane[165].getComponent(0).getBackground())) {
            searchModuleRight(164,169,color);
        } else if (pane[163].getComponent(0) != null&&color.equals(pane[163].getComponent(0).getBackground())) {
            searchModuleLeft(164,160,color);
        } else if (pane[174].getComponent(0) != null&&color.equals(pane[174].getComponent(0).getBackground())) {
            searchModuleDown(164,color);
        } else if (pane[154].getComponent(0) != null&&color.equals(pane[154].getComponent(0).getBackground())) {
            searchModuleUp(164,color);
        }
    }
    private void button165(){
        Color color = pane[165].getComponent(0).getBackground();
        if (pane[166].getComponent(0) != null&&color.equals(pane[166].getComponent(0).getBackground())) {
            searchModuleRight(165,169,color);
        } else if (pane[164].getComponent(0) != null&&color.equals(pane[164].getComponent(0).getBackground())) {
            searchModuleLeft(165,160,color);
        } else if (pane[175].getComponent(0) != null&&color.equals(pane[175].getComponent(0).getBackground())) {
            searchModuleDown(165,color);
        } else if (pane[155].getComponent(0) != null&&color.equals(pane[155].getComponent(0).getBackground())) {
            searchModuleUp(165,color);
        }
    }
    private void button166(){
        Color color = pane[166].getComponent(0).getBackground();
        if (pane[167].getComponent(0) != null&&color.equals(pane[167].getComponent(0).getBackground())) {
            searchModuleRight(166,169,color);
        } else if (pane[165].getComponent(0) != null&&color.equals(pane[165].getComponent(0).getBackground())) {
            searchModuleLeft(166,160,color);
        } else if (pane[176].getComponent(0) != null&&color.equals(pane[176].getComponent(0).getBackground())) {
            searchModuleDown(166,color);
        } else if (pane[156].getComponent(0) != null&&color.equals(pane[156].getComponent(0).getBackground())) {
            searchModuleUp(166,color);
        }
    }
    private void button167(){
        Color color = pane[167].getComponent(0).getBackground();
        if (pane[168].getComponent(0) != null&&color.equals(pane[168].getComponent(0).getBackground())) {
            searchModuleRight(167,169,color);
        } else if (pane[166].getComponent(0) != null&&color.equals(pane[166].getComponent(0).getBackground())) {
            searchModuleLeft(167,160,color);
        } else if (pane[177].getComponent(0) != null&&color.equals(pane[177].getComponent(0).getBackground())) {
            searchModuleDown(167,color);
        } else if (pane[157].getComponent(0) != null&&color.equals(pane[157].getComponent(0).getBackground())) {
            searchModuleUp(167,color);
        }
    }
    private void button168(){
        Color color = pane[168].getComponent(0).getBackground();
        if (pane[169].getComponent(0) != null&&color.equals(pane[169].getComponent(0).getBackground())) {
            searchModuleRight(168,169,color);
        } else if (pane[167].getComponent(0) != null&&color.equals(pane[167].getComponent(0).getBackground())) {
            searchModuleLeft(168,160,color);
        } else if (pane[178].getComponent(0) != null&&color.equals(pane[178].getComponent(0).getBackground())) {
            searchModuleDown(168,color);
        } else if (pane[158].getComponent(0) != null&&color.equals(pane[158].getComponent(0).getBackground())) {
            searchModuleUp(168,color);
        }
    }
    private void button169(){
        Color color = pane[169].getComponent(0).getBackground();
        if (pane[168].getComponent(0) != null&&color.equals(pane[168].getComponent(0).getBackground())) {
            searchModuleLeft(169,160,color);
        } else if (pane[179].getComponent(0) != null&&color.equals(pane[179].getComponent(0).getBackground())) {
            searchModuleDown(169,color);
        } else if (pane[159].getComponent(0) != null&&color.equals(pane[159].getComponent(0).getBackground())) {
            searchModuleUp(169,color);
        }
    }
    private void button170(){
        Color color = pane[170].getComponent(0).getBackground();
        if (pane[171].getComponent(0) != null&&color.equals(pane[171].getComponent(0).getBackground())) {
            searchModuleRight(170,179,color);
        } else if (pane[180].getComponent(0) != null&&color.equals(pane[180].getComponent(0).getBackground())) {
            searchModuleDown(170,color);
        } else if (pane[160].getComponent(0) != null&&color.equals(pane[160].getComponent(0).getBackground())) {
            searchModuleUp(170,color);
        }
    }
    private void button171(){
        Color color = pane[171].getComponent(0).getBackground();
        if (pane[172].getComponent(0) != null&&color.equals(pane[172].getComponent(0).getBackground())) {
            searchModuleRight(171,179,color);
        } else if (pane[170].getComponent(0) != null&&color.equals(pane[170].getComponent(0).getBackground())) {
            searchModuleLeft(171,170,color);
        } else if (pane[181].getComponent(0) != null&&color.equals(pane[181].getComponent(0).getBackground())) {
            searchModuleDown(171,color);
        } else if (pane[161].getComponent(0) != null&&color.equals(pane[161].getComponent(0).getBackground())) {
            searchModuleUp(171,color);
        }
    }
    private void button172(){
        Color color = pane[172].getComponent(0).getBackground();
        if (pane[173].getComponent(0) != null&&color.equals(pane[173].getComponent(0).getBackground())) {
            searchModuleRight(172,179,color);
        } else if (pane[171].getComponent(0) != null&&color.equals(pane[171].getComponent(0).getBackground())) {
            searchModuleLeft(172,170,color);
        } else if (pane[182].getComponent(0) != null&&color.equals(pane[182].getComponent(0).getBackground())) {
            searchModuleDown(172,color);
        } else if (pane[162].getComponent(0) != null&&color.equals(pane[162].getComponent(0).getBackground())) {
            searchModuleUp(172,color);
        }
    }
    private void button173(){
        Color color = pane[173].getComponent(0).getBackground();
        if (pane[174].getComponent(0) != null&&color.equals(pane[174].getComponent(0).getBackground())) {
            searchModuleRight(173,179,color);
        } else if (pane[172].getComponent(0) != null&&color.equals(pane[172].getComponent(0).getBackground())) {
            searchModuleLeft(173,170,color);
        } else if (pane[183].getComponent(0) != null&&color.equals(pane[183].getComponent(0).getBackground())) {
            searchModuleDown(173,color);
        } else if (pane[163].getComponent(0) != null&&color.equals(pane[163].getComponent(0).getBackground())) {
            searchModuleUp(173,color);
        }
    }
    private void button174(){
        Color color = pane[174].getComponent(0).getBackground();
        if (pane[175].getComponent(0) != null&&color.equals(pane[175].getComponent(0).getBackground())) {
            searchModuleRight(174,179,color);
        } else if (pane[173].getComponent(0) != null&&color.equals(pane[173].getComponent(0).getBackground())) {
            searchModuleLeft(174,170,color);
        } else if (pane[184].getComponent(0) != null&&color.equals(pane[184].getComponent(0).getBackground())) {
            searchModuleDown(174,color);
        } else if (pane[164].getComponent(0) != null&&color.equals(pane[164].getComponent(0).getBackground())) {
            searchModuleUp(174,color);
        }
    }
    private void button175(){
        Color color = pane[175].getComponent(0).getBackground();
        if (pane[176].getComponent(0) != null&&color.equals(pane[176].getComponent(0).getBackground())) {
            searchModuleRight(175,179,color);
        } else if (pane[174].getComponent(0) != null&&color.equals(pane[174].getComponent(0).getBackground())) {
            searchModuleLeft(175,170,color);
        } else if (pane[185].getComponent(0) != null&&color.equals(pane[185].getComponent(0).getBackground())) {
            searchModuleDown(175,color);
        } else if (pane[165].getComponent(0) != null&&color.equals(pane[165].getComponent(0).getBackground())) {
            searchModuleUp(175,color);
        }
    }
    private void button176(){
        Color color = pane[176].getComponent(0).getBackground();
        if (pane[177].getComponent(0) != null&&color.equals(pane[177].getComponent(0).getBackground())) {
            searchModuleRight(176,179,color);
        } else if (pane[175].getComponent(0) != null&&color.equals(pane[175].getComponent(0).getBackground())) {
            searchModuleLeft(176,170,color);
        } else if (pane[186].getComponent(0) != null&&color.equals(pane[186].getComponent(0).getBackground())) {
            searchModuleDown(176,color);
        } else if (pane[166].getComponent(0) != null&&color.equals(pane[166].getComponent(0).getBackground())) {
            searchModuleUp(176,color);
        }
    }
    private void button177(){
        Color color = pane[177].getComponent(0).getBackground();
        if (pane[178].getComponent(0) != null&&color.equals(pane[178].getComponent(0).getBackground())) {
            searchModuleRight(177,179,color);
        } else if (pane[176].getComponent(0) != null&&color.equals(pane[176].getComponent(0).getBackground())) {
            searchModuleLeft(177,170,color);
        } else if (pane[187].getComponent(0) != null&&color.equals(pane[187].getComponent(0).getBackground())) {
            searchModuleDown(177,color);
        } else if (pane[167].getComponent(0) != null&&color.equals(pane[167].getComponent(0).getBackground())) {
            searchModuleUp(177,color);
        }
    }
    private void button178(){
        Color color = pane[178].getComponent(0).getBackground();
        if (pane[179].getComponent(0) != null&&color.equals(pane[179].getComponent(0).getBackground())) {
            searchModuleRight(178,179,color);
        } else if (pane[177].getComponent(0) != null&&color.equals(pane[177].getComponent(0).getBackground())) {
            searchModuleLeft(178,170,color);
        } else if (pane[188].getComponent(0) != null&&color.equals(pane[188].getComponent(0).getBackground())) {
            searchModuleDown(178,color);
        } else if (pane[168].getComponent(0) != null&&color.equals(pane[168].getComponent(0).getBackground())) {
            searchModuleUp(178,color);
        }
    }
    private void button179(){
        Color color = pane[179].getComponent(0).getBackground();
        if (pane[178].getComponent(0) != null&&color.equals(pane[178].getComponent(0).getBackground())) {
            searchModuleLeft(179,170,color);
        } else if (pane[189].getComponent(0) != null&&color.equals(pane[189].getComponent(0).getBackground())) {
            searchModuleDown(179,color);
        } else if (pane[169].getComponent(0) != null&&color.equals(pane[169].getComponent(0).getBackground())) {
            searchModuleUp(179,color);
        }
    }
    private void button180(){
        Color color = pane[180].getComponent(0).getBackground();
        if (pane[181].getComponent(0) != null&&color.equals(pane[181].getComponent(0).getBackground())) {
            searchModuleRight(180,189,color);
        } else if (pane[190].getComponent(0) != null&&color.equals(pane[190].getComponent(0).getBackground())) {
            searchModuleDown(180,color);
        } else if (pane[170].getComponent(0) != null&&color.equals(pane[170].getComponent(0).getBackground())) {
            searchModuleUp(180,color);
        }
    }
    private void button181(){
        Color color = pane[181].getComponent(0).getBackground();
        if (pane[182].getComponent(0) != null&&color.equals(pane[182].getComponent(0).getBackground())) {
            searchModuleRight(181,189,color);
        } else if (pane[180].getComponent(0) != null&&color.equals(pane[180].getComponent(0).getBackground())) {
            searchModuleLeft(181,180,color);
        } else if (pane[191].getComponent(0) != null&&color.equals(pane[191].getComponent(0).getBackground())) {
            searchModuleDown(181,color);
        } else if (pane[171].getComponent(0) != null&&color.equals(pane[171].getComponent(0).getBackground())) {
            searchModuleUp(181,color);
        }
    }
    private void button182(){
        Color color = pane[182].getComponent(0).getBackground();
        if (pane[183].getComponent(0) != null&&color.equals(pane[183].getComponent(0).getBackground())) {
            searchModuleRight(182,189,color);
        } else if (pane[181].getComponent(0) != null&&color.equals(pane[181].getComponent(0).getBackground())) {
            searchModuleLeft(182,180,color);
        } else if (pane[192].getComponent(0) != null&&color.equals(pane[192].getComponent(0).getBackground())) {
            searchModuleDown(182,color);
        } else if (pane[172].getComponent(0) != null&&color.equals(pane[172].getComponent(0).getBackground())) {
            searchModuleUp(182,color);
        }
    }
    private void button183(){
        Color color = pane[183].getComponent(0).getBackground();
        if (pane[184].getComponent(0) != null&&color.equals(pane[184].getComponent(0).getBackground())) {
            searchModuleRight(183,189,color);
        } else if (pane[182].getComponent(0) != null&&color.equals(pane[182].getComponent(0).getBackground())) {
            searchModuleLeft(183,180,color);
        } else if (pane[193].getComponent(0) != null&&color.equals(pane[193].getComponent(0).getBackground())) {
            searchModuleDown(183,color);
        } else if (pane[173].getComponent(0) != null&&color.equals(pane[173].getComponent(0).getBackground())) {
            searchModuleUp(183,color);
        }
    }
    private void button184(){
        Color color = pane[184].getComponent(0).getBackground();
        if (pane[185].getComponent(0) != null&&color.equals(pane[185].getComponent(0).getBackground())) {
            searchModuleRight(184,189,color);
        } else if (pane[183].getComponent(0) != null&&color.equals(pane[183].getComponent(0).getBackground())) {
            searchModuleLeft(184,180,color);
        } else if (pane[194].getComponent(0) != null&&color.equals(pane[194].getComponent(0).getBackground())) {
            searchModuleDown(184,color);
        } else if (pane[174].getComponent(0) != null&&color.equals(pane[174].getComponent(0).getBackground())) {
            searchModuleUp(184,color);
        }
    }
    private void button185(){
        Color color = pane[185].getComponent(0).getBackground();
        if (pane[186].getComponent(0) != null&&color.equals(pane[186].getComponent(0).getBackground())) {
            searchModuleRight(185,189,color);
        } else if (pane[184].getComponent(0) != null&&color.equals(pane[184].getComponent(0).getBackground())) {
            searchModuleLeft(185,180,color);
        } else if (pane[195].getComponent(0) != null&&color.equals(pane[195].getComponent(0).getBackground())) {
            searchModuleDown(185,color);
        } else if (pane[175].getComponent(0) != null&&color.equals(pane[175].getComponent(0).getBackground())) {
            searchModuleUp(185,color);
        }
    }
    private void button186(){
        Color color = pane[186].getComponent(0).getBackground();
        if (pane[187].getComponent(0) != null&&color.equals(pane[187].getComponent(0).getBackground())) {
            searchModuleRight(186,189,color);
        } else if (pane[185].getComponent(0) != null&&color.equals(pane[185].getComponent(0).getBackground())) {
            searchModuleLeft(186,180,color);
        } else if (pane[196].getComponent(0) != null&&color.equals(pane[196].getComponent(0).getBackground())) {
            searchModuleDown(186,color);
        } else if (pane[176].getComponent(0) != null&&color.equals(pane[176].getComponent(0).getBackground())) {
            searchModuleUp(186,color);
        }
    }
    private void button187(){
        Color color = pane[187].getComponent(0).getBackground();
        if (pane[188].getComponent(0) != null&&color.equals(pane[188].getComponent(0).getBackground())) {
            searchModuleRight(187,189,color);
        } else if (pane[186].getComponent(0) != null&&color.equals(pane[186].getComponent(0).getBackground())) {
            searchModuleLeft(187,180,color);
        } else if (pane[197].getComponent(0) != null&&color.equals(pane[197].getComponent(0).getBackground())) {
            searchModuleDown(187,color);
        } else if (pane[177].getComponent(0) != null&&color.equals(pane[177].getComponent(0).getBackground())) {
            searchModuleUp(187,color);
        }
    }
    private void button188(){
        Color color = pane[188].getComponent(0).getBackground();
        if (pane[189].getComponent(0) != null&&color.equals(pane[189].getComponent(0).getBackground())) {
            searchModuleRight(188,189,color);
        } else if (pane[187].getComponent(0) != null&&color.equals(pane[187].getComponent(0).getBackground())) {
            searchModuleLeft(188,180,color);
        } else if (pane[198].getComponent(0) != null&&color.equals(pane[198].getComponent(0).getBackground())) {
            searchModuleDown(188,color);
        } else if (pane[178].getComponent(0) != null&&color.equals(pane[178].getComponent(0).getBackground())) {
            searchModuleUp(188,color);
        }
    }
    private void button189(){
        Color color = pane[189].getComponent(0).getBackground();
        if (pane[188].getComponent(0) != null&&color.equals(pane[188].getComponent(0).getBackground())) {
            searchModuleLeft(189,180,color);
        } else if (pane[199].getComponent(0) != null&&color.equals(pane[199].getComponent(0).getBackground())) {
            searchModuleDown(189,color);
        } else if (pane[179].getComponent(0) != null&&color.equals(pane[179].getComponent(0).getBackground())) {
            searchModuleUp(189,color);
        }
    }
    private void button190(){
        Color color = pane[190].getComponent(0).getBackground();
        if (pane[191].getComponent(0) != null&&color.equals(pane[191].getComponent(0).getBackground())) {
            searchModuleRight(190,199,color);
        } else if (pane[180].getComponent(0) != null&&color.equals(pane[180].getComponent(0).getBackground())) {
            searchModuleUp(190,color);
        }
    }
    private void button191(){
        Color color = pane[191].getComponent(0).getBackground();
        if (pane[192].getComponent(0) != null&&color.equals(pane[192].getComponent(0).getBackground())) {
            searchModuleRight(191,199,color);
        } else if (pane[190].getComponent(0) != null&&color.equals(pane[190].getComponent(0).getBackground())) {
            searchModuleLeft(191,190,color);
        } else if (pane[181].getComponent(0) != null&&color.equals(pane[181].getComponent(0).getBackground())) {
            searchModuleUp(191,color);
        }
    }
    private void button192(){
        Color color = pane[192].getComponent(0).getBackground();
        if (pane[193].getComponent(0) != null&&color.equals(pane[193].getComponent(0).getBackground())) {
            searchModuleRight(192,199,color);
        } else if (pane[191].getComponent(0) != null&&color.equals(pane[191].getComponent(0).getBackground())) {
            searchModuleLeft(192,190,color);
        } else if (pane[182].getComponent(0) != null&&color.equals(pane[182].getComponent(0).getBackground())) {
            searchModuleUp(192,color);
        }
    }
    private void button193(){
        Color color = pane[193].getComponent(0).getBackground();
        if (pane[194].getComponent(0) != null&&color.equals(pane[194].getComponent(0).getBackground())) {
            searchModuleRight(193,199,color);
        } else if (pane[192].getComponent(0) != null&&color.equals(pane[192].getComponent(0).getBackground())) {
            searchModuleLeft(193,190,color);
        } else if (pane[183].getComponent(0) != null&&color.equals(pane[183].getComponent(0).getBackground())) {
            searchModuleUp(193,color);
        }
    }
    private void button194(){
        Color color = pane[194].getComponent(0).getBackground();
        if (pane[195].getComponent(0) != null&&color.equals(pane[195].getComponent(0).getBackground())) {
            searchModuleRight(194,199,color);
        } else if (pane[193].getComponent(0) != null&&color.equals(pane[193].getComponent(0).getBackground())) {
            searchModuleLeft(194,190,color);
        } else if (pane[184].getComponent(0) != null&&color.equals(pane[184].getComponent(0).getBackground())) {
            searchModuleUp(194,color);
        }
    }
    private void button195(){
        Color color = pane[195].getComponent(0).getBackground();
        if (pane[196].getComponent(0) != null&&color.equals(pane[196].getComponent(0).getBackground())) {
            searchModuleRight(195,199,color);
        } else if (pane[194].getComponent(0) != null&&color.equals(pane[194].getComponent(0).getBackground())) {
            searchModuleLeft(195,190,color);
        } else if (pane[185].getComponent(0) != null&&color.equals(pane[185].getComponent(0).getBackground())) {
            searchModuleUp(195,color);
        }
    }
    private void button196(){
        Color color = pane[196].getComponent(0).getBackground();
        if (pane[197].getComponent(0) != null&&color.equals(pane[197].getComponent(0).getBackground())) {
            searchModuleRight(196,199,color);
        } else if (pane[195].getComponent(0) != null&&color.equals(pane[195].getComponent(0).getBackground())) {
            searchModuleLeft(196,190,color);
        } else if (pane[186].getComponent(0) != null&&color.equals(pane[186].getComponent(0).getBackground())) {
            searchModuleUp(196,color);
        }
    }
    private void button197(){
        Color color = pane[197].getComponent(0).getBackground();
        if (pane[198].getComponent(0) != null&&color.equals(pane[198].getComponent(0).getBackground())) {
            searchModuleRight(197,199,color);
        } else if (pane[196].getComponent(0) != null&&color.equals(pane[196].getComponent(0).getBackground())) {
            searchModuleLeft(197,190,color);
        } else if (pane[187].getComponent(0) != null&&color.equals(pane[187].getComponent(0).getBackground())) {
            searchModuleUp(197,color);
        }
    }
    private void button198(){
        Color color = pane[198].getComponent(0).getBackground();
        if (pane[199].getComponent(0) != null&&color.equals(pane[199].getComponent(0).getBackground())) {
            searchModuleRight(198,199,color);
        } else if (pane[197].getComponent(0) != null&&color.equals(pane[197].getComponent(0).getBackground())) {
            searchModuleLeft(198,190,color);
        } else if (pane[188].getComponent(0) != null&&color.equals(pane[188].getComponent(0).getBackground())) {
            searchModuleUp(198,color);
        }
    }
    private void button199(){
        Color color = pane[199].getComponent(0).getBackground();
        if (pane[198].getComponent(0) != null&&color.equals(pane[198].getComponent(0).getBackground())) {
            searchModuleLeft(199,190,color);
        } else if (pane[189].getComponent(0) != null&&color.equals(pane[189].getComponent(0).getBackground())) {
            searchModuleUp(199,color);
        }
    }
    private void choice(JButton []clrBtn,int k){
        writeUndo(clrBtn);
        undoBtn.setEnabled(true);
        redoBtn.setEnabled(false);
        if (clrBtn[k].getParent().equals(pane[0])) {
            button0();
        } else if (clrBtn[k].getParent().equals(pane[1])) {
            button1();
        } else if (clrBtn[k].getParent().equals(pane[2])) {
            button2();
        } else if (clrBtn[k].getParent().equals(pane[3])) {
            button3();
        } else if (clrBtn[k].getParent().equals(pane[4])) {
            button4();
        } else if (clrBtn[k].getParent().equals(pane[5])) {
            button5();
        } else if (clrBtn[k].getParent().equals(pane[6])) {
            button6();
        } else if (clrBtn[k].getParent().equals(pane[7])) {
            button7();
        } else if (clrBtn[k].getParent().equals(pane[8])) {
            button8();
        } else if (clrBtn[k].getParent().equals(pane[9])) {
            button9();
        } else if (clrBtn[k].getParent().equals(pane[10])) {
            button10();
        } else if (clrBtn[k].getParent().equals(pane[11])) {
            button11();
        } else if (clrBtn[k].getParent().equals(pane[12])) {
            button12();
        } else if (clrBtn[k].getParent().equals(pane[13])) {
            button13();
        } else if (clrBtn[k].getParent().equals(pane[14])) {
            button14();
        } else if (clrBtn[k].getParent().equals(pane[15])) {
            button15();
        } else if (clrBtn[k].getParent().equals(pane[16])) {
            button16();
        } else if (clrBtn[k].getParent().equals(pane[17])) {
            button17();
        } else if (clrBtn[k].getParent().equals(pane[18])) {
            button18();
        } else if (clrBtn[k].getParent().equals(pane[19])) {
            button19();
        }else if (clrBtn[k].getParent().equals(pane[20])) {
            button20();
        } else if (clrBtn[k].getParent().equals(pane[21])) {
            button21();
        } else if (clrBtn[k].getParent().equals(pane[22])) {
            button22();
        } else if (clrBtn[k].getParent().equals(pane[23])) {
            button23();
        } else if (clrBtn[k].getParent().equals(pane[24])) {
            button24();
        } else if (clrBtn[k].getParent().equals(pane[25])) {
            button25();
        } else if (clrBtn[k].getParent().equals(pane[26])) {
            button26();
        } else if (clrBtn[k].getParent().equals(pane[27])) {
            button27();
        } else if (clrBtn[k].getParent().equals(pane[28])) {
            button28();
        } else if (clrBtn[k].getParent().equals(pane[29])) {
            button29();
        }else if (clrBtn[k].getParent().equals(pane[30])) {
            button30();
        } else if (clrBtn[k].getParent().equals(pane[31])) {
            button31();
        } else if (clrBtn[k].getParent().equals(pane[32])) {
            button32();
        } else if (clrBtn[k].getParent().equals(pane[33])) {
            button33();
        } else if (clrBtn[k].getParent().equals(pane[34])) {
            button34();
        } else if (clrBtn[k].getParent().equals(pane[35])) {
            button35();
        } else if (clrBtn[k].getParent().equals(pane[36])) {
            button36();
        } else if (clrBtn[k].getParent().equals(pane[37])) {
            button37();
        } else if (clrBtn[k].getParent().equals(pane[38])) {
            button38();
        } else if (clrBtn[k].getParent().equals(pane[39])) {
            button39();
        }else if (clrBtn[k].getParent().equals(pane[40])) {
            button40();
        } else if (clrBtn[k].getParent().equals(pane[41])) {
            button41();
        } else if (clrBtn[k].getParent().equals(pane[42])) {
            button42();
        } else if (clrBtn[k].getParent().equals(pane[43])) {
            button43();
        } else if (clrBtn[k].getParent().equals(pane[44])) {
            button44();
        } else if (clrBtn[k].getParent().equals(pane[45])) {
            button45();
        } else if (clrBtn[k].getParent().equals(pane[46])) {
            button46();
        } else if (clrBtn[k].getParent().equals(pane[47])) {
            button47();
        } else if (clrBtn[k].getParent().equals(pane[48])) {
            button48();
        } else if (clrBtn[k].getParent().equals(pane[49])) {
            button49();
        }else if (clrBtn[k].getParent().equals(pane[50])) {
            button50();
        } else if (clrBtn[k].getParent().equals(pane[51])) {
            button51();
        } else if (clrBtn[k].getParent().equals(pane[52])) {
            button52();
        } else if (clrBtn[k].getParent().equals(pane[53])) {
            button53();
        } else if (clrBtn[k].getParent().equals(pane[54])) {
            button54();
        } else if (clrBtn[k].getParent().equals(pane[55])) {
            button55();
        } else if (clrBtn[k].getParent().equals(pane[56])) {
            button56();
        } else if (clrBtn[k].getParent().equals(pane[57])) {
            button57();
        } else if (clrBtn[k].getParent().equals(pane[58])) {
            button58();
        } else if (clrBtn[k].getParent().equals(pane[59])) {
            button59();
        }else if (clrBtn[k].getParent().equals(pane[60])) {
            button60();
        } else if (clrBtn[k].getParent().equals(pane[61])) {
            button61();
        } else if (clrBtn[k].getParent().equals(pane[62])) {
            button62();
        } else if (clrBtn[k].getParent().equals(pane[63])) {
            button63();
        } else if (clrBtn[k].getParent().equals(pane[64])) {
            button64();
        } else if (clrBtn[k].getParent().equals(pane[65])) {
            button65();
        } else if (clrBtn[k].getParent().equals(pane[66])) {
            button66();
        } else if (clrBtn[k].getParent().equals(pane[67])) {
            button67();
        } else if (clrBtn[k].getParent().equals(pane[68])) {
            button68();
        } else if (clrBtn[k].getParent().equals(pane[69])) {
            button69();
        }else if (clrBtn[k].getParent().equals(pane[70])) {
            button70();
        } else if (clrBtn[k].getParent().equals(pane[71])) {
            button71();
        } else if (clrBtn[k].getParent().equals(pane[72])) {
            button72();
        } else if (clrBtn[k].getParent().equals(pane[73])) {
            button73();
        } else if (clrBtn[k].getParent().equals(pane[74])) {
            button74();
        } else if (clrBtn[k].getParent().equals(pane[75])) {
            button75();
        } else if (clrBtn[k].getParent().equals(pane[76])) {
            button76();
        } else if (clrBtn[k].getParent().equals(pane[77])) {
            button77();
        } else if (clrBtn[k].getParent().equals(pane[78])) {
            button78();
        } else if (clrBtn[k].getParent().equals(pane[79])) {
            button79();
        }else if (clrBtn[k].getParent().equals(pane[80])) {
            button80();
        } else if (clrBtn[k].getParent().equals(pane[81])) {
            button81();
        } else if (clrBtn[k].getParent().equals(pane[82])) {
            button82();
        } else if (clrBtn[k].getParent().equals(pane[83])) {
            button83();
        } else if (clrBtn[k].getParent().equals(pane[84])) {
            button84();
        } else if (clrBtn[k].getParent().equals(pane[85])) {
            button85();
        } else if (clrBtn[k].getParent().equals(pane[86])) {
            button86();
        } else if (clrBtn[k].getParent().equals(pane[87])) {
            button87();
        } else if (clrBtn[k].getParent().equals(pane[88])) {
            button88();
        } else if (clrBtn[k].getParent().equals(pane[89])) {
            button89();
        }else if (clrBtn[k].getParent().equals(pane[90])) {
            button90();
        } else if (clrBtn[k].getParent().equals(pane[91])) {
            button91();
        } else if (clrBtn[k].getParent().equals(pane[92])) {
            button92();
        } else if (clrBtn[k].getParent().equals(pane[93])) {
            button93();
        } else if (clrBtn[k].getParent().equals(pane[94])) {
            button94();
        } else if (clrBtn[k].getParent().equals(pane[95])) {
            button95();
        } else if (clrBtn[k].getParent().equals(pane[96])) {
            button96();
        } else if (clrBtn[k].getParent().equals(pane[97])) {
            button97();
        } else if (clrBtn[k].getParent().equals(pane[98])) {
            button98();
        } else if (clrBtn[k].getParent().equals(pane[99])) {
            button99();
        }if (clrBtn[k].getParent().equals(pane[100])) {
            button100();
        } else if (clrBtn[k].getParent().equals(pane[101])) {
            button101();
        } else if (clrBtn[k].getParent().equals(pane[102])) {
            button102();
        } else if (clrBtn[k].getParent().equals(pane[103])) {
            button103();
        } else if (clrBtn[k].getParent().equals(pane[104])) {
            button104();
        } else if (clrBtn[k].getParent().equals(pane[105])) {
            button105();
        } else if (clrBtn[k].getParent().equals(pane[106])) {
            button106();
        } else if (clrBtn[k].getParent().equals(pane[107])) {
            button107();
        } else if (clrBtn[k].getParent().equals(pane[108])) {
            button108();
        } else if (clrBtn[k].getParent().equals(pane[109])) {
            button109();
        } else if (clrBtn[k].getParent().equals(pane[110])) {
            button110();
        } else if (clrBtn[k].getParent().equals(pane[111])) {
            button111();
        } else if (clrBtn[k].getParent().equals(pane[112])) {
            button112();
        } else if (clrBtn[k].getParent().equals(pane[113])) {
            button113();
        } else if (clrBtn[k].getParent().equals(pane[114])) {
            button114();
        } else if (clrBtn[k].getParent().equals(pane[115])) {
            button115();
        } else if (clrBtn[k].getParent().equals(pane[116])) {
            button116();
        } else if (clrBtn[k].getParent().equals(pane[117])) {
            button117();
        } else if (clrBtn[k].getParent().equals(pane[118])) {
            button118();
        } else if (clrBtn[k].getParent().equals(pane[119])) {
            button119();
        }else if (clrBtn[k].getParent().equals(pane[120])) {
            button120();
        } else if (clrBtn[k].getParent().equals(pane[121])) {
            button121();
        } else if (clrBtn[k].getParent().equals(pane[122])) {
            button122();
        } else if (clrBtn[k].getParent().equals(pane[123])) {
            button123();
        } else if (clrBtn[k].getParent().equals(pane[124])) {
            button124();
        } else if (clrBtn[k].getParent().equals(pane[125])) {
            button125();
        } else if (clrBtn[k].getParent().equals(pane[126])) {
            button126();
        } else if (clrBtn[k].getParent().equals(pane[127])) {
            button127();
        } else if (clrBtn[k].getParent().equals(pane[128])) {
            button128();
        } else if (clrBtn[k].getParent().equals(pane[129])) {
            button129();
        }else if (clrBtn[k].getParent().equals(pane[130])) {
            button130();
        } else if (clrBtn[k].getParent().equals(pane[131])) {
            button131();
        } else if (clrBtn[k].getParent().equals(pane[132])) {
            button132();
        } else if (clrBtn[k].getParent().equals(pane[133])) {
            button133();
        } else if (clrBtn[k].getParent().equals(pane[134])) {
            button134();
        } else if (clrBtn[k].getParent().equals(pane[135])) {
            button135();
        } else if (clrBtn[k].getParent().equals(pane[136])) {
            button136();
        } else if (clrBtn[k].getParent().equals(pane[137])) {
            button137();
        } else if (clrBtn[k].getParent().equals(pane[138])) {
            button138();
        } else if (clrBtn[k].getParent().equals(pane[139])) {
            button139();
        }else if (clrBtn[k].getParent().equals(pane[140])) {
            button140();
        } else if (clrBtn[k].getParent().equals(pane[141])) {
            button141();
        } else if (clrBtn[k].getParent().equals(pane[142])) {
            button142();
        } else if (clrBtn[k].getParent().equals(pane[143])) {
            button143();
        } else if (clrBtn[k].getParent().equals(pane[144])) {
            button144();
        } else if (clrBtn[k].getParent().equals(pane[145])) {
            button145();
        } else if (clrBtn[k].getParent().equals(pane[146])) {
            button146();
        } else if (clrBtn[k].getParent().equals(pane[147])) {
            button147();
        } else if (clrBtn[k].getParent().equals(pane[148])) {
            button148();
        } else if (clrBtn[k].getParent().equals(pane[149])) {
            button149();
        }else if (clrBtn[k].getParent().equals(pane[150])) {
            button150();
        } else if (clrBtn[k].getParent().equals(pane[151])) {
            button151();
        } else if (clrBtn[k].getParent().equals(pane[152])) {
            button152();
        } else if (clrBtn[k].getParent().equals(pane[153])) {
            button153();
        } else if (clrBtn[k].getParent().equals(pane[154])) {
            button154();
        } else if (clrBtn[k].getParent().equals(pane[155])) {
            button155();
        } else if (clrBtn[k].getParent().equals(pane[156])) {
            button156();
        } else if (clrBtn[k].getParent().equals(pane[157])) {
            button157();
        } else if (clrBtn[k].getParent().equals(pane[158])) {
            button158();
        } else if (clrBtn[k].getParent().equals(pane[159])) {
            button159();
        }else if (clrBtn[k].getParent().equals(pane[160])) {
            button160();
        } else if (clrBtn[k].getParent().equals(pane[161])) {
            button161();
        } else if (clrBtn[k].getParent().equals(pane[162])) {
            button162();
        } else if (clrBtn[k].getParent().equals(pane[163])) {
            button163();
        } else if (clrBtn[k].getParent().equals(pane[164])) {
            button164();
        } else if (clrBtn[k].getParent().equals(pane[165])) {
            button165();
        } else if (clrBtn[k].getParent().equals(pane[166])) {
            button166();
        } else if (clrBtn[k].getParent().equals(pane[167])) {
            button167();
        } else if (clrBtn[k].getParent().equals(pane[168])) {
            button168();
        } else if (clrBtn[k].getParent().equals(pane[169])) {
            button169();
        }else if (clrBtn[k].getParent().equals(pane[170])) {
            button170();
        } else if (clrBtn[k].getParent().equals(pane[171])) {
            button171();
        } else if (clrBtn[k].getParent().equals(pane[172])) {
            button172();
        } else if (clrBtn[k].getParent().equals(pane[173])) {
            button173();
        } else if (clrBtn[k].getParent().equals(pane[174])) {
            button174();
        } else if (clrBtn[k].getParent().equals(pane[175])) {
            button175();
        } else if (clrBtn[k].getParent().equals(pane[176])) {
            button176();
        } else if (clrBtn[k].getParent().equals(pane[177])) {
            button177();
        } else if (clrBtn[k].getParent().equals(pane[178])) {
            button178();
        } else if (clrBtn[k].getParent().equals(pane[179])) {
            button179();
        }else if (clrBtn[k].getParent().equals(pane[180])) {
            button180();
        } else if (clrBtn[k].getParent().equals(pane[181])) {
            button181();
        } else if (clrBtn[k].getParent().equals(pane[182])) {
            button182();
        } else if (clrBtn[k].getParent().equals(pane[183])) {
            button183();
        } else if (clrBtn[k].getParent().equals(pane[184])) {
            button184();
        } else if (clrBtn[k].getParent().equals(pane[185])) {
            button185();
        } else if (clrBtn[k].getParent().equals(pane[186])) {
            button186();
        } else if (clrBtn[k].getParent().equals(pane[187])) {
            button187();
        } else if (clrBtn[k].getParent().equals(pane[188])) {
            button188();
        } else if (clrBtn[k].getParent().equals(pane[189])) {
            button189();
        }else if (clrBtn[k].getParent().equals(pane[190])) {
            button190();
        } else if (clrBtn[k].getParent().equals(pane[191])) {
            button191();
        } else if (clrBtn[k].getParent().equals(pane[192])) {
            button192();
        } else if (clrBtn[k].getParent().equals(pane[193])) {
            button193();
        } else if (clrBtn[k].getParent().equals(pane[194])) {
            button194();
        } else if (clrBtn[k].getParent().equals(pane[195])) {
            button195();
        } else if (clrBtn[k].getParent().equals(pane[196])) {
            button196();
        } else if (clrBtn[k].getParent().equals(pane[197])) {
            button197();
        } else if (clrBtn[k].getParent().equals(pane[198])) {
            button198();
        } else if (clrBtn[k].getParent().equals(pane[199])) {
            button199();
        }

        writeRedo(clrBtn);
    }
    private void searchModuleRight(int x, int y, Color cr){
        int r;
        for ( r=x; r<=y; r++){
            if (pane[r].getComponent(0)!=null&&pane[r].getComponent(0).getBackground().equals(cr)){
                    pane[r].getComponent(0).setBackground(gy);
                    if(r<190&&pane[r+10].getComponent(0) != null&&cr.equals(pane[r+10].getComponent(0).getBackground())){
                            for (int d=(r+10);d<200;d=d+10){
                                if (cr.equals(pane[d].getComponent(0).getBackground())){
                                    pane[d].getComponent(0).setBackground(gy);
                                    downVerticalSearch(d,cr);
                                }
                                else{
                                    break;
                                }
                            }
                    }
                    if(r>9&&pane[r-10].getComponent(0) != null&&cr.equals(pane[r-10].getComponent(0).getBackground())){
                            for (int u=(r-10);u>=0;u=u-10){
                                if (cr.equals(pane[u].getComponent(0).getBackground())){
                                    pane[u].getComponent(0).setBackground(gy);
                                    upVerticalSearch(u,cr);
                                }
                                else{
                                    break;
                                }
                            }
                    }
            }
            else{
                break;
            }
        }
        int l;
        if (x>9){
            String fString = Integer.toString(x/10);
            String xString = fString + "0";
            int xInt = Integer.parseInt(xString);
            for(l=(x-1);l>=(xInt);l--){
                if (pane[l].getComponent(0) != null&&cr.equals(pane[l].getComponent(0).getBackground())){
                        pane[l].getComponent(0).setBackground(gy);
                        if(l<190&&pane[l+10].getComponent(0) != null&&cr.equals(pane[l+10].getComponent(0).getBackground())){
                                for (int d=(l+10);d<200;d=d+10){
                                    if (cr.equals(pane[d].getComponent(0).getBackground())){
                                        downVerticalSearch(d,cr);
                                    }
                                    else{
                                        break;
                                    }
                                }
                        }
                        if(l>9&&pane[l-10].getComponent(0) != null&&cr.equals(pane[l-10].getComponent(0).getBackground())){
                                for (int u=(l-10);u>=0;u=u-10){
                                    if (cr.equals(pane[u].getComponent(0).getBackground())){
                                        pane[u].getComponent(0).setBackground(gy);
                                        upVerticalSearch(u,cr);
                                    }
                                    else{
                                        break;
                                    }
                                }
                        }
                }
                else{
                    break;
                }
            }
        }
        else if (x>0){
            for (l=(x-1);l<=0;l--){
                if (pane[l].getComponent(0) != null&&cr.equals(pane[l].getComponent(0).getBackground())){
                        pane[l].getComponent(0).setBackground(gy);
                        if(l<190&&pane[l+10].getComponent(0) != null&&cr.equals(pane[l+10].getComponent(0).getBackground())){
                                for (int d=(l+10);d<200;d=d+10){
                                    if (cr.equals(pane[d].getComponent(0).getBackground())){
                                        pane[d].getComponent(0).setBackground(gy);
                                        downVerticalSearch(d,cr);
                                    }
                                    else{
                                        break;
                                    }
                                }
                        }
                        if(l>9&&pane[l-10].getComponent(0) != null&&cr.equals(pane[l-10].getComponent(0).getBackground())){
                                for (int u=(l-10);u>=0;u=u-10){
                                    if (cr.equals(pane[u].getComponent(0).getBackground())){
                                        pane[u].getComponent(0).setBackground(gy);
                                        upVerticalSearch(u,cr);
                                    }
                                    else{
                                        break;
                                    }
                                }
                        }
                }
                else{
                    break;
                }
            }
        }
        moveComponentVertically(x);
    }
    private void searchModuleLeft(int x, int y, Color cr){
        int l;
        for ( l=x; l>=y; l--){
                if (pane[l].getComponent(0).getBackground().equals(cr)){
                    pane[l].getComponent(0).setBackground(gy);
                        if (l<190&&cr.equals(pane[l+10].getComponent(0).getBackground())){
                            for (int d=(l+10);d<200;d=d+10){
                                if (cr.equals(pane[d].getComponent(0).getBackground())){
                                    pane[d].getComponent(0).setBackground(gy);
                                    downVerticalSearch(d,cr);
                                }
                                else{
                                    break;
                                }
                            }
                        }
                        if (l>9&&cr.equals(pane[l-10].getComponent(0).getBackground())){
                            for (int u=(l-10);u>=0;u=u-10){
                                    if (cr.equals(pane[u].getComponent(0).getBackground())){
                                        pane[u].getComponent(0).setBackground(gy);
                                        upVerticalSearch(u,cr);

                                    }
                                    else{
                                        break;
                                    }
                            }
                        }

                }
                else{
                    break;
                }

        }
        moveComponentVertically(x);
    }
    private void searchModuleDown(int x, Color cr){
        int d;
        for ( d=x; d<200; d=d+10){
                if (pane[d].getComponent(0).getBackground().equals(cr)){
                    pane[d].getComponent(0).setBackground(gy);
                    downVerticalSearch(d,cr);
                }
                else{
                    break;
                }

        }
            if (x>9&&cr.equals(pane[x-10].getComponent(0).getBackground())){
                for (int u=(x-10);u>=0;u=u-10){
                    if (cr.equals(pane[u].getComponent(0).getBackground())){
                        pane[u].getComponent(0).setBackground(gy);
                        upVerticalSearch(u,cr);
                    }
                    else{
                        break;
                    }
                }
            }
        moveComponentVertically(x);
    }
    private void searchModuleUp(int x, Color cr){
        int u;
        for (u=x;u>=0;u=u-10){
            if (cr.equals(pane[u].getComponent(0).getBackground())){
                pane[u].getComponent(0).setBackground(gy);
                upVerticalSearch(u,cr);
            } else{
                break;
            }
        }
        moveComponentVertically(x);
    }
    private void downVerticalSearch(int d, Color cr){
        if (d+1<200&&pane[d+1].getComponent(0).getBackground().equals(cr)){
            if (d>9){
                String pString = Integer.toString(d/10);
                String rString = pString + "9";
                int rInt = Integer.parseInt(rString);
                for(int dr=(d+1);dr<=(rInt);dr++){
                    if(cr.equals(pane[dr].getComponent(0).getBackground())){
                        pane[dr].getComponent(0).setBackground(gy);
                        if ((dr+10)<190&&pane[dr+10].getComponent(0).getBackground().equals(cr)){
                            for (int drd=(dr+10);drd<200;drd=drd+10){
                                if(cr.equals(pane[drd].getComponent(0).getBackground())) {
                                    pane[drd].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((dr-10)>9&&pane[dr-10].getComponent(0).getBackground().equals(cr)){
                            for (int dru=(dr-10);dru>=0;dru=dru-10){
                                if(cr.equals(pane[dru].getComponent(0).getBackground())) {
                                    pane[dru].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else if (d>-1){
                for (int dr=(d+1);dr<=9;dr++){
                    if(cr.equals(pane[dr].getComponent(0).getBackground())){
                        pane[dr].getComponent(0).setBackground(gy);
                        if ((dr+10)<190&&pane[dr+10].getComponent(0).getBackground().equals(cr)){
                            for (int drd=(dr+10);drd<200;drd=drd+10){
                                if(cr.equals(pane[drd].getComponent(0).getBackground())) {
                                    pane[drd].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((dr-10)>9&&pane[dr-10].getComponent(0).getBackground().equals(cr)){
                            for (int dru=(dr-10);dru>=0;dru=dru-10){
                                if(cr.equals(pane[dru].getComponent(0).getBackground())) {
                                    pane[dru].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        }
        if (pane[d-1].getComponent(0).getBackground().equals(cr)){
            if (d>9){
                String pString = Integer.toString(d/10);
                String lString = pString + "0";
                int lInt = Integer.parseInt(lString);
                for(int dl=(d-1);dl>=(lInt);dl--){
                    if(cr.equals(pane[dl].getComponent(0).getBackground())){
                        pane[dl].getComponent(0).setBackground(gy);
                        if ((dl+10)<190&&pane[dl+10].getComponent(0).getBackground().equals(cr)){
                            for (int dld=(dl+10);dld<200;dld=dld+10){
                                if(cr.equals(pane[dld].getComponent(0).getBackground())) {
                                    pane[dld].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((dl-10)>9&&pane[dl-10].getComponent(0).getBackground().equals(cr)){
                            for (int dlu=(dl-10);dlu>=0;dlu=dlu-10){
                                if(cr.equals(pane[dlu].getComponent(0).getBackground())) {
                                    pane[dlu].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else if (d>-1){
                for (int dl=(d-1);dl>=0;dl--){
                    if(cr.equals(pane[dl].getComponent(0).getBackground())){
                        pane[dl].getComponent(0).setBackground(gy);
                        if ((dl+10)<190&&pane[dl+10].getComponent(0).getBackground().equals(cr)){
                            for (int dld=(dl+10);dld<200;dld=dld+10){
                                if(cr.equals(pane[dld].getComponent(0).getBackground())) {
                                    pane[dld].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((dl-10)>9&&pane[dl-10].getComponent(0).getBackground().equals(cr)){
                            for (int dlu=(dl-10);dlu>=0;dlu=dlu-10){
                                if(cr.equals(pane[dlu].getComponent(0).getBackground())) {
                                    pane[dlu].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        }
    }
    private void upVerticalSearch(int u,Color cr){
        if ((u+1)!=200&&pane[u+1].getComponent(0).getBackground().equals(cr)){
            if (u>9){
                String pString = Integer.toString(u/10);
                String rString = pString + "9";
                int rInt = Integer.parseInt(rString);
                for(int ur=(u+1);ur<=(rInt);ur++){
                    if(cr.equals(pane[ur].getComponent(0).getBackground())){
                        pane[ur].getComponent(0).setBackground(gy);
                        if ((ur+10)<190&&pane[ur+10].getComponent(0).getBackground().equals(cr)){
                            for (int urd=(ur+10);urd<200;urd=urd+10){
                                if(cr.equals(pane[urd].getComponent(0).getBackground())) {
                                    pane[urd].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((ur-10)>9&&pane[ur-10].getComponent(0).getBackground().equals(cr)){
                            for (int uru=(ur-10);uru>=0;uru=uru-10){
                                if(cr.equals(pane[uru].getComponent(0).getBackground())) {
                                    pane[uru].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
            else if (u>-1){
                for (int ur=(u+1);ur<=9;ur++){
                    if(cr.equals(pane[ur].getComponent(0).getBackground())){
                        pane[ur].getComponent(0).setBackground(gy);
                        if ((ur+10)<190&&pane[ur+10].getComponent(0).getBackground().equals(cr)){
                            for (int urd=(ur+10);urd<200;urd=urd+10){
                                if(cr.equals(pane[urd].getComponent(0).getBackground())) {
                                    pane[urd].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                        if ((ur-10)>9&&pane[ur-10].getComponent(0).getBackground().equals(cr)){
                            for (int uru=(ur-10);uru>=0;uru=uru-10){
                                if(cr.equals(pane[uru].getComponent(0).getBackground())) {
                                    pane[uru].getComponent(0).setBackground(gy);
                                }else{
                                    break;
                                }
                            }
                        }
                    }
                    else{
                        break;
                    }
                }
            }
        }
        if (u>0&&pane[u-1].getComponent(0).getBackground().equals(cr)) {
            if (u > 9) {
                String pString = Integer.toString(u / 10);
                String lString = pString + "0";
                int lInt = Integer.parseInt(lString);
                for (int ul = (u - 1); ul >= (lInt); ul--) {
                    if (cr.equals(pane[ul].getComponent(0).getBackground())) {
                        pane[ul].getComponent(0).setBackground(gy);
                        if ((ul + 10) < 190 && pane[ul + 10].getComponent(0).getBackground().equals(cr)) {
                            for (int uld = (ul + 10); uld < 200; uld = uld + 10) {
                                if (cr.equals(pane[uld].getComponent(0).getBackground())) {
                                    pane[uld].getComponent(0).setBackground(gy);
                                } else {
                                    break;
                                }
                            }
                        }
                        if ((ul - 10) > 9 && pane[ul - 10].getComponent(0).getBackground().equals(cr)) {
                            for (int ulu = (ul - 10); ulu >= 0; ulu = ulu - 10) {
                                if (cr.equals(pane[ulu].getComponent(0).getBackground())) {
                                    pane[ulu].getComponent(0).setBackground(gy);
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
            } else if (u > -1) {
                for (int ul = (u - 1); ul >= 0; ul--) {
                    if (cr.equals(pane[ul].getComponent(0).getBackground())) {
                        pane[ul].getComponent(0).setBackground(gy);
                        if ((ul + 10) < 190 && pane[ul + 10].getComponent(0).getBackground().equals(cr)) {
                            for (int uld = (ul + 10); uld < 200; uld = uld + 10) {
                                if (cr.equals(pane[uld].getComponent(0).getBackground())) {
                                    pane[uld].getComponent(0).setBackground(gy);
                                } else {
                                    break;
                                }
                            }
                        }
                        if ((ul - 10) > 9 && pane[ul - 10].getComponent(0).getBackground().equals(cr)) {
                            for (int ulu = (ul - 10); ulu >= 0; ulu = ulu - 10) {
                                if (cr.equals(pane[ulu].getComponent(0).getBackground())) {
                                    pane[ulu].getComponent(0).setBackground(gy);
                                } else {
                                    break;
                                }
                            }
                        }
                    } else {
                        break;
                    }
                }
            }

        }
    }
    private void moveComponentVertically(int x){
        System.out.println(x);
        for (int m = 0;m<200;m++){
            if (pane[m].getComponent(0).getBackground().equals(gy)){
                int d=1;
                int y =((m)/10);
                y=y*10;
                y=(m)-y;
                do {
                    for (int l=m;l>=10;l=l-10){
                        pane[l].getComponent(0).setBackground(pane[l - 10].getComponent(0).getBackground());
                    }
                    d=d+1;
                    if (!pane[m].getComponent(0).getBackground().equals(gy)){
                        break;
                    }
                    if (d==20){
                        break;
                    }
                }while (pane[m].getComponent(0).getBackground().equals(gy));
                pane[y].getComponent(0).setBackground(gy);
            }
        }
        playSound();
        moveComponentHorizontally();
        addButton();
        setScore();
        gameOver();
    }
    private void moveComponentHorizontally(){
        for (int i=190;i<200;i++){
            if (pane[i].getComponent(0).getBackground().equals(gy)){
                for (int j=i;j>0;j=j-10){
                    int l=j/10;
                    String m = Integer.toString(l);
                    l = Integer.parseInt(m+"9");
                    int n=0;
                    do {
                        for (int k=j;k<l;k++){
                            pane[k].getComponent(0).setBackground(pane[k+1].getComponent(0).getBackground());
                        }
                        pane[l].getComponent(0).setBackground(gy);
                        n++;
                        if (n==9){
                            break;
                        }
                    }while (pane[i].getComponent(0).getBackground().equals(gy));
                }
            }
        }
    }
    private void addButton(){
        Random rn = new Random();
        if (init<25){
            for (int i=0;i<200;i++){
                int j = rn.nextInt(8);
                if (pane[i].getComponent(0).getBackground().equals(gy)){
                    if (j == 1) {
                        Color C = new Color(255, 0, 0); //red
                        btn[i].setBackground(C);
                    } else if (j == 2) {
                        Color C = new Color(0, 255, 0); //green
                        btn[i].setBackground(C);
                    } else if (j == 3) {
                        Color C = new Color(0, 0, 255); //blue
                        btn[i].setBackground(C);
                    } else if (j == 4) {
                        Color C = new Color(0, 0, 0); //black
                        btn[i].setBackground(C);
                    } else if (j == 5) {
                        Color C = new Color(255, 0, 255); //purple
                        btn[i].setBackground(C);
                    } else if (j == 6) {
                        Color C = new Color(0, 255, 255);//cyan
                        btn[i].setBackground(C);
                    } else if (j == 7) {
                        Color C = new Color(255, 255, 0); //yellow
                        btn[i].setBackground(C);
                    } else if (j == 8) {
                        Color C = new Color(128, 128, 128);
                        btn[i].setBackground(C);
                    } else if (j == 0) {
                        Color C = new Color(139, 69, 19);
                        btn[i].setBackground(C);
                    } else {
                        Color C = new Color(0, 0, 0);
                        btn[i].setBackground(C);
                    }
                }
            }
        }
    }
    private void writeUndo(JButton []clrBtn){
        int intCr;
        undoArray = new int [200];
        for (int i =0;i<200;i++){

            if (clrBtn[i].getBackground().equals(gy)) {
                intCr=9;
            }else if (clrBtn[i].getBackground().equals(new Color(139,69,19))){
                intCr=0;
            } else if (clrBtn[i].getBackground().equals(new Color(255,0,0))){
                intCr=1;
            }else if (clrBtn[i].getBackground().equals(new Color(0,255,0))){
                intCr=2;
            }else if (clrBtn[i].getBackground().equals(new Color(0,0,255))){
                intCr=3;
            }else if (clrBtn[i].getBackground().equals(new Color(0,0,0))){
                intCr=4;
            }else if (clrBtn[i].getBackground().equals(new Color(255,0,255))){
                intCr=5;
            }else if (clrBtn[i].getBackground().equals(new Color(0,255,255))){
                intCr=6;
            }else if (clrBtn[i].getBackground().equals(new Color(255, 255, 0))){
                intCr=7;
            }else if (clrBtn[i].getBackground().equals(new Color(128,128,128))){
                intCr=8;
            }else{
                intCr=9;
            }
            undoArray[i] = intCr;
        }
    }
    private void writeRedo(JButton []clrBtn){
        int cr;
        redoArray= new int [200];
        for (int i =0;i<200;i++){
            if (clrBtn[i].getBackground().equals(gy)) {
                cr=9;
            }else if (clrBtn[i].getBackground().equals(new Color(139,69,19))){
                cr=0;
            } else if (clrBtn[i].getBackground().equals(new Color(255,0,0))){
                cr=1;
            }else if (clrBtn[i].getBackground().equals(new Color(0,255,0))){
                cr=2;
            }else if (clrBtn[i].getBackground().equals(new Color(0,0,255))){
                cr=3;
            }else if (clrBtn[i].getBackground().equals(new Color(0,0,0))){
                cr=4;
            }else if (clrBtn[i].getBackground().equals(new Color(255,0,255))){
                cr=5;
            }else if (clrBtn[i].getBackground().equals(new Color(0,255,255))){
                cr=6;
            }else if (clrBtn[i].getBackground().equals(new Color(255, 255, 0))){
                cr=7;
            }else if (clrBtn[i].getBackground().equals(new Color(128,128,128))){
                cr=8;
            }else{
                cr=9;
            }
            redoArray[i]=cr;
        }
    }
    private void undo(){
        for (int i=0; i<200;i++){
            pane[i].removeAll();
            colorButton(i,undoArray[i]);
        }
        undoBtn.setEnabled(false);
        redoBtn.setEnabled(true);
    }
    private void redo(){
        for (int i=0; i<200;i++){
            pane[i].removeAll();
            colorButton(i,redoArray[i]);
        }
        undoBtn.setEnabled(true);
        redoBtn.setEnabled(false);
    }
    public void actionPerformed(ActionEvent event){
        //choice(b,k);
    }
    private void timer(){
        sec = new Timer();
        sec.schedule(new TimeTask(),0,1000);
    }
    class TimeTask extends TimerTask {
        public void run(){
            count=count+1;
            if (counter<10){
                if (count<10){
                    if (count==60){
                        count=0;
                        counter=counter+1;
                        timerLabel2.setText(String.valueOf("0"+counter+":0"+count));
                    }else {
                        timerLabel2.setText(String.valueOf("0"+counter+":0"+count));
                    }
                }else{
                    if (count==60){
                        count=0;
                        counter=counter+1;
                        timerLabel2.setText(String.valueOf("0"+counter+":"+count));
                    }else {
                        timerLabel2.setText(String.valueOf("0"+counter+":"+count));
                    }
                }
            }else{
                if (count<10){
                    if (count==60){
                        count=0;
                        counter=counter+1;
                        timerLabel2.setText(String.valueOf(""+counter+":0"+count));
                    }else {
                        timerLabel2.setText(String.valueOf(""+counter+":0"+count));
                    }
                }else{
                    if (count==60){
                        count=0;
                        counter=counter+1;
                        timerLabel2.setText(String.valueOf(""+counter+":"+count));
                    }else {
                        timerLabel2.setText(String.valueOf(""+counter+":"+count));
                    }
                }
            }
        }
    }
    private void setScore(){
        int greyBtn=0;
        for (int scr=0;scr<200;scr++){
            if (pane[scr].getComponent(0).getBackground().equals(gy)){
                greyBtn=greyBtn+1;
            }
        }
        int score;
        if(init<25){
            score=225-init;
        }else{
            score = (200-greyBtn);
        }
        String scr = Integer.toString(score);
        scoreLabel2.setText(scr);
        init++;
    }
    private void playSound(){

        String soundName = "clickButton.wav";
        if(sound1==1){
            try{
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }catch (IOException ioe){
                System.out.println("Audio stream ioe file exception"+ioe);
            }catch (UnsupportedAudioFileException uae){
                System.out.println("Un supported audio file exception"+uae);
            }catch (LineUnavailableException lue){
                System.out.println("line unavailable exception"+lue);
            }
        }

    }
    private void settings(){
        GridLayout gridLayout = new GridLayout(7,1);
        JPanel panel = new JPanel(gridLayout);
        JLabel label = new JLabel("Number of Different Colours");
        ButtonGroup clrGroup = new ButtonGroup();
        clrGroup.add(radioBtn9);
        clrGroup.add(radioButton8);
        clrGroup.add(radioButton7);
        clrGroup.add(radioButton6);
        panel.add(soundRadioButton);
        if (sound1==1){
            soundRadioButton.setSelected(true);
        }else{
            soundRadioButton.setSelected(false);
        }
        panel.add(label);
        panel.add(radioBtn9);
        panel.add(radioButton8);
        panel.add(radioButton7);
        panel.add(radioButton6);
        if (bound1==9){
            radioBtn9.setSelected(true);
        }else if (bound1==8){
            radioButton8.setSelected(true);
        }else if (bound1==7){
            radioButton7.setSelected(true);
        }else if (bound1==6){
            radioButton6.setSelected(true);
        }else{
            radioButton7.setSelected(true);
        }

        JOptionPane.showMessageDialog(null,panel,"SETTINGS",JOptionPane.PLAIN_MESSAGE);
        int sound, bound;
        if (soundRadioButton.isSelected()  ){
            sound = 1;
        }else{
            sound = 0;
        }
        if (radioBtn9.isSelected()){
            bound=9;
        }else if (radioButton8.isSelected()){
            bound=8;
        }else if(radioButton7.isSelected()){
            bound=7;
        }else if (radioButton6.isSelected()){
            bound = 6;
        }else {
            bound=8;
        }
        sound1=sound;
        if (bound1!=bound){
            Object [] options = {"YES","NO","CANCEL"};
            int ans =JOptionPane.showOptionDialog(null,"You have just changed the number of colours in a game.\n " +
                    "DO YOU WANT TO OPEN A NEW GAME WITH THE NEW SETTINGS???","SETTINGS",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (ans==0){
                bound1 = bound;
                init=0;
                for (int i=0; i<200;i++) {
                    pane[i].removeAll();
                }
                randomButton();
                count=0;
                counter=0;
                scoreLabel2.setText("225");
                timer();
                restartBtn.setEnabled(true);
            }else if (ans==1){
                bound1 = bound;
            }else if (ans==2){
                bound=bound1;
            }

        }

        try{
            PrintWriter outfile = new PrintWriter(new FileWriter("settings.bin"));
            outfile.println(sound);
            outfile.println(bound);
            outfile.close();
        }catch (IOException ioe){
            System.out.println("FILE HIGH SCORE NOT FOUND");
        }
    }
    private int gameOver(){
        for (int i=0;i<200;i++){
            if (pane[i].getComponent(0).getBackground().equals(gy)){
                pane[i].getComponent(0).setEnabled(false);
            }
            if (!pane[i].getComponent(0).getBackground().equals(gy)){
                pane[i].getComponent(0).setEnabled(true);
            }
        }
        int k =0;
        for (int cg=0;cg<200;cg++){
            if (cg<199&&!pane[cg].getComponent(0).getBackground().equals(gy)&&pane[cg].getComponent(0).getBackground().equals(pane[cg+1].getComponent(0).getBackground())||
                    cg<190&&!pane[cg].getComponent(0).getBackground().equals(gy)&&pane[cg].getComponent(0).getBackground().equals(pane[cg+10].getComponent(0).getBackground())){
                k=k+1;
            }
        }
        if (k==0){
            sec.cancel();
            System.out.println("GAME OVER");
            new CalcHighScore(scoreLabel2.getText(),timerLabel2.getText());
            for (int i=0;i<200;i++){
                pane[i].getComponent(0).setEnabled(false);
            }
            restartBtn.setEnabled(false);
            undoBtn.setEnabled(false);
            redoBtn.setEnabled(false);
            Object [] options = {"NEW GAME","CREDITS","HIGH SCORE"};
            int ans =JOptionPane.showOptionDialog(null,"GAME OVER!!!","COLOUR CLICKS",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,null,options,options[0]);
            if (ans==0){
                init=0;
                for (int i=0; i<200;i++) {
                    pane[i].removeAll();
                }
                randomButton();
                count=0;
                counter=0;
                scoreLabel2.setText("225");
                timer();
                restartBtn.setEnabled(true);
            }else if(ans==1){
                new Credits();
            }else if (ans==2){
                new CalcHighScore();
            }
        }
        return k;
    }
    private class CalcHighScore extends HighScore {
        CalcHighScore(String scr,String tm) {
            writeHighScore(scr,tm);
        }
        CalcHighScore(){
            highScoreGUI();
        }
    }
    public static void main(String args[]){
        new ColorBlocks();
    }
}