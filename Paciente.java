public class Paciente implements Comparable<Paciente>
{
    private String nombre;
    private String sintoma;
    private int prioridad;

    public Paciente(String nombre, String sintoma, int prioridad)
    {
        this.nombre = nombre;
        this.sintoma = sintoma;
        this.prioridad = prioridad;
    }

    public String getNombre()
    {
        return nombre;
    }
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getSintoma()
    {
        return sintoma;
    }
    public void setSintoma(String sintoma)
    {
        this.sintoma = sintoma;
    }

    public int getPrioridad()
    {
        return prioridad;
    }
    public void setPrioridad(int prioridad)
    {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Paciente otro) {
        return Integer.compare(this.prioridad, otro.prioridad); // menor número = mayor prioridad
    }

    @Override
    public String toString()
    {
        return nombre + ", " + sintoma + ", prioridad: " + prioridad;
    }
}