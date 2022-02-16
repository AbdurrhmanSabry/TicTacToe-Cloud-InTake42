/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.SinglePlayer;

import models.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Hossam
 */
public class SinglePlayerController implements Initializable {
    @FXML AnchorPane ap;
    @FXML GridPane grid;
    
    ArrayList<Button> availablePositions = new ArrayList<Button>();
    
    @FXML Button backbtn;
    @FXML Button restartbtn;
    @FXML Button btn1;
    @FXML Button btn2;
    @FXML Button btn3;
    @FXML Button btn4;
    @FXML Button btn5;
    @FXML Button btn6;
    @FXML Button btn7;
    @FXML Button btn8;
    @FXML Button btn9;
    
    Board current_board= new Board();
    int counter=0;
    Session currentSession;
    Boolean playerTurn=true;
    String pick ; 
    Person loggedPlayer;
    Thread pc;
    @FXML
    
    private boolean isEmpty(Button pos)
    {
//        Button btn =(Button) grid.getChildren().get(pos);
  
        if(pos.getText().isEmpty())
        {
            return true;
        }
        else return false;
    
    }
    public void PlayerMove(ActionEvent event) throws InterruptedException 
    {
        Button btn = (Button) event.getSource(); 
        if(!availablePositions.isEmpty() && playerTurn){
              if(isEmpty(btn))
              {
                  btn.setText("X");
                  availablePositions.remove(btn);
                  counter++;
                  if(counter>=5)
                  {
                      if(current_board.checkWin("X"))
                      { 
                          System.out.println("player one win");
                           availablePositions.clear();

                      }
                  }
                  playerTurn=false;
              }
              else
                  System.out.println("used");
        }
     }
    
    
    public void restart (ActionEvent event)
    {  
        resetGrid(); 
    }
    public void resetGrid(){
        availablePositions.add(btn1);
        availablePositions.add(btn2);
        availablePositions.add(btn3);
        availablePositions.add(btn4);
        availablePositions.add(btn5);
        availablePositions.add(btn6);
        availablePositions.add(btn7);
        availablePositions.add(btn8);
        availablePositions.add(btn9);
        for(Button b: availablePositions)
        {
            b.setText("");
        
        }
        current_board.board.clear();
        current_board.board.addAll(availablePositions);
        playerTurn=true;

      }
    
    public void pcMove() throws InterruptedException
    {
        if(!availablePositions.isEmpty())
        {
            int pos=Pc.randomMove(availablePositions.size());
            availablePositions.get(pos).setText("O");
            availablePositions.remove(availablePositions.get(pos));
            counter++;
            if(counter>=6)
               {
                   if(current_board.checkWin("O"))
                   { 
                       System.out.println("pc win");
                       availablePositions.clear();

                   }


               }
        }
        
    }
    
    
    
    public void back2MainRoom(ActionEvent event) throws IOException
    
    {
        pc.stop();
        Parent View = FXMLLoader.load(getClass().getClassLoader().getResource("views/MainRoom/MainRoom.fxml"));
        Scene ViewScene = new Scene(View);       
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(ViewScene);
        window.show();
    
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        resetGrid();    
        pc = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true){
                    
                    if(!playerTurn){
                        System.out.println("Pc turn");
                        try {
                            Thread.sleep(2000);
                            
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run() {
                                    try {
                                        pcMove();
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(SinglePlayerController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            playerTurn=true;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SinglePlayerController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        System.out.println("Player's turn");
                    }
                }
            }
            
        });
        pc.start();        
    }    
    
}
