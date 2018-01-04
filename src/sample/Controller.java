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
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    @FXML
    private GridPane fieldPane;

    @FXML
    private GridPane deathPane;

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
    private int Mode; //0=not start 1= new game 2=history game 3=newgame finish 4=history finish
    private int Step; //history step
    private String historyFilename;
    private boolean isRunning;
    private Image emptyImg = null;

    //初始化
    public void InitController(){
        isRunning = false;
        Mode = 0;
        Step = 0;
        field = null;
        historyFilename = "";
        mainPane.setFocusTraversable(true);
        mainPane.requestFocus();
        emptyImg = new Image(this.getClass().getResourceAsStream("/Empty.png"));
    }

    //清空DeathPane
    private void cleanDeathPane(){
        for(int i = 0; i < field.sizeX; ++i) {
            for (int j = 0; j < field.sizeY; ++j) {
                ImageView tmp = (ImageView) deathPane.getChildren().get(field.sizeX * j + i);
                tmp.setImage(emptyImg);
            }
        }
    }

    //清空FieldPane
    private void cleanFieldPane(){
        for(int i = 0; i < field.sizeX; ++i) {
            for (int j = 0; j < field.sizeY; ++j) {
                ImageView tmp = (ImageView) fieldPane.getChildren().get(field.sizeX * j + i);
                tmp.setImage(emptyImg);
            }
        }
    }

    //将战场情况显示到UI
    private void display(Field field){ //如果一方全部阵亡 则结束
        if(field == null) {
            cleanFieldPane();
            cleanDeathPane();
            return;
        }
        field.setIsDisplaying(true);
        //如果游戏结束 清空战斗场面 准备重新放置
        if(field.isGameOver() == 1) {
            cleanDeathPane();
            //cleanFieldPane();
            winnerImageView.setImage(new Image(this.getClass().getResourceAsStream("/GoodGuyWins.png")));
            operationTipImageView.setImage(new Image(this.getClass().getResourceAsStream("/operationTip.png")));
        }
        else if(field.isGameOver() == -1) {
            cleanDeathPane();
            //cleanFieldPane();
            winnerImageView.setImage(new Image(this.getClass().getResourceAsStream("/BadGuyWins.png")));
            operationTipImageView.setImage(new Image(this.getClass().getResourceAsStream("/operationTip.png")));
        }
        //如果还在游戏...
        else {
            winnerImageView.setImage(emptyImg);
            operationTipImageView.setImage(emptyImg);
        }
        //开始在底层放置东西
        for(int i = 0; i < field.sizeX; ++i) {
            for (int j = 0; j < field.sizeY; ++j) {
                if(!field.getCreatures()[i][j].getClass().getSimpleName().equals("Space") && field.getCreatures()[i][j].isDead()){
                    ImageView tmp = (ImageView) deathPane.getChildren().get(field.sizeX * j + i);
                    tmp.setImage(field.getCreatures()[i][j].report());
                    field.Delete(i, j);
                }
            }
        }
        //开始在上层放置东西
        for(int i = 0; i < field.sizeX; ++i) {
            for (int j = 0; j < field.sizeY; ++j) {
                if(field.getCreatures()[i][j].getClass().getSimpleName().equals("Space") || field.getCreatures()[i][j].isDead() == false) {
                    ImageView tmp = (ImageView) fieldPane.getChildren().get(field.sizeX * j + i);
                    tmp.setImage(field.getCreatures()[i][j].report());
                }
            }
        }
        field.setIsDisplaying(false);
    }

    //游戏结束 对UI进行操作
    public void gameOver(){
        cleanDeathPane();
        cleanFieldPane();
        field.setRunAllThread(false);
        InitController();
        fieldPane.setVisible(false);
        deathPane.setVisible(false);
        fieldPane.setDisable(true);
        deathPane.setDisable(true);
        winnerImageView.setVisible(false);
        operationTipImageView.setVisible(false);
        winnerImageView.setImage(emptyImg);
        operationTipImageView.setImage(emptyImg);
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
            }
        }, 1500);
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
                deathPane.setDisable(false);
                deathPane.setVisible(true);
                Mode = 1;
                field = new Field();
                field.getReady();
                field.saveCurrentField();
                display(field);
                this.cancel();
            }
        }, 1500);
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
            display(field);
            new Animation().fade_out_in(backgroundImageView, new Image(this.getClass().getResourceAsStream("/fightScene2.png")));
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    fieldPane.setDisable(false);
                    fieldPane.setVisible(true);
                    deathPane.setDisable(false);
                    deathPane.setVisible(true);
                    Mode = 2;
                    Step = 0;
                    historyFilename = file.getPath();
                    field.getReady();
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

    @FXML
    void pressKeyEventHandler(KeyEvent keyEvent){
        //开始界面
        if(Mode == 0){
            if(keyEvent.getCode() == KeyCode.L)
                clickHistoryButton(null);
        }
        //新游戏界面
        else if(Mode == 1){ //new Game
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE){
                if(!isRunning) {
                    isRunning = true;
                    field.startRun();
                    Timer timerCheck = new Timer();
                    timerCheck.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            int wins = field.isGameOver();
                            if (wins != 0) {
                                Mode = 3;
                                isRunning = false;
                                this.cancel();
                            }
                        }
                    },1000,1000);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            field.saveCurrentField();
                            display(field);
                            if(Mode != 1) {
                                this.cancel();
                            }
                        }
                    }, 500, 500);
                }
            }
        }
        //读取历史界面
        else if(Mode == 2){//load History
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.SPACE) {
                if(!isRunning) {
                    isRunning = true;
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (field.getHistoryField(historyFilename, Step++) == false) { //文件异常
                                gameOver();
                                this.cancel();
                            }
                            else {
                                display(field);
                                int wins = field.isGameOver();
                                if (wins != 0) {
                                    Mode = 4;
                                    isRunning = false;
                                    this.cancel();
                                }
                            }
                        }
                    }, 500, 500);
                }
            }
        }
        //新游戏结束界面 & 读取历史结束界面
        else if(Mode == 3 || Mode == 4){//new Game end
            if(keyEvent.getCode() == KeyCode.ESCAPE)
                gameOver();
            else if(keyEvent.getCode() == KeyCode.L)
                clickHistoryButton(null);
            else if(keyEvent.getCode() == KeyCode.SPACE)
                gameOver();
            //保存
            else if(keyEvent.getCode() == KeyCode.S && Mode == 3) {
                HLWFileFilter filter = new HLWFileFilter();
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(filter);
                fc.setMultiSelectionEnabled(false);
                int result = fc.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    if (!file.getPath().endsWith(".hlw")) {
                        file = new File(file.getPath() + ".hlw");
                    }
                    System.out.println("file path = " + file.getAbsolutePath());
                    try {
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    new FileModel().saveToFile(file.getAbsolutePath(), field.getFieldDocument());
                }
            }
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