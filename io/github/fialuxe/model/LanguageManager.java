package io.github.fialuxe.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 多言語対応を管理するクラス
 * 英語と日本語の切り替えをサポート
 */
public class LanguageManager {
    public enum Language {
        ENGLISH, JAPANESE
    }
    
    private static Language currentLanguage = Language.JAPANESE;
    private static Map<String, Map<Language, String>> translations = new HashMap<>();
    
    static {
        // ゲームタイトル
        addTranslation("game.title", "Planarity Game", "Planarityゲーム");
        addTranslation("window.title", "Planarity Game - Untangle the Graph", "Planarity Game - 交差を解消してグラフを平面的にしよう");
        
        // 説明文
        addTranslation("game.description", "Drag nodes to eliminate edge crossings!", "ノードをドラッグして辺の交差を解消しよう!");
        
        // ボタン
        addTranslation("button.difficulty", "Difficulty:", "難易度:");
        addTranslation("button.newGame", "New Game", "新しいゲーム");
        addTranslation("button.shuffle", "Shuffle", "シャッフル");
        addTranslation("button.help", "How to Play", "遊び方");
        addTranslation("button.language", "Language", "言語");
        
        // 難易度
        addTranslation("difficulty.easy", "Easy (6 nodes)", "簡単 (6ノード)");
        addTranslation("difficulty.normal", "Normal (8 nodes)", "普通 (8ノード)");
        addTranslation("difficulty.hard", "Hard (10 nodes)", "難しい (10ノード)");
        addTranslation("difficulty.expert", "Expert (12 nodes)", "超難 (12ノード)");
        
        // 情報パネル
        addTranslation("info.cleared", "✓ Cleared! Intersections: 0", "✓ クリア！交差: 0");
        addTranslation("info.intersections", "Intersecting edges: ", "交差している辺: ");
        addTranslation("info.stats", "Nodes: %d / Edges: %d", "ノード: %d / 辺: %d");
        
        // クリアメッセージ
        addTranslation("solved.title", "Congratulations!", "おめでとう！");
        addTranslation("solved.message", "All intersections eliminated!", "すべての交差を解消しました！");
        
        // ヘルプ
        addTranslation("help.title", "How to Play", "遊び方");
        addTranslation("help.objective.title", "Objective:", "目的:");
        addTranslation("help.objective.text", "• Eliminate all edge crossings to make the graph planar", "• すべての辺の交差をなくして、グラフを平面的に配置する");
        
        addTranslation("help.controls.title", "Controls:", "操作方法:");
        addTranslation("help.controls.1", "• Drag nodes with your mouse to move them", "• ノード（点）をマウスでドラッグして移動できます");
        addTranslation("help.controls.2", "• Red lines are edges that cross other edges", "• 赤い線は他の辺と交差している辺です");
        addTranslation("help.controls.3", "• When all edges turn green, you've solved the puzzle!", "• 緑色になったら全ての交差が解消されクリアです！");
        
        addTranslation("help.tips.title", "Tips:", "ヒント:");
        addTranslation("help.tips.1", "• Start by organizing outer nodes first", "• 外側のノードから整理していくと解きやすいです");
        addTranslation("help.tips.2", "• Use intersection markers (white circles) as guides", "• 交差点のマーカー（白い円）を目印にしましょう");
        addTranslation("help.tips.3", "• Increase difficulty for more challenge!", "• 難易度を上げると、より挑戦的になります！");
    }
    
    private static void addTranslation(String key, String english, String japanese) {
        Map<Language, String> langMap = new HashMap<>();
        langMap.put(Language.ENGLISH, english);
        langMap.put(Language.JAPANESE, japanese);
        translations.put(key, langMap);
    }
    
    public static void setLanguage(Language language) {
        currentLanguage = language;
    }
    
    public static Language getCurrentLanguage() {
        return currentLanguage;
    }
    
    public static String getText(String key) {
        Map<Language, String> langMap = translations.get(key);
        if (langMap == null) {
            return key; // キーが見つからない場合はキーをそのまま返す
        }
        return langMap.getOrDefault(currentLanguage, key);
    }
    
    public static String getText(String key, Object... args) {
        String text = getText(key);
        return String.format(text, args);
    }
    
    public static String[] getDifficultyOptions() {
        return new String[] {
            getText("difficulty.easy"),
            getText("difficulty.normal"),
            getText("difficulty.hard"),
            getText("difficulty.expert")
        };
    }
    
    public static String getHelpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("【").append(getText("help.title")).append("】\n\n");
        
        sb.append(getText("help.objective.title")).append("\n");
        sb.append(getText("help.objective.text")).append("\n\n");
        
        sb.append(getText("help.controls.title")).append("\n");
        sb.append(getText("help.controls.1")).append("\n");
        sb.append(getText("help.controls.2")).append("\n");
        sb.append(getText("help.controls.3")).append("\n\n");
        
        sb.append(getText("help.tips.title")).append("\n");
        sb.append(getText("help.tips.1")).append("\n");
        sb.append(getText("help.tips.2")).append("\n");
        sb.append(getText("help.tips.3"));
        
        return sb.toString();
    }
}
