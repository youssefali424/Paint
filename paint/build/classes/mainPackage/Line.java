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
public class Line extends Shape{
    private int endX,endY;
    public Line(int x1,int y1,int x2,int  y2,Color c1,boolean filled){
    super(x1,y1,c1,filled);
    endX=x2;endY=y2;
    }
    public int getEndX(){return endX;}
    public int getEndY(){return endY;}
    @Override
    public void draw(Graphics2D g){
        g.setColor(getColor());
        g.drawLine(getX(), getY(), endX, endY);
    }

    @Override
    public void fill(Graphics2D g) {
         g.setColor(getColor());
        g.drawLine(getX(), getY(), endX, endY);
    }
}
