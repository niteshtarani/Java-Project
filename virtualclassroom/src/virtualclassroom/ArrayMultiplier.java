package virtualclassroom;


import javax.swing.border.*;


import java.awt.event.MouseListener;
import javax.swing.filechooser.*;
import java.lang.Runnable;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import java.awt.event.MouseEvent;


import java.util.ArrayList;
import java.io.*;
import java.net.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.*;

public class ArrayMultiplier extends JFrame {

    Socket client;
    ServerSocket arrayServer;
    String shapeType = "";
    Shape s;
    protected UndoManager undoManager = new UndoManager();
    JButton undoButton = new JButton("undo");
    JButton redoButton = new JButton("Redo");
    Image OSC;
    int widthOfOSC, heightOfOSC;
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    ArrayList<Shape> xshapes = new ArrayList<Shape>();

    public static void main(String argv[]) throws Exception {
        new ArrayMultiplier();
    }

    public void actionPerformed(ActionEvent ae) {
        shapeType = ae.getActionCommand().toString();
        if (shapeType.equals("clear")) {
            clearOSC();
            repaint();
        }
    }

    public ArrayMultiplier() throws Exception {
        super("Server");
        setSize(600, 600);
        setBackground(Color.gray);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.OceanTheme");
//            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Action clear = new AbstractAction("Clear",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\new.png")) {

            public void actionPerformed(ActionEvent ae) {
                clearOSC();
                repaint();
            }
        };


        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu mFile = new JMenu("File");

        mFile.add(clear);

        Action actionOpen = new AbstractAction("Open", new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\open.png")) {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Image files", "jpg", "jpeg", "gif");
                chooser.setFileFilter(filter);

                // show file chooser dialog
                int result = chooser.showOpenDialog(ArrayMultiplier.this);

                // if image file accepted, set it as icon of the label
                if (result == JFileChooser.APPROVE_OPTION) {
                    String pathname = chooser.getSelectedFile().getPath();
                    try {
                        shapes = new ArrayList();
                        FileInputStream fis = new FileInputStream(pathname);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        shapes = (ArrayList) (ois.readObject());
                        ois.close();
                        fis.close();
                        repaint();
                    } catch (Exception ex) {
                        System.out.println("Trouble reading display list vector");
                    }

                }
            }
        };

        mFile.add(actionOpen);


        Action actionSave = new AbstractAction("Save", new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\save.png")) {

            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Image files", "jpg", "jpeg", "gif");
                chooser.setFileFilter(filter);

                // show file chooser dialog
                int result = chooser.showSaveDialog(ArrayMultiplier.this);


                if (result == JFileChooser.APPROVE_OPTION) {
                    String pathname = chooser.getSelectedFile().getPath();
                    try {
                        FileOutputStream fos = new FileOutputStream(pathname);
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(shapes);
                        oos.flush();
                        oos.close();
                        fos.close();
                    } catch (Exception ex) {
                        System.out.println("Trouble writing display list vector");
                    }

                }
            }
        };

        mFile.add(actionSave);

        mFile.addSeparator();
        Action actionExit = new AbstractAction("Exit", new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\exit.png")) {

            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        mFile.add(actionExit);
        menuBar.add(mFile);

        JToolBar m_toolBar1 = new JToolBar(JToolBar.HORIZONTAL);

        Action actionRectangle = new AbstractAction("Rectangle",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\rect.png")) {

            public void actionPerformed(ActionEvent ae) {
                shapeType = "Rectangle";
            }
        };
        Action pen = new AbstractAction("pen",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\pen.png")) {

            public void actionPerformed(ActionEvent ae) {
                shapeType = "pen";
            }
        };
        Action actionLine = new AbstractAction("Line",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\line.png")) {

            public void actionPerformed(ActionEvent ae) {
                shapeType = "line";
            }
        };
        Action actionoval = new AbstractAction("Oval",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\oval.png")) {

            public void actionPerformed(ActionEvent ae) {
                shapeType = "oval";
            }
        };


        JButton penButton = new SmallButton(pen, "Pen");
        JButton ovalButton = new SmallButton(actionoval, "Oval");
        JButton rectangleButton = new SmallButton(actionRectangle, "Rectangle");
        JButton lineButton = new SmallButton(actionLine, "Line");
        JButton newButton = new SmallButton(clear, "Clear");
        JButton openButton = new SmallButton(actionOpen, "Open");
        JButton saveButton = new SmallButton(actionSave, "Save");
        m_toolBar1.setLayout(new BoxLayout(m_toolBar1,
                BoxLayout.LINE_AXIS));


        m_toolBar1.add(newButton);
        m_toolBar1.add(openButton);
        m_toolBar1.add(saveButton);
        m_toolBar1.addSeparator();
        m_toolBar1.add(rectangleButton);
        m_toolBar1.add(lineButton);
        m_toolBar1.add(ovalButton);
        m_toolBar1.addSeparator();
        m_toolBar1.add(penButton);
        m_toolBar1.addSeparator();
        m_toolBar1.add(undoButton);
        m_toolBar1.addSeparator();
        m_toolBar1.add(redoButton);




        getContentPane().add(m_toolBar1, BorderLayout.NORTH);


        undoButton.setText(undoManager.getUndoPresentationName());


        class PaintSurface extends JComponent implements Runnable {

            int i = 0;
            Shape s, r;
            Point startDrag, endDrag, initial[];

            public PaintSurface() throws Exception {

               


                arrayServer = new ServerSocket(4007);

                System.out.println("Server listening on port 4007.");

                undoButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            undoManager.undo();
                        } catch (CannotRedoException cre) {
                            cre.printStackTrace();
                        }

                        PaintSurface.this.run();
                        repaint();
                        undoButton.setEnabled(undoManager.canUndo());
                        redoButton.setEnabled(true);
                    }
                });
                redoButton.addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            undoManager.redo();
                        } catch (CannotRedoException cre) {
                            cre.printStackTrace();
                        }

                        PaintSurface.this.run();
                        repaint();
                        undoButton.setEnabled(undoManager.canUndo());
                        redoButton.setEnabled(undoManager.canRedo());
                    }
                });


                addMouseListener(new MouseAdapter() {

                    public void mousePressed(MouseEvent e) {
                        startDrag = new Point(e.getX(), e.getY());
                        endDrag = startDrag;
                        repaint();

                    }

                    public void mouseReleased(MouseEvent e) {
                        if (shapeType.equals("Rectangle")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;
                            Shape r = new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
                            shapes.add(r);
                            PaintSurface.this.run();

                            undoManager.undoableEditHappened(new UndoableEditEvent(this, new UndoablePaintSquare(r, shapes)));


                        }
                        if (shapeType.equals("line")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;
                            Shape r = new Line2D.Float(x1, y1, x2, y2);
                            shapes.add(r);
                            PaintSurface.this.run();

                            undoManager.undoableEditHappened(new UndoableEditEvent(this, new UndoablePaintSquare(r, shapes)));
                        }
                        if (shapeType.equals("oval")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;
                            Shape r = new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
                            shapes.add(r);
                            PaintSurface.this.run();

                            undoManager.undoableEditHappened(new UndoableEditEvent(this, new UndoablePaintSquare(r, shapes)));
                        }


                        startDrag = null;
                        endDrag = null;

                        repaint();

                    }
                });

                addMouseMotionListener(new MouseMotionAdapter() {

                    public void mouseDragged(MouseEvent e) {
                        endDrag = new Point(e.getX(), e.getY());
                        if (shapeType.equals("pen")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;

                            Shape r = new Rectangle2D.Float(x2, y2, 0, 0);
                            shapes.add(r);
                            PaintSurface.this.run();

                            undoManager.undoableEditHappened(new UndoableEditEvent(this, new UndoablePaintSquare(r, shapes)));


                        }
                        repaint();
                    }
                });
            }

            public void paint(Graphics g) {
                if (undoButton.isEnabled()) {
                    redoButton.setEnabled(true);
                }
                Graphics2D g2 = (Graphics2D) g;

                if (shapeType.equals("Rectangle") || shapeType.equals("line") || shapeType.equals("oval") || shapes != null || shapeType.equals("pen")) {
                    for (Shape s : shapes) {
                        g2.setPaint(Color.BLUE);
                        g2.draw(s);
                    }


                    if (startDrag != null && endDrag != null) {

                        g2.setPaint(Color.BLUE);
                        if (shapeType.equals("Rectangle")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;
                            Shape r = new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
                            g2.draw(r);


                        }
                        if (shapeType.equals("line")) {
                            Shape r = new Line2D.Float(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                            g2.draw(r);
                        }
                        if (shapeType.equals("pen")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;

                            Shape r = new Rectangle2D.Float(x2, y2, 0, 0);
                            g2.draw(r);


                        }
                        if (shapeType.equals("oval")) {
                            int x1 = startDrag.x;
                            int x2 = endDrag.x;
                            int y1 = startDrag.y;
                            int y2 = endDrag.y;
                            Shape r = new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
                            g2.draw(r);
                        }

                    }
                }
            }

            public void run() {



                ObjectOutputStream oos = null;

                try {
                    try {

                        oos = new ObjectOutputStream(client.getOutputStream());

                    } catch (Exception e1) {
                        try {
                            client.close();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    oos.writeObject(shapes);
                    oos.flush();

                    //oos.close();
                    //client.close();
                } catch (Exception e) {
                }


            }
        }



        add(new PaintSurface(), BorderLayout.CENTER);
        setVisible(true);
        try {
            System.out.println("Waiting for connections.");
            client =  arrayServer.accept();
            System.out.println("Accepted a connection from: " + client.getInetAddress());

        } catch (Exception e) {
        }

    }

    public void clearOSC() {

        OSC = createImage(getSize().width, getSize().height);
        widthOfOSC = getSize().width;
        heightOfOSC = getSize().height;
        Graphics OSG = OSC.getGraphics();
        OSG.setColor(getBackground());
        OSG.fillRect(0, 0, widthOfOSC, heightOfOSC);
        OSG.dispose();

        shapes.clear();


    }

    class SmallButton extends JButton implements MouseListener {

        protected Border m_raised;
        protected Border m_lowered;
        protected Border m_inactive;

        public SmallButton(Action act, String tip) {
            super((Icon) act.getValue(Action.SMALL_ICON));
            m_raised = new BevelBorder(BevelBorder.RAISED);
            m_lowered = new BevelBorder(BevelBorder.LOWERED);
            m_inactive = new EmptyBorder(2, 2, 2, 2);
            setBorder(m_inactive);
            setMargin(new Insets(1, 1, 1, 1));
            setToolTipText(tip);
            addActionListener(act);
            addMouseListener(this);
            setRequestFocusEnabled(false);
        }

        public float getAlignmentY() {
            return 0.5f;
        }

        public void mousePressed(MouseEvent e) {
            setBorder(m_lowered);
        }

        public void mouseReleased(MouseEvent e) {
            setBorder(m_inactive);
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            setBorder(m_raised);
        }

        public void mouseExited(MouseEvent e) {
            setBorder(m_inactive);
        }
    }

    class UndoablePaintSquare extends AbstractUndoableEdit {
        //ArrayList<Shape> shapes = new ArrayList<Shape>();

        Shape a;
        protected Point point;

        public UndoablePaintSquare(Shape t, ArrayList<Shape> shap) {
            shapes = shap;

            a = t;

        }

        public String getPresentationName() {
            return "Square Addition";
        }

        public void undo() {
            super.undo();
            shapes.remove(a);

        }

        public void redo() {
            super.redo();
            shapes.add(a);

        }
    }
}
