# Planarity

<img width="638" alt="Screenshot 2025-05-04 at 15 43 51" src="https://github.com/user-attachments/assets/89a6cbde-0d17-444e-9f38-f8f58a18aa4e" />


## Overview
### What is the purpose of this program?
This program is for playing Planarity.  
It adopts the MVC model. when executed, it displays a randomly generated puzzle.

### Where is the Main located?
The Main is located in PlanarityGame.java within the view folder.

### What are the issues with this program?
As noted in the comments, issues include casting processes and deprecated methods, indicating that the implementation is outdated and better alternatives may exist. These are considered challenges, but since there are no plans to use this program elsewhere at the moment, they have been left unaddressed. Updates may occur if new ideas arise.
(Also, it is too simple to play...)

## Requirement
compiling Java code

## Usage
1. Download the source code:
Clone the repository using Git
```bash
git clone <repository-url>
```
Or download the source code as a ZIP file from the repository page.

2. Navigate to the project directory:
Open a terminal and change to the project folder:
```bash
cd path/to/planarity
```
3. Compile the Java code
Compile all .java files using the Java compiler:
```
javac io/github/fialuxe/view/PlanarityGame.java
```
* Ensure you are in the root directory of the project, as the above command properly work.
4. Run the program:
Execute the compiled program:
```
java io/github/fialuxe/view/PlanarityGame
```
This will launch the Planarity game, displaying a randomly generated puzzle.
5. Play the game
Follow the on-screen instructions to interact with the puzzle. Drag vertices to untangle the graph and make it planar (no edges crossing).
## Author
[@fialuxe](https://github.com/fialuxe)
## Licence
MIT



## Japanese
### このプログラムは何ですか？
このプログラムはPlanarityをプレイするためのプログラムです。MVCモデルが採用されており、実行されることで、ランダムに生成されたパズルを表示します。
### どこにMainがありますか？
viewフォルダ内のPlanarityGame.javaにMainがあります。
### このプログラムの課題は何ですか?
コメントに残していますが、キャスト処理やdeprecationなど、実装方法が古い、もっとより良い実装が存在する可能性があります。そこが課題ではあると考えていますが、現状、このプログラムを他で使うことを考えていないため、放置されています。アイデアが思いつき次第、更新される可能性があります。

