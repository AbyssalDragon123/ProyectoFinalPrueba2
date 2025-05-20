package Configuracion;

import java.awt.Component;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;

public class UrlOpener {

    /**
     * Abre la URL indicada en el navegador por defecto,
     * mostrando diálogos de error en caso de fallo.
     *
     * @param url    La dirección web a abrir (por ejemplo "https://www.youtube.com")
     * @param parent El componente padre para los diálogos (puede ser 'null')
     */
    public static void openUrl(String url, Component parent) {
        if (!Desktop.isDesktopSupported()) {
            JOptionPane.showMessageDialog(
                parent,
                "Tu plataforma no soporta la operación de abrir el navegador.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            JOptionPane.showMessageDialog(
                parent,
                "La acción de abrir enlaces no está soportada.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            desktop.browse(new URI(url));
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(
                parent,
                "La URL proporcionada no es válida:\n" + ex.getInput(),
                "URL Inválida",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                parent,
                "No se pudo abrir el navegador:\n" + ex.getMessage(),
                "I/O Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
