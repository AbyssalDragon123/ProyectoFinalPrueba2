package ComboItem;

public class ComboItemDocente{
private int id;
    private String label;

    public ComboItemDocente(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label; // Esto es lo que se mostrará en el JComboBox
    }
}