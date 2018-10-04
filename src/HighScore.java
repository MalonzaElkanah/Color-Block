import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

class HighScore {
    private JLabel []labels = new JLabel[30];
    private JFrame frame2 = new JFrame("HIGH SCORE");
void highScoreGUI(){
    frame2.setResizable(false);
    frame2.setSize(350,350);
    frame2.setLocation(500,150);
    frame2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    GridLayout gridLayout2 = new GridLayout(1,3,5,2);
    GridLayout gridLayout4 = new GridLayout(11,3);
    GridLayout gridLayout5 = new GridLayout(1,1);
    JPanel pane4 = new JPanel(gridLayout4);
    JPanel pane5 = new JPanel(gridLayout2);
    frame2.add(pane4, BorderLayout.CENTER);
    frame2.add(pane5,BorderLayout.SOUTH);
    frame2.setVisible(true);
    Font forte = new Font("forte",Font.BOLD,14);
    JLabel timerLabel = new JLabel("Time");
    timerLabel.setFont(forte);
    JLabel scoreLabel = new JLabel("Score");
    scoreLabel.setFont(forte);
    JLabel numberLabel = new JLabel("NO.");
    numberLabel.setFont(forte);
    JButton closeBtn = new JButton("CLOSE");
    pane5.add(closeBtn);
    closeBtn.addActionListener(new Close());
    JButton resetBtn = new JButton("RESET");
    resetBtn.addActionListener(new ReSet());
    pane5.add(resetBtn);
    pane4.setLayout(gridLayout4);
    JPanel []panels = new JPanel[gridLayout4.getColumns()*gridLayout4.getRows()];
    for (int i= 0; i<(gridLayout4.getColumns()*gridLayout4.getRows());i++){
        panels[i]= new JPanel();
        pane4.add(panels[i]);
        panels[i].setLayout(gridLayout5);
    }
    panels[0].add(numberLabel);
    panels[1].add(scoreLabel);
    panels[2].add(timerLabel);
    for(int i=0;i<30;i++){
        labels[i]= new JLabel();
    }
    int l=0;
    for (int k =3; k<33;k++){
        panels[k].add(labels[l]);
        l++;
    }
    int j = 0;
    for (int i= 0;i<30;i=i+3 ){
        j++;
        labels[i].setText(Integer.toString(j));
    }
    readHighScore();
}
private class Close implements ActionListener{
    public void actionPerformed(ActionEvent e){
        frame2.setVisible(false);
    }
}
private class ReSet implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
            outfile.close();
        }catch (IOException ioe){
            System.out.println("FILE NOT FOUND !!!");
        }
        for (int i= 1;i<30;i=i+3 ){
            labels[i].setText("-");
            labels[i+1].setText("-");
        }
    }
}
private void readHighScore(){

    Scanner input = null;
    try {
        input = new Scanner(new FileInputStream("highScore.bin"));
    }catch(FileNotFoundException ieo){
        System.out.println("FILE highScore.bin WAS NOT FOUND!!!");
    }
    try {
        File file = new File("highScore.bin");
        if (file.exists()){
            FileReader fr = new FileReader(file);
            LineNumberReader lineRdr = new LineNumberReader(fr);

            if (lineRdr.readLine()!=null){
                try{
                    for (int i= 1;i<30;i=i+3 ){
                        if(input!=null){
                        labels[i].setText(input.nextLine());
                        labels[i+1].setText(input.nextLine());
                        }
                    }
                }catch(NoSuchElementException nse){
                    System.out.println("No such line found");
                }

            }else {
                for (int i= 1;i<30;i=i+3 ){
                    labels[i].setText("-");
                    labels[i+1].setText(" -");
                }
            }
            lineRdr.close();
        }
    }catch(IOException ieo){
        System.out.println("FILE high score.bin WAS NOT FOUND!!!");
    }
}
void writeHighScore(String score, String time){
    int intScore=Integer.parseInt(score);
    Scanner input = null;
    try {
        input = new Scanner(new FileInputStream("highScore.bin"));
    }catch(FileNotFoundException ieo){
        System.out.println("FILE high score.bin WAS NOT FOUND!!!");
    }
    int LineNumber = 0;
    try {
        File file = new File("highScore.bin");
        if (file.exists()){
            FileReader fr = new FileReader(file);
            LineNumberReader lineRdr = new LineNumberReader(fr);

            while (lineRdr.readLine()!=null){
                LineNumber++;
            }
            lineRdr.close();
        }
    }catch(IOException ieo){
        System.out.println("FILE high score.bin WAS NOT FOUND!!!");
    }
    int z=LineNumber/2;int temp;System.out.println("z is:"+z);
    if (z>1){
        int []t;
        t = new int[z];
        for (int i =0; i<z;i++ ){
            try{
                if(input!=null){
                    t[i]=Integer.parseInt(input.nextLine());
                    System.out.println(t[i]);
                    input.nextLine();
                }
            }catch ( NullPointerException npe){
                System.out.println("exception null pointer");
            }
        }
        if (z>9){
            try{
                PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin",true));
                outfile.println(score);
                outfile.println(time);
                outfile.close();
            }catch (IOException ioe){
                System.out.println("FILE HIGH SCORE NOT FOUND");
            }
            int q=z+1;
            String [] ti;String temp2;
            ti=new  String[q];
            int []p;
            p = new int[q];
            Scanner input1;
            try {
                input1 = new Scanner(new FileInputStream("highScore.bin"));
                for (int i = 0; i<q;i++){
                    p[i]=Integer.parseInt(input1.nextLine());
                    ti[i]=input1.nextLine();
                    System.out.println(p[i]);
                    System.out.println(ti[i]);
                }
            }catch(FileNotFoundException ieo){
                System.out.println("FILE high score.bin WAS NOT FOUND!!!");
            }
            for(int y=0;y<q;y++){
                for (int x=y+1;x<q;x++){
                    if (p[y]>p[x]){
                        temp = p[y];
                        temp2 = ti[y];
                        p[y]=p[x];
                        ti[y]=ti[x];
                        p[x]=temp;
                        ti[x]=temp2;
                    }
                }
            } System.out.println("ASCENDING ORDER:");
            try{
                PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
                for (int y = 0;y<10;y++){
                    System.out.println(p[y]);
                    System.out.println(ti[y]);
                    outfile.println(p[y]);
                    outfile.println(ti[y]);
                }
                outfile.close();
            }catch (IOException ioe){
                System.out.println("FILE HIGH SCORE NOT FOUND");
            }
        }else {
                try{
                    PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin",true));
                    outfile.println(score);
                    outfile.println(time);
                    outfile.close();
                }catch (IOException ioe){
                    System.out.println("FILE HIGH SCORE NOT FOUND");
                }
                int q=z+1;
                String [] ti;String temp2;
                ti=new  String[q];
                int []p;
                p = new int[q];
                Scanner input1;
                try {
                    input1 = new Scanner(new FileInputStream("highScore.bin"));
                    for (int i = 0; i<q;i++){
                        p[i]=Integer.parseInt(input1.nextLine());
                        ti[i]=input1.nextLine();
                        System.out.println(p[i]);
                        System.out.println(ti[i]);
                    }
                }catch(FileNotFoundException ieo){
                    System.out.println("FILE high score.bin WAS NOT FOUND!!!");
                }
                for(int y=0;y<z;y++){
                    for (int x=y+1;x<z;x++){
                        if (p[y]>p[x]){
                            temp = p[y];
                            temp2 = ti[y];
                            p[y]=p[x];
                            ti[y]=ti[x];
                            p[x]=temp;
                            ti[x]=temp2;
                        }
                    }
                } System.out.println("ASCENDING ORDER:");
                    try{
                        PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
                        for (int y = 0;y<q;y++){
                            System.out.println(p[y]);
                            System.out.println(ti[y]);
                            outfile.println(p[y]);
                            outfile.println(ti[y]);
                        }
                        outfile.close();
                    }catch (IOException ioe){
                        System.out.println("FILE HIGH SCORE NOT FOUND");
                    }
        }
    }else if (z>0){
        if (input!=null){
            int scoreX=Integer.parseInt(input.nextLine());
            String timeX = input.nextLine();
            if (intScore<scoreX){
                try{
                    PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
                    outfile.println(score);
                    outfile.println(time);
                    outfile.println(scoreX);
                    outfile.println(timeX);
                    outfile.close();
                }catch (IOException ioe){
                    System.out.println("FILE HIGH SCORE NOT FOUND");
                }
            }else {
                try{
                    PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
                    outfile.println(scoreX);
                    outfile.println(timeX);
                    outfile.println(score);
                    outfile.println(time);
                    outfile.close();
                }catch (IOException ioe){
                    System.out.println("FILE HIGH SCORE NOT FOUND");
                }
            }
        }

    }else if (z>-1){
        try{
            PrintWriter outfile = new PrintWriter(new FileWriter("highScore.bin"));
            outfile.println(score);
            outfile.println(time);
            outfile.close();
        }catch (IOException ioe){
            System.out.println("FILE HIGH SCORE NOT FOUND");
        }
    }
}
}