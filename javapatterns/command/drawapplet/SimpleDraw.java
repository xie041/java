/* Generated by Together */

package com.javapatterns.command.drawapplet;

import java.awt.*;
import java.applet.*;
import java.util.*;
import java.awt.event.*;

public class SimpleDraw extends Applet implements ActionListener {
    /**
     * @link aggregation
     * @clientRole applet
     * @supplierRole drawing 
     */
    private Drawing	drawing;

    /**
     * @link aggregation
     * @supplierRole commands*/
    private CommandList	commands;
    private Button	undoButton;
    private Button	redoButton;

    public void init() {
        Panel panel = new Panel();
        commands = new CommandList();
        setLayout( new BorderLayout() );
        drawing = new Drawing( this );
        add( "Center", drawing );
        undoButton = new Button( "Undo" );
        redoButton = new Button( "Redo" );
        undoButton.addActionListener( this );
        redoButton.addActionListener( this );
        panel.add( undoButton );
        panel.add( redoButton );
        add( "South", panel );
        updateButtons();
     }

     public void execute( Command command ) {
        commands.execute( command );
        updateButtons();
     }

     private void updateButtons() {
        undoButton.setEnabled( commands.canUnexecuteCommand() );
        redoButton.setEnabled( commands.canReexecuteCommand() );
     }

     public void actionPerformed(ActionEvent event) {
        if (event.getSource() == undoButton) {
            commands.unexecute();
            updateButtons();
        }
        if (event.getSource() == redoButton) {
            commands.reexecute();
            updateButtons();
           }
     }
}

