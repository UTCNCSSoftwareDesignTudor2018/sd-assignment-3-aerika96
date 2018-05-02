package presentation.views;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LogInView {

    private JButton logIn;

    private JTextField userName;
    private JPasswordField passWord;

    private JLabel user;
    private JLabel pass;

    private JPanel center;
    private JPanel fillIn;
    private JPanel button;

    private JFrame mainFrame = new JFrame("Log In");

    public LogInView() {
        logIn = new JButton("LOG IN");
        userName = new JTextField();
        passWord = new JPasswordField(45);
        user = new JLabel("Username: ");
        pass = new JLabel("PassWord: ");

        center = new JPanel();

        fillIn = new JPanel();
        fillIn.add(user);
        fillIn.add(userName);
        fillIn.add(pass);
        fillIn.add(passWord);
        fillIn.setLayout(new GridLayout(0,2));

        button = new JPanel();
        button.add(new JPanel());
        button.add(logIn);
        button.add(new JPanel());
        button.setLayout(new GridLayout(0,3));


        center.add(fillIn);
        center.add(button);
        center.setLayout(new GridLayout(0,1));

        mainFrame.setLayout(new BorderLayout(150,150));
        mainFrame.add(center,BorderLayout.CENTER);
        mainFrame.add(new JPanel(),BorderLayout.NORTH);
        mainFrame.add(new JPanel(),BorderLayout.SOUTH);
        mainFrame.add(new JPanel(),BorderLayout.EAST);
        mainFrame.add(new JPanel(),BorderLayout.WEST);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(800,450);

    }

    public void showErrorMessage (String errorMessage){
        JOptionPane.showMessageDialog(mainFrame,errorMessage );
    }

    public String getUserName() {
        return userName.getText();
    }


    public String getPassWord() {
        return new String(passWord.getPassword());
    }

    public void setMainFrameFalse() {
        mainFrame.setVisible(false);
    }

    public void addLoginListener (ActionListener st){
        logIn.addActionListener(st);
    }


}
