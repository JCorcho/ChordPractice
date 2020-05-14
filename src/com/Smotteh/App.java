package com.Smotteh;

import org.jfugue.devices.MusicTransmitterToParserListener;
import org.jfugue.parser.ParserListenerAdapter;
import org.jfugue.theory.Note;

import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class App {
    private JButton chordBtn;
    private JPanel mainPanel;


    private JLabel chordLbl;
    private JFrame frame;


    //Logic Global Variables
    static ArrayList<String> chords = new ArrayList<>();
    static File chordTXT = new File("src\\chords.txt");
    static String curChord = "C Major";

    static ArrayList<CustomNote> notesNeeded = new ArrayList<>();

    public static void main(String[] args) throws IOException, FontFormatException, MidiUnavailableException {

        //CREATE APP OBJECT
        App app = new App();

        MidiController midiController = new MidiController();
        midiController.setApp(app);


        MusicTransmitterToParserListener _device = new MusicTransmitterToParserListener(midiController.device);
        _device.addParserListener(new ParserListenerAdapter() {
            @Override public void onNotePressed(Note note) {
                midiController.sendKeyPress(note.toString().charAt(0));
                System.out.println("note: " + note);
            }
            @Override public void onNoteReleased(Note note) {
                midiController.sendKeyRelease(note.toString().charAt(0));
            }
        } );

        _device.startListening();



        //INITIAL FRAME SETTINGS
        app.frame = new JFrame();
        app.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        app.frame.setUndecorated(false);

        //ADDITIONAL FRAME SETTINGS
        app.frame.add(app.mainPanel, BorderLayout.CENTER);
        app.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.frame.setTitle("Chord Practice");
        app.frame.setVisible(true);

        //LOGIC
        fillChords();
    }

    public App() {
        chordBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    changeChord();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
    }

    //METHODS
    public void changeChord() throws FileNotFoundException {

        curChord = GetRandChord();
        chordLbl.setText(curChord);
        chordLbl.paintImmediately(chordLbl.getVisibleRect());
        setChordsNeeded(curChord);
        System.out.println(chordLbl.getText());

    }

    public void checkForNewChord(char c) throws FileNotFoundException {
        boolean check = true;
        for (CustomNote note : notesNeeded) {
            if(note.getQuality() == c) {
                note.setPressed(true);
            }

            if(!note.pressed) {

                check = false;
            }
        }
        if(!check) {

            System.out.println("Cannot make new chord, notes: ");
            for(int i = 0; i < notesNeeded.size(); i++) {
                if(!notesNeeded.get(i).isPressed())
                    System.out.print(notesNeeded.get(i).getQuality() + ", ");
            }
            System.out.println("are still not pressed.");
        } else if(check) {
            changeChord();
        }
    }

    public void setChordsNeeded(String string) {
        notesNeeded.clear();
        int removeIndex = string.indexOf('–');
        string = string.substring(removeIndex + 1);


        string = string.replaceAll("♯", "");
        string = string.replaceAll("♭", "");
        string = string.replaceAll(" ", "");

        for(int i = 0; i < string.length(); i++) {
            CustomNote tempNote = new CustomNote(string.charAt(i));
            notesNeeded.add(tempNote);
           // System.out.println("Note needed: " + tempNote.getQuality());
        }


    }



    public static void fillChords() throws FileNotFoundException {
        //LOGIC
        Scanner scanner = new Scanner(chordTXT);

        while(scanner.hasNextLine()) {
            String added = scanner.nextLine();
            chords.add(added);
        }
    }

    public static String GetRandChord() throws FileNotFoundException {
        //LOGIC
        Random rand = new Random();
        String chordToDisplay = chords.get(rand.nextInt(chords.size()));

        return chordToDisplay;
    }


}
