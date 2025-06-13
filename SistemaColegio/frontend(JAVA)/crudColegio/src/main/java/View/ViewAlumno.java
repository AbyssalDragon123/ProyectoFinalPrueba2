/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import Modelos.ModeloAlumno;
import Modelos.ModeloAula;
import Modelos.ModeloEncargado;
import ComboItem.ComboItem;
import Service.ServiceAlumno;
import Service.ServiceAula;
import Service.ServiceEncargado;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para la vista de Alumnos.
 * Permite la gestión de alumnos: crear, editar, eliminar y listar.
 */
public class ViewAlumno extends javax.swing.JInternalFrame {
private String token;
    // Servicios para interactuar con las capas de negocio
    private ServiceAlumno serviceAlumno;
    private ServiceEncargado serviceEncargado = new ServiceEncargado();
    private ServiceAula serviceAula = new ServiceAula();

    // Banderas para controlar eventos y evitar recursión
    private boolean ignorarEventosSeleccion = false;
    private boolean limpiandoCampos = false;
    
    /**
     * Constructor de la clase ViewAlumno.
     * Inicializa los componentes, carga datos y configura la interfaz.
     */
    public ViewAlumno(String token) {
        this.token = token;
        this.serviceAlumno = new ServiceAlumno();
        initComponents();
        cargarEncargados();
        cargarAulas();
        cargarAlumnosEnTabla();
        txtIdAlumno.setVisible(false);
        setPlaceholders();
        agregarSeleccionTablaListener();
        
    }

    /**
     * Carga los encargados desde el servicio y los añade al combo.
     */
    private void cargarEncargados() {
        try {
            System.out.println("comboEncargado = " + comboEncargado);
            List<ModeloEncargado> lista = serviceEncargado.obtenerEncargados(token);
            comboEncargado.removeAllItems(); // Limpia el combo antes de añadir nuevos elementos
            for (ModeloEncargado e : lista) {
                String descripcion = e.getIdEncargado() + " - " + e.getNombreEncargado() + " " + e.getApellidoEncargado();
                comboEncargado.addItem(new ComboItem(e.getIdEncargado(), descripcion)); // Añade el encargado al combo
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar encargados: " + ex.getMessage());
        }
    }

    /**
     * Carga las aulas desde el servicio y las añade al combo.
     */
    private void cargarAulas() {
        try {
            System.out.println("comboEncargado = " + comboEncargado);
            List<ModeloAula> aulas = serviceAula.getAulas(token);
            comboAula.removeAllItems(); // Limpia el combo antes de añadir nuevos elementos
            for (ModeloAula a : aulas) {
                String descripcion = a.getIdAula() + " - " + a.getGrado() + " " + a.getSeccion();
                comboAula.addItem(new ComboItem(a.getIdAula(), descripcion)); // Añade el aula al combo
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar aulas: " + ex.getMessage());
        }
    }

    /**
     * Carga los alumnos desde el servicio y los muestra en la tabla.
     */
private void cargarAlumnosEnTabla() {
    try {
        java.util.List<ModeloAlumno> listaAlumnos = serviceAlumno.getAlumnos(token);

        String[] columnas = {"ID", "Nombre", "Apellido", "Género", "ID Encargado", "ID Aula"};
        Object[][] datos = new Object[listaAlumnos.size()][columnas.length];

        for (int i = 0; i < listaAlumnos.size(); i++) {
            ModeloAlumno a = listaAlumnos.get(i);
            datos[i][0] = a.getIdAlumno();
            datos[i][1] = a.getNombreAlumno();
            datos[i][2] = a.getApellidoAlumno();
            datos[i][3] = a.getGeneroAlumno();
            datos[i][4] = a.getFkIdEncargado();
            datos[i][5] = a.getFkIdAula();
        }
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evita que las celdas sean editables
            }
        };
        tbAlumno.setModel(model);

        // Configurar líneas de la cuadrícula con borde negro y espacio entre celdas
        tbAlumno.setShowGrid(true);
        tbAlumno.setGridColor(Color.BLACK);
        tbAlumno.setIntercellSpacing(new java.awt.Dimension(1, 1));

        // Oculta la columna ID
        tbAlumno.getColumnModel().getColumn(0).setMinWidth(0);
        tbAlumno.getColumnModel().getColumn(0).setMaxWidth(0);
        tbAlumno.getColumnModel().getColumn(0).setWidth(0);

        // Cambiar color de fondo y texto del encabezado
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(56, 91, 81)); // Verde oscuro
        headerRenderer.setForeground(new Color(245, 236, 213)); // Texto claro
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(tbAlumno.getTableHeader().getFont().deriveFont(Font.BOLD, 24f)); // Tamaño más grande y negrita

        tbAlumno.setRowHeight(40);  // 40 píxeles de alto por fila
        tbAlumno.getColumnModel().getColumn(1).setPreferredWidth(150);  // Columna "Nombre"
        tbAlumno.getColumnModel().getColumn(2).setPreferredWidth(150);  // Columna "Apellido"
        tbAlumno.getColumnModel().getColumn(3).setPreferredWidth(150);  // Columna "Genero"
        tbAlumno.getColumnModel().getColumn(4).setPreferredWidth(150);  // Columna "Encargado"
        tbAlumno.getColumnModel().getColumn(5).setPreferredWidth(150);  // Columna "Aula"

        for (int i = 0; i < tbAlumno.getColumnModel().getColumnCount(); i++) {
            tbAlumno.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        // Cambiar fondo del viewport del JScrollPane que contiene la tabla
        jScrollPane1.getViewport().setBackground(new Color(87, 142, 126));

        // Renderer personalizado para celdas: fondo verde y texto centrado, sin borde
        DefaultTableCellRenderer greenCenterRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBackground(new Color(87, 142, 126));
                setForeground(new Color(245, 236, 213));
                setFont(getFont().deriveFont(Font.PLAIN, 16f));
                setHorizontalAlignment(SwingConstants.CENTER);
                setBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0)); // Sin borde para evitar doble línea
                return this;
            }
        };

        tbAlumno.getColumnModel().getColumn(1).setCellRenderer(greenCenterRenderer);
        tbAlumno.getColumnModel().getColumn(2).setCellRenderer(greenCenterRenderer);
        tbAlumno.getColumnModel().getColumn(3).setCellRenderer(greenCenterRenderer);
        tbAlumno.getColumnModel().getColumn(4).setCellRenderer(greenCenterRenderer);
        tbAlumno.getColumnModel().getColumn(5).setCellRenderer(greenCenterRenderer);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al cargar alumnos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        ignorarEventosSeleccion = false; // Reactiva el listener después de cargar los datos
    }
}
    /**
     * Agrega un listener para la selección de filas en la tabla.
     * Cuando se selecciona una fila, los datos del alumno se muestran en los campos.
     */
    private void agregarSeleccionTablaListener() {
        tbAlumno.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tbAlumno.getSelectedRow() != -1) {
                int fila = tbAlumno.getSelectedRow();
                txtIdAlumno.setText(tbAlumno.getValueAt(fila, 0).toString());
                txtNombre.setText(tbAlumno.getValueAt(fila, 1).toString());
                txtApellido.setText(tbAlumno.getValueAt(fila, 2).toString());
                String genero = tbAlumno.getValueAt(fila, 3).toString();
                comboGenero.setSelectedItem(genero);

                int idEncargado = Integer.parseInt(tbAlumno.getValueAt(fila, 4).toString());
                int idAula = Integer.parseInt(tbAlumno.getValueAt(fila, 5).toString());

                seleccionarEncargadoEnCombo(idEncargado);
                seleccionarAulaEnCombo(idAula);
            }
        });
    }

    /**
     * Selecciona el encargado en el combo basado en el ID.
     * @param idEncargado ID del encargado a seleccionar.
     */
    private void seleccionarEncargadoEnCombo(int idEncargado) {
        for (int i = 0; i < comboEncargado.getItemCount(); i++) {
            ComboItem item = comboEncargado.getItemAt(i);
            if (item.getId() == idEncargado) {
                comboEncargado.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Selecciona el aula en el combo basado en el ID.
     * @param idAula ID del aula a seleccionar.
     */
    private void seleccionarAulaEnCombo(int idAula) {
        for (int i = 0; i < comboAula.getItemCount(); i++) {
            ComboItem item = comboAula.getItemAt(i);
            if (item.getId() == idAula) {
                comboAula.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Establece los placeholders en los campos de texto.
     */
   private void setPlaceholders() {
        // grado
        txtNombre.setText("Ingrese el Nombre");
        txtNombre.setHorizontalAlignment(JTextField.CENTER);
        txtNombre.setForeground(Color.decode("#A3C6BC"));
        txtNombre.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtNombre.getText().equals("Ingrese el Nombre")) {
                    txtNombre.setText("");
                    txtNombre.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtNombre.getText().isEmpty()) {
                    txtNombre.setText("Ingrese el Nombre");
                    txtNombre.setForeground(Color.decode("#A3C6BC"));
                }
            }
        });   
        // grado
        txtApellido.setText("Ingrese el Apellido");
        txtApellido.setHorizontalAlignment(JTextField.CENTER);
        txtApellido.setForeground(Color.decode("#A3C6BC"));
        txtApellido.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtApellido.getText().equals("Ingrese el Apellido")) {
                    txtApellido.setText("");
                    txtApellido.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtApellido.getText().isEmpty()) {
                    txtApellido.setText("Ingrese el Apellido");
                    txtApellido.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
   }

 

   

    /**
     * Limpia los campos del formulario.
     */
   private void limpiarCampos() {
    limpiandoCampos = true; // Para evitar validaciones o eventos mientras limpias

    // Limpiar JTextFields
    txtIdAlumno.setText("");
    
    txtNombre.setText("Ingrese el Nombre");
    txtNombre.setForeground(Color.decode("#F9F3E6"));
    
    txtApellido.setText("Ingrese el Apellido");
    txtApellido.setForeground(Color.decode("#F9F3E6"));
    
    // Resetear JComboBox
    comboGenero.setSelectedIndex(-1);
    comboEncargado.setSelectedIndex(-1);
    comboAula.setSelectedIndex(-1);
    
    // Limpiar selección de tabla
    tbAlumno.clearSelection();

    limpiandoCampos = false;
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        txtIdAlumno = new javax.swing.JTextField();
        comboGenero = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbAlumno = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        comboAula = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        comboEncargado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btRegistrar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLImpiar = new javax.swing.JButton();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(null);

        jPanel8.setBackground(new java.awt.Color(61, 61, 61));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Alumno", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel8.setForeground(new java.awt.Color(245, 236, 213));

        txtIdAlumno.setBackground(new java.awt.Color(87, 142, 126));
        txtIdAlumno.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdAlumno.setForeground(new java.awt.Color(255, 255, 255));
        txtIdAlumno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtIdAlumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdAlumnoActionPerformed(evt);
            }
        });

        comboGenero.setBackground(new java.awt.Color(87, 142, 126));
        comboGenero.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboGenero.setForeground(new java.awt.Color(245, 236, 213));
        comboGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Femenino", "Masculino" }));
        comboGenero.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(245, 236, 213));
        jLabel1.setText("Apellido:");

        jLabel7.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(245, 236, 213));
        jLabel7.setText("Genero:");

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 236, 213));
        jLabel8.setText("Nombre:");

        txtNombre.setBackground(new java.awt.Color(87, 142, 126));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(245, 236, 213));
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        txtApellido.setBackground(new java.awt.Color(87, 142, 126));
        txtApellido.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(245, 236, 213));
        txtApellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtIdAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtApellido))))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtIdAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(61, 61, 61));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Alumnos", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N

        tbAlumno.setBackground(new java.awt.Color(87, 142, 126));
        tbAlumno.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tbAlumno.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tbAlumno);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 905, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(61, 61, 61));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos Aulas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel7.setForeground(new java.awt.Color(245, 236, 213));

        comboAula.setBackground(new java.awt.Color(87, 142, 126));
        comboAula.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboAula.setForeground(new java.awt.Color(245, 236, 213));
        comboAula.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        comboAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboAulaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(245, 236, 213));
        jLabel3.setText("Aula:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboAula, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboAula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        jPanel6.setBackground(new java.awt.Color(61, 61, 61));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos encargado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N

        comboEncargado.setBackground(new java.awt.Color(87, 142, 126));
        comboEncargado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboEncargado.setForeground(new java.awt.Color(245, 236, 213));
        comboEncargado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        comboEncargado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboEncargadoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(245, 236, 213));
        jLabel2.setText("Encargado:");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(comboEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comboEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(61, 61, 61));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N

        btRegistrar.setBackground(new java.awt.Color(87, 142, 126));
        btRegistrar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btRegistrar.setForeground(new java.awt.Color(245, 236, 213));
        btRegistrar.setText("Registrar");
        btRegistrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegistrarActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(87, 142, 126));
        btnEditar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(245, 236, 213));
        btnEditar.setText("Editar");
        btnEditar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(87, 142, 126));
        btnEliminar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(245, 236, 213));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLImpiar.setBackground(new java.awt.Color(87, 142, 126));
        btnLImpiar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLImpiar.setForeground(new java.awt.Color(245, 236, 213));
        btnLImpiar.setText("Limpiar");
        btnLImpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLImpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLImpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLImpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLImpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(27, 27, 27)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegistrarActionPerformed


    try {
        ComboItem encargadoSeleccionado = (ComboItem) comboEncargado.getSelectedItem();
        ComboItem aulaSeleccionada = (ComboItem) comboAula.getSelectedItem();

        ModeloAlumno nuevoAlumno = new ModeloAlumno();
        nuevoAlumno.setNombreAlumno(txtNombre.getText().trim());
        nuevoAlumno.setApellidoAlumno(txtApellido.getText().trim());
        nuevoAlumno.setGeneroAlumno((String) comboGenero.getSelectedItem());
        nuevoAlumno.setFkIdEncargado(encargadoSeleccionado.getId());
        nuevoAlumno.setFkIdAula(aulaSeleccionada.getId());

        ModeloAlumno alumnoCreado = serviceAlumno.crearAlumno(nuevoAlumno, token);
        if (alumnoCreado != null && alumnoCreado.getIdAlumno() > 0) {
            JOptionPane.showMessageDialog(this, "Alumno registrado exitosamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAlumnosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al registrar alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btRegistrarActionPerformed

    private void comboAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboAulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboAulaActionPerformed

    private void txtIdAlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdAlumnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdAlumnoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        if (txtIdAlumno.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un alumno de la tabla para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try {
        int idAlumno = Integer.parseInt(txtIdAlumno.getText());
        ComboItem encargadoSeleccionado = (ComboItem) comboEncargado.getSelectedItem();
        ComboItem aulaSeleccionada = (ComboItem) comboAula.getSelectedItem();

        ModeloAlumno alumnoEditado = new ModeloAlumno();
        alumnoEditado.setIdAlumno(idAlumno);
        alumnoEditado.setNombreAlumno(txtNombre.getText().trim());
        alumnoEditado.setApellidoAlumno(txtApellido.getText().trim());
        alumnoEditado.setGeneroAlumno((String) comboGenero.getSelectedItem());
        alumnoEditado.setFkIdEncargado(encargadoSeleccionado.getId());
        alumnoEditado.setFkIdAula(aulaSeleccionada.getId());

        boolean actualizado = serviceAlumno.actualizarAlumno(alumnoEditado, token);
        if (actualizado) {
            JOptionPane.showMessageDialog(this, "Alumno actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAlumnosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al actualizar alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        if (txtIdAlumno.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un alumno de la tabla para eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este alumno?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    try {
        int idAlumno = Integer.parseInt(txtIdAlumno.getText());
        boolean eliminado = serviceAlumno.eliminarAlumno(idAlumno, token);
        if (eliminado) {
            JOptionPane.showMessageDialog(this, "Alumno eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarAlumnosEnTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID Alumno debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error al eliminar alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLImpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLImpiarActionPerformed
        // TODO add your handling code here:
        limpiarCampos();
    }//GEN-LAST:event_btnLImpiarActionPerformed

    private void comboEncargadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboEncargadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboEncargadoActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtApellidoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btRegistrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnLImpiar;
    private javax.swing.JComboBox<ComboItem> comboAula;
    private javax.swing.JComboBox<ComboItem> comboEncargado;
    private javax.swing.JComboBox<String> comboGenero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbAlumno;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtIdAlumno;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
    }

