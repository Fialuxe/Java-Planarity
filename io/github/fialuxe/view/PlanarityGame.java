package io.github.fialuxe.view;

import io.github.fialuxe.controller.*;
import io.github.fialuxe.model.*;
import io.github.fialuxe.model.LanguageManager.Language;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlanarityGame extends JFrame {
    private GraphModel model;
    private PlanarityPanel view;
    private GraphController controller;
    private JComboBox<String> difficultySelector;
    private JPanel topPanel;
    private JButton newGameButton;
    private JButton shuffleButton;
    private JButton helpButton;
    private JButton languageButton;
    private JLabel difficultyLabel;
    
    public PlanarityGame() {
        super(LanguageManager.getText("window.title"));
        
        // モデルとビューの初期化
        model = new GraphModel();
        view = new PlanarityPanel(model);
        controller = new GraphController(model, view);
        
        // レイアウト設定
        setLayout(new BorderLayout());
        
        // トップパネル（コントロール）
        topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);
        
        // メインゲームエリア
        add(view, BorderLayout.CENTER);
        
        // 初期グラフ生成
        generateGraph(6); // デフォルトは6ノード
        
        // ウィンドウ設定
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // アイコンとルック＆フィールの設定
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
        
        // 難易度選択
        difficultyLabel = new JLabel(LanguageManager.getText("button.difficulty"));
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(difficultyLabel);
        
        difficultySelector = new JComboBox<>(LanguageManager.getDifficultyOptions());
        difficultySelector.setFont(new Font("Arial", Font.PLAIN, 12));
        difficultySelector.addActionListener(e -> {
            int index = difficultySelector.getSelectedIndex();
            int nodes = 6 + (index * 2);
            generateGraph(nodes);
        });
        panel.add(difficultySelector);
        
        // 新しいゲームボタン
        newGameButton = createStyledButton(LanguageManager.getText("button.newGame"), new Color(70, 130, 180));
        newGameButton.addActionListener(e -> {
            int index = difficultySelector.getSelectedIndex();
            int nodes = 6 + (index * 2);
            generateGraph(nodes);
        });
        panel.add(newGameButton);
        
        // シャッフルボタン
        shuffleButton = createStyledButton(LanguageManager.getText("button.shuffle"), new Color(150, 100, 180));
        shuffleButton.addActionListener(e -> shuffleNodes());
        panel.add(shuffleButton);
        
        // ヘルプボタン
        helpButton = createStyledButton(LanguageManager.getText("button.help"), new Color(100, 160, 100));
        helpButton.addActionListener(e -> showHelp());
        panel.add(helpButton);
        
        // 言語切り替えボタン
        languageButton = createStyledButton("🌐 EN/日本語", new Color(200, 100, 60));
        languageButton.addActionListener(e -> toggleLanguage());
        panel.add(languageButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        // ホバー効果
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.brighter());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void generateGraph(int nodeCount) {
        GraphGenerator generator = new GraphGenerator(model);
        generator.generateRandomGraph(nodeCount, view.getWidth(), view.getHeight());
        view.repaint();
    }
    
    private void shuffleNodes() {
        GraphGenerator generator = new GraphGenerator(model);
        generator.shuffleNodes(view.getWidth(), view.getHeight());
        view.repaint();
    }
    
    private void showHelp() {
        JOptionPane.showMessageDialog(
            this,
            LanguageManager.getHelpMessage(),
            LanguageManager.getText("help.title"),
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    private void toggleLanguage() {
        // 言語を切り替え
        if (LanguageManager.getCurrentLanguage() == LanguageManager.Language.JAPANESE) {
            LanguageManager.setLanguage(LanguageManager.Language.ENGLISH);
        } else {
            LanguageManager.setLanguage(LanguageManager.Language.JAPANESE);
        }
        
        // UIを更新
        updateLanguage();
    }
    
    private void updateLanguage() {
        // ウィンドウタイトル
        setTitle(LanguageManager.getText("window.title"));
        
        // ボタンテキスト
        difficultyLabel.setText(LanguageManager.getText("button.difficulty"));
        newGameButton.setText(LanguageManager.getText("button.newGame"));
        shuffleButton.setText(LanguageManager.getText("button.shuffle"));
        helpButton.setText(LanguageManager.getText("button.help"));
        
        // 難易度選択の更新
        int selectedIndex = difficultySelector.getSelectedIndex();
        difficultySelector.removeAllItems();
        for (String option : LanguageManager.getDifficultyOptions()) {
            difficultySelector.addItem(option);
        }
        difficultySelector.setSelectedIndex(selectedIndex);
        
        // ビューの再描画
        view.repaint();
        
        // レイアウトの更新
        topPanel.revalidate();
        topPanel.repaint();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanarityGame game = new PlanarityGame();
            game.setVisible(true);
        });
    }
}
