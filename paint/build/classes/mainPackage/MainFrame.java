/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;

/**
 *
 * @author youssef ali
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    Vector<Shape> shapeVec;
    JToggleButton button=null;
    Color mainColor=Color.RED;
    private JPanel jPanel2;
    int tool=1;
    int oldX,oldY;
    int endX,endY;
    boolean flag=false;
    int ovalX,ovalY;
    Image Img;
    boolean openFlag=false;
    boolean filled=false;
    int eraseRadius=50;
    JLabel label;
    Cursor cursorPanel ;
    int index;
    Vector<Integer> indexs;
    
    public MainFrame() {
        setPanel();
        initComponents();
        shapeVec=new Vector<>();
        indexs=new Vector<>();
        jToggleButton4.doClick();
        jLabel6.setBackground(mainColor);
        jLabel6.setOpaque(true);
        label = new JLabel("") {
         @Override
         public void paint(Graphics g) {
            super.paint(g);
            g.drawOval(5, 5, eraseRadius, eraseRadius);
         }
      };
        this.add(label);
        label.setVisible(true);
        
        label.setBounds(this.getWidth()-120, 120, 110,110);
        label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("C:\\Users\\youssef ali\\Desktop\\eraser.png");
        cursorPanel = toolkit.createCustomCursor(image , new Point(this.getX(), 
           this.getY()), "img");
        
        
    }

    class Panel2 extends JPanel {

        Panel2() {
            // set a preferred size for the custom panel.
            setPreferredSize(new Dimension(500,500));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D gObj=(Graphics2D)g;
            
            
            
            if(openFlag){
            if(Img!=null){
            
            g.drawImage(Img, 0, 0, jPanel2.getWidth(), jPanel2.getHeight(), jPanel2);
            }
            }
            for(int i=0;i<shapeVec.size();i++){
                if(shapeVec.elementAt(i).getFill()){shapeVec.elementAt(i).fill(gObj);}
                else{shapeVec.elementAt(i).draw(gObj);}
            }
            gObj.setColor(mainColor);
            if(flag){
                if(!filled){
            if(tool==2){g.drawLine(oldX, oldY, endX, endY);}
            else if(tool==3){g.drawOval(oldX, oldY, endX, endY);}
            else if(tool==4){g.drawRect(oldX, oldY, endX, endY);}
                }
                else{
            if(tool==2){g.drawLine(oldX, oldY, endX, endY);}
            else if(tool==3){g.fillOval(oldX, oldY, endX, endY);}
            else if(tool==4){g.fillRect(oldX, oldY, endX, endY);}}
            }
            
            
            
        }
    }
    public void setPanel(){
    jPanel2 = new Panel2();
    
    
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        jPanel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
                oldX=evt.getX();oldY=evt.getY();
                handlePressed(evt.getX(),evt.getY());
            }
            @Override
            public void mouseReleased(MouseEvent evt) {
                handleRelease(evt.getX(),evt.getY());
            }
            @Override
            public void mouseMoved(MouseEvent e){
                jPanel2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        jPanel2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent evt) {
                
                
                handleDragged(evt.getX(),evt.getY());
                
            }
         
        });

        // add the component to the frame to see it!
        this.add(jPanel2);
        jPanel2.setVisible(true);
        
        jPanel2.setBounds(20, 50, 550,400);
        jPanel2.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }
public void handlePressed(int x,int y){
if(tool==1){
    Line point;
    point=new Line(oldX,oldY,x,y,mainColor,filled);
    indexs.add(shapeVec.size());
    
    shapeVec.add(point);
    
}
else if(tool==2){
flag=true;
endX=x;endY=y;
}
else if(tool==3){
flag=true;
ovalX=x;ovalY=y;
endX=0;endY=0;
}
else if(tool==4){flag=true;
ovalX=x;ovalY=y;
endX=0;endY=0;}
else if(tool==5){
    oldX=x-(int)(eraseRadius/2);
    oldY=y-(int)(eraseRadius/2)+20;
    Oval oval=new Oval(oldX,oldY,eraseRadius,eraseRadius,Color.WHITE,true);
    indexs.add(shapeVec.size());
    shapeVec.add(oval);
    oldX=x;oldY=y;
}
jPanel2.repaint();
}
public void handleDragged(int x,int y){
if(tool==1){
    Line point;
    point=new Line(oldX,oldY,x,y,mainColor,filled);
    
    
    shapeVec.add(point);
    oldX=x;oldY=y;
}
else if(tool==2){
endX=x;endY=y;

}
else if(tool==3){
    endX = Math.abs(x-ovalX);
    endY = Math.abs(y-ovalY);
    oldX = Math.min(ovalX, x);
    oldY = Math.min(ovalY, y);
    
    
}
else if(tool==4){
endX = Math.abs(x-ovalX);
    endY = Math.abs(y-ovalY);
    oldX = Math.min(ovalX, x);
    oldY = Math.min(ovalY, y);
}
else if(tool==5){
    /*int[] arrX,arrY;
    if(Math.abs(x-oldX)>0){
        int numMax,numMin;
    if(x>oldX){numMax=x;numMin=oldX;}
    else{numMax=oldX;numMin=x;}
    arrX=new int[numMax-numMin];
    for(int i=0;i<(numMax-numMin);i++){
    arrX[i]=numMin+i;
    }
    }else{arrX=new int[1];arrX[0]=x;}
    
    if(Math.abs(y-oldY)>0){
        int numMax,numMin;
    if(y>oldY){numMax=y;numMin=oldY;}
    else{numMax=oldY;numMin=y;}
    arrY=new int[numMax-numMin];
    for(int i=0;i<(numMax-numMin);i++){
    arrY[i]=numMin+i;
    }
    }else{arrY=new int[1];arrY[0]=y;}
    int d;
    if(arrX.length>arrY.length)
    {d=arrX.length;}
    else{d=arrY.length;}
        for(int j=0;j<d;j++)            
        {
            int jX=j,jY=j;
            if(jY>=arrY.length){jY=arrY.length-1;}
            if(jX>=arrX.length){jX=arrX.length-1;}*/
            oldX=oldX-(int)(eraseRadius/2);
            oldY=oldY-(int)(eraseRadius/2)+20;
            Oval oval=new Oval(oldX,oldY,eraseRadius,eraseRadius,Color.WHITE,true);
            
            shapeVec.add(oval);
        //}
    oldX=x;oldY=y;
    
}
jPanel2.repaint();
}
public void handleRelease(int x,int y){
if(tool==2){
endX=x;endY=y;
Line point;

point=new Line(oldX,oldY,endX,endY,mainColor,filled);
indexs.add(shapeVec.size());
shapeVec.add(point);
flag=false;
}
else if(tool==3){
    /*oldX = Math.min(oldX, x);
    oldY = Math.min(oldY, y);
    endX = Math.abs(oldX - x);
    endY = Math.abs(oldY - y);*/
    Oval oval=new Oval(oldX,oldY,endX,endY,mainColor,filled);
    indexs.add(shapeVec.size());
    shapeVec.add(oval);
flag=false;
}
else if(tool==4){
Rectangle1 rec=new Rectangle1(oldX,oldY,endX,endY,mainColor,filled);
indexs.add(shapeVec.size());
shapeVec.add(rec);
flag=false;
}

jPanel2.repaint();
}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToggleButton1 = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jCheckBox1 = new javax.swing.JCheckBox();
        jToggleButton5 = new javax.swing.JToggleButton();
        jSlider1 = new javax.swing.JSlider();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 500));
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jToggleButton1.setText("rectangle");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        jToggleButton2.setText("circle");
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });

        jToggleButton3.setText("line");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });

        jToggleButton4.setText("free draw");
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });

        jButton1.setText("choose color");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("open");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jCheckBox1.setText("fiiled");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });

        jToggleButton5.setText("erase");
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });

        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jButton4.setText("undo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton1)
                        .addGap(0, 0, 0)
                        .addComponent(jToggleButton2)
                        .addGap(0, 0, 0)
                        .addComponent(jToggleButton3)
                        .addGap(0, 0, 0)
                        .addComponent(jToggleButton4)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jToggleButton5)))
                        .addGap(16, 16, 16))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2)
                                    .addComponent(jButton3))
                                .addComponent(jToggleButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jToggleButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jToggleButton5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 142, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(90, 90, 90))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        jToggleButton4.setEnabled(false);
        if(button!=null){
        button.setEnabled(true);
        button.setSelected(false);
        }
        button=jToggleButton4;
        button.grabFocus();
        tool=1;
        jPanel2.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
       jToggleButton3.setEnabled(false);
        if(button!=null){
            
        button.setEnabled(true);
        button.setSelected(false);
        }
        button=jToggleButton3;
        button.grabFocus();
        tool=2;jPanel2.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        jToggleButton2.setEnabled(false);
        if(button!=null){
        button.setEnabled(true);
        button.setSelected(false);
        }
        button=jToggleButton2;button.grabFocus();
        tool=3;jPanel2.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        jToggleButton1.setEnabled(false);
        if(button!=null){
        button.setEnabled(true);
        button.setSelected(false);
        }
        button=jToggleButton1;
        button.grabFocus();
        tool=4;jPanel2.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       Color newColor = JColorChooser.showDialog(null, "Choose a color", mainColor);
       if(newColor!=null){mainColor=newColor;}
       jLabel6.setBackground(mainColor);
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            BufferedImage bufferedImage = new BufferedImage(jPanel2.getWidth(), jPanel2.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setColor(Color.white);
            g2d.fillRect(0, 0, 4000, 4000);
            if(openFlag){
            if(Img!=null){

            g2d.drawImage(Img, 0, 0, 4000, 4000, jPanel2);
            }
            }
            for(int i=0;i<shapeVec.size();i++){
                if(shapeVec.elementAt(i).getFill()){shapeVec.elementAt(i).fill(g2d);}
                else{shapeVec.elementAt(i).draw(g2d);}
            }
            
            
            g2d.dispose();
            
  final FileDialog fd = new FileDialog(this, "save", FileDialog.SAVE);
  
  
  System.setProperty("apple.awt.fileDialogForDirectories",
                     String.valueOf(DIRECTORIES_ONLY));
  fd.setVisible(true);
  String h="";
  int value;
  if (fd.getFile() != null) {
    h=fd.getDirectory();
    value = JFileChooser.APPROVE_OPTION;
  }
  else {
    value = JFileChooser.CANCEL_OPTION;
  }
  
  

    if(value==JFileChooser.APPROVE_OPTION){
            File outputfile = new File(fd.getDirectory(), fd.getFile()+".jpg");
    
            ImageIO.write(bufferedImage, "jpg", outputfile);
    }
            
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       openFlag=true;boolean open=true;
       if(!shapeVec.isEmpty()){
        int a=JOptionPane.showConfirmDialog(this,"save before opening a new file?");  
        if(a==JOptionPane.YES_OPTION){  
         jButton2.doClick();
}  
        if(a==JOptionPane.CANCEL_OPTION){  
        open=false;
        }
        }
       if(open){
        
       FileDialog fd = new FileDialog(this, "open file", FileDialog.LOAD);
        fd.setMultipleMode(false);
        fd.setMode(FileDialog.LOAD);
        fd.setFile("*.jpg;*.jpeg;*.png");
       
        fd.setVisible(true);
        
        File[] files =fd.getFiles();
        if(files.length != 0){
        String h="";
        
        h =files[0].getAbsolutePath();
        ImageIcon imgThisImg = new ImageIcon(h);
        Img=imgThisImg.getImage();
        shapeVec.clear();
        }}
        jPanel2.repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        if(jCheckBox1.isSelected()){filled=true;}
        else{filled=false;}
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        jToggleButton5.setEnabled(false);
        if(button!=null){
        button.setEnabled(true);
        button.setSelected(false);
        }
        button=jToggleButton5;
        button.grabFocus();
        tool=5;jPanel2.setCursor(cursorPanel);
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
       int f=(int)jSlider1.getValue();
       eraseRadius=f;
       label.repaint();
    }//GEN-LAST:event_jSlider1StateChanged

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
     
    }//GEN-LAST:event_formMouseMoved

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Vector<Shape> temp=new Vector<>();
        if(!indexs.isEmpty()){
        for(int i=0;i<indexs.elementAt(indexs.size()-1);i++){
            
         temp.add(shapeVec.elementAt(i));
        }
        shapeVec=temp;
        indexs.remove(indexs.size()-1);
        jPanel2.repaint();}
    }//GEN-LAST:event_jButton4ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    // End of variables declaration//GEN-END:variables
}
