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
public class Rectangle1 extends Shape{
    private int height,width;
    public Rectangle1(int x1,int y1,int x2,int  y2,Color c1,boolean filled){
    super(x1,y1,c1,filled);
    height=y2;width=x2;
    }
    public int getWidth(){return width;}
    public int getHeight(){return height;}

    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.drawRect(getX(), getY(), width, height);
        
    }

    @Override
    public void fill(Graphics2D g) {
        g.setColor(getColor());
        g.fillRect(getX(), getY(), width, height);
        
    }
}
