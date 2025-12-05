package emu.nebula.nbcommand.controller;

import emu.nebula.nbcommand.Launcher;
import emu.nebula.nbcommand.service.I18nManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class AboutDialogController {
    
    @FXML
    private Label titleLabel;
    
    @FXML
    private Label versionLabel;
    
    @FXML
    private Label authorLabel;
    
    @FXML
    private Label introLabel;
    
    @FXML
    private TextArea introTextArea;
    
    @FXML
    private Button closeButton;
    
    private I18nManager i18n = I18nManager.getInstance();

    @FXML
    private void initialize() {
        // 初始化界面文本
        updateUIText();
        
        // 关闭按钮事件
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
    
    private void updateUIText() {
        titleLabel.setText("NB指令远程GM工具");
        versionLabel.setText("版本: " + Launcher.version);
        authorLabel.setText("作者: 战意电竞丶圆头奶龙仙人");
        introLabel.setText("简介:");
        introTextArea.setText("这是一个用于远程执行游戏服务器指令的图形化工具。");
        closeButton.setText("关闭");
    }
}