/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package View;

import ComboItem.ComboItem;
import Modelos.ModeloNotas;
import Modelos.ModeloAlumno;
import Modelos.ModeloAsignatura;
import Modelos.ModeloAula;
import Modelos.ModeloDocente;
import static Modelos.SesionUsuario.token;
import Service.ServiceAlumno;
import Service.ServiceAsignatura;
import Service.ServiceAula;
import Service.ServiceDocente;
import Service.ServiceNotas;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ViewNotas extends javax.swing.JInternalFrame {

private ServiceNotas serviceNotas = new ServiceNotas();
    private ServiceAlumno serviceAlumno = new ServiceAlumno();
    private ServiceAsignatura serviceAsignatura = new ServiceAsignatura();
    private ServiceAula serviceAula = new ServiceAula();
    private ServiceDocente serviceDocente = new ServiceDocente();

    public ViewNotas() {
       initComponents();
        txtIdNotas.setVisible(false);
        inicializarCombos();
        inicializarTabla();
        cargarNotasEnTabla();
        agregarListeners();
    }

    
    private void inicializarCombos() {
        try {
            List<ModeloAlumno> alumnos = serviceAlumno.getAlumnos(token);
            comboAlumno.removeAllItems();
            for (ModeloAlumno a : alumnos) {
                comboAlumno.addItem(new ComboItem(a.getIdAlumno(), a.getNombreAlumno() + " " + a.getApellidoAlumno()));
            }
            comboAlumno.setSelectedIndex(-1);

            List<ModeloAsignatura> asignaturas = serviceAsignatura.obtenerAsignatura();
            comboAsignatura.removeAllItems();
            for (ModeloAsignatura asig : asignaturas) {
                comboAsignatura.addItem(new ComboItem(asig.getIdAsignatura(), asig.getNombreAsignatura()));
            }
            comboAsignatura.setSelectedIndex(-1);

            List<ModeloAula> aulas = serviceAula.getAulas(token);
            comboAula.removeAllItems();
            for (ModeloAula aula : aulas) {
                String descripcion = aula.getGrado() + " - " + aula.getSeccion();
                comboAula.addItem(new ComboItem(aula.getIdAula(), descripcion));
            }
            comboAula.setSelectedIndex(-1);

            List<ModeloDocente> docentes = serviceDocente.getDocentes();
            comboDocente.removeAllItems();
            for (ModeloDocente doc : docentes) {
                comboDocente.addItem(new ComboItem(doc.getIdDocente(), doc.getNombreDocente() + " " + doc.getApellidoDocente()));
            }
            comboDocente.setSelectedIndex(-1);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando combos: " + e.getMessage());
            e.printStackTrace();
        }
    }

private void inicializarTabla() {
    // Definir columnas y modelo
   DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Nota", "Descripción", "Alumno", "Asignatura", "Aula", "Docente"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblNotas.setModel(model);
        tblNotas.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblNotas.setRowHeight(40);
        tblNotas.setShowGrid(true);
        tblNotas.setGridColor(Color.BLACK);
        jScrollPane2.setPreferredSize(new Dimension(923, 283));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(56, 91, 81));
        headerRenderer.setForeground(new Color(245, 236, 213));
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setFont(tblNotas.getTableHeader().getFont().deriveFont(Font.BOLD));
        for (int i = 0; i < tblNotas.getColumnModel().getColumnCount(); i++) {
            tblNotas.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        Border thickBorder = BorderFactory.createLineBorder(Color.BLACK, 0);

        DefaultTableCellRenderer baseCellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                c.setBackground(new Color(87, 142, 126));
                c.setForeground(new Color(245, 236, 213));
                setHorizontalAlignment(SwingConstants.CENTER);

                Object notaObj = table.getValueAt(row, 1);
                boolean notaBaja = false;
                if (notaObj != null) {
                    try {
                        double nota = Double.parseDouble(notaObj.toString());
                        if (nota <= 60.99) {
                            notaBaja = true;
                        }
                    } catch (NumberFormatException e) {
                    }
                }

                if (notaBaja) {
                    c.setBackground(new Color(Integer.parseInt("FD8E7E", 16)));
                    c.setForeground(new Color(Integer.parseInt("FFFFFF", 16)));
                } else if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }

                if (c instanceof JComponent) {
                    if (hasFocus) {
                        ((JComponent) c).setBorder(BorderFactory.createLineBorder(Color.green, 1));
                    } else {
                        ((JComponent) c).setBorder(thickBorder);
                    }
                }

                return c;
            }
        };

        for (int i = 0; i < tblNotas.getColumnCount(); i++) {
            tblNotas.getColumnModel().getColumn(i).setCellRenderer(baseCellRenderer);
        }
    }

    private void cargarNotasEnTabla() {
        try {
            List<ModeloNotas> notas = serviceNotas.getNotas(token);
            DefaultTableModel model = (DefaultTableModel) tblNotas.getModel();
            model.setRowCount(0);
            for (ModeloNotas n : notas) {
                String alumnoNombre = buscarDescripcionCombo(comboAlumno, n.getFkIdAlumno());
                String asignaturaNombre = buscarDescripcionCombo(comboAsignatura, n.getFkIdAsignatura());
                String aulaNombre = buscarDescripcionCombo(comboAula, n.getFkIdAula());
                String docenteNombre = buscarDescripcionCombo(comboDocente, n.getFkIdDocente());

                model.addRow(new Object[]{
                    n.getIdNotas(),
                    n.getNotaValor(),
                    n.getDescripcion(),
                    alumnoNombre,
                    asignaturaNombre,
                    aulaNombre,
                    docenteNombre
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando notas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String buscarDescripcionCombo(JComboBox<ComboItem> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            ComboItem item = combo.getItemAt(i);
            if (item.getId() == id) {
                return item.toString();
            }
        }
        return "Desconocido";
    }
    private void agregarListeners() {
       btnRegistrar4.addActionListener(e -> registrarNota());
    btnEditar.addActionListener(e -> editarNota());
    btnEliminar.addActionListener(e -> eliminarNota());
    btnLimpiar.addActionListener(e -> limpiarFormulario());

    tblNotas.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            cargarFilaSeleccionada();
        }
    });

    // Listener para comboAlumno: al seleccionar un alumno, actualizar comboAula con su aula
    comboAlumno.addActionListener(e -> {
        ComboItem alumnoSeleccionado = (ComboItem) comboAlumno.getSelectedItem();
        if (alumnoSeleccionado != null) {
            try {
                // Obtener el alumno completo para saber su aula
                ModeloAlumno alumno = serviceAlumno.getAlumno(alumnoSeleccionado.getId(), token);
                int idAula = alumno.getFkIdAula();

                // Buscar el índice del aula en comboAula y seleccionarlo
                for (int i = 0; i < comboAula.getItemCount(); i++) {
                    if (comboAula.getItemAt(i).getId() == idAula) {
                        comboAula.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar aula del alumno: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            comboAula.setSelectedIndex(-1);
        }
    });

    // Listener para comboAsignatura: al seleccionar una asignatura, actualizar comboDocente con el docente que la da
    comboAsignatura.addActionListener(e -> {
        ComboItem asignaturaSeleccionada = (ComboItem) comboAsignatura.getSelectedItem();
        if (asignaturaSeleccionada != null) {
            try {
                // Obtener el docente que da la asignatura
                ModeloAsignatura asignatura = serviceAsignatura.obtenerAsignaturaPorId(asignaturaSeleccionada.getId());
                int idDocente = asignatura.getFkIdDocente();

                // Buscar el índice del docente en comboDocente y seleccionarlo
                for (int i = 0; i < comboDocente.getItemCount(); i++) {
                    if (comboDocente.getItemAt(i).getId() == idDocente) {
                        comboDocente.setSelectedIndex(i);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar docente de la asignatura: " + ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            comboDocente.setSelectedIndex(-1);
        }
    });
    }
public void generarPdfAlumnoNotasPDFBox(ModeloAlumno alumno, List<ModeloNotas> notas) throws IOException {
   // --- INICIO DE LA MODIFICACIÓN ---
        String defaultPath = "C:\\Users\\Admin\\OneDrive\\Escritorio\\ProyectoFinal2\\ProyectoFinalPrueba2\\Documentacion\\Registro de Notas";
        String fileName = "Reporte_Notas_Alumno_" + alumno.getIdAlumno() + ".pdf";
        File directory = new File(defaultPath);

        // Asegúrate de que el directorio exista, si no, créalo
        if (!directory.exists()) {
            directory.mkdirs(); // Crea los directorios necesarios si no existen
        }

        File fileToSave = new File(directory, fileName);
        // --- FIN DE LA MODIFICACIÓN ---

    PDDocument document = new PDDocument();
    PDPage page = new PDPage();
    document.addPage(page);

    PDPageContentStream contentStream = new PDPageContentStream(document, page);
    float pageWidth = page.getMediaBox().getWidth();
    float margin = 50; // Margen para el contenido

    // Cargar imagen desde recursos
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

    // Posición del logo (ajustado para que esté arriba del título del colegio)
    float logoYStart = 780; // Un poco más arriba que la fecha
    float imageX = (pageWidth - imageWidth) / 2;
    float imageY = logoYStart - imageHeight;

    contentStream.drawImage(pdImage, imageX, imageY, imageWidth, imageHeight);

    // Título "LCT - Colegio" (se mueve un poco hacia abajo para dar espacio al logo)
    String colegioText = "LCT - Colegio";
    contentStream.setNonStrokingColor(34, 85, 34);
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 26);
    float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(colegioText) / 1000 * 26;
    float startX = (pageWidth - textWidth) / 2;
    float colegioTextY = imageY - 30; // Debajo del logo, con un pequeño espacio
    
    contentStream.beginText();
    contentStream.newLineAtOffset(startX, colegioTextY);
    contentStream.showText(colegioText);
    contentStream.endText();

    // Fecha a la derecha, cerca de la parte superior, pero debajo del logo
    LocalDate fechaActual = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    String fechaTexto = fechaActual.format(formatter);
    contentStream.setNonStrokingColor(0, 0, 0);
    contentStream.setFont(PDType1Font.HELVETICA, 12);
    float fechaTextWidth = PDType1Font.HELVETICA.getStringWidth(fechaTexto) / 1000 * 12;
    float fechaPosX = pageWidth - fechaTextWidth - margin; // Usar el margen
    float fechaPosY = logoYStart; // A la misma altura que el tope del logo

    contentStream.beginText();
    contentStream.newLineAtOffset(fechaPosX, fechaPosY);
    contentStream.showText(fechaTexto);
    contentStream.endText();
    
    // Reporte de Notas
    String reporteText = "Reporte de Notas";
    contentStream.setNonStrokingColor(56, 142, 81);
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
    textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(reporteText) / 1000 * 20;
    startX = (pageWidth - textWidth) / 2;
    float reporteTextY = colegioTextY - 30; // Debajo del título del colegio
    contentStream.beginText();
    contentStream.newLineAtOffset(startX, reporteTextY);
    contentStream.showText(reporteText);
    contentStream.endText();

    contentStream.setNonStrokingColor(0, 0, 0);
    contentStream.setFont(PDType1Font.HELVETICA, 14);
    contentStream.beginText();
    contentStream.newLineAtOffset(margin, reporteTextY - 40); // Ajuste para que no se sobreponga
    contentStream.showText("Alumno: " + alumno.getNombreAlumno() + " " + alumno.getApellidoAlumno());

    String aulaAlumno = "Desconocido";
    for (int i = 0; i < comboAula.getItemCount(); i++) {
        ComboItem item = comboAula.getItemAt(i);
        if (item.getId() == alumno.getFkIdAula()) {
            aulaAlumno = item.toString();
            break;
        }
    }
    contentStream.newLineAtOffset(0, -18);
    contentStream.showText("Aula: " + aulaAlumno);
    contentStream.endText();

    float yStartContent = reporteTextY - 100; // Punto de inicio para la tabla, ajustado
    float yPosition = yStartContent;
    float rowHeight = 25;
    float tableBottomY = 100;
    float tableWidth = pageWidth - 2 * margin;

    String[] headers = {"Docente", "Asignatura", "Nota", "Descripción"};
    float[] colWidths = {150, 150, 50, tableWidth - 150 - 150 - 50};

    contentStream.setNonStrokingColor(198, 239, 206);
    contentStream.addRect(margin, yPosition - rowHeight + 5, tableWidth, rowHeight);
    contentStream.fill();

    contentStream.setNonStrokingColor(34, 85, 34);
    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
    float nextX = margin;
    for (int i = 0; i < headers.length; i++) {
        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, yPosition - 18);
        contentStream.showText(headers[i]);
        contentStream.endText();
        nextX += colWidths[i];
    }
    yPosition -= rowHeight;

    contentStream.setNonStrokingColor(0, 0, 0);
    contentStream.setFont(PDType1Font.HELVETICA, 12);

    for (ModeloNotas nota : notas) {
        String docenteNombre = buscarDescripcionCombo(comboDocente, nota.getFkIdDocente());
        String asignaturaNombre = buscarDescripcionCombo(comboAsignatura, nota.getFkIdAsignatura());
        String notaStr = String.format("%.2f", nota.getNotaValor());
        String descripcion = nota.getDescripcion();

        List<String> descripcionLineas = splitTextToLines(descripcion, PDType1Font.HELVETICA, 12, colWidths[3] - 10);
        float filaAltura = rowHeight * descripcionLineas.size();

        if (yPosition - filaAltura < tableBottomY) {
            contentStream.close();
            page = new PDPage();
            document.addPage(page);
            contentStream = new PDPageContentStream(document, page);
            yPosition = yStartContent; // Reiniciar yPosition para la nueva página
            
            // Redibujar encabezados en la nueva página
            contentStream.setNonStrokingColor(198, 239, 206);
            contentStream.addRect(margin, yPosition - rowHeight + 5, tableWidth, rowHeight);
            contentStream.fill();

            contentStream.setNonStrokingColor(34, 85, 34);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            nextX = margin;
            for (int i = 0; i < headers.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(nextX + 5, yPosition - 18);
                contentStream.showText(headers[i]);
                contentStream.endText();
                nextX += colWidths[i];
            }
            yPosition -= rowHeight;
            contentStream.setNonStrokingColor(0, 0, 0);
            contentStream.setFont(PDType1Font.HELVETICA, 12);
        }

        if (((int)((yStartContent - yPosition) / rowHeight)) % 2 == 0) { // Usar yStartContent para el cálculo del color de la fila
            contentStream.setNonStrokingColor(240, 255, 240);
            contentStream.addRect(margin, yPosition - filaAltura + 5, tableWidth, filaAltura);
            contentStream.fill();
            contentStream.setNonStrokingColor(0, 0, 0);
        }

        nextX = margin;

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, yPosition - 18);
        contentStream.showText(docenteNombre);
        contentStream.endText();
        nextX += colWidths[0];

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, yPosition - 18);
        contentStream.showText(asignaturaNombre);
        contentStream.endText();
        nextX += colWidths[1];

        contentStream.beginText();
        contentStream.newLineAtOffset(nextX + 5, yPosition - 18);
        contentStream.showText(notaStr);
        contentStream.endText();
        nextX += colWidths[2];

        float textY = yPosition - 18;
        for (String linea : descripcionLineas) {
            contentStream.beginText();
            contentStream.newLineAtOffset(nextX + 5, textY);
            contentStream.showText(linea);
            contentStream.endText();
            textY -= rowHeight;
        }

        contentStream.setStrokingColor(34, 85, 34);
        contentStream.setLineWidth(0.5f);
        float lineX = margin;
        for (int i = 0; i <= headers.length; i++) {
            contentStream.moveTo(lineX, yPosition + 5);
            contentStream.lineTo(lineX, yPosition - filaAltura + 5);
            contentStream.stroke();
            if (i < headers.length) {
                lineX += colWidths[i];
            }
        }

        contentStream.moveTo(margin, yPosition - filaAltura + 5);
        contentStream.lineTo(margin + tableWidth, yPosition - filaAltura + 5);
        contentStream.stroke();

        yPosition -= filaAltura;
    }

    contentStream.moveTo(margin, yStartContent + 5); // Usar yStartContent aquí también para la línea superior
    contentStream.lineTo(margin + tableWidth, yStartContent + 5);
    contentStream.stroke();

    contentStream.close();
    document.save(fileToSave);
    document.close();

    JOptionPane.showMessageDialog(this, "PDF generado: " + fileToSave.getAbsolutePath());

    if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(fileToSave);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo abrir el PDF automáticamente: " + e.getMessage());
        }
    }
}

/**
 * Divide un texto en líneas que quepan en un ancho dado, usando la fuente y tamaño indicados.
 */
private List<String> splitTextToLines(String text, PDType1Font font, int fontSize, float maxWidth) throws IOException {
    List<String> lines = new ArrayList<>();
    String[] words = text.split(" ");
    StringBuilder line = new StringBuilder();

    for (String word : words) {
        String testLine = line.length() == 0 ? word : line + " " + word;
        float size = font.getStringWidth(testLine) / 1000 * fontSize;
        if (size > maxWidth) {
            if (line.length() > 0) {
                lines.add(line.toString());
                line = new StringBuilder(word);
            } else {
                // Palabra muy larga, la agregamos igual
                lines.add(word);
                line = new StringBuilder();
            }
        } else {
            line = new StringBuilder(testLine);
        }
    }
    if (line.length() > 0) {
        lines.add(line.toString());
    }
    return lines;
}




private void cargarFilaSeleccionada() {
        int fila = tblNotas.getSelectedRow();
        if (fila >= 0) {
            DefaultTableModel model = (DefaultTableModel) tblNotas.getModel();
            txtIdNotas.setText(model.getValueAt(fila, 0).toString());
            txtNota.setText(model.getValueAt(fila, 1).toString());
            txtDescripcion.setText(model.getValueAt(fila, 2).toString());
            seleccionarComboPorDescripcion(comboAlumno, model.getValueAt(fila, 3).toString());
            seleccionarComboPorDescripcion(comboAsignatura, model.getValueAt(fila, 4).toString());
            seleccionarComboPorDescripcion(comboAula, model.getValueAt(fila, 5).toString());
            seleccionarComboPorDescripcion(comboDocente, model.getValueAt(fila, 6).toString());
        }
    }

    private void seleccionarComboPorDescripcion(JComboBox<ComboItem> combo, String descripcion) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            ComboItem item = combo.getItemAt(i);
            if (item.toString().equals(descripcion)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(-1);
    }

    private ModeloNotas leerDatosFormulario() {
        ModeloNotas nota = new ModeloNotas();
        try {
            nota.setIdNotas(Integer.parseInt(txtIdNotas.getText()));
        } catch (NumberFormatException e) {
            nota.setIdNotas(0);
        }
        try {
            nota.setNotaValor(Double.parseDouble(txtNota.getText()));
        } catch (NumberFormatException e) {
            nota.setNotaValor(0);
        }
        nota.setDescripcion(txtDescripcion.getText());

        nota.setFkIdAlumno(obtenerIdSeleccionado(comboAlumno));
        nota.setFkIdAsignatura(obtenerIdSeleccionado(comboAsignatura));
        nota.setFkIdAula(obtenerIdSeleccionado(comboAula));
        nota.setFkIdDocente(obtenerIdSeleccionado(comboDocente));
        return nota;
    }

    private int obtenerIdSeleccionado(JComboBox<ComboItem> combo) {
        ComboItem item = (ComboItem) combo.getSelectedItem();
        if (item != null) {
            return item.getId();
        }
        return -1;
    }

    private void limpiarFormulario() {
        txtIdNotas.setText("");
        txtNota.setText("");
        txtDescripcion.setText("");
        comboAlumno.setSelectedIndex(-1);
        comboAsignatura.setSelectedIndex(-1);
        comboAula.setSelectedIndex(-1);
        comboDocente.setSelectedIndex(-1);
        tblNotas.clearSelection();
    }

    private void registrarNota() {
       try {
            ModeloNotas nota = leerDatosFormulario();
            if (nota.getNotaValor() <= 0) {
                JOptionPane.showMessageDialog(this, "Ingrese una nota válida.");
                return;
            }
            if (nota.getDescripcion().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una descripción.");
                return;
            }
            if (nota.getFkIdAlumno() == -1 || nota.getFkIdAsignatura() == -1 || nota.getFkIdAula() == -1 || nota.getFkIdDocente() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione todos los campos obligatorios.");
                return;
            }
            ModeloNotas creado = serviceNotas.crearNota(nota, token);
            JOptionPane.showMessageDialog(this, "Nota registrada con ID: " + creado.getIdNotas());
            cargarNotasEnTabla();
            limpiarFormulario();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void editarNota() {
        try {
            ModeloNotas nota = leerDatosFormulario();
            if (nota.getIdNotas() <= 0) {
                JOptionPane.showMessageDialog(this, "Seleccione una nota para editar.");
                return;
            }
            boolean exito = serviceNotas.actualizarNota(nota, token);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Nota actualizada correctamente.");
                cargarNotasEnTabla();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la nota.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void eliminarNota() {
        try {
            int id = 0;
            try {
                id = Integer.parseInt(txtIdNotas.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Seleccione una nota para eliminar.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar la nota con ID " + id + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean exito = serviceNotas.eliminarNota(id, token);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Nota eliminada correctamente.");
                    cargarNotasEnTabla();
                    limpiarFormulario();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar la nota.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar nota: " + e.getMessage());
            e.printStackTrace();
        }
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNota = new javax.swing.JTextField();
        comboAlumno = new javax.swing.JComboBox<>();
        comboAula = new javax.swing.JComboBox<>();
        comboAsignatura = new javax.swing.JComboBox<>();
        comboDocente = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNotas = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnRegistrar4 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnRestaurar = new javax.swing.JButton();
        txtIdNotas = new javax.swing.JTextField();

        setBackground(new java.awt.Color(245, 236, 213));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(61, 61, 61));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Datos de Notas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(245, 236, 213));

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(245, 236, 213));
        jLabel8.setText("Alumno:");

        jLabel9.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(245, 236, 213));
        jLabel9.setText("Asignatura:");

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(245, 236, 213));
        jLabel10.setText("Docente:");

        jLabel11.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(245, 236, 213));
        jLabel11.setText("Aula:");

        jLabel12.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(245, 236, 213));
        jLabel12.setText("Descripcion:");

        txtDescripcion.setBackground(new java.awt.Color(87, 142, 126));
        txtDescripcion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(245, 236, 213));
        txtDescripcion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(245, 236, 213));
        jLabel13.setText("Nota:");

        txtNota.setBackground(new java.awt.Color(87, 142, 126));
        txtNota.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNota.setForeground(new java.awt.Color(245, 236, 213));
        txtNota.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        comboAlumno.setBackground(new java.awt.Color(87, 142, 126));
        comboAlumno.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboAlumno.setForeground(new java.awt.Color(245, 236, 213));

        comboAula.setBackground(new java.awt.Color(87, 142, 126));
        comboAula.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboAula.setForeground(new java.awt.Color(245, 236, 213));

        comboAsignatura.setBackground(new java.awt.Color(87, 142, 126));
        comboAsignatura.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboAsignatura.setForeground(new java.awt.Color(245, 236, 213));

        comboDocente.setBackground(new java.awt.Color(87, 142, 126));
        comboDocente.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        comboDocente.setForeground(new java.awt.Color(245, 236, 213));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(comboAlumno, 0, 250, Short.MAX_VALUE)
                            .addComponent(txtDescripcion))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 2, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboAula, 0, 229, Short.MAX_VALUE)
                                        .addComponent(comboDocente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addComponent(txtNota))
                        .addGap(17, 17, 17))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(comboAsignatura, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAula, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)
                        .addComponent(comboDocente, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNota, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(61, 61, 61));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(245, 236, 213), 2), "Tabla de Notas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(245, 236, 213))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(245, 236, 213));

        tblNotas.setBackground(new java.awt.Color(87, 142, 126));
        tblNotas.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        tblNotas.setForeground(new java.awt.Color(245, 236, 213));
        tblNotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Nota", "Descripcion", "Alumno", "Aula", "Docente", "Asignatura"
            }
        ));
        tblNotas.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(tblNotas);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 962, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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

        btnBuscar.setBackground(new java.awt.Color(87, 142, 126));
        btnBuscar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(245, 236, 213));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
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

        btnRestaurar.setBackground(new java.awt.Color(87, 142, 126));
        btnRestaurar.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        btnRestaurar.setForeground(new java.awt.Color(245, 236, 213));
        btnRestaurar.setText("Restaurar");
        btnRestaurar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaurarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnRestaurar, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRestaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtIdNotas.setBackground(new java.awt.Color(87, 142, 126));
        txtIdNotas.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtIdNotas.setForeground(new java.awt.Color(255, 255, 255));
        txtIdNotas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdNotas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdNotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed

    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnRegistrar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar4ActionPerformed

    }//GEN-LAST:event_btnRegistrar4ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed

    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        ComboItem alumnoSeleccionado = (ComboItem) comboAlumno.getSelectedItem();
    if (alumnoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un alumno para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idAlumno = alumnoSeleccionado.getId();
    filtrarNotasPorAlumno(idAlumno);
}

private void filtrarNotasPorAlumno(int idAlumno) {
    try {
            List<ModeloNotas> notas = serviceNotas.getNotasPorAlumno(idAlumno, token);
            DefaultTableModel model = (DefaultTableModel) tblNotas.getModel();
            model.setRowCount(0);
            for (ModeloNotas n : notas) {
                String alumnoNombre = buscarDescripcionCombo(comboAlumno, n.getFkIdAlumno());
                String asignaturaNombre = buscarDescripcionCombo(comboAsignatura, n.getFkIdAsignatura());
                String aulaNombre = buscarDescripcionCombo(comboAula, n.getFkIdAula());
                String docenteNombre = buscarDescripcionCombo(comboDocente, n.getFkIdDocente());

                model.addRow(new Object[]{
                    n.getIdNotas(),
                    n.getNotaValor(),
                    n.getDescripcion(),
                    alumnoNombre,
                    asignaturaNombre,
                    aulaNombre,
                    docenteNombre
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error filtrando notas: " + e.getMessage());
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
ComboItem alumnoSeleccionado = (ComboItem) comboAlumno.getSelectedItem();
    if (alumnoSeleccionado == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un alumno para imprimir.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idAlumno = alumnoSeleccionado.getId();
    try {
        ModeloAlumno alumno = serviceAlumno.getAlumno(idAlumno, token );
        List<ModeloNotas> notas = serviceNotas.getNotasPorAlumno(idAlumno, token);
        generarPdfAlumnoNotasPDFBox(alumno, notas);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error generando PDF: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaurarActionPerformed
        // TODO add your handling code here:
        cargarNotasEnTabla();
    comboAlumno.setSelectedIndex(-1); // Opcional: limpiar selección del combo alumno
    }//GEN-LAST:event_btnRestaurarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnRegistrar4;
    private javax.swing.JButton btnRestaurar;
    private javax.swing.JComboBox<ComboItem> comboAlumno;
    private javax.swing.JComboBox<ComboItem> comboAsignatura;
    private javax.swing.JComboBox<ComboItem> comboAula;
    private javax.swing.JComboBox<ComboItem> comboDocente;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblNotas;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtIdNotas;
    private javax.swing.JTextField txtNota;
    // End of variables declaration//GEN-END:variables
}
