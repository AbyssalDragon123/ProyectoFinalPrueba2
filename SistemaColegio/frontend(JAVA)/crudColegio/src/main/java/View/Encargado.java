package View;

import Service.ServiceEncargado;
import Modelos.ModeloEncargado;
import static Modelos.SesionUsuario.token;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Encargado extends javax.swing.JInternalFrame {
private final ServiceEncargado service = new ServiceEncargado();

    public Encargado() {
        initComponents();
        setPlaceholders();
        txtIdEncargado.setVisible(false);
        this.cargarEncargadosEnTabla();

        // Listener para seleccionar fila y cargar datos en campos
        tblEncargado.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblEncargado.getSelectedRow() != -1) {
                int fila = tblEncargado.getSelectedRow();
                txtIdEncargado.setText(tblEncargado.getValueAt(fila, 0).toString());
                txtNombre.setText(tblEncargado.getValueAt(fila, 1).toString());
                txtApellido.setText(tblEncargado.getValueAt(fila, 2).toString());
                txtCorreo.setText(tblEncargado.getValueAt(fila, 3).toString());
                txtTelefono.setText(tblEncargado.getValueAt(fila, 4).toString());
                txtDireccion.setText(tblEncargado.getValueAt(fila, 5).toString());
                txtDPI.setText(tblEncargado.getValueAt(fila, 6).toString());
            }
        });
    }
private boolean existeDuplicado(String correo, String dpi, Integer idActual) {
    List<ModeloEncargado> lista = service.obtenerEncargados(token);
    if (lista == null) return false;

    for (ModeloEncargado e : lista) {
        // Si idActual es null (registro nuevo) o diferente al id del encargado actual (edición)
    if (idActual == null || e.getIdEncargado() != idActual.intValue()) {
            if (e.getCorreoEncargado().equalsIgnoreCase(correo)) {
                JOptionPane.showMessageDialog(this, "Ya existe un encargado con este correo.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return true;
            }
            if (e.getDpiEncargado().equals(dpi)) {
                JOptionPane.showMessageDialog(this, "Ya existe un encargado con este DPI.", "Duplicado", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
    }
    return false;
}
    private void cargarEncargadosEnTabla() {
        try {
            List<ModeloEncargado> listaEncargados = service.obtenerEncargados(token);
            if (listaEncargados == null) {
                JOptionPane.showMessageDialog(this, "No se pudo obtener la lista de encargados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String[] columnas = {"ID", "Nombre", "Apellido", "Correo", "Teléfono", "Dirección", "DPI"};
            Object[][] datos = new Object[listaEncargados.size()][columnas.length];

            for (int i = 0; i < listaEncargados.size(); i++) {
                ModeloEncargado encargado = listaEncargados.get(i);
                datos[i][0] = encargado.getIdEncargado();
                datos[i][1] = encargado.getNombreEncargado();
                datos[i][2] = encargado.getApellidoEncargado();
                datos[i][3] = encargado.getCorreoEncargado();
                datos[i][4] = encargado.getTelefonoEncargado();
                datos[i][5] = encargado.getDireccionEncargado();
                datos[i][6] = encargado.getDpiEncargado();
            }

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(datos, columnas) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tblEncargado.setModel(model);

            // Ocultar columna ID
            tblEncargado.getColumnModel().getColumn(0).setMinWidth(0);
            tblEncargado.getColumnModel().getColumn(0).setMaxWidth(0);
            tblEncargado.getColumnModel().getColumn(0).setWidth(0);

            // Estilos para encabezado y filas
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(56, 91, 81)); // Verde oscuro
            headerRenderer.setForeground(new Color(245, 236, 213)); // Texto claro
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            headerRenderer.setFont(tblEncargado.getTableHeader().getFont().deriveFont(Font.BOLD));
            for (int i = 0; i < tblEncargado.getColumnModel().getColumnCount(); i++) {
                tblEncargado.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }

            tblEncargado.setRowHeight(40);
            jScrollPane2.getViewport().setBackground(new Color(87, 142, 126));

            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
                @Override
                public java.awt.Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setBackground(new Color(87, 142, 126));
                    setForeground(new Color(245, 236, 213));
                    setHorizontalAlignment(SwingConstants.CENTER);
                    return this;
                }
            };

            for (int i = 1; i < tblEncargado.getColumnCount(); i++) {
                tblEncargado.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar encargados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPlaceholders() {
        // Puedes implementar placeholders si quieres, similar a ViewAlumno
         // Nombre
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
        // APELLIDO
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
        // APELLIDO
        txtCorreo.setText("Example@gmail.com");
        txtCorreo.setHorizontalAlignment(JTextField.CENTER);
        txtCorreo.setForeground(Color.decode("#A3C6BC"));
        txtCorreo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtCorreo.getText().equals("Example@gmail.com")) {
                    txtCorreo.setText("");
                    txtCorreo.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtCorreo.getText().isEmpty()) {
                    txtCorreo.setText("Example@gmail.com");
                    txtCorreo.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        // APELLIDO
        txtTelefono.setText("Ingrese Numero de Telefono");
        txtTelefono.setHorizontalAlignment(JTextField.CENTER);
        txtTelefono.setForeground(Color.decode("#A3C6BC"));
        txtTelefono.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtTelefono.getText().equals("Ingrese Numero de Telefono")) {
                    txtTelefono.setText("");
                    txtTelefono.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtTelefono.getText().isEmpty()) {
                    txtTelefono.setText("Ingrese Numero de Telefono");
                    txtTelefono.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        
        // APELLIDO
        txtDPI.setText("Ingrese el DPI");
        txtDPI.setHorizontalAlignment(JTextField.CENTER);
        txtDPI.setForeground(Color.decode("#A3C6BC"));
        txtDPI.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtDPI.getText().equals("Ingrese el DPI")) {
                    txtDPI.setText("");
                    txtDPI.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtDPI.getText().isEmpty()) {
                    txtDPI.setText("Ingrese el DPI");
                    txtDPI.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
        // APELLIDO
        txtDireccion.setText("Ingrese la Direccion");
        txtDireccion.setHorizontalAlignment(JTextField.CENTER);
        txtDireccion.setForeground(Color.decode("#A3C6BC"));
        txtDireccion.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtDireccion.getText().equals("Ingrese la Direccion")) {
                    txtDireccion.setText("");
                    txtDireccion.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtDireccion.getText().isEmpty()) {
                    txtDireccion.setText("Ingrese la Direccion");
                    txtDireccion.setForeground(Color.decode("#A3C6BC"));
                }
            }
        }); 
    }

private void generarCartaCompromisoPDF() {
    String nombre = txtNombre.getText().trim();
    String apellido = txtApellido.getText().trim();
    String dpi = txtDPI.getText().trim();
    String direccion = txtDireccion.getText().trim();
    String telefono = txtTelefono.getText().trim();

    if (nombre.isEmpty() || apellido.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Complete los datos del encargado para imprimir la carta.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
        return;
    }

    try (PDDocument document = new PDDocument()) {
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float pageWidth = page.getMediaBox().getWidth();
        float margin = 50;
        float yStart = 750;

        // Título centrado arriba
        String titulo = "Colegio LCT";
        int fontSizeTitulo = 18;
        float tituloWidth = PDType1Font.TIMES_BOLD.getStringWidth(titulo) / 1000 * fontSizeTitulo;
        float tituloX = (pageWidth - tituloWidth) / 2;
        float tituloY = yStart;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_BOLD, fontSizeTitulo);
        contentStream.newLineAtOffset(tituloX, tituloY);
        contentStream.showText(titulo);
        contentStream.endText();

        // Cargar imagen desde recursos y dibujarla debajo del título
        PDImageXObject pdImage;
        try (InputStream is = getClass().getResourceAsStream("/logo.jpg")) {
            if (is == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el archivo logo.jpg en los recursos.", "Error", JOptionPane.ERROR_MESSAGE);
                contentStream.close();
                return;
            }
            pdImage = PDImageXObject.createFromByteArray(document, is.readAllBytes(), "logo");
        }

        float imageWidth = 100;
        float imageHeight = 100 * pdImage.getHeight() / pdImage.getWidth();
        float imageX = (pageWidth - imageWidth) / 2;
        float imageY = tituloY - 40 - imageHeight;

        contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);

        // Fecha a la derecha, debajo de la imagen
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String fechaTexto = "Fecha: " + fecha;
        int fontSizeFecha = 12;
        float fechaWidth = PDType1Font.TIMES_ROMAN.getStringWidth(fechaTexto) / 1000 * fontSizeFecha;
        float fechaX = pageWidth - margin - fechaWidth;
        float fechaY = imageY - 20;

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSizeFecha);
        contentStream.newLineAtOffset(fechaX, fechaY);
        contentStream.showText(fechaTexto);
        contentStream.endText();

        // Texto principal
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.setLeading(16f);

        float textStartX = margin;
        float textStartY = fechaY - 50;
        contentStream.newLineAtOffset(textStartX, textStartY);

        contentStream.showText("Carta de Compromiso");
        contentStream.newLine();
        contentStream.newLine();

        String texto = "Yo, " + nombre + " " + apellido + ", con DPI número " + dpi + ", " +
                "domiciliado en " + direccion + ", teléfono " + telefono + ". " +
                "Me comprometo a que mi hijo(a) cumpla con todas las normas de convivencia, disciplina y reglamento interno del Colegio LCT. " +
                "Estoy consciente de que el incumplimiento de estas normas puede conllevar sanciones establecidas por la institución y este reglamento es fundamental para el buen desarrollo y convivencia dentro de la institución educativa.";

        List<String> lineas = dividirTextoEnLineas(texto, PDType1Font.TIMES_ROMAN, 12, pageWidth - 2 * margin);

        for (String linea : lineas) {
            contentStream.showText(linea);
            contentStream.newLine();
        }

        contentStream.newLine();
        contentStream.showText("Firma del Encargado:");
        contentStream.newLine();
        contentStream.endText();

        // Firma centrada
        String lineaFirma = "______________________________";
        String nombreCompleto = nombre + " " + apellido;

        float fontSize = 12;
        float lineaWidth = PDType1Font.TIMES_ROMAN.getStringWidth(lineaFirma) / 1000 * fontSize;
        float nombreWidth = PDType1Font.TIMES_ROMAN.getStringWidth(nombreCompleto) / 1000 * fontSize;
        float centerX = pageWidth / 2;
        float firmaY = textStartY - (lineas.size() * 16) - (5 * 16);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
        contentStream.newLineAtOffset(centerX - lineaWidth / 2, firmaY);
        contentStream.showText(lineaFirma);
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
        contentStream.newLineAtOffset(centerX - nombreWidth / 2, firmaY - 20);
        contentStream.showText(nombreCompleto);
        contentStream.endText();

        contentStream.close();

        // --- Inicio de la sección de guardado modificada ---

            // Ruta base donde quieres guardar el archivo
            String rutaBase = "C:\\Users\\Admin\\OneDrive\\Escritorio\\ProyectoFinal2\\ProyectoFinalPrueba2\\Documentacion\\Convenio";
            String nombreArchivo = "CartaCompromiso_" + nombre + "_" + apellido + ".pdf";
            File directorioDestino = new File(rutaBase);

            // Crear el directorio si no existe
            if (!directorioDestino.exists()) {
                directorioDestino.mkdirs(); // Crea los directorios necesarios
            }

            File archivoPDF = new File(directorioDestino, nombreArchivo);

            document.save(archivoPDF);
            JOptionPane.showMessageDialog(this, "PDF generado correctamente en:\n" + archivoPDF.getAbsolutePath());

            // Abrir automáticamente
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(archivoPDF);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "No se pudo abrir el PDF automáticamente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            // --- Fin de la sección de guardado modificada ---

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error generando PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    
}



// Método auxiliar para dividir texto en líneas que quepan en el ancho dado
private List<String> dividirTextoEnLineas(String texto, PDType1Font font, int fontSize, float maxWidth) throws IOException {
    List<String> lineas = new java.util.ArrayList<>();
    String[] palabras = texto.split(" ");
    StringBuilder lineaActual = new StringBuilder();

    for (String palabra : palabras) {
        String pruebaLinea = lineaActual.length() == 0 ? palabra : lineaActual + " " + palabra;
        float width = font.getStringWidth(pruebaLinea) / 1000 * fontSize;
        if (width > maxWidth) {
            lineas.add(lineaActual.toString());
            lineaActual = new StringBuilder(palabra);
        } else {
            if (lineaActual.length() > 0) {
                lineaActual.append(" ");
            }
            lineaActual.append(palabra);
        }
    }
    if (lineaActual.length() > 0) {
        lineas.add(lineaActual.toString());
    }
    return lineas;
}

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        txtIdEncargado = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtApellido = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDPI = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEncargado = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(null);
        setMaximumSize(new java.awt.Dimension(1031, 710));
        setMinimumSize(new java.awt.Dimension(1031, 710));
        setPreferredSize(new java.awt.Dimension(1031, 710));

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Encargados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(245, 236, 213));

        txtNombre.setBackground(new java.awt.Color(87, 142, 126));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(245, 236, 213));
        txtNombre.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtIdEncargado.setBackground(new java.awt.Color(87, 142, 126));
        txtIdEncargado.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdEncargado.setForeground(new java.awt.Color(255, 255, 255));
        txtIdEncargado.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtCorreo.setBackground(new java.awt.Color(87, 142, 126));
        txtCorreo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCorreo.setForeground(new java.awt.Color(245, 236, 213));
        txtCorreo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 236, 213));
        jLabel8.setText("Nombre:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(245, 236, 213));
        jLabel9.setText("Correo:");

        txtApellido.setBackground(new java.awt.Color(87, 142, 126));
        txtApellido.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtApellido.setForeground(new java.awt.Color(245, 236, 213));
        txtApellido.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTelefono.setBackground(new java.awt.Color(87, 142, 126));
        txtTelefono.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTelefono.setForeground(new java.awt.Color(245, 236, 213));
        txtTelefono.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(245, 236, 213));
        jLabel10.setText("Telefono:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(245, 236, 213));
        jLabel12.setText("DPI:");

        txtDPI.setBackground(new java.awt.Color(87, 142, 126));
        txtDPI.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDPI.setForeground(new java.awt.Color(245, 236, 213));
        txtDPI.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(245, 236, 213));
        jLabel13.setText("Dirección:");

        txtDireccion.setBackground(new java.awt.Color(87, 142, 126));
        txtDireccion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(245, 236, 213));
        txtDireccion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(245, 236, 213));
        jLabel1.setText("Apellido:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                        .addComponent(txtCorreo)
                        .addComponent(txtDPI))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(txtTelefono)
                    .addComponent(txtDireccion)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(84, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtIdEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtIdEncargado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDPI, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jPanel3.setBackground(new java.awt.Color(61, 61, 61));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Acciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(245, 236, 213));

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

        btnLimpiar.setBackground(new java.awt.Color(87, 142, 126));
        btnLimpiar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(245, 236, 213));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnRegistrar4.setBackground(new java.awt.Color(87, 142, 126));
        btnRegistrar4.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRegistrar4.setForeground(new java.awt.Color(245, 236, 213));
        btnRegistrar4.setText("Registrarse");
        btnRegistrar4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegistrar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar4ActionPerformed(evt);
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

        btnImprimir.setBackground(new java.awt.Color(87, 142, 126));
        btnImprimir.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(245, 236, 213));
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imprimir (1).png"))); // NOI18N
        btnImprimir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(162, 162, 162)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Encargados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblEncargado.setBackground(new java.awt.Color(87, 142, 126));
        tblEncargado.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblEncargado.setForeground(new java.awt.Color(245, 236, 213));
        tblEncargado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblEncargado.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblEncargado);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 923, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
try {
        if (txtIdEncargado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un encargado para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este encargado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtIdEncargado.getText());
            boolean exito = service.eliminarEncargado(id, token);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Encargado eliminado correctamente.");
                cargarEncargadosEnTabla();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar encargado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }     
     
    }//GEN-LAST:event_btnEliminarActionPerformed
private void limpiarCampos() {
    txtIdEncargado.setText("");
    txtNombre.setText("");
    txtApellido.setText("");
    txtCorreo.setText("");
    txtTelefono.setText("");
    txtDireccion.setText("");
    txtDPI.setText("");
    tblEncargado.clearSelection();
}
private boolean esCorreoValido(String correo) {
    String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    return correo.matches(regex);
}
private boolean esDPIValido(String dpi) {
    return dpi.matches("\\d+"); // Solo dígitos
}
private boolean esTelefonoValido(String telefono) {
    return telefono.matches("\\d+"); // Solo dígitos
}

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
   limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar4ActionPerformed
try {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String dpi = txtDPI.getText().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números.", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esDPIValido(dpi)) {
            JOptionPane.showMessageDialog(this, "El DPI solo debe contener números.", "DPI inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar duplicados (idActual = null porque es nuevo registro)
        if (existeDuplicado(correo, dpi, null)) {
            return; // Ya mostró mensaje dentro del método
        }

        ModeloEncargado nuevoEncargado = new ModeloEncargado();
        nuevoEncargado.setNombreEncargado(nombre);
        nuevoEncargado.setApellidoEncargado(apellido);
        nuevoEncargado.setCorreoEncargado(correo);
        nuevoEncargado.setTelefonoEncargado(telefono);
        nuevoEncargado.setDireccionEncargado(direccion);
        nuevoEncargado.setDpiEncargado(dpi);

        ModeloEncargado creado = service.crearEncargado(nuevoEncargado, token);
        if (creado != null) {
            JOptionPane.showMessageDialog(this, "Encargado registrado correctamente.");
            cargarEncargadosEnTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar encargado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnRegistrar4ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
try {
        if (txtIdEncargado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un encargado para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String dpi = txtDPI.getText().trim();
        Integer idActual = Integer.parseInt(txtIdEncargado.getText());

        if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || dpi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido.", "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(this, "El teléfono solo debe contener números.", "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!esDPIValido(dpi)) {
            JOptionPane.showMessageDialog(this, "El DPI solo debe contener números.", "DPI inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar duplicados (excluir el encargado que se está editando)
        if (existeDuplicado(correo, dpi, idActual)) {
            return; // Ya mostró mensaje dentro del método
        }

        ModeloEncargado encargadoEdit = new ModeloEncargado();
        encargadoEdit.setIdEncargado(idActual);
        encargadoEdit.setNombreEncargado(nombre);
        encargadoEdit.setApellidoEncargado(apellido);
        encargadoEdit.setCorreoEncargado(correo);
        encargadoEdit.setTelefonoEncargado(telefono);
        encargadoEdit.setDireccionEncargado(direccion);
        encargadoEdit.setDpiEncargado(dpi);

        boolean exito = service.actualizarEncargado(encargadoEdit, token);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Encargado editado correctamente.");
            cargarEncargadosEnTabla();
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Error al editar encargado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Seleccione un encargado para imprimir la carta.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    generarCartaCompromisoPDF();
    }//GEN-LAST:event_btnImprimirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblEncargado;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDPI;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtIdEncargado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
