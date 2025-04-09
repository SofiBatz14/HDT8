class PruebaUnitaria {
    public static void main(String[] args) {
        VectorHeap<Paciente> heap = new VectorHeap<>();

        Paciente p1 = new Paciente("Carlos", "fiebre", 3);
        Paciente p2 = new Paciente("Ana", "fractura", 1);
        Paciente p3 = new Paciente("Luis", "dolor abdominal", 2);

        heap.add(p1);
        heap.add(p2);
        heap.add(p3);

        System.out.println("Esperado: Ana, fractura, prioridad: 1");
        System.out.println("Resultado: " + heap.remove());

        System.out.println("Esperado: Luis, dolor abdominal, prioridad: 2");
        System.out.println("Resultado: " + heap.remove());

        System.out.println("Esperado: Carlos, fiebre, prioridad: 3");
        System.out.println("Resultado: " + heap.remove());

        System.out.println("Cola vacía: " + heap.isEmpty());
    }
}