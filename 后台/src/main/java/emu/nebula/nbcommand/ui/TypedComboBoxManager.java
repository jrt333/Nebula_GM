package emu.nebula.nbcommand.ui;

import emu.nebula.nbcommand.service.TypedDataManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.stream.Collectors;

/**
 * 管理带类型的ComboBox控件，处理手动过滤和类型过滤功能
 */
public class TypedComboBoxManager {
    private final ComboBox<String> comboBox;
    private final TypedDataManager typedDataManager;
    private final String dataIdentifier;
    private String currentType = "all";
    // 标记是否正在更新项目，避免触发不必要的事件
    private boolean updatingItems = false;

    public TypedComboBoxManager(ComboBox<String> comboBox, TypedDataManager typedDataManager, String dataIdentifier) {
        this.comboBox = comboBox;
        this.typedDataManager = typedDataManager;
        this.dataIdentifier = dataIdentifier;
        setupSelectionListener();
    }

    /**
     * 设置选择监听器
     */
    private void setupSelectionListener() {
        comboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !updatingItems) {
                // 当选择一个项目时，更新编辑器文本
                try {
                    updatingItems = true;
                    comboBox.getEditor().setText(newVal);
                } finally {
                    updatingItems = false;
                }
            }
        });
    }

    /**
     * 手动更新过滤结果
     */
    public void updateFilter() {
        String searchText = comboBox.getEditor().getText();
        if (searchText == null || searchText.isEmpty()) {
            // 如果没有输入文本，显示当前类型的所有数据
            updateComboBoxItems(getCurrentItems(), searchText);
            return;
        }
        
        try {
            ObservableList<String> baseItems = getCurrentItems();
            final String lowerSearchText = searchText.toLowerCase();
            
            // 根据输入文本过滤数据（支持ID和标题的模糊搜索）
            ObservableList<String> filteredItems = baseItems.stream()
                    .filter(item -> {
                        // 支持对ID和标题的搜索
                        if (item != null && item.contains(" - ")) {
                            try {
                                String id = item.substring(0, item.indexOf(" - "));
                                String title = item.substring(item.indexOf(" - ") + 3);
                                return id.toLowerCase().contains(lowerSearchText) || title.toLowerCase().contains(lowerSearchText);
                            } catch (Exception e) {
                                // 如果解析出错，就进行简单的包含检查
                                return item.toLowerCase().contains(lowerSearchText);
                            }
                        } else {
                            return item != null && item.contains(searchText);
                        }
                    })
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            
            // 更新下拉列表
            updateComboBoxItems(filteredItems, searchText);
        } catch (Exception e) {
            // 出错时回退到显示所有项目
            ObservableList<String> allItems = typedDataManager.getDataList(dataIdentifier, "all");
            updateComboBoxItems(allItems, searchText);
        }
    }

    /**
     * 更新ComboBox项目，避免并发修改异常
     */
    private void updateComboBoxItems(ObservableList<String> items, String editorText) {
        javafx.application.Platform.runLater(() -> {
            try {
                updatingItems = true;
                comboBox.setItems(items);
                // 恢复编辑器文本
                if (editorText != null) {
                    comboBox.getEditor().setText(editorText);
                }
                // 展开下拉列表（如果有项目且编辑器有文本）
                if (!items.isEmpty() && editorText != null && !editorText.isEmpty()) {
                    comboBox.show();
                }
            } finally {
                updatingItems = false;
            }
        });
    }

    /**
     * 获取当前应该显示的项目列表
     */
    private ObservableList<String> getCurrentItems() {
        if (currentType != null && !currentType.isEmpty() && !"all".equals(currentType)) {
            return typedDataManager.getDataList(dataIdentifier, currentType);
        } else {
            return typedDataManager.getDataList(dataIdentifier, "all");
        }
    }

    /**
     * 更新当前类型
     */
    public void updateType(String type) {
        this.currentType = type;
        ObservableList<String> dataList = typedDataManager.getDataList(dataIdentifier, type);
        javafx.application.Platform.runLater(() -> {
            try {
                updatingItems = true;
                comboBox.setItems(dataList);
                comboBox.getEditor().clear();
            } finally {
                updatingItems = false;
            }
        });
    }

    /**
     * 获取数据标识符
     */
    public String getDataIdentifier() {
        return dataIdentifier;
    }
    
    /**
     * 获取当前类型
     */
    public String getCurrentType() {
        return currentType;
    }
    
    /**
     * 重新加载数据
     * 当语言切换时调用此方法更新内部状态
     */
    public void reloadData() {
        // 重新加载当前类型的数据
        updateType(currentType);
    }
}