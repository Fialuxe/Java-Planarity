# 🎮 Planarity Game - Enhanced Edition

A modern, visually appealing implementation of the classic Planarity puzzle game built with Java Swing.

[![Java](https://img.shields.io/badge/Java-8+-orange.svg)](https://www.oracle.com/java/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Cross--Platform-lightgrey.svg)]()

<img width="638" alt="Screenshot 2025-05-04 at 15 43 51" src="https://github.com/user-attachments/assets/89a6cbde-0d17-444e-9f38-f8f58a18aa4e" />

---

## 📖 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Screenshots](#-screenshots)
- [Getting Started](#-getting-started)
  - [Requirements](#requirements)
  - [Installation](#installation)
  - [Running the Game](#running-the-game)
- [How to Play](#-how-to-play)
- [Technical Details](#-technical-details)
- [Project Structure](#-project-structure)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [Author](#-author)
- [License](#-license)
- [日本語](#-日本語)

---

## 🎮 Overview

**Planarity** is a captivating graph-based puzzle game where players must untangle a network of interconnected nodes. The objective is simple yet challenging: rearrange the nodes by dragging them around the screen until no edges (lines) cross each other.

This enhanced edition transforms the classic Planarity concept into a polished, portfolio-ready application with:
- 🎨 **Beautiful, modern graphics** with gradient effects and smooth animations
- 🖱️ **Intuitive mouse controls** with visual feedback
- 🌐 **Bilingual support** (English/Japanese) with instant language switching
- 🎯 **Multiple difficulty levels** ranging from 6 to 12 nodes
- ✨ **Professional UI/UX design** suitable for all skill levels

Whether you're a puzzle enthusiast, a graph theory student, or just looking for a brain-teasing challenge, this game offers an engaging and visually pleasing experience.

---

## ✨ Features

#### 🎨 Visual Enhancements
- **Modern Color Scheme**: Beautiful gradient effects and carefully selected color palette
- **Smooth Rendering**: Anti-aliasing for crisp, professional graphics
- **Visual Feedback**: 
  - Hover effects on nodes
  - Different colors for normal edges (blue/grey) and intersecting edges (red)
  - Solved state shown in green with animated success message
  - Intersection markers highlighting crossing points
  - Node shadows and gradient effects for depth

#### 🖱️ Improved User Experience
- **Intuitive Controls**: 
  - Hover cursor changes to hand when over nodes
  - Larger click/drag areas for easier interaction
  - Smooth dragging with boundary constraints
- **Interactive UI**:
  - Difficulty selector (Easy: 6 nodes → Super Hard: 12 nodes)
  - "New Game" button to generate fresh puzzles
  - "Shuffle" button to randomize current puzzle
  - "Help" button with detailed instructions in Japanese
- **Real-time Information Panel**:
  - Current intersection count display
  - Node and edge statistics
  - Clear visual indication when puzzle is solved

#### 🎯 Gameplay Enhancements
- **Multiple Difficulty Levels**: Choose from Easy (6 nodes) to Expert (12 nodes)
- **Smart Graph Generation**: Circular initial layout with guaranteed solvable puzzles
- **Dynamic Puzzle Shuffling**: Randomize node positions for varied gameplay
- **Victory Celebration**: Animated success message when puzzle is solved
- **Boundary Constraints**: Nodes automatically stay within the playable area

#### 🌐 Internationalization
- **Bilingual Interface**: Seamlessly switch between English and Japanese
- **Dynamic Text Updates**: All UI elements update instantly when changing language
- **Localized Help**: Context-sensitive help in both languages

---

## 📸 Screenshots

### Game Interface (Japanese Mode)
The main game screen shows the information panel, difficulty selector, and control buttons.

### Intersection Visualization
Red lines indicate crossing edges, with white circular markers highlighting exact intersection points.

### Victory Screen
A congratulatory message appears when all intersections are eliminated.

### English Mode
Complete UI translation available at the click of a button.

---

## 🚀 Getting Started

### Requirements

Before running the game, ensure you have the following installed:

- **Java Development Kit (JDK)**: Version 8 or higher
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://openjdk.org/)
  - Verify installation: `java -version` and `javac -version`
- **Operating System**: Windows, macOS, or Linux (cross-platform compatible)
- **Terminal/Command Prompt**: For compilation and execution
- *(Optional)* **IDE**: IntelliJ IDEA, Eclipse, or VS Code for development

### Installation

#### Option 1: Clone with Git

```bash
# Clone the repository
git clone https://github.com/Fialuxe/Java-Planarity.git

# Navigate to the project directory
cd Java-Planarity
```

#### Option 2: Download ZIP

1. Visit the [repository page](https://github.com/Fialuxe/Java-Planarity)
2. Click the green "Code" button
3. Select "Download ZIP"
4. Extract the downloaded file
5. Navigate to the extracted folder in your terminal

### Running the Game

#### Step 1: Compile the Source Code

From the project root directory, compile all Java files:

```bash
javac io/github/fialuxe/model/*.java io/github/fialuxe/controller/*.java io/github/fialuxe/view/*.java
```

**Expected Output**: No output means successful compilation. If you see errors, check that:
- You're in the correct directory
- JDK is properly installed
- All source files are present

#### Step 2: Run the Game

Execute the compiled program:

```bash
java io.github.fialuxe.view.PlanarityGame
```

**Expected Result**: A game window should appear with the Planarity interface.

#### Troubleshooting

**"Command not found" error**:
- Ensure JDK is installed and added to your system PATH
- Try using the full path to `javac` and `java`

**"Cannot find symbol" errors**:
- Make sure all files are in the correct directory structure
- Try cleaning and recompiling: `rm io/github/fialuxe/*/*.class` then recompile

**Game window doesn't appear**:
- Check if your system supports GUI applications
- On headless systems, ensure X11 forwarding is enabled

---

## 🎯 How to Play

### Objective

**Eliminate all edge crossings** to create a planar graph where no lines intersect.

### Controls

| Action | Method |
|--------|--------|
| **Move a node** | Click and drag any blue circle (node) |
| **View node details** | Hover your mouse over a node |
| **Start new puzzle** | Click "New Game" button |
| **Randomize layout** | Click "Shuffle" button |
| **Change difficulty** | Select from dropdown menu |
| **Switch language** | Click "🌐 EN/日本語" button |
| **View instructions** | Click "How to Play" / "遊び方" button |

### Visual Indicators

- 🔵 **Blue Circles**: Nodes (draggable points)
- ⚫ **Grey Lines**: Edges that don't cross (good!)
- 🔴 **Red Lines**: Edges that intersect (need to fix!)
- ⚪ **White Circle Markers**: Exact intersection points
- 🟢 **Green Lines**: All edges when puzzle is solved
- 💚 **Green Node**: Currently being dragged
- 💙 **Light Blue Node**: Mouse hover effect

### Tips & Strategies

1. **Start with outer nodes**: Move perimeter nodes first to create space
2. **Follow the markers**: White intersection markers show where conflicts are
3. **Work systematically**: Focus on eliminating one intersection at a time
4. **Use the shuffle**: If stuck, shuffle and try a different approach
5. **Increase difficulty gradually**: Master easier levels before attempting expert mode

### Winning the Game

When all edges turn **green** and the intersection count reaches **zero**, you've won! A congratulatory message will appear celebrating your success.

---

## 🔧 Technical Details

### Architecture

This application follows the **Model-View-Controller (MVC)** design pattern for clean code organization and maintainability:

```
┌─────────────────────────────────────────┐
│           PlanarityGame (Main)          │
│         - Application Entry Point       │
└─────────────┬───────────────────────────┘
              │
    ┌─────────┴──────────┐
    │                    │
┌───▼─────────┐    ┌────▼──────────┐
│   Model     │    │  Controller   │
├─────────────┤    ├───────────────┤
│ GraphModel  │◄───│GraphController│
│ GraphGen.   │    └───────┬───────┘
│ LanguageMgr │            │
└─────┬───────┘            │
      │                    │
      │    ┌───────────────▼┐
      └────►     View       │
           ├────────────────┤
           │ PlanarityPanel │
           │ PlanarityGame  │
           └────────────────┘
```

**Model** (`io.github.fialuxe.model`):
- `GraphModel.java`: Core graph data structure and intersection detection
- `GraphGenerator.java`: Random graph generation algorithms
- `Point.java`: 2D coordinate representation
- `Edge.java`: Edge (connection) between nodes
- `LanguageManager.java`: Internationalization and text management

**View** (`io.github.fialuxe.view`):
- `PlanarityGame.java`: Main window and UI controls
- `PlanarityPanel.java`: Custom game rendering with Graphics2D

**Controller** (`io.github.fialuxe.controller`):
- `GraphController.java`: Mouse event handling and user interaction

### Key Technologies

- **Java Swing**: GUI framework for cross-platform interface
- **Graphics2D**: Advanced 2D graphics rendering
- **Observer Pattern**: Real-time view updates when model changes
- **Anti-aliasing**: Smooth, professional-quality graphics
- **Gradient Paint**: Modern visual effects

### Performance Optimizations

- Efficient intersection detection using mathematical algorithms
- Cached intersection lists to avoid redundant calculations
- Optimized rendering with double buffering
- Boundary checking to prevent out-of-bounds operations

---

## 📁 Project Structure

```
Java-Planarity/
│
├── README.md                          # This file
├── IMPROVEMENTS.md                    # Detailed changelog
│
└── io/github/fialuxe/
    ├── model/                        # Business logic layer
    │   ├── GraphModel.java          # Graph state management
    │   ├── GraphGenerator.java      # Puzzle generation
    │   ├── Point.java               # 2D point representation
    │   ├── Edge.java                # Edge data structure
    │   └── LanguageManager.java     # i18n support
    │
    ├── view/                         # Presentation layer
    │   ├── PlanarityGame.java       # Main window (Entry point)
    │   └── PlanarityPanel.java      # Game canvas
    │
    └── controller/                   # User interaction layer
        └── GraphController.java      # Mouse event handling
```

### File Descriptions

| File | Lines | Purpose |
|------|-------|---------|
| `GraphModel.java` | ~150 | Manages graph state, intersection detection, and Observer pattern |
| `GraphGenerator.java` | ~100 | Generates solvable puzzles with configurable difficulty |
| `PlanarityPanel.java` | ~280 | Renders the game with modern graphics and effects |
| `PlanarityGame.java` | ~180 | Creates UI controls and manages application lifecycle |
| `GraphController.java` | ~80 | Handles mouse events with hover and drag support |
| `LanguageManager.java` | ~120 | Manages bilingual text resources |

---

## 🚀 Future Enhancements

Potential features for future development:

### Gameplay
- [ ] **Timer mode**: Track how quickly puzzles are solved
- [ ] **Move counter**: Count the number of node movements
- [ ] **Hint system**: Provide subtle clues for stuck players
- [ ] **Undo/Redo**: Allow players to reverse their moves
- [ ] **Custom puzzles**: Let users create and share puzzles

### UI/UX
- [ ] **Sound effects**: Audio feedback for moves and victories
- [ ] **Animations**: Smooth node transitions and victory effects
- [ ] **Themes**: Dark mode and customizable color schemes
- [ ] **Accessibility**: Keyboard navigation and screen reader support

### Technical
- [ ] **Save/Load**: Persist game state between sessions
- [ ] **Leaderboard**: Online ranking system
- [ ] **Statistics**: Track personal best times and success rates
- [ ] **More languages**: Add support for additional languages
- [ ] **Mobile version**: Port to Android/iOS

### Advanced Features
- [ ] **Graph algorithms**: Visualize planar graph algorithms
- [ ] **Educational mode**: Teach graph theory concepts
- [ ] **Challenge mode**: Time-limited or move-limited puzzles

---

## 🤝 Contributing

Contributions are welcome! If you'd like to improve this project:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/AmazingFeature`)
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)
4. **Push** to the branch (`git push origin feature/AmazingFeature`)
5. **Open** a Pull Request

Please ensure your code follows the existing style and includes appropriate comments.

---

## 👨‍💻 Author

## 💻 Requirement
* Java Development Kit (JDK) 8 or higher
* A Java compiler (javac)
* A terminal or IDE (e.g., IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Usage

### 1. Download the source code
Clone the repository using Git:
```bash
git clone https://github.com/Fialuxe/Java-Planarity.git
```
Or download the source code as a ZIP file from the repository page.

### 2. Navigate to the project directory
```bash
cd Java-Planarity
```

### 3. Compile the Java code
```bash
javac io/github/fialuxe/model/*.java io/github/fialuxe/controller/*.java io/github/fialuxe/view/*.java
```

### 4. Run the program
```bash
java io.github.fialuxe.view.PlanarityGame
```

### 5. Play the game! 🎮
- **Drag nodes** with your mouse to rearrange the graph
- **Watch the edges**: Red edges are crossing, green means solved!
- **Select difficulty** from the dropdown menu
- **Click "New Game"** to try a different puzzle
- **Click "Help"** for detailed instructions

## 🎯 How to Play
1. The goal is to arrange all nodes so that no edges (lines) cross each other
2. Click and drag any node to move it
3. Red edges indicate intersections - try to eliminate all of them!
4. When all edges turn green, you've won! 🎉
5. Use the difficulty selector to challenge yourself with more complex puzzles

## 🛠️ Technical Improvements
- Added proper Observable pattern with `setChanged()` calls
- Enhanced `GraphGenerator` with parameterized graph generation
- Implemented shuffle functionality for varying puzzle difficulty
- Added null-safety checks in Observer pattern
- Modern Swing UI components with custom styling
- Efficient intersection detection and visualization

## 📝 Future Improvements (Ideas)
- Add timer and move counter
- Save/load puzzle states
- Leaderboard system
- More graph layout algorithms
- Sound effects and animations
- Undo/redo functionality

## 👨‍💻 Author

**Fialuxe**
- GitHub: [@fialuxe](https://github.com/fialuxe)
- Repository: [Java-Planarity](https://github.com/Fialuxe/Java-Planarity)

---

## 📄 License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 Fialuxe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🙏 Acknowledgments

- Inspired by the original [Planarity](http://planarity.net/) game
- Built with Java Swing framework
- Thanks to the open-source community for continuous inspiration

---

---

# 📖 日本語

<details>
<summary><b>日本語版のREADMEを表示（クリックして展開）</b></summary>

## 🎮 概要

**Planarity（プラナリティ）**は、ノード（点）とエッジ（線）で構成されたグラフを整理するパズルゲームです。すべてのエッジが交差しないように、ノードをドラッグして配置し直すのが目的です。

このエンハンス版は、オリジナルのPlanarityゲームをベースに、以下の特徴を持つポートフォリオ品質のアプリケーションとして作り直したものです：

- 🎨 **美しいグラフィック** - グラデーション効果とスムーズなアニメーション
- 🖱️ **直感的な操作** - マウスでドラッグするだけの簡単操作
- 🌐 **バイリンガル対応** - 英語/日本語をワンクリックで切り替え
- 🎯 **複数の難易度** - 6ノードから12ノードまで4段階
- ✨ **プロフェッショナルなUI** - 初心者から上級者まで楽しめる設計

パズル好きな方、グラフ理論を学ぶ学生、または頭の体操をしたい方まで、幅広い層に楽しんでいただけます。

---

## ✨ 主な機能

### 🎨 ビジュアルの改善
- **モダンなカラーパレット** - 目に優しく洗練された配色
- **アンチエイリアシング** - なめらかで高品質な描画
- **グラデーション効果** - ノードに立体感と深みを追加
- **視覚的フィードバック**：
  - マウスオーバー時：ノードが明るく大きくなる
  - ドラッグ中：ノードが緑色にハイライト
  - 交差エッジ：赤色で明確に表示
  - 解決時：すべてのエッジが緑色に
- **交差マーカー** - 白い円で交差点を明示
- **シャドウ効果** - ノードに影をつけて立体的に

### 🖱️ 操作性の向上
- **直感的なカーソル** - ノード上で手のアイコンに変化
- **広いクリック範囲** - 10pxから15pxに拡大して操作しやすく
- **境界制御** - ノードが画面外に出ないように自動制限
- **スムーズなドラッグ** - 遅延のない快適な操作感

### 🎮 ゲーム機能
- **4段階の難易度**：
  - 簡単（6ノード）- 初心者向け
  - 普通（8ノード）- ほどよい挑戦
  - 難しい（10ノード）- 考える必要あり
  - 超難（12ノード）- エキスパート向け
- **スマートな問題生成** - 必ず解ける問題を自動生成
- **シャッフル機能** - 同じグラフで配置を変えて再挑戦
- **クリア演出** - おめでとうメッセージで達成感を演出

### 🌐 多言語対応
- **日本語/英語の切り替え** - ボタン一つで瞬時に切り替え
- **完全なローカライゼーション** - すべてのUIテキストが翻訳済み
- **ヘルプも翻訳** - 各言語で適切な説明を表示

---

## 🚀 始め方

### 必要な環境

このゲームを実行するには以下が必要です：

- **Java開発キット (JDK)**: バージョン8以上
  - [Oracle](https://www.oracle.com/java/technologies/downloads/)または[OpenJDK](https://openjdk.org/)からダウンロード
  - インストール確認：`java -version` および `javac -version`
- **OS**: Windows、macOS、またはLinux（クロスプラットフォーム対応）
- **ターミナル**: コンパイルと実行に使用
- *（オプション）* **IDE**: IntelliJ IDEA、Eclipse、VS Codeなど

### インストール方法

#### 方法1: Gitでクローン

```bash
# リポジトリをクローン
git clone https://github.com/Fialuxe/Java-Planarity.git

# プロジェクトディレクトリに移動
cd Java-Planarity
```

#### 方法2: ZIPダウンロード

1. [リポジトリページ](https://github.com/Fialuxe/Java-Planarity)にアクセス
2. 緑色の「Code」ボタンをクリック
3. 「Download ZIP」を選択
4. ダウンロードしたファイルを解凍
5. ターミナルで解凍したフォルダに移動

### 実行方法

#### ステップ1: ソースコードのコンパイル

プロジェクトのルートディレクトリで、すべてのJavaファイルをコンパイルします：

```bash
javac io/github/fialuxe/model/*.java io/github/fialuxe/controller/*.java io/github/fialuxe/view/*.java
```

**期待される結果**: 何も表示されなければコンパイル成功です。エラーが出た場合は以下を確認してください：
- 正しいディレクトリにいるか
- JDKが正しくインストールされているか
- すべてのソースファイルが存在するか

#### ステップ2: ゲームの起動

コンパイルしたプログラムを実行します：

```bash
java io.github.fialuxe.view.PlanarityGame
```

**期待される結果**: ゲームウィンドウが表示されます。

#### トラブルシューティング

**「コマンドが見つかりません」エラー**:
- JDKがインストールされ、PATHに追加されているか確認
- `javac`と`java`のフルパスを使用してみる

**「シンボルが見つかりません」エラー**:
- すべてのファイルが正しいディレクトリ構造にあるか確認
- クリーンして再コンパイル: `rm io/github/fialuxe/*/*.class` その後再コンパイル

**ゲームウィンドウが表示されない**:
- システムがGUIアプリケーションをサポートしているか確認
- ヘッドレスシステムの場合、X11フォワーディングが有効か確認

---

## 🎯 遊び方

### 目的

**すべてのエッジ（線）の交差をなくす**ことで、平面的なグラフを作成します。

### 操作方法

| アクション | 方法 |
|--------|------|
| **ノードを動かす** | 青い円（ノード）をクリック＆ドラッグ |
| **ノードの詳細を見る** | ノードにマウスをホバー |
| **新しいパズル** | 「新しいゲーム」ボタンをクリック |
| **配置をランダム化** | 「シャッフル」ボタンをクリック |
| **難易度を変更** | ドロップダウンメニューから選択 |
| **言語を切り替え** | 「🌐 EN/日本語」ボタンをクリック |
| **説明を見る** | 「遊び方」/「How to Play」ボタンをクリック |

### 視覚的なインジケーター

- 🔵 **青い円**: ノード（ドラッグ可能な点）
- ⚫ **灰色の線**: 交差していないエッジ（良い状態！）
- 🔴 **赤い線**: 交差しているエッジ（修正が必要！）
- ⚪ **白い円マーカー**: 正確な交差地点
- 🟢 **緑の線**: パズル解決時のすべてのエッジ
- 💚 **緑のノード**: 現在ドラッグ中
- 💙 **明るい青のノード**: マウスホバー中

### 攻略のヒント

1. **外側から始める**: まず周辺のノードを整理してスペースを作る
2. **マーカーを活用**: 白い交差マーカーで問題箇所を把握
3. **一つずつ解決**: 一度に一つの交差を解消することに集中
4. **シャッフルを使う**: 行き詰まったら配置を変えて再挑戦
5. **段階的に難易度を上げる**: 簡単なレベルをマスターしてから上級に挑戦

### クリア条件

すべてのエッジが**緑色**になり、交差数が**ゼロ**になったら勝利です！おめでとうメッセージが表示されます。

---

## 🔧 技術詳細

### アーキテクチャ

このアプリケーションは**MVC（Model-View-Controller）**デザインパターンを採用しています：

**Model（モデル）** - ビジネスロジック層
- `GraphModel.java`: グラフの状態管理と交差判定
- `GraphGenerator.java`: パズル生成アルゴリズム
- `Point.java`, `Edge.java`: データ構造
- `LanguageManager.java`: 多言語対応

**View（ビュー）** - プレゼンテーション層
- `PlanarityGame.java`: メインウィンドウとUIコントロール
- `PlanarityPanel.java`: ゲーム描画キャンバス

**Controller（コントローラー）** - ユーザー操作層
- `GraphController.java`: マウスイベント処理

### 使用技術

- **Java Swing**: クロスプラットフォームGUIフレームワーク
- **Graphics2D**: 高度な2Dグラフィックス描画
- **Observerパターン**: モデル変更時のリアルタイム更新
- **アンチエイリアシング**: なめらかで高品質な描画
- **グラデーションペイント**: モダンな視覚効果

---

## 📁 プロジェクト構成

```
Java-Planarity/
│
├── README.md                          # このファイル
├── IMPROVEMENTS.md                    # 詳細な変更履歴
│
└── io/github/fialuxe/
    ├── model/                        # ビジネスロジック層
    │   ├── GraphModel.java          # グラフ状態管理
    │   ├── GraphGenerator.java      # パズル生成
    │   ├── Point.java               # 2D座標
    │   ├── Edge.java                # エッジデータ構造
    │   └── LanguageManager.java     # 多言語対応
    │
    ├── view/                         # プレゼンテーション層
    │   ├── PlanarityGame.java       # メインウィンドウ（エントリーポイント）
    │   └── PlanarityPanel.java      # ゲームキャンバス
    │
    └── controller/                   # ユーザー操作層
        └── GraphController.java      # マウスイベント処理
```

---

## 🚀 今後の拡張予定

### ゲームプレイ
- [ ] **タイマーモード**: パズル解決時間の計測
- [ ] **移動カウンター**: ノード移動回数のカウント
- [ ] **ヒント機能**: 行き詰まったときのヘルプ
- [ ] **元に戻す/やり直す**: 操作の取り消しと再実行
- [ ] **カスタムパズル**: ユーザーが作成・共有できるパズル

### UI/UX
- [ ] **サウンドエフェクト**: 操作音や勝利音
- [ ] **アニメーション**: スムーズな移動効果
- [ ] **テーマ**: ダークモードやカスタマイズ可能な配色
- [ ] **アクセシビリティ**: キーボード操作やスクリーンリーダー対応

### 技術的改善
- [ ] **保存/読み込み**: ゲーム状態の永続化
- [ ] **ランキング**: オンラインスコアボード
- [ ] **統計情報**: 個人記録や成功率の追跡
- [ ] **言語追加**: 他言語のサポート
- [ ] **モバイル版**: Android/iOSへの移植

---

## 👨‍💻 作者

**Fialuxe**
- GitHub: [@fialuxe](https://github.com/fialuxe)
- リポジトリ: [Java-Planarity](https://github.com/Fialuxe/Java-Planarity)

---

## 📄 ライセンス

このプロジェクトは**MITライセンス**の下で公開されています。

---

## 🙏 謝辞

- オリジナルの[Planarity](http://planarity.net/)ゲームにインスパイアされました
- Java Swingフレームワークを使用して構築
- オープンソースコミュニティの継続的なインスピレーションに感謝

</details>

---

## ⭐ Star History

If you found this project helpful or interesting, please consider giving it a star! ⭐

---

**Made with ❤️ and ☕ by Fialuxe**

