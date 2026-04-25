package com.mycompany.chess.client;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class EndScreen extends JFrame {

    public EndScreen(String resultText) {
        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 240);
        setLocationRelativeTo(null);

        JLabel title = new JLabel(resultText, SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));

        JButton playAgain = new JButton("Play Again");
        playAgain.addActionListener(e -> {
            new enter().setVisible(true);
            dispose();
        });

        JButton closeButton = new JButton("Exit");
        closeButton.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel();
        buttons.add(playAgain);
        buttons.add(closeButton);

        add(title, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }
}
