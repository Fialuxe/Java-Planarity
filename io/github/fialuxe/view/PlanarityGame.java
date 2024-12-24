package io.github.fialuxe.view;

import io.github.fialuxe.controller.*;
import io.github.fialuxe.model.*;
import javax.swing.*;

public class PlanarityGame {
    public static void main(String[] args) {
        GraphModel model = new GraphModel();
        PlanarityPanel view = new PlanarityPanel(model);
        GraphController controller = new GraphController(model, view);
        GraphGenerator g = new GraphGenerator(model);
        g.generateRandomGraph();
        // ウィンドウ設定
        JFrame frame = new JFrame("Planarity Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
