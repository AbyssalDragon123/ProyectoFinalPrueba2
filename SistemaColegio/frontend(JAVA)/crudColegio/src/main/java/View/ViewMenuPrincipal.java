/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package View;

import Configuracion.UrlOpener;
import Controller.LoginController;
import View.ViewLogin;
import Service.ServiceLogin;
import Modelos.SesionUsuario;
import static Modelos.SesionUsuario.rol;
import View.ViewAlumno;
import View.Asignatura;
import View.Encargado;
import View.ViewAula;
import View.ViewUsuarioCRUD;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;


/**
 *
 * @author osbel
 */
public class ViewMenuPrincipal extends javax.swing.JFrame {
private String rolUsuario;
    private String token;  // <-- Aquí guardamos el token
private LoginController loginController; // Controlador para manejar sesión

    private boolean menuVisible = true;
    private Timer timer;
    private int menuWidth = 230;
    private int animationStep = 10;

    private void toggleMenu() {
        if (timer != null && timer.isRunning()) {
            return;
        }
        if (menuVisible) {
            hideMenuWithAnimation();
        } else {
            showMenuWithAnimation();
        }
    }

    public ViewMenuPrincipal(String nombreUsuario, String rolUsuario, String token, LoginController loginController) {
        this.rolUsuario = rolUsuario;
        this.token = token;  // Guardamos el token
        this.loginController = loginController;
        setUndecorated(true);
        initComponents();

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(jPanel1, BorderLayout.NORTH);
        this.getContentPane().add(kGradientPanel1, BorderLayout.WEST);
        this.getContentPane().add(panelFormularios, BorderLayout.CENTER);
        this.getContentPane().add(kGradientPanel2, BorderLayout.EAST);

        setLocationRelativeTo(null);

        lblUsario.setText(nombreUsuario);

        if (!"director".equalsIgnoreCase(rolUsuario)) {
            btnUsuarios.setEnabled(true);
        } else {
            btnUsuarios.setEnabled(true);
        }

        panelFormularios.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                for (javax.swing.JInternalFrame frame : panelFormularios.getAllFrames()) {
                    frame.setSize(panelFormularios.getSize());
                }
            }
        });

        // Asignar listeners a los botones
        btnAsignatura.addActionListener(this::btnAsignaturaActionPerformed);
        btnAlumnos.addActionListener(this::btnAlumnosActionPerformed);
        btnEncargados.addActionListener(this::btnEncargadosActionPerformed);
        btnGrado.addActionListener(this::btnGradoActionPerformed);
        btnNotas.addActionListener(this::btnNotasActionPerformed);
        btnDocentes.addActionListener(this::btnDocentesActionPerformed);
        
    }
    // Setter para el controlador
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }

    private void abrirUsuarios() {
        ViewUsuarioCRUD usuariocrud = new ViewUsuarioCRUD(token);
        usuariocrud.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) usuariocrud.getUI()).setNorthPane(null);
        usuariocrud.setSize(panelFormularios.getSize());

        panelFormularios.removeAll();
        panelFormularios.add(usuariocrud);
        panelFormularios.repaint();
        panelFormularios.revalidate();

        usuariocrud.setVisible(true);
    }

    private void hideMenuWithAnimation() {
        timer = new Timer(20, null);
        timer.addActionListener(e -> {
            int currentWidth = kGradientPanel1.getWidth();
            if (currentWidth > 0) {
                int newWidth = Math.max(currentWidth - animationStep, 0);
                kGradientPanel1.setPreferredSize(new Dimension(newWidth, kGradientPanel1.getHeight()));
                kGradientPanel1.revalidate();
                kGradientPanel1.repaint();
                mainContentPanel.revalidate();
                mainContentPanel.repaint();
            } else {
                timer.stop();
                kGradientPanel1.setVisible(false);
                menuVisible = false;
            }
        });
        timer.start();
    }

    private void showMenuWithAnimation() {
        kGradientPanel1.setVisible(true);
        timer = new Timer(15, null);
        timer.addActionListener(e -> {
            int currentWidth = kGradientPanel1.getWidth();
            if (currentWidth < menuWidth) {
                int newWidth = Math.min(currentWidth + animationStep, menuWidth);
                kGradientPanel1.setPreferredSize(new Dimension(newWidth, kGradientPanel1.getHeight()));
                kGradientPanel1.revalidate();
                kGradientPanel1.repaint();
                mainContentPanel.revalidate();
                mainContentPanel.repaint();
            } else {
                timer.stop();
                menuVisible = true;
            }
        });
        timer.start();
        panelFormularios.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                if (panelFormularios.getAllFrames().length > 0) {
                    for (javax.swing.JInternalFrame frame : panelFormularios.getAllFrames()) {
                        frame.setSize(panelFormularios.getSize());
                    }
                }
            }
        });
    }

    public void configurarCerrarSesion(ActionListener listener) {
        btnCerrarSesion.addActionListener(listener);
    }

    private void mostrarContenidoInicial() {
        panelFormularios.removeAll();
        panelFormularios.add(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(btnWhatsapp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(btnFacebook, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(btnInstagram, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.add(btnInstagram1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        panelFormularios.repaint();
        panelFormularios.revalidate();
    }
    


    // Supongamos que tienes un menú con item "Asignaturas"
    private void menuAsignaturaActionPerformed(java.awt.event.ActionEvent evt) {
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        btnAsignatura = new javax.swing.JButton();
        btnAlumnos = new javax.swing.JButton();
        btnEncargados = new javax.swing.JButton();
        btnGrado = new javax.swing.JButton();
        btnNotas = new javax.swing.JButton();
        btnDocentes = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jbtRecuperar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        lblUsario = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnAbrirMenu = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        kGradientPanel2 = new keeptoo.KGradientPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnMenu = new javax.swing.JButton();
        mainContentPanel = new javax.swing.JPanel();
        panelFormularios = new javax.swing.JDesktopPane();
        btnWhatsapp = new javax.swing.JLabel();
        btnInstagram = new javax.swing.JLabel();
        btnFacebook = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnInstagram1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        jMenuItem5.setText("jMenuItem5");

        jMenuItem6.setText("jMenuItem6");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(144, 194, 231));

        kGradientPanel1.setkEndColor(new java.awt.Color(61, 61, 61));
        kGradientPanel1.setkStartColor(new java.awt.Color(44, 47, 53));

        btnAsignatura.setBackground(new java.awt.Color(87, 142, 126));
        btnAsignatura.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnAsignatura.setForeground(new java.awt.Color(245, 236, 213));
        btnAsignatura.setText("ASIGNATURAS");
        btnAsignatura.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAsignatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignaturaActionPerformed(evt);
            }
        });

        btnAlumnos.setBackground(new java.awt.Color(87, 142, 126));
        btnAlumnos.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnAlumnos.setForeground(new java.awt.Color(245, 236, 213));
        btnAlumnos.setText("ALUMNOS");
        btnAlumnos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAlumnosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAlumnosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAlumnosMouseExited(evt);
            }
        });
        btnAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlumnosActionPerformed(evt);
            }
        });

        btnEncargados.setBackground(new java.awt.Color(87, 142, 126));
        btnEncargados.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnEncargados.setForeground(new java.awt.Color(245, 236, 213));
        btnEncargados.setText("ENCARGADOS");
        btnEncargados.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEncargados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncargadosActionPerformed(evt);
            }
        });

        btnGrado.setBackground(new java.awt.Color(87, 142, 126));
        btnGrado.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnGrado.setForeground(new java.awt.Color(245, 236, 213));
        btnGrado.setText("AULA");
        btnGrado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGradoActionPerformed(evt);
            }
        });

        btnNotas.setBackground(new java.awt.Color(87, 142, 126));
        btnNotas.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnNotas.setForeground(new java.awt.Color(245, 236, 213));
        btnNotas.setText("NOTAS");
        btnNotas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnNotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotasActionPerformed(evt);
            }
        });

        btnDocentes.setBackground(new java.awt.Color(87, 142, 126));
        btnDocentes.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnDocentes.setForeground(new java.awt.Color(245, 236, 213));
        btnDocentes.setText("DOCENTES");
        btnDocentes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocentesActionPerformed(evt);
            }
        });

        btnUsuarios.setBackground(new java.awt.Color(87, 142, 126));
        btnUsuarios.setFont(new java.awt.Font("Cooper Black", 0, 18)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(245, 236, 213));
        btnUsuarios.setText("USUARIOS");
        btnUsuarios.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("¿Olvidaste tu Contraseña?");

        jbtRecuperar.setBackground(new java.awt.Color(4, 189, 125));
        jbtRecuperar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jbtRecuperar.setForeground(new java.awt.Color(4, 189, 125));
        jbtRecuperar.setText("Clic Aqui");
        jbtRecuperar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtRecuperar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtRecuperarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnEncargados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAsignatura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDocentes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlumnos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGrado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNotas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsuarios, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel12))
                    .addGroup(kGradientPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jbtRecuperar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(btnAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnDocentes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnEncargados, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtRecuperar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(44, 47, 53));

        jLabel1.setFont(new java.awt.Font("Century Schoolbook", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("MENU PRINCIPAL COLEGIO LCT");

        jSeparator1.setForeground(new java.awt.Color(245, 236, 213));

        jSeparator2.setForeground(new java.awt.Color(245, 236, 213));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Usuario:");

        lblUsario.setBackground(new java.awt.Color(58, 170, 137));
        lblUsario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUsario.setForeground(new java.awt.Color(255, 229, 163));
        lblUsario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUsario.setText("......");

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(87, 142, 126));

        btnAbrirMenu.setBackground(new java.awt.Color(87, 142, 126));
        btnAbrirMenu.setForeground(new java.awt.Color(245, 236, 213));
        btnAbrirMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/menu (1).png"))); // NOI18N
        btnAbrirMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirMenuActionPerformed(evt);
            }
        });

        btnCerrarSesion.setBackground(new java.awt.Color(87, 142, 126));
        btnCerrarSesion.setFont(new java.awt.Font("Cooper Black", 0, 14)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(245, 236, 213));
        btnCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar-sesion (4).png"))); // NOI18N
        btnCerrarSesion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnAbrirMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(lblUsario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUsario)
                                .addGap(0, 0, 0)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnAbrirMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        kGradientPanel2.setkEndColor(new java.awt.Color(61, 61, 61));
        kGradientPanel2.setkStartColor(new java.awt.Color(44, 47, 53));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 236, 213));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("<html> <p><center>Ofrecemos una educación integral y de excelencia que fomenta el aprendizaje significativo, el respeto, la responsabilidad y la solidaridad. Preparamos a nuestros estudiantes para enfrentar los retos del mundo actual con ética, conocimiento y habilidades, capacitándolos para contribuir positivamente a su comunidad y al país.");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel9.setBackground(new java.awt.Color(87, 142, 126));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(58, 170, 137));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("MISION");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setBackground(new java.awt.Color(87, 142, 126));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(58, 170, 137));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("VISION");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(245, 236, 213));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("<html> <p><center>Ser una institución educativa líder reconocida por formar estudiantes íntegros, críticos y creativos, comprometidos con el desarrollo sostenible y el bienestar de la sociedad, mediante una educación de calidad, innovadora y basada en valores humanos.");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnClose.setBackground(new java.awt.Color(87, 142, 126));
        btnClose.setFont(new java.awt.Font("Cooper Black", 0, 14)); // NOI18N
        btnClose.setForeground(new java.awt.Color(245, 236, 213));
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cerrar (1).png"))); // NOI18N
        btnClose.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnMenu.setBackground(new java.awt.Color(87, 142, 126));
        btnMenu.setFont(new java.awt.Font("Cooper Black", 0, 14)); // NOI18N
        btnMenu.setForeground(new java.awt.Color(245, 236, 213));
        btnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casa (4).png"))); // NOI18N
        btnMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kGradientPanel2Layout = new javax.swing.GroupLayout(kGradientPanel2);
        kGradientPanel2.setLayout(kGradientPanel2Layout);
        kGradientPanel2Layout.setHorizontalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kGradientPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
        kGradientPanel2Layout.setVerticalGroup(
            kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kGradientPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(kGradientPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        mainContentPanel.setBackground(new java.awt.Color(245, 236, 213));
        mainContentPanel.setForeground(new java.awt.Color(245, 236, 213));
        mainContentPanel.setLayout(new java.awt.BorderLayout());

        panelFormularios.setBackground(new java.awt.Color(255, 255, 255));
        panelFormularios.setPreferredSize(new java.awt.Dimension(796, 661));

        btnWhatsapp.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnWhatsapp.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnWhatsapp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-whatsapp-64.png"))); // NOI18N
        btnWhatsapp.setText("Whatsapp ");
        btnWhatsapp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnWhatsapp.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnWhatsapp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnWhatsappMouseClicked(evt);
            }
        });

        btnInstagram.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnInstagram.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnInstagram.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-instagram-64.png"))); // NOI18N
        btnInstagram.setText("Instagram");
        btnInstagram.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInstagram.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnInstagram.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInstagramMouseClicked(evt);
            }
        });

        btnFacebook.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnFacebook.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnFacebook.setIcon(new javax.swing.ImageIcon(getClass().getResource("/facebook.png"))); // NOI18N
        btnFacebook.setText("Facebook");
        btnFacebook.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFacebook.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnFacebook.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnFacebookMouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("¿Quieres saber más sobre nosotros?");

        jLabel5.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(61, 141, 122));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("¡Síguenos en nuestras redes sociales!");

        btnInstagram1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        btnInstagram1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnInstagram1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-tik-tok-64 (1).png"))); // NOI18N
        btnInstagram1.setText("Tik-Tok");
        btnInstagram1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInstagram1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnInstagram1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnInstagram1MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("<html><p><h1><center>¡Bienvenidos a LCT!</h1></center>\n"
            +"<p> <center>Nos alegra recibirlos en esta gran familia, donde cada día es una oportunidad para aprender, crecer y dejar huella. Aquí cultivamos un ambiente de confianza y entusiasmo para que cada estudiante descubra y potencie su mejor versión.</center>\n"
            + "<h2>Nuestros Valores</h2>"
            + "<p> 1. Integridad Actuamos: siempre con honestidad y responsabilidad."
            + "<p> 2.  Excelencia Académica: Nos esforzamos por superar nuestras metas y desarrollar el pensamiento crítico. "
            + "<p> 3. Respeto: Valoramos la diversidad y tratamos a todos con dignidad.\n" +
            "<p> 4. Innovación: Fomentamos la creatividad y el uso responsable de la tecnología.\n" +
            "<p> 5. Solidaridad: Trabajamos unidos, apoyándonos y contribuyendo al bienestar de la comunidad.");

        panelFormularios.setLayer(btnWhatsapp, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(btnInstagram, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(btnFacebook, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(btnInstagram1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        panelFormularios.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout panelFormulariosLayout = new javax.swing.GroupLayout(panelFormularios);
        panelFormularios.setLayout(panelFormulariosLayout);
        panelFormulariosLayout.setHorizontalGroup(
            panelFormulariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormulariosLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 32, Short.MAX_VALUE))
            .addGroup(panelFormulariosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelFormulariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelFormulariosLayout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(btnWhatsapp, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnFacebook, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInstagram1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnInstagram, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        panelFormulariosLayout.setVerticalGroup(
            panelFormulariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelFormulariosLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelFormulariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnWhatsapp)
                    .addComponent(btnInstagram)
                    .addComponent(btnFacebook)
                    .addComponent(btnInstagram1))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1051, 1051, 1051)
                        .addComponent(mainContentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(kGradientPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelFormularios, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(kGradientPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(kGradientPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(panelFormularios, javax.swing.GroupLayout.PREFERRED_SIZE, 627, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(639, 639, 639)
                            .addComponent(mainContentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(kGradientPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInstagram1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInstagram1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInstagram1MouseClicked

    private void btnFacebookMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFacebookMouseClicked
        // TODO add your handling code here:
        UrlOpener.openUrl("https://www.facebook.com/profile.php?id=61576630377327", this);
    }//GEN-LAST:event_btnFacebookMouseClicked

    private void btnInstagramMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInstagramMouseClicked
        // TODO add your handling code here:
        UrlOpener.openUrl("https://www.instagram.com/colegio_lct/", this);
    }//GEN-LAST:event_btnInstagramMouseClicked

    private void btnWhatsappMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnWhatsappMouseClicked
        // TODO add your handling code here:
        UrlOpener.openUrl("https://whatsapp.com/channel/0029VbAj1wpDJ6H9jca6Ed1K", this);
    }//GEN-LAST:event_btnWhatsappMouseClicked

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
// Limpiar sesión
    Modelos.SesionUsuario.nombreUsuario = null;
    Modelos.SesionUsuario.rol = null;
    Modelos.SesionUsuario.token = null; // Limpiar el token

    // Cerrar ventana actual
    this.dispose();

    // Crear nueva ventana de login y su controlador
    ViewLogin login = new ViewLogin();
    ServiceLogin servicio = new ServiceLogin();
    new LoginController(login, servicio);
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAbrirMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirMenuActionPerformed
        // TODO add your handling code here:
        toggleMenu();
    }//GEN-LAST:event_btnAbrirMenuActionPerformed

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        // TODO add your handling code here:
        mostrarContenidoInicial();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void jbtRecuperarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtRecuperarMouseClicked
ViewRecuperarContrasena recuperarContrasena = new ViewRecuperarContrasena(this.loginController);
    recuperarContrasena.setVisible(true);
    recuperarContrasena.setLocationRelativeTo(null);
    this.dispose();
    }//GEN-LAST:event_jbtRecuperarMouseClicked

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed

        if ("director".equalsIgnoreCase(rolUsuario)) {
            abrirUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "No tienes acceso a este apartado.", "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocentesActionPerformed
        ViewDocente docente = new ViewDocente();
        docente.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) docente.getUI()).setNorthPane(null);

        panelFormularios.removeAll();
        panelFormularios.add(docente);
        panelFormularios.repaint();
        panelFormularios.revalidate();

        docente.setSize(panelFormularios.getSize());
        docente.setVisible(true);
    }//GEN-LAST:event_btnDocentesActionPerformed

    private void btnNotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotasActionPerformed
        // TODO add your handling code here:
        // Ocultar la barra de título (agregar estas líneas)
        ViewNotas notas = new ViewNotas();
        notas.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) notas.getUI()).setNorthPane(null);

        panelFormularios.removeAll();
        panelFormularios.add(notas);
        panelFormularios.repaint();
        panelFormularios.revalidate();

        notas.setSize(panelFormularios.getSize());
        notas.setVisible(true);
    }//GEN-LAST:event_btnNotasActionPerformed

    private void btnGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGradoActionPerformed
        ViewAula aula = new ViewAula();
        aula.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) aula.getUI()).setNorthPane(null);

        panelFormularios.removeAll(); // Limpia el panel
        panelFormularios.add(aula);   // Agrega el formulario
        panelFormularios.repaint();   // Refresca
        panelFormularios.revalidate();

        aula.setVisible(true);        // Muestra el formulario
    }//GEN-LAST:event_btnGradoActionPerformed

    private void btnEncargadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncargadosActionPerformed
        // TODO add your handling code here:
        // Ocultar la barra de título (agregar estas líneas)
        Encargado encargado = new Encargado();
        encargado.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) encargado.getUI()).setNorthPane(null);

        panelFormularios.removeAll(); // Limpia el panel
        panelFormularios.add(encargado); // Agrega el formulario
        panelFormularios.repaint(); // Refresca
        panelFormularios.revalidate();

        encargado.setVisible(true); // Muestra el formulario
    }//GEN-LAST:event_btnEncargadosActionPerformed

    private void btnAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlumnosActionPerformed
        // TODO add your handling code here:
        // Ocultar la barra de título (agregar estas líneas)
        ViewAlumno alumno = new ViewAlumno(token);
        alumno.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) alumno.getUI()).setNorthPane(null);

        alumno.setSize(panelFormularios.getSize());
        panelFormularios.removeAll(); // Limpia el panel
        panelFormularios.add(alumno); // Agrega el formulario
        panelFormularios.repaint(); // Refresca
        panelFormularios.revalidate();
        alumno.setSize(panelFormularios.getSize()); // Ajusta tamaño al panel
        alumno.setVisible(true); // Muestra el formulario
    }//GEN-LAST:event_btnAlumnosActionPerformed

    private void btnAlumnosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlumnosMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlumnosMouseExited

    private void btnAlumnosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlumnosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlumnosMouseEntered

    private void btnAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAlumnosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAlumnosMouseClicked

    private void btnAsignaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignaturaActionPerformed
        // Ocultar la barra de título (agregar estas líneas)
        Asignatura asignaturas = new Asignatura(token);
        asignaturas.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) asignaturas.getUI()).setNorthPane(null);

        panelFormularios.removeAll();
        panelFormularios.add(asignaturas);
        panelFormularios.repaint();
        panelFormularios.revalidate();

        asignaturas.setVisible(true);
    }//GEN-LAST:event_btnAsignaturaActionPerformed
    /**
     * @param args the command line arguments
     */
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrirMenu;
    private javax.swing.JButton btnAlumnos;
    private javax.swing.JButton btnAsignatura;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDocentes;
    private javax.swing.JButton btnEncargados;
    private javax.swing.JLabel btnFacebook;
    private javax.swing.JButton btnGrado;
    private javax.swing.JLabel btnInstagram;
    private javax.swing.JLabel btnInstagram1;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton btnNotas;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel btnWhatsapp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel jbtRecuperar;
    private keeptoo.KGradientPanel kGradientPanel1;
    private keeptoo.KGradientPanel kGradientPanel2;
    private javax.swing.JLabel lblUsario;
    private javax.swing.JPanel mainContentPanel;
    private javax.swing.JDesktopPane panelFormularios;
    // End of variables declaration//GEN-END:variables

    public Object getBtnCerrarSesion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
