package virtualclassroom;


import javax.swing.border.*;


import java.awt.event.MouseListener;




import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;






import java.awt.geom.Ellipse2D;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.*;


import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KLOP extends JFrame implements Runnable{

    Socket socket;
    String shapeType = "";
    Shape s;
    PaintSurface p = new PaintSurface();
    JButton undoButton = new JButton("undo");
    JButton redoButton = new JButton("redo");
    protected UndoManager undoManager = new UndoManager();
    Image OSC;  // The off-screen canvas (created in setupOSC()).
    ArrayList<Shape> shapes = new ArrayList<Shape>();
    int widthOfOSC, heightOfOSC;

    public static void main(String[] args) {
        new KLOP();
    }

    public void actionPerformed(ActionEvent ae) {
        shapeType = ae.getActionCommand().toString();
        if (shapeType.equals("clear")) {
            clearOSC();
            repaint();
        }
    }
    
    public KLOP(String client)
    {
        super(client);
    }

    public KLOP() {
        super("Client");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new PaintSurface(), BorderLayout.CENTER);
        setVisible(true);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.metal.OceanTheme");
//            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            System.out.println("exception is " + e);
        }
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
                int result = chooser.showOpenDialog(KLOP.this);

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
                        "jpg", "jpeg", "gif","png");
                chooser.setFileFilter(filter);

                // show file chooser dialog
                int result = chooser.showSaveDialog(KLOP.this);


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

        Action pen = new AbstractAction("pen",
                new ImageIcon("D:\\PROGRAMMIMG\\JAVA\\project\\virtualclassroomn\\src\\virtualclassroom\\pen.png")) {

            public void actionPerformed(ActionEvent ae) {
                shapeType = "pen";
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


        undoButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                } catch (CannotRedoException cre) {
                    cre.printStackTrace();
                }

                repaint();
                undoButton.setEnabled(undoManager.canUndo());

            }
        });
        redoButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (CannotRedoException cre) {
                    cre.printStackTrace();
                }
                repaint();
                undoButton.setEnabled(undoManager.canUndo());
                redoButton.setEnabled(undoManager.canRedo());
            }
        });
        undoButton.setText(undoManager.getUndoPresentationName());
        try {
            String ipfromserver = welcomestudents.ipad;
            socket = new Socket(ipfromserver, 4007);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


        while (true) {

            ObjectInputStream ois = null;
            try {
                // open a socket connection

                ois = new ObjectInputStream(socket.getInputStream());
                shapes = (ArrayList) (ois.readObject());

                repaint();
                //  ois.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

        }






    }

    public void run() {
        new KLOP();
    }

    private class PaintSurface extends JComponent {

        int i = 0;
        Shape s;
        Point startDrag, endDrag, initial[];

        public PaintSurface() {
            addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    startDrag = new Point(e.getX(), e.getY());
                    endDrag = startDrag;
                    repaint();
                }

                public void mouseReleased(MouseEvent e) {
                    if (shapeType.equals("Rectangle")) {
                        Shape r = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
                        undoManager.undoableEditHappened(new UndoableEditEvent(p, new UndoablePaintSquare(r, shapes)));
                        shapes.add(r);
                    }
                    if (shapeType.equals("line")) {
                        Shape r = makeLine(startDrag.x, startDrag.y, e.getX(), e.getY());
                        undoManager.undoableEditHappened(new UndoableEditEvent(p, new UndoablePaintSquare(r, shapes)));
                        shapes.add(r);
                    }
                    if (shapeType.equals("oval")) {
                        Shape r = makeOval(startDrag.x, startDrag.y, e.getX(), e.getY());
                        undoManager.undoableEditHappened(new UndoableEditEvent(p, new UndoablePaintSquare(r, shapes)));
                        shapes.add(r);
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
                        //PaintSurface.this.run();

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

            if (shapeType.equals("Rectangle") || shapeType.equals("line") || shapeType.equals("oval") || shapes != null) {
                for (Shape s : shapes) {
                    g2.setPaint(Color.BLUE);
                    g2.draw(s);
                }


                if (startDrag != null && endDrag != null) {

                    g2.setPaint(Color.BLUE);
                    if (shapeType.equals("Rectangle")) {
                        Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                        g2.draw(r);
                    }
                    if (shapeType.equals("line")) {
                        Shape r = makeLine(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
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
                        Shape r = makeOval(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
                        g2.draw(r);
                    }

                }
            }

        }

        class UndoablePaintSquare extends AbstractUndoableEdit {

            ArrayList<Shape> shapes = new ArrayList<Shape>();
            Shape r;
            protected Point point;

            public UndoablePaintSquare(Shape t, ArrayList<Shape> shap) {
                shapes = shap;
                r = t;
            }

            public String getPresentationName() {
                return "Square Addition";
            }

            public void undo() {
                super.undo();
                shapes.remove(r);
            }

            public void redo() {
                super.redo();
                shapes.add(r);
            }
        }
    }

    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2) {
        return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private Line2D.Float makeLine(int x1, int y1, int x2, int y2) {
        return new Line2D.Float(x1, y1, x2, y2);
    }

    private Ellipse2D.Float makeOval(int x1, int y1, int x2, int y2) {

        return new Ellipse2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private void clearOSC() {
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
}
