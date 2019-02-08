/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author youssef ali
 */
public abstract class Shape {
    private int x,y;
    private Color c;
    private boolean filled=false;
    
    public Shape(int x1,int y1,Color c1,boolean f){
        x=x1;y=y1;
        c=c1;
        filled=f;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public boolean getFill(){return filled;}
    public Color getColor(){return c;}
    public abstract void draw(Graphics2D g);
    public abstract void fill(Graphics2D g);
}
