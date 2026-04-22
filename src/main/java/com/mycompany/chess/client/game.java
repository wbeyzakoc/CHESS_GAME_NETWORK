/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.chess.client;

import java.awt.Component;
import java.awt.Cursor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;

/**
 *
 * @author beyzamacbook
 */
public class game extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(game.class.getName());
List<JButton> pieceButtons;
List<JButton> boardButtons;

Map<String, String> piecePositions = new HashMap<>();
String selectedPiece = null;
JButton selectedButton = null;
    /**
     * Creates new form game
     */
    public game() {
        initComponents();

        // 🔥 BUTONLARA NAME VER
        // 🔥 HER BUTONUN VARIABLE ADINI NAME YAP (EN SAĞLAM)
        att1.setName("att1");
        att2.setName("att2");

        piyont1.setName("piyont1");
        piyont2.setName("piyont2");
        piyont3.setName("piyont3");
        piyont4.setName("piyont4");
        piyont5.setName("piyont5");
        piyont6.setName("piyont6");
        piyont7.setName("piyont7");
        piyont8.setName("piyont8");

        saht.setName("saht");
        vezirt.setName("vezirt");
        kalet1.setName("kalet1");
        kalet2.setName("kalet2");
        filt1.setName("filt1");
        filt2.setName("filt2");

        aty1.setName("aty1");
        aty2.setName("aty2");

        piyony1.setName("piyony1");
        piyony2.setName("piyony2");
        piyony3.setName("piyony3");
        piyony4.setName("piyony4");
        piyony5.setName("piyony5");
        piyony6.setName("piyony6");
        piyony7.setName("piyony7");
        piyony8.setName("piyony8");

        sahy.setName("sahy");
        veziry.setName("veziry");
        kaley1.setName("kaley1");
        kaley2.setName("kaley2");
        fily1.setName("fily1");
        fily2.setName("fily2");

        // 🔥 TAHTA NAME (BU ŞART)
        A1.setName("A1");
        A2.setName("A2");
        A3.setName("A3");
        A4.setName("A4");
        A5.setName("A5");
        A6.setName("A6");
        A7.setName("A7");
        A8.setName("A8");

        B1.setName("B1");
        B2.setName("B2");
        B3.setName("B3");
        B4.setName("B4");
        B5.setName("B5");
        B6.setName("B6");
        B7.setName("B7");
        B8.setName("B8");

        C1.setName("C1");
        C2.setName("C2");
        C3.setName("C3");
        C4.setName("C4");
        C5.setName("C5");
        C6.setName("C6");
        C7.setName("C7");
        C8.setName("C8");

        D1.setName("D1");
        D2.setName("D2");
        D3.setName("D3");
        D4.setName("D4");
        D5.setName("D5");
        D6.setName("D6");
        D7.setName("D7");
        D8.setName("D8");

        E1.setName("E1");
        E2.setName("E2");
        E3.setName("E3");
        E4.setName("E4");
        E5.setName("E5");
        E6.setName("E6");
        E7.setName("E7");
        E8.setName("E8");

        F1.setName("F1");
        F2.setName("F2");
        F3.setName("F3");
        F4.setName("F4");
        F5.setName("F5");
        F6.setName("F6");
        F7.setName("F7");
        F8.setName("F8");

        G1.setName("G1");
        G2.setName("G2");
        G3.setName("G3");
        G4.setName("G4");
        G5.setName("G5");
        G6.setName("G6");
        G7.setName("G7");
        G8.setName("G8");

        H1.setName("H1");
        H2.setName("H2");
        H3.setName("H3");
        H4.setName("H4");
        H5.setName("H5");
        H6.setName("H6");
        H7.setName("H7");
        H8.setName("H8");

        // 🔥 KONUM MAP
        piecePositions.put("att1", "B1");
        piecePositions.put("att2", "G1");
        piecePositions.put("piyont1", "A2");
        piecePositions.put("piyont2", "B2");
        // 🎯 LİSTELER (BURASI KRİTİK)
        pieceButtons = Arrays.asList(
                att1, att2, piyont1, piyont2, piyont3, piyont4,
                piyont5, piyont6, piyont7, piyont8,
                saht, vezirt, kalet1, kalet2, filt1, filt2,
                aty1, aty2, piyony1, piyony2, piyony3, piyony4,
                piyony5, piyony6, piyony7, piyony8,
                sahy, veziry, kaley1, kaley2, fily1, fily2
        );

        boardButtons = Arrays.asList(
                A1, A2, A3, A4, A5, A6, A7, A8,
                B1, B2, B3, B4, B5, B6, B7, B8,
                C1, C2, C3, C4, C5, C6, C7, C8,
                D1, D2, D3, D4, D5, D6, D7, D8,
                E1, E2, E3, E4, E5, E6, E7, E8,
                F1, F2, F3, F4, F5, F6, F7, F8,
                G1, G2, G3, G4, G5, G6, G7, G8,
                H1, H2, H3, H4, H5, H6, H7, H8
        );

        // 🔥 METHODLARI ÇAĞIR
        secimMethodu(pieceButtons, boardButtons);
        hedefSec(boardButtons);
        
        
        
        
        
        
        
        
        for (Component c : jPanel1.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;

                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        for (Component c : jPanel1.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBorderPainted(false);
            }
        
        }
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        att1 = new javax.swing.JButton();
        kalet2 = new javax.swing.JButton();
        kalet1 = new javax.swing.JButton();
        vezirt = new javax.swing.JButton();
        att2 = new javax.swing.JButton();
        saht = new javax.swing.JButton();
        filt2 = new javax.swing.JButton();
        filt1 = new javax.swing.JButton();
        piyont1 = new javax.swing.JButton();
        piyont2 = new javax.swing.JButton();
        piyont3 = new javax.swing.JButton();
        piyont4 = new javax.swing.JButton();
        piyont5 = new javax.swing.JButton();
        piyont6 = new javax.swing.JButton();
        piyont7 = new javax.swing.JButton();
        piyont8 = new javax.swing.JButton();
        kaley1 = new javax.swing.JButton();
        aty1 = new javax.swing.JButton();
        fily1 = new javax.swing.JButton();
        veziry = new javax.swing.JButton();
        sahy = new javax.swing.JButton();
        fily2 = new javax.swing.JButton();
        aty2 = new javax.swing.JButton();
        kaley2 = new javax.swing.JButton();
        piyony1 = new javax.swing.JButton();
        piyony2 = new javax.swing.JButton();
        piyony3 = new javax.swing.JButton();
        piyony4 = new javax.swing.JButton();
        piyony5 = new javax.swing.JButton();
        piyony6 = new javax.swing.JButton();
        piyony7 = new javax.swing.JButton();
        piyony8 = new javax.swing.JButton();
        A8 = new javax.swing.JButton();
        A1 = new javax.swing.JButton();
        A2 = new javax.swing.JButton();
        A3 = new javax.swing.JButton();
        A4 = new javax.swing.JButton();
        A5 = new javax.swing.JButton();
        A6 = new javax.swing.JButton();
        A7 = new javax.swing.JButton();
        B1 = new javax.swing.JButton();
        B2 = new javax.swing.JButton();
        B3 = new javax.swing.JButton();
        B4 = new javax.swing.JButton();
        B5 = new javax.swing.JButton();
        B6 = new javax.swing.JButton();
        B7 = new javax.swing.JButton();
        B8 = new javax.swing.JButton();
        C1 = new javax.swing.JButton();
        C2 = new javax.swing.JButton();
        C3 = new javax.swing.JButton();
        C4 = new javax.swing.JButton();
        C5 = new javax.swing.JButton();
        C6 = new javax.swing.JButton();
        C7 = new javax.swing.JButton();
        C8 = new javax.swing.JButton();
        D1 = new javax.swing.JButton();
        D2 = new javax.swing.JButton();
        D3 = new javax.swing.JButton();
        D4 = new javax.swing.JButton();
        D5 = new javax.swing.JButton();
        D6 = new javax.swing.JButton();
        D7 = new javax.swing.JButton();
        D8 = new javax.swing.JButton();
        E1 = new javax.swing.JButton();
        E2 = new javax.swing.JButton();
        E3 = new javax.swing.JButton();
        E4 = new javax.swing.JButton();
        E5 = new javax.swing.JButton();
        E6 = new javax.swing.JButton();
        E7 = new javax.swing.JButton();
        E8 = new javax.swing.JButton();
        F1 = new javax.swing.JButton();
        F2 = new javax.swing.JButton();
        F3 = new javax.swing.JButton();
        F4 = new javax.swing.JButton();
        F5 = new javax.swing.JButton();
        F6 = new javax.swing.JButton();
        F7 = new javax.swing.JButton();
        F8 = new javax.swing.JButton();
        G1 = new javax.swing.JButton();
        G2 = new javax.swing.JButton();
        G3 = new javax.swing.JButton();
        G4 = new javax.swing.JButton();
        G5 = new javax.swing.JButton();
        G6 = new javax.swing.JButton();
        G7 = new javax.swing.JButton();
        G8 = new javax.swing.JButton();
        H1 = new javax.swing.JButton();
        H2 = new javax.swing.JButton();
        H3 = new javax.swing.JButton();
        H4 = new javax.swing.JButton();
        H5 = new javax.swing.JButton();
        H6 = new javax.swing.JButton();
        H7 = new javax.swing.JButton();
        H8 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        att1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/att-Picsart-BackgroundRemover.png")); // NOI18N
        att1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                att1ActionPerformed(evt);
            }
        });
        jPanel1.add(att1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 100, 110));

        kalet2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/kalet-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(kalet2, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 80, 70, 110));

        kalet1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/kalet-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(kalet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 70, 110));

        vezirt.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/vezirt-fotor-bg-remover-2026042220499.png")); // NOI18N
        vezirt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vezirtActionPerformed(evt);
            }
        });
        jPanel1.add(vezirt, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, -1, 120));

        att2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/att-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(att2, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 80, -1, 120));

        saht.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/saht-fotor-bg-remover-2026042220454.png")); // NOI18N
        jPanel1.add(saht, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 70, 130));

        filt2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/filt-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(filt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, -1, -1));

        filt1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/filt-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(filt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));

        piyont1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        piyont1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piyont1ActionPerformed(evt);
            }
        });
        jPanel1.add(piyont1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 60, 100));

        piyont2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 200, 60, 100));

        piyont3.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 200, 60, 100));

        piyont4.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 60, 100));

        piyont5.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 50, 100));

        piyont6.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, -1, 100));

        piyont7.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 200, -1, 100));

        piyont8.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyont-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyont8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 200, 60, 100));

        kaley1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/kaley-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(kaley1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 790, -1, 100));

        aty1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/aty-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(aty1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 780, -1, 110));

        fily1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/fily-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(fily1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 780, 70, 110));

        veziry.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/veziry.png")); // NOI18N
        jPanel1.add(veziry, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 790, -1, -1));

        sahy.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/sahy-fotor-bg-remover-20260422204714.png")); // NOI18N
        sahy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sahyActionPerformed(evt);
            }
        });
        jPanel1.add(sahy, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 770, -1, 130));

        fily2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/fily-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(fily2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 780, -1, 110));

        aty2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/aty-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(aty2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 780, -1, -1));

        kaley2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/kaley-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(kaley2, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 780, -1, -1));

        piyony1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 680, -1, -1));

        piyony2.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        piyony2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                piyony2ActionPerformed(evt);
            }
        });
        jPanel1.add(piyony2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 680, -1, -1));

        piyony3.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 680, -1, -1));

        piyony4.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 680, -1, -1));

        piyony5.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 680, -1, -1));

        piyony6.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony6, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 680, -1, -1));

        piyony7.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        jPanel1.add(piyony7, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 670, -1, -1));

        piyony8.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/piyony-Picsart-BackgroundRemover.png")); // NOI18N
        piyony8.setActionCommand("");
        jPanel1.add(piyony8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 670, -1, -1));

        A8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                A8ActionPerformed(evt);
            }
        });
        jPanel1.add(A8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 110, -1, 70));
        jPanel1.add(A1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 790, -1, 80));
        jPanel1.add(A2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 700, 80, 80));
        jPanel1.add(A3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 610, -1, 70));
        jPanel1.add(A4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, 80, 70));
        jPanel1.add(A5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 400, 70, 70));
        jPanel1.add(A6, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 80, 80));
        jPanel1.add(A7, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, -1, 70));
        jPanel1.add(B1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 790, 80, 90));
        jPanel1.add(B2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 700, 70, 70));
        jPanel1.add(B3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 600, 80, 70));
        jPanel1.add(B4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, 80, 70));
        jPanel1.add(B5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 400, 70, 70));
        jPanel1.add(B6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 300, 70, 70));
        jPanel1.add(B7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, 80, 70));
        jPanel1.add(B8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 70, 60));
        jPanel1.add(C1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 800, 80, 80));
        jPanel1.add(C2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 690, 80, 90));
        jPanel1.add(C3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 600, 70, 70));
        jPanel1.add(C4, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, 70, 70));
        jPanel1.add(C5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 400, 70, 70));
        jPanel1.add(C6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 70, 70));
        jPanel1.add(C7, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 70, 80));
        jPanel1.add(C8, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 80, 80));
        jPanel1.add(D1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 800, 80, 80));
        jPanel1.add(D2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 700, 80, 80));
        jPanel1.add(D3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 600, 80, 80));
        jPanel1.add(D4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 500, 70, 70));
        jPanel1.add(D5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 400, 80, 80));
        jPanel1.add(D6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 300, 70, 80));
        jPanel1.add(D7, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 80, 80));
        jPanel1.add(D8, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 80, 90));
        jPanel1.add(E1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 800, 70, 70));
        jPanel1.add(E2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 690, 80, 90));
        jPanel1.add(E3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 600, 70, 70));
        jPanel1.add(E4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, 80, 80));
        jPanel1.add(E5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 400, 90, 70));
        jPanel1.add(E6, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 310, 70, 70));
        jPanel1.add(E7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, 70, 70));
        jPanel1.add(E8, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 80, 80));
        jPanel1.add(F1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 800, 90, 70));
        jPanel1.add(F2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 690, 90, 90));
        jPanel1.add(F3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 600, 90, 80));
        jPanel1.add(F4, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 500, 80, 70));
        jPanel1.add(F5, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 400, 90, 80));
        jPanel1.add(F6, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 300, 80, 80));
        jPanel1.add(F7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 200, 80, 90));
        jPanel1.add(F8, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 80, 80));
        jPanel1.add(G1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 800, 70, 70));
        jPanel1.add(G2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 690, 90, 90));
        jPanel1.add(G3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 600, 80, 80));
        jPanel1.add(G4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 500, 90, 80));
        jPanel1.add(G5, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 400, 80, 80));
        jPanel1.add(G6, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 300, 90, 80));
        jPanel1.add(G7, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 200, 70, 70));
        jPanel1.add(G8, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 70, 70));
        jPanel1.add(H1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 800, 70, 70));
        jPanel1.add(H2, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 690, 80, 90));
        jPanel1.add(H3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 600, 90, 80));
        jPanel1.add(H4, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 500, 70, 70));
        jPanel1.add(H5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 400, 90, 80));
        jPanel1.add(H6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 300, 70, 70));
        jPanel1.add(H7, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 200, 70, 70));
        jPanel1.add(H8, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, 70, 70));

        jLabel1.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/ChatGPT Image 22 Nis 2026 16_09_23.png")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 0, 940, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 964, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void A8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_A8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_A8ActionPerformed

    private void att1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_att1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_att1ActionPerformed

    private void vezirtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vezirtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vezirtActionPerformed

    private void piyont1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piyont1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_piyont1ActionPerformed

    private void sahyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sahyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sahyActionPerformed

    private void piyony2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_piyony2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_piyony2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new game().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton A1;
    private javax.swing.JButton A2;
    private javax.swing.JButton A3;
    private javax.swing.JButton A4;
    private javax.swing.JButton A5;
    private javax.swing.JButton A6;
    private javax.swing.JButton A7;
    private javax.swing.JButton A8;
    private javax.swing.JButton B1;
    private javax.swing.JButton B2;
    private javax.swing.JButton B3;
    private javax.swing.JButton B4;
    private javax.swing.JButton B5;
    private javax.swing.JButton B6;
    private javax.swing.JButton B7;
    private javax.swing.JButton B8;
    private javax.swing.JButton C1;
    private javax.swing.JButton C2;
    private javax.swing.JButton C3;
    private javax.swing.JButton C4;
    private javax.swing.JButton C5;
    private javax.swing.JButton C6;
    private javax.swing.JButton C7;
    private javax.swing.JButton C8;
    private javax.swing.JButton D1;
    private javax.swing.JButton D2;
    private javax.swing.JButton D3;
    private javax.swing.JButton D4;
    private javax.swing.JButton D5;
    private javax.swing.JButton D6;
    private javax.swing.JButton D7;
    private javax.swing.JButton D8;
    private javax.swing.JButton E1;
    private javax.swing.JButton E2;
    private javax.swing.JButton E3;
    private javax.swing.JButton E4;
    private javax.swing.JButton E5;
    private javax.swing.JButton E6;
    private javax.swing.JButton E7;
    private javax.swing.JButton E8;
    private javax.swing.JButton F1;
    private javax.swing.JButton F2;
    private javax.swing.JButton F3;
    private javax.swing.JButton F4;
    private javax.swing.JButton F5;
    private javax.swing.JButton F6;
    private javax.swing.JButton F7;
    private javax.swing.JButton F8;
    private javax.swing.JButton G1;
    private javax.swing.JButton G2;
    private javax.swing.JButton G3;
    private javax.swing.JButton G4;
    private javax.swing.JButton G5;
    private javax.swing.JButton G6;
    private javax.swing.JButton G7;
    private javax.swing.JButton G8;
    private javax.swing.JButton H1;
    private javax.swing.JButton H2;
    private javax.swing.JButton H3;
    private javax.swing.JButton H4;
    private javax.swing.JButton H5;
    private javax.swing.JButton H6;
    private javax.swing.JButton H7;
    private javax.swing.JButton H8;
    private javax.swing.JButton att1;
    private javax.swing.JButton att2;
    private javax.swing.JButton aty1;
    private javax.swing.JButton aty2;
    private javax.swing.JButton filt1;
    private javax.swing.JButton filt2;
    private javax.swing.JButton fily1;
    private javax.swing.JButton fily2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton kalet1;
    private javax.swing.JButton kalet2;
    private javax.swing.JButton kaley1;
    private javax.swing.JButton kaley2;
    private javax.swing.JButton piyont1;
    private javax.swing.JButton piyont2;
    private javax.swing.JButton piyont3;
    private javax.swing.JButton piyont4;
    private javax.swing.JButton piyont5;
    private javax.swing.JButton piyont6;
    private javax.swing.JButton piyont7;
    private javax.swing.JButton piyont8;
    private javax.swing.JButton piyony1;
    private javax.swing.JButton piyony2;
    private javax.swing.JButton piyony3;
    private javax.swing.JButton piyony4;
    private javax.swing.JButton piyony5;
    private javax.swing.JButton piyony6;
    private javax.swing.JButton piyony7;
    private javax.swing.JButton piyony8;
    private javax.swing.JButton saht;
    private javax.swing.JButton sahy;
    private javax.swing.JButton vezirt;
    private javax.swing.JButton veziry;
    // End of variables declaration//GEN-END:variables

    public void secimMethodu(List<JButton> pieceButtons, List<JButton> boardButtons) {

        // 🎯 BAŞTA
        for (JButton b : boardButtons) {
            b.setEnabled(false);
        }
        for (JButton b : pieceButtons) {
            b.setEnabled(true);
        }

        // 🎯 TAŞ SEÇME
        for (JButton btn : pieceButtons) {

            btn.addActionListener(e -> {

                selectedPiece = btn.getName(); // att1 vs

                System.out.println("Seçilen taş: " + selectedPiece);

                // 🔥 TAŞLARI KAPAT
                for (JButton b : pieceButtons) {
                    b.setEnabled(false);
                }

                // 🔥 TAHTAYI AÇ
                for (JButton b : boardButtons) {
                    b.setEnabled(true);
                }
            });
        }
    }

    public void hedefSec(List<JButton> boardButtons) {

        for (JButton btn : boardButtons) {

            btn.addActionListener(e -> {

                if (selectedPiece == null) {
                    return;
                }

                String target = btn.getName(); // A3 vs

                System.out.println("Hedef: " + target);

                // 🔥 TAŞIN KONUMUNU GÜNCELLE
                piecePositions.put(selectedPiece, target);
                // 🔥 TAŞI BUL
                JButton sourceButton = null;

                for (JButton b : pieceButtons) {
                    if (b.getName().equals(selectedPiece)) {
                        sourceButton = b;
                        break;
                    }
                }

// 🔥 HEDEF BUTONU BUL
                JButton targetButton = null;

                for (JButton b : boardButtons) {
                    if (b.getName().equals(target)) {
                        targetButton = b;
                        break;
                    }
                }

// 🔥 İKON TAŞIMA
                if (sourceButton != null && targetButton != null) {

                    targetButton.setIcon(sourceButton.getIcon()); // yeni yere koy
                    sourceButton.setIcon(null);                   // eskiyi temizle
                }

                System.out.println("Yeni konum: " + piecePositions.get(selectedPiece));

                // RESET
                selectedPiece = null;
            });
        }
    }
}
