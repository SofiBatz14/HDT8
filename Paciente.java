public class Paciente implements Comparable<Paciente>
{
    private String nombre;
    private String sintoma;
    private char prioridad;

    public Paciente(String nombre, String sintoma, char prioridad)
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

    public char getPrioridad()
    {
        return prioridad;
    }
    public void setPrioridad(char prioridad)
    {
        this.prioridad = prioridad;
    }

    @Override
    public int compareTo(Paciente otro)
    {
        return Character.compare(this.prioridad, otro.prioridad);
    }

    @Override
    public String toString()
    {
        return nombre + ", " + sintoma + ", prioridad: " + prioridad;
    }
}