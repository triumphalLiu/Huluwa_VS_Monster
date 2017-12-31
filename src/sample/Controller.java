package sample;

import Field.Field;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private GridPane fieldPane;

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView startButton;

    @FXML
    private ImageView historyButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView aboutButton;

    private Field field;
    private int Mode; //0=not start 1= new game 2=history game
    private int Step; //history step
    private String historyFilename;

    public void InitController(){
        Mode = 0;
        Step = 0;
        field = null;
        historyFilename = "";
        mainPane.setFocusTraversable(true);
        mainPane.requestFocus();
    }

    @FXML
    void clickStartButton(MouseEvent event){
        //更换战斗背景 隐藏控件
        startButton.setVisible(false);
        historyButton.setVisible(false);
        exitButton.setVisible(false);
        aboutButton.setVisible(false);
        new Animation().fade_out_in(backgroundImageView, new Image(this.getClass().getResourceAsStream("/fightScene1.png")));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                fieldPane.setDisable(false);
                fieldPane.setVisible(true);
                Mode = 1;
                //战斗 TODO
                field = new Field();
                field.getReady();
                display(field);
                this.cancel();
            }
        }, 1500);
    }

    void display(Field field){
        if(field == null)
            return;
        for(int i = 0; i < field.sizeX; ++i) {
            for (int j = 0; j < field.sizeY; ++j) {
                ImageView tmp = (ImageView) fieldPane.getChildren().get(field.sizeX*j+i);
                tmp.setImage(field.getCreatures()[i][j].report());
            }
        }
    }

    @FXML
    void clickHistoryButton(MouseEvent event){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e) {
            System.out.println(e.getStackTrace());
        }
        //打开选择文件对话框
        JFileChooser jFileChooser = new JFileChooser(".");
        jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        HLWFileFilter hlwFileFilter = new HLWFileFilter();
        jFileChooser.addChoosableFileFilter(hlwFileFilter);
        jFileChooser.setFileFilter(hlwFileFilter);
        jFileChooser.setDialogTitle("选择回放文件");
        jFileChooser.showOpenDialog(null);
        File file = jFileChooser.getSelectedFile();
        if(file != null && file.exists()) {
            System.out.println("文件:" + file.getAbsolutePath());
            startButton.setVisible(false);
            historyButton.setVisible(false);
            exitButton.setVisible(false);
            aboutButton.setVisible(false);
            new Animation().fade_out_in(backgroundImageView, new Image(this.getClass().getResourceAsStream("/fightScene2.png")));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    fieldPane.setDisable(false);
                    Mode = 2;
                    Step = 0;
                    historyFilename = file.getName();
                    field = new Field();
                    field.getHistoryField(historyFilename, Step++);
                    display(field);
                }
            }, 1500);
        }
    }

    @FXML
    void clickAboutButton(MouseEvent event){
        String cmd = "rundll32 url.dll,FileProtocolHandler https://github.com/triumphalLiu/Huluwa_VS_Monster";
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(cmd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void clickExitButton(MouseEvent event){
        System.exit(0);
    }

    void gameOver(){
        Mode = 0;
        field = null;
        fieldPane.setVisible(false);
        fieldPane.setDisable(true);
        new Animation().fade_out_in(backgroundImageView, new Image(this.getClass().getResourceAsStream("/Background.png")));
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startButton.setVisible(true);
                historyButton.setVisible(true);
                exitButton.setVisible(true);
                aboutButton.setVisible(true);
            }
        }, 1500);
    }

    @FXML
    void pressKeyEventHandler(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        if(Mode == 0){
            if(keyEvent.getCode() == KeyCode.L)
                clickHistoryButton(null);
        }
        else if(Mode == 1){
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE){
                field.move();
                display(field);
            }
        }
        else if(Mode == 2){
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE)
                field.getHistoryField(historyFilename, Step++);
        }
    }
}

class HLWFileFilter extends FileFilter {
    public String getDescription() {
        return "*.hlw;";
    }

    public boolean accept(File file) {
        String name = file.getName();
        return file.isDirectory() || name.toLowerCase().endsWith(".hlw");  // 仅显示.hlw文件
    }
}