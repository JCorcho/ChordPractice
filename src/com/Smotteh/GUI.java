package com.Smotteh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class GUI implements ActionListener {


    //Logic Global Variables
    static ArrayList<String> chords = new ArrayList<>();
    static File chordTXT = new File("src\\chords.txt");
    String curChord = "C Major";

    //Swing Global Variables
    private JFrame frame;
    private JButton button;
    private JLabel chordLabel;
    private JPanel panel;

    public GUI() {

        //INITIAL FRAME SETTINGS
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);

        //BUTTON SETTINGS
        button = new JButton("New Chord");
        button.addActionListener(this);
        button.setSize(100,100);
        chordLabel = new JLabel("C Major");

        //PANEL SETTINGS
        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(chordLabel);
        panel.add(button);

        //ADDITIONAL FRAME SETTINGS
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Chord Practice");
        frame.setVisible(true);
    }

    public static void fillChords() throws FileNotFoundException {
        //LOGIC
        Scanner scanner = new Scanner(chordTXT);

        while(scanner.hasNextLine()) {
            String added = scanner.nextLine();
            chords.add(added);
        }
    }

    public String GetRandChord() throws FileNotFoundException {
        //LOGIC
        Random rand = new Random();
        return chords.get(rand.nextInt(chords.size()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            curChord =  GetRandChord();
            chordLabel.setText(curChord);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //GUI
        new GUI();

        //Logic
        fillChords();

    }
}
