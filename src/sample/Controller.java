package sample;

import Field.Field;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private ImageView [][] fieldImageViews;
    private Field field;
    private int Mode; //0=not start 1= new game 2=history game

    public Controller(){
        Mode = 0;
        field = new Field();
        fieldImageViews = new ImageView[field.sizeX][field.sizeY];
    }

    @FXML
    void clickStartButton(MouseEvent event){
        //更换战斗背景 隐藏控件
        backgroundImageView.setImage(new Image(this.getClass().getResourceAsStream("/fightScene1.png")));
        startButton.setVisible(false);
        historyButton.setVisible(false);
        exitButton.setVisible(false);
        aboutButton.setVisible(false);
        fieldPane.setDisable(false);
        //战斗 TODO
        field.getReady();
        display(field);
    }

    void display(Field field){
        for(int i = 0; i < field.sizeX; ++i)
            for(int j = 0; j < field.sizeY; ++j){
                fieldImageViews[i][j] = new ImageView();
                fieldImageViews[i][j].setImage(field.getCreatures()[i][j].report());
                fieldPane.add(fieldImageViews[i][j], i, j);
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
            backgroundImageView.setImage(new Image(this.getClass().getResourceAsStream("/fightScene2.png")));
            startButton.setVisible(false);
            historyButton.setVisible(false);
            exitButton.setVisible(false);
            aboutButton.setVisible(false);
            //读取文件 载入战斗场面 TODO
            field.getHistoryField(file.getName(), 0);
            display(field);
        }
    }

    @FXML
    void clickAboutButton(MouseEvent event){
        String email_info = "liu@triumphal.cn";
        String web_info = "https://github.com/triumphalLiu/Huluwa_VS_Monster";
        //添加一个包含超链接的对话框
        LinkButton email = new LinkButton("邮箱：", email_info);
        LinkButton website = new LinkButton("源码：", web_info);
        JLabel label = new JLabel("信息已复制到剪贴板:)");
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(3,1));
        jPanel.add(email);
        jPanel.add(website);
        jPanel.add(label);
        //复制内容到剪贴板
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(email_info + ", " + web_info);
        clip.setContents(tText, null);
        //显示对话框
        JOptionPane jOptionPane = new JOptionPane();
        jOptionPane.showMessageDialog(null, jPanel, "关于 葫芦娃V1.0", JOptionPane.INFORMATION_MESSAGE);
    }

    @FXML
    void clickExitButton(MouseEvent event){
        System.exit(0);
    }

    void gameOver(){
        backgroundImageView.setImage(new Image(this.getClass().getResourceAsStream("/Background.png")));
        startButton.setVisible(true);
        historyButton.setVisible(true);
        exitButton.setVisible(true);
        aboutButton.setVisible(true);
    }

    @FXML
    void pressKeyEventHandler(KeyEvent keyEvent){
        System.out.println("1");
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

class LinkButton extends JLabel {
    private String text;
    private String disc;
    private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);  //建立一个光标为手指类型的鼠标

    public LinkButton(String name, String web) {
        text = web;
        disc = name;
        setText();
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Runtime rt = Runtime.getRuntime();
                try {
                    String cmd = "rundll32 url.dll,FileProtocolHandler " + text;
                    rt.exec(cmd);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                setClickedText();
            }

            //鼠标移入标签时,设置文本样式事件
            public void mouseEntered(MouseEvent e) {
                setHandCursor();
                setMoveInText();
            }

            //鼠标移出标签时,设置文本样式事件
            public void mouseExited(MouseEvent e) {
                setDefaultCursor();
                setMoveOutText();
            }
        });
    }

    //设置初始样式
    private void setText() {
        String content = "<html><font color=blue>" + this.disc + this.text + "</font></html>";
        super.setText(content);
    }

    //设置鼠标单击样式
    private void setClickedText() {
        String content = "<html><font color=blue>" + this.disc + this.text + "</font></html>";
        super.setText(content);
    }

    //设置鼠标移入样式
    private void setMoveInText(){
        String content = "<html><font color=blue>" + this.disc + this.text + "</font></html>";
        super.setText(content);
    }

    //设置鼠标移出样式
    private void setMoveOutText(){
        String content = "<html><font color=blue>" + this.disc + this.text + "</font></html>";
        super.setText(content);
    }

    //设置光标为手指
    private void setHandCursor() {
        this.setCursor(handCursor);
    }

    //设置光标为默认
    private void setDefaultCursor() {
        this.setCursor(null);
    }
}