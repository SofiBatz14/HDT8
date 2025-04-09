import java.io.*;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        VectorHeap<Paciente> colaPacientes = new VectorHeap<>();

        try
        {
            File archivo = new File("pacientes.txt");
            Scanner scanner = new Scanner(archivo);

            while (scanner.hasNextLine())
            {
                String linea = scanner.nextLine();
                String[] partes = linea.split(",");
                if (partes.lenght == 3)
                {
                    String nombre = partes[0].trim();
                    String sintoma = partes[1].trim();
                    char prioridad = partes[2].trim().charAt(0);
                    Paciente p = new Paciente(nombre, sintoma, prioridad);
                    colaPacientes.add(p);
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Archivo no encontrado: " + e.getMessage());
            return;
        }
        System.out.println("\nAtendiendo pacientes por prioridad:\n");
        while (!colaPacientes.isEmpty())
        {
            Paciente p = colaPacientes.remove();
            System.out.println(p);
        }
    }
}
