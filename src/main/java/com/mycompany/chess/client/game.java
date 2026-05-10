/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.chess.client;
 
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author beyzamacbook
 */
public class game extends javax.swing.JFrame {

    private ClientConnection conn;
    boolean beyazSirasi = true;
    JButton[][] board;
    GameLogic logic = new GameLogic();
    Map<JButton, Piece> pieceMap = new HashMap<>();

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(game.class.getName());
    JButton secilenTas = null;

    List<JButton> taslar = new ArrayList<>();
    List<JButton> kareler = new ArrayList<>();
    List<String> yunanAldigiTaslar = new ArrayList<>();
    List<String> turkunAldigiTaslar = new ArrayList<>();

    private boolean onlineMode = false;
    private boolean oyunBitti = false;
    private String oyuncuRengi = "beyaz";
    private String serverHost = "127.0.0.1";
    private int serverPort = 5003;
    private String roomCode = "default";
    private boolean botMode = false;
    private String botRengi = "siyah";
    private final Random random = new Random();

    /**
     * Creates new form game
     */
    public game() {
        this("127.0.0.1", 5003, "default");
    }

    public game(String serverHost, int serverPort) {
        this(serverHost, serverPort, "default");
    }

    public game(String serverHost, int serverPort, String roomCode) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.roomCode = roomCode == null || roomCode.trim().isEmpty() ? "default" : roomCode.trim();
        initComponents();

        yukleGorseller();

        setupPieces();

        taslar = Arrays.asList(att1, att2, piyont1, piyont2, piyont3, piyont4, piyont5, piyont6, piyont7, piyont8, saht, vezirt, kalet1, kalet2, filt1, filt2,
                aty1, aty2, piyony1, piyony2, piyony3, piyony4, piyony5, piyony6, piyony7, piyony8, sahy, veziry, kaley1, kaley2, fily1, fily2
        );

        board = new JButton[][]{
            {A1, A2, A3, A4, A5, A6, A7, A8},
            {B1, B2, B3, B4, B5, B6, B7, B8},
            {C1, C2, C3, C4, C5, C6, C7, C8},
            {D1, D2, D3, D4, D5, D6, D7, D8},
            {E1, E2, E3, E4, E5, E6, E7, E8},
            {F1, F2, F3, F4, F5, F6, F7, F8},
            {G1, G2, G3, G4, G5, G6, G7, G8},
            {H1, H2, H3, H4, H5, H6, H7, H8}
        };

        kareler = Arrays.asList(A1, A2, A3, A4, A5, A6, A7, A8,
                B1, B2, B3, B4, B5, B6, B7, B8,
                C1, C2, C3, C4, C5, C6, C7, C8,
                D1, D2, D3, D4, D5, D6, D7, D8,
                E1, E2, E3, E4, E5, E6, E7, E8,
                F1, F2, F3, F4, F5, F6, F7, F8,
                G1, G2, G3, G4, G5, G6, G7, G8,
                H1, H2, H3, H4, H5, H6, H7, H8);

        yunanınAldıkları.setBounds(130, 70, 220, 40);
        turkunAldıkları.setBounds(130, 130, 220, 40);
        guncelleAlinanTasLabellari();
        baslangicDurumu();
        tasSecim();
        kareSecim();

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

        baglantiyiArkaPlandaBaslat();

    }

    private void baglantiyiArkaPlandaBaslat() {
        turn.setText("CONNECTING...");
        setOyunKontrolleriAktif(false);

        new Thread(() -> {
            try {
                ClientConnection connection = baglanSunucuya();
                connection.send("JOIN," + roomCode);
                SwingUtilities.invokeLater(() -> {
                    conn = connection;
                    onlineMode = true;
                    baslangicDurumu();
                    dinlemeyiBaslat();
                    JOptionPane.showMessageDialog(this,
                            "Server bağlantısı başarılı: " + serverHost + ":" + serverPort);
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    onlineMode = false;
                    baslangicDurumu();
                    JOptionPane.showMessageDialog(this,
                            "Server bağlantısı başarısız!\n"
                            + "Önce AWS üzerinde Server.java çalışıyor olmalı.\n"
                            + "Denenen adresler: " + serverHost + ":" + serverPort + ", " + serverHost + ":5002 ve " + serverHost + ":5001");
                });
            }
        }, "chess-connect").start();
    }

    private void setOyunKontrolleriAktif(boolean aktif) {
        for (JButton t : taslar) {
            t.setEnabled(aktif);
        }
        for (JButton k : kareler) {
            k.setEnabled(false);
        }
    }

    private ClientConnection baglanSunucuya() throws Exception {
        int[] ports = {serverPort, 5002, 5001};
        Exception lastError = null;

        for (int port : ports) {
            try {
                serverPort = port;
                return new ClientConnection(serverHost, port);
            } catch (Exception e) {
                lastError = e;
            }
        }

        throw lastError;
    }

    private void dinlemeyiBaslat() {
        new Thread(() -> {
            try {
                while (true) {
                    String msg = conn.receive();
                    if (msg == null) {
                        break;
                    }
                    handleIncomingMove(msg);
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> rakipAyrildiBotuBaslat());
            }
        }, "chess-client-listener").start();
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
        jPanel2 = new javax.swing.JPanel();
        score = new javax.swing.JLabel();
        turn = new javax.swing.JLabel();
        yunanınAldıkları = new javax.swing.JLabel();
        TURK = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        turkunAldıkları = new javax.swing.JLabel();
        yunan = new javax.swing.JLabel();
        turk = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 102, 0));
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
        A6.addActionListener(e -> {
            System.out.println(
                A6.getX() + " , " + A6.getY()
            );
        });
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

        jPanel2.setBackground(new java.awt.Color(196, 192, 182));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        score.setFont(new java.awt.Font("Luminari", 3, 36)); // NOI18N
        score.setText("SCORE");
        jPanel2.add(score, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, -1, -1));

        turn.setFont(new java.awt.Font("Luminari", 2, 48)); // NOI18N
        turn.setText(".....");
        jPanel2.add(turn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 410, 100));

        yunanınAldıkları.setFont(new java.awt.Font("Luminari", 0, 13)); // NOI18N
        yunanınAldıkları.setText("....");
        jPanel2.add(yunanınAldıkları, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 370, 40));

        TURK.setFont(new java.awt.Font("Luminari", 2, 18)); // NOI18N
        TURK.setText("TURK");
        jPanel2.add(TURK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 60, -1));

        jLabel4.setFont(new java.awt.Font("Luminari", 2, 18)); // NOI18N
        jLabel4.setText("GREEN");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        turkunAldıkları.setFont(new java.awt.Font("Luminari", 0, 13)); // NOI18N
        turkunAldıkları.setText("....");
        jPanel2.add(turkunAldıkları, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 380, 30));

        yunan.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/yunan.png")); // NOI18N
        jPanel2.add(yunan, new org.netbeans.lib.awtextra.AbsoluteConstraints(-90, 260, 540, 730));

        turk.setIcon(new javax.swing.ImageIcon("/Users/beyzamacbook/NetBeansProjects/chess/img/turk.png")); // NOI18N
        jPanel2.add(turk, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 260, 510, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 986, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JLabel TURK;
    private javax.swing.JButton att1;
    private javax.swing.JButton att2;
    private javax.swing.JButton aty1;
    private javax.swing.JButton aty2;
    private javax.swing.JButton filt1;
    private javax.swing.JButton filt2;
    private javax.swing.JButton fily1;
    private javax.swing.JButton fily2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
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
    private javax.swing.JLabel score;
    private javax.swing.JLabel turk;
    private javax.swing.JLabel turkunAldıkları;
    private javax.swing.JLabel turn;
    private javax.swing.JButton vezirt;
    private javax.swing.JButton veziry;
    private javax.swing.JLabel yunan;
    private javax.swing.JLabel yunanınAldıkları;
    // End of variables declaration//GEN-END:variables

    public void konumlariKaydetVeYazdir() {

        Map<String, Point> konumlar = new HashMap<>();

        JButton[][] board = {
            {A1, A2, A3, A4, A5, A6, A7, A8},
            {B1, B2, B3, B4, B5, B6, B7, B8},
            {C1, C2, C3, C4, C5, C6, C7, C8},
            {D1, D2, D3, D4, D5, D6, D7, D8},
            {E1, E2, E3, E4, E5, E6, E7, E8},
            {F1, F2, F3, F4, F5, F6, F7, F8},
            {G1, G2, G3, G4, G5, G6, G7, G8},
            {H1, H2, H3, H4, H5, H6, H7, H8}
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String key = "" + (char) ('A' + i) + (j + 1);
                Point p = board[i][j].getLocation();
                konumlar.put(key, p);
                System.out.println(key + " -> X:" + p.x + " Y:" + p.y);
            }
        }
    }

    public void baslangicDurumu() {
        guncelleSiraLabeli();
        if (oyunBitti) {
            for (JButton t : taslar) {
                t.setEnabled(false);
            }
            for (JButton k : kareler) {
                k.setEnabled(false);
            }
            return;
        }
        for (JButton t : taslar) {
            t.setEnabled(true);
        }
        for (JButton k : kareler) {
            k.setEnabled(false);
        }
    }

    public void tasSecim() {

        for (JButton tas : taslar) {

            tas.addActionListener(e -> {
                if (oyunBitti) {
                    return;
                }

                JButton tiklanan = (JButton) e.getSource();
                Piece p = pieceMap.get(tiklanan);

                if (p == null) {
                    return;
                }

                if (onlineMode && !isMyTurn()) {
                    JOptionPane.showMessageDialog(this, "Şu an sıra sende değil.");
                    return;
                }

                if (secilenTas != null) {
                    Piece seciliParca = pieceMap.get(secilenTas);

                    if (seciliParca == null) {
                        secilenTas = null;
                        baslangicDurumu();
                        return;
                    }

                    if (tiklanan == secilenTas) {
                        return;
                    }

                    if (seciliParca.color.equals(p.color)) {
                        secilenTas = tiklanan;
                        System.out.println("Seçilen taş değişti: " + p.type + " " + p.color);
                        return;
                    }

                    hamleYap(p.x, p.y);
                    return;
                }

                if (beyazSirasi && !p.color.equals("beyaz")) {
                    System.out.println("Sıra beyazda!");
                    return;
                }

                if (!beyazSirasi && !p.color.equals("siyah")) {
                    System.out.println("Sıra siyahta!");
                    return;
                }

                if (onlineMode && !p.color.equals(oyuncuRengi)) {
                    JOptionPane.showMessageDialog(this, "Sadece kendi taşlarını oynatabilirsin.");
                    return;
                }

                secilenTas = tiklanan;
                System.out.println("Seçilen taş: " + p.type + " " + p.color);

                for (JButton k : kareler) {
                    k.setEnabled(true);
                }
            });
        }
    }

    public void kareSecim() {

        for (JButton kare : kareler) {

            kare.addActionListener(e -> {
                if (oyunBitti) {
                    return;
                }

                if (secilenTas == null) {
                    JOptionPane.showMessageDialog(this, "Önce taş seç!");
                    return;
                }

                JButton tiklananKare = (JButton) e.getSource();
                Point hedef = getIndex(tiklananKare);

                if (hedef == null) {
                    return;
                }

                hamleYap(hedef.x, hedef.y);
            });
        }
    }

    private void hamleYap(int hedefX, int hedefY) {
        if (secilenTas == null) {
            return;
        }

        Piece tasObj = pieceMap.get(secilenTas);
        if (tasObj == null) {
            secilenTas = null;
            baslangicDurumu();
            return;
        }

        Point kaynak = new Point(tasObj.x, tasObj.y);
        JButton hedefKare = board[hedefX][hedefY];

        if (hedefKare == null) {
            secilenTas = null;
            baslangicDurumu();
            return;
        }

        boolean gecerliHamle = uygulaHamleyiTahtada(secilenTas, hedefX, hedefY, true);
        if (gecerliHamle && onlineMode && conn != null) {
            String msg = "MOVE," + kaynak.x + "," + kaynak.y + "," + hedefX + "," + hedefY;
            conn.send(msg);
        }
        secilenTas = null;
        baslangicDurumu();
        botHamlesiniGerekirseBaslat();

    }

    private boolean uygulaHamleyiTahtada(JButton kaynakTas, int hedefX, int hedefY, boolean showErrors) {
        Piece tasObj = pieceMap.get(kaynakTas);
        JButton hedefKare = board[hedefX][hedefY];

        if (tasObj == null || hedefKare == null) {
            return false;
        }

        Piece hedefTas = logic.board[hedefX][hedefY];
        int kaynakX = tasObj.x;
        int kaynakY = tasObj.y;
        boolean rokHamlesi = "sah".equals(tasObj.type) && Math.abs(hedefX - kaynakX) == 2;
        int kaleEskiX = hedefX > kaynakX ? 7 : 0;
        int kaleYeniX = hedefX > kaynakX ? hedefX - 1 : hedefX + 1;
        JButton rokKalesi = rokHamlesi ? findButtonByCoords(kaleEskiX, kaynakY) : null;

        if (hedefTas != null && hedefTas.color.equals(tasObj.color)) {
            if (showErrors) {
                JOptionPane.showMessageDialog(this, "Kendi taşını yiyemezsin!");
            }
            return false;
        }

        if (!logic.move(tasObj, hedefX, hedefY)) {
            if (showErrors) {
                JOptionPane.showMessageDialog(this, "Geçersiz hamle!");
            }
            return false;
        }

        if (hedefTas != null) {
            JButton sil = findButtonByPiece(hedefTas);
            if (sil != null) {
                sil.setVisible(false);
                pieceMap.remove(sil);
            }
            alinanTasiKaydet(tasObj, hedefTas);
        }

        tasiKaliciTasi(kaynakTas, hedefKare);
        if (rokHamlesi && rokKalesi != null) {
            tasiKaliciTasi(rokKalesi, board[kaleYeniX][kaynakY]);
            jPanel1.setComponentZOrder(rokKalesi, 0);
        }
        guncelleTerfiGorseli(kaynakTas, tasObj);
        jPanel1.setComponentZOrder(kaynakTas, 0);
        jPanel1.revalidate();
        jPanel1.repaint();
        beyazSirasi = !beyazSirasi;
        guncelleSiraLabeli();
        oyununBitisDurumunuKontrolEt(tasObj.color);
        return true;
    }

    private void oyununBitisDurumunuKontrolEt(String hamleYapanRenk) {
        String siradakiRenk = GameLogic.opposite(hamleYapanRenk);
        boolean sah = logic.isKingInCheck(siradakiRenk);
        boolean hamleVar = logic.hasAnyLegalMove(siradakiRenk);

        if (sah && !hamleVar) {
            oyunuBitir(takimAdi(hamleYapanRenk) + " kazandı. Şah mat!");
        } else if (!sah && !hamleVar) {
            oyunuBitir("Pat! Oyun berabere.");
        } else if (sah) {
            JOptionPane.showMessageDialog(this, takimAdi(siradakiRenk) + " şah altında!");
        }
    }

    private void oyunuBitir(String sonuc) {
        oyunBitti = true;
        turn.setText("GAME OVER");
        SwingUtilities.invokeLater(() -> {
            new EndScreen(sonuc).setVisible(true);
            dispose();
        });
    }

    private void rakipAyrildiBotuBaslat() {
        if (oyunBitti || botMode) {
            return;
        }

        onlineMode = false;
        botMode = true;
        botRengi = GameLogic.opposite(oyuncuRengi);
        secilenTas = null;
        JOptionPane.showMessageDialog(this, "Rakip oyundan ayrıldı. Bilgisayar devralıyor.");
        baslangicDurumu();
        botHamlesiniGerekirseBaslat();
    }

    private void botHamlesiniGerekirseBaslat() {
        if (!botMode || oyunBitti || !botunSirasiMi()) {
            return;
        }

        setOyunKontrolleriAktif(false);
        javax.swing.Timer timer = new javax.swing.Timer(700, e -> {
            ((javax.swing.Timer) e.getSource()).stop();
            botHamlesiYap();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private boolean botunSirasiMi() {
        return (beyazSirasi && "beyaz".equals(botRengi))
                || (!beyazSirasi && "siyah".equals(botRengi));
    }

    private void botHamlesiYap() {
        if (oyunBitti || !botunSirasiMi()) {
            baslangicDurumu();
            return;
        }

        BotHamlesi hamle = enIyiBotHamlesiniBul();
        if (hamle == null) {
            oyununBitisDurumunuKontrolEt(GameLogic.opposite(botRengi));
            return;
        }

        secilenTas = hamle.tasButton;
        uygulaHamleyiTahtada(hamle.tasButton, hamle.hedefX, hamle.hedefY, false);
        secilenTas = null;
        baslangicDurumu();
    }

    private BotHamlesi enIyiBotHamlesiniBul() {
        List<BotHamlesi> enIyiHamleler = new ArrayList<>();
        int enIyiPuan = Integer.MIN_VALUE;

        for (Map.Entry<JButton, Piece> entry : new ArrayList<>(pieceMap.entrySet())) {
            JButton tasButton = entry.getKey();
            Piece tas = entry.getValue();
            if (tas == null || !botRengi.equals(tas.color)) {
                continue;
            }

            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (!logic.isLegalMove(tas, x, y)) {
                        continue;
                    }

                    Piece hedef = logic.board[x][y];
                    int puan = hedef == null ? 0 : tasDegeri(hedef);
                    if (puan > enIyiPuan) {
                        enIyiPuan = puan;
                        enIyiHamleler.clear();
                    }
                    if (puan == enIyiPuan) {
                        enIyiHamleler.add(new BotHamlesi(tasButton, x, y, puan));
                    }
                }
            }
        }

        if (enIyiHamleler.isEmpty()) {
            return null;
        }

        return enIyiHamleler.get(random.nextInt(enIyiHamleler.size()));
    }

    private int tasDegeri(Piece tas) {
        if (tas == null) {
            return 0;
        }

        switch (tas.type) {
            case "vezir":
                return 9;
            case "kale":
                return 5;
            case "fil":
            case "at":
                return 3;
            case "piyon":
                return 1;
            default:
                return 0;
        }
    }

    private static class BotHamlesi {

        private final JButton tasButton;
        private final int hedefX;
        private final int hedefY;
        @SuppressWarnings("unused")
        private final int puan;

        BotHamlesi(JButton tasButton, int hedefX, int hedefY, int puan) {
            this.tasButton = tasButton;
            this.hedefX = hedefX;
            this.hedefY = hedefY;
            this.puan = puan;
        }
    }

    private String takimAdi(String renk) {
        return "beyaz".equals(renk) ? "Türk" : "Yunan";
    }

    private void guncelleTerfiGorseli(JButton tasButton, Piece tasObj) {
        if (!"vezir".equals(tasObj.type) || !piyonButonuMu(tasButton)) {
            return;
        }

        tasButton.setIcon(AssetLoader.icon("beyaz".equals(tasObj.color)
                ? "vezirt-fotor-bg-remover-2026042220499.png"
                : "veziry.png"));
    }

    private boolean piyonButonuMu(JButton button) {
        return button == piyont1 || button == piyont2 || button == piyont3 || button == piyont4
                || button == piyont5 || button == piyont6 || button == piyont7 || button == piyont8
                || button == piyony1 || button == piyony2 || button == piyony3 || button == piyony4
                || button == piyony5 || button == piyony6 || button == piyony7 || button == piyony8;
    }

    private JButton findButtonByPiece(Piece piece) {
        for (Map.Entry<JButton, Piece> entry : pieceMap.entrySet()) {
            if (entry.getValue() == piece) {
                return entry.getKey();
            }
        }
        return null;
    }

    private JButton findButtonByCoords(int x, int y) {
        for (Map.Entry<JButton, Piece> entry : pieceMap.entrySet()) {
            Piece piece = entry.getValue();
            if (piece.x == x && piece.y == y) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void tasiKaliciTasi(JButton tas, JButton hedefKare) {
        int width = tas.getWidth();
        int height = tas.getHeight();

        tas.setBounds(hedefKare.getX(), hedefKare.getY(), width, height);
        jPanel1.add(tas, new org.netbeans.lib.awtextra.AbsoluteConstraints(
                hedefKare.getX(), hedefKare.getY(), width, height));
    }

    public void setupPieces() {

        pieceMap.clear();
        logic = new GameLogic();
        yunanAldigiTaslar.clear();
        turkunAldigiTaslar.clear();
        guncelleAlinanTasLabellari();

        pieceMap.put(piyont1, new Piece("piyon", "beyaz", 0, 6));
        pieceMap.put(piyont2, new Piece("piyon", "beyaz", 1, 6));
        pieceMap.put(piyont3, new Piece("piyon", "beyaz", 2, 6));
        pieceMap.put(piyont4, new Piece("piyon", "beyaz", 3, 6));
        pieceMap.put(piyont5, new Piece("piyon", "beyaz", 4, 6));
        pieceMap.put(piyont6, new Piece("piyon", "beyaz", 5, 6));
        pieceMap.put(piyont7, new Piece("piyon", "beyaz", 6, 6));
        pieceMap.put(piyont8, new Piece("piyon", "beyaz", 7, 6));

        pieceMap.put(piyony1, new Piece("piyon", "siyah", 0, 1));
        pieceMap.put(piyony2, new Piece("piyon", "siyah", 1, 1));
        pieceMap.put(piyony3, new Piece("piyon", "siyah", 2, 1));
        pieceMap.put(piyony4, new Piece("piyon", "siyah", 3, 1));
        pieceMap.put(piyony5, new Piece("piyon", "siyah", 4, 1));
        pieceMap.put(piyony6, new Piece("piyon", "siyah", 5, 1));
        pieceMap.put(piyony7, new Piece("piyon", "siyah", 6, 1));
        pieceMap.put(piyony8, new Piece("piyon", "siyah", 7, 1));

        pieceMap.put(kalet1, new Piece("kale", "beyaz", 0, 7));
        pieceMap.put(kalet2, new Piece("kale", "beyaz", 7, 7));
        pieceMap.put(kaley1, new Piece("kale", "siyah", 0, 0));
        pieceMap.put(kaley2, new Piece("kale", "siyah", 7, 0));

        pieceMap.put(att1, new Piece("at", "beyaz", 1, 7));
        pieceMap.put(att2, new Piece("at", "beyaz", 6, 7));
        pieceMap.put(aty1, new Piece("at", "siyah", 1, 0));
        pieceMap.put(aty2, new Piece("at", "siyah", 6, 0));

        pieceMap.put(filt1, new Piece("fil", "beyaz", 2, 7));
        pieceMap.put(filt2, new Piece("fil", "beyaz", 5, 7));
        pieceMap.put(fily1, new Piece("fil", "siyah", 2, 0));
        pieceMap.put(fily2, new Piece("fil", "siyah", 5, 0));

        pieceMap.put(vezirt, new Piece("vezir", "beyaz", 3, 7));
        pieceMap.put(veziry, new Piece("vezir", "siyah", 3, 0));

        pieceMap.put(saht, new Piece("sah", "beyaz", 4, 7));
        pieceMap.put(sahy, new Piece("sah", "siyah", 4, 0));

        for (Piece p : pieceMap.values()) {
            logic.board[p.x][p.y] = p;
        }
    }

    Point getIndex(JButton btn) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == btn) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private void guncelleSiraLabeli() {
        if (beyazSirasi) {
            yunan.setVisible(false);
            turk.setVisible(true);
            turn.setText("TURK'S TURN");
        } else {
            yunan.setVisible(true);
            turk.setVisible(false);
            turn.setText("GREEK'S TURN");

        }
    }

    private void alinanTasiKaydet(Piece alanTas, Piece yenilenTas) {
        String tasAdi = tasAdi(yenilenTas);

        if (alanTas.color.equals("beyaz")) {
            yunanAldigiTaslar.add(tasAdi);
        } else {
            turkunAldigiTaslar.add(tasAdi);
        }

        guncelleAlinanTasLabellari();
    }

    private void guncelleAlinanTasLabellari() {
        yunanınAldıkları.setText(yunanAldigiTaslar.isEmpty() ? "-" : String.join(", ", yunanAldigiTaslar));
        turkunAldıkları.setText(turkunAldigiTaslar.isEmpty() ? "-" : String.join(", ", turkunAldigiTaslar));
    }

    private String tasAdi(Piece tas) {
        if (tas == null) {
            return "";
        }

        switch (tas.type) {
            case "piyon":
                return "Piyon";
            case "kale":
                return "Kale";
            case "at":
                return "At";
            case "fil":
                return "Fil";
            case "vezir":
                return "Vezir";
            case "sah":
                return "Sah";
            default:
                return tas.type;
        }
    }

    private boolean isMyTurn() {
        return (beyazSirasi && "beyaz".equals(oyuncuRengi))
                || (!beyazSirasi && "siyah".equals(oyuncuRengi));
    }

    private void yukleGorseller() {
        att1.setIcon(AssetLoader.icon("att-Picsart-BackgroundRemover.png"));
        att2.setIcon(AssetLoader.icon("att-Picsart-BackgroundRemover.png"));
        kalet1.setIcon(AssetLoader.icon("kalet-Picsart-BackgroundRemover.png"));
        kalet2.setIcon(AssetLoader.icon("kalet-Picsart-BackgroundRemover.png"));
        vezirt.setIcon(AssetLoader.icon("vezirt-fotor-bg-remover-2026042220499.png"));
        saht.setIcon(AssetLoader.icon("saht-fotor-bg-remover-2026042220454.png"));
        filt1.setIcon(AssetLoader.icon("filt-Picsart-BackgroundRemover.png"));
        filt2.setIcon(AssetLoader.icon("filt-Picsart-BackgroundRemover.png"));
        piyont1.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont2.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont3.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont4.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont5.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont6.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont7.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        piyont8.setIcon(AssetLoader.icon("piyont-Picsart-BackgroundRemover.png"));
        kaley1.setIcon(AssetLoader.icon("kaley-Picsart-BackgroundRemover.png"));
        kaley2.setIcon(AssetLoader.icon("kaley-Picsart-BackgroundRemover.png"));
        aty1.setIcon(AssetLoader.icon("aty-Picsart-BackgroundRemover.png"));
        aty2.setIcon(AssetLoader.icon("aty-Picsart-BackgroundRemover.png"));
        fily1.setIcon(AssetLoader.icon("fily-Picsart-BackgroundRemover.png"));
        fily2.setIcon(AssetLoader.icon("fily-Picsart-BackgroundRemover.png"));
        veziry.setIcon(AssetLoader.icon("veziry.png"));
        sahy.setIcon(AssetLoader.icon("sahy-fotor-bg-remover-20260422204714.png"));
        piyony1.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony2.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony3.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony4.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony5.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony6.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony7.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        piyony8.setIcon(AssetLoader.icon("piyony-Picsart-BackgroundRemover.png"));
        jLabel1.setIcon(AssetLoader.icon("ChatGPT Image 22 Nis 2026 16_09_23.png"));
        yunan.setIcon(AssetLoader.icon("yunan.png"));
        turk.setIcon(AssetLoader.icon("turk.png"));
    }

    
    
    
    private void handleIncomingMove(String msg) {
        try {
            String[] parts = msg.split(",");
            if (parts.length == 0) {
                return;
            }

            if ("COLOR".equals(parts[0]) && parts.length >= 2) {
                oyuncuRengi = parts[1];
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                        "Online oyuncu rengin: " + takimAdi(oyuncuRengi)));
                return;
            }

            if ("INFO".equals(parts[0]) && parts.length >= 2) {
                SwingUtilities.invokeLater(() -> {
                    turn.setText(parts[1]);
                    if (parts[1].contains("Rakip oyundan ayrıldı")) {
                        rakipAyrildiBotuBaslat();
                    }
                });
                return;
            }

            int offset = "MOVE".equals(parts[0]) ? 1 : 0;
            if (parts.length < offset + 4) {
                return;
            }

            int fromX = Integer.parseInt(parts[offset]);
            int fromY = Integer.parseInt(parts[offset + 1]);
            int toX = Integer.parseInt(parts[offset + 2]);
            int toY = Integer.parseInt(parts[offset + 3]);

            JButton source = findButtonByCoords(fromX, fromY);

            if (source != null) {
                SwingUtilities.invokeLater(() -> {
                    uygulaHamleyiTahtada(source, toX, toY, false);
                    secilenTas = null;
                    baslangicDurumu();
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
