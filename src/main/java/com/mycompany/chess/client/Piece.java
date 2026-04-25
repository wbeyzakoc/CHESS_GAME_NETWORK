/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chess.client;

/**
 *
 * @author beyzamacbook
 */
public class Piece implements Cloneable {
    public String type;
    public String color;
    public int x, y;
    public boolean hasMoved = false;

    public Piece(String t, String c, int x, int y) {
        this.type = t;
        this.color = c;
        this.x = x;
        this.y = y;
    }

    public Piece clone() {
        Piece p = new Piece(type, color, x, y);
        p.hasMoved = hasMoved;
        return p;
    }
}