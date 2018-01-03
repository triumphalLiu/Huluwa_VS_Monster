package sample;

import field.Field;
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
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private GridPane fieldPane;

    @FXML
    private Pane mainPane;

    @FXML
    private ImageView titleImageView;

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

    @FXML
    private ImageView winnerImageView;

    @FXML
    private ImageView operationTipImageView;

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
        winnerImageView.setVisible(true);
        operationTipImageView.setVisible(true);
        startButton.setVisible(false);
        historyButton.setVisible(false);
        exitButton.setVisible(false);
        aboutButton.setVisible(false);
        titleImageView.setVisible(false);
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

    private void display(Field field){ //如果一方全部阵亡 则结束 TODO
        if(field == null)
            return;
        if(field.isGameOver() == 1) {
            winnerImageView.setImage(new Image(this.getClass().getResourceAsStream("/GoodGuyWins.png")));
            operationTipImageView.setImage(new Image(this.getClass().getResourceAsStream("/operationTip.png")));
        }
        else if(field.isGameOver() == -1) {
            winnerImageView.setImage(new Image(this.getClass().getResourceAsStream("/BadGuyWins.png")));
            operationTipImageView.setImage(new Image(this.getClass().getResourceAsStream("/operationTip.png")));
        }
        else {
            winnerImageView.setImage(new Image(this.getClass().getResourceAsStream("/Empty.png")));
            operationTipImageView.setImage(new Image(this.getClass().getResourceAsStream("/Empty.png")));
        }
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
            winnerImageView.setVisible(true);
            operationTipImageView.setVisible(true);
            startButton.setVisible(false);
            historyButton.setVisible(false);
            exitButton.setVisible(false);
            aboutButton.setVisible(false);
            titleImageView.setVisible(false);
            field = new Field();
            field.CleanField();
            display(field);
            new Animation().fade_out_in(backgroundImageView, new Image(this.getClass().getResourceAsStream("/fightScene2.png")));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    fieldPane.setDisable(false);
                    fieldPane.setVisible(true);
                    Mode = 2;
                    Step = 0;
                    historyFilename = file.getPath();
                    if(field.getHistoryField(historyFilename, Step++) == false){
                        //文件异常
                        gameOver();
                    };
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
        historyFilename = "";
        fieldPane.setVisible(false);
        display(new Field());
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
                titleImageView.setVisible(true);
                winnerImageView.setVisible(false);
                operationTipImageView.setVisible(false);
            }
        }, 1500);
    }

    @FXML
    void pressKeyEventHandler(KeyEvent keyEvent){
        System.out.println(keyEvent.getCode());
        if(Mode == 0){ //start Page
            if(keyEvent.getCode() == KeyCode.L)
                clickHistoryButton(null);
        }
        else if(Mode == 1){ //new Game
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE){
                field.move();
                checkGameOver();
            }
        }
        else if(Mode == 2){//load History
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE) {
                if(field.getHistoryField(historyFilename, Step++) == false) //文件异常
                    gameOver();
                checkGameOver();
            }
        }
        else if(Mode == 3){//new Game end
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.L)
                clickHistoryButton(null);
            else if(keyEvent.getCode() == KeyCode.SPACE)
                gameOver();
            //save...
        }
    }

    private void checkGameOver(){
        int wins = field.isGameOver();
        display(field);
        if(wins != 0)
            Mode = 3;
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