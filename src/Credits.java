import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Credits extends JFrame{
    private JFrame frame3 = new JFrame("COLOUR CLICKS");
    Credits(){
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame3.setLocation(500,150);
        frame3.setSize(350,350);
        frame3.setResizable(false);
        GridLayout gridLayout = new GridLayout(1,1,5,2);
        GridLayout gridLayout3 = new GridLayout(2,1,5,2);
        GridLayout gridLayout4 = new GridLayout(3,1);
        JPanel pane2 = new JPanel(gridLayout);
        JPanel pane3 = new JPanel(gridLayout);
        JPanel pane4 = new JPanel(gridLayout3);
        JPanel pane5 = new JPanel(gridLayout3);
        Font forte = new Font("forte",Font.BOLD,15);
        Font forte2 = new Font("forte",Font.BOLD,12);
        Font Harrington = new Font("Berlin Sans FB",Font.PLAIN,14);
        JLabel creditsLabel = new JLabel("Colour Clicks Credits");
        creditsLabel.setFont(forte);
        creditsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel copyRightLabel = new JLabel("Copyrights:\n THINK-LIKE-MALONE SOFTWARE 2017");
        copyRightLabel.setFont(forte2);
        copyRightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextArea creditTxtArea = new JTextArea("COLOUR CLICKS Version 1.0.0 IS CREATED AND \nDEVELOPED BY THINK-LIKE-MALONE SOFTWARE.\n " +
                "We like to give thanks to Malone for dedicating his time to create it.");
        creditTxtArea.setSize(340,300);
        creditTxtArea.setEditable(false);
        creditTxtArea.setLineWrap(true);
        creditTxtArea.setBackground(copyRightLabel.getBackground());
        creditTxtArea.setFont(Harrington);
        JTextArea creditTxtArea2 = new JTextArea("WORDS ZANGU ZIMEISHIA HAPO\n \n" +
                "NIMESAHAU KITU MOJA... send feed backs and comments to elkanahmalonza@gmail.com\n New Update is " +
                "coming soon. So send the feed backs\n" + "\t AM OUT...DAMN BOYZ and GALZ");
        creditTxtArea2.setSize(340,300);
        creditTxtArea2.setEditable(false);
        creditTxtArea2.setLineWrap(true);
        creditTxtArea2.setBackground(copyRightLabel.getBackground());
        creditTxtArea2.setFont(Harrington);
        JButton closeBtn = new JButton("CLOSE");
        closeBtn.addActionListener(new Close());
        pane2.add(closeBtn);
        pane5.add(copyRightLabel);
        pane5.add(pane2);
        pane4.setLayout(gridLayout4);
        pane4.add(creditTxtArea);
        pane4.add(creditTxtArea2);
        pane3.add(creditsLabel);
        frame3.add(pane3,BorderLayout.NORTH);
        frame3.add(pane4, BorderLayout.CENTER);
        frame3.add(pane5,BorderLayout.SOUTH);
        frame3.setVisible(true);
    }
    private class Close implements ActionListener {
        public void actionPerformed(ActionEvent e){
            frame3.setVisible(false);
        }
    }
}