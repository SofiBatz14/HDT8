import java.util.Vector;

public class VectorHeap<E extends Comparable<E>>
{
    protected Vector<E> data;

    public VectorHeap()
    {
        data = new Vector<>();
    }

    public void add(E value)
    {
        data.add(value);
        percolateUp(data.size() - 1);
    }

    public E remove()
    {
        if (data.isEmpty()) return null;
        E minVal = data.get(0);
        if (data.size() == 1)
        {
            data.remove(0);
        }
        else
        {
            data.set(0, data.get(data.size() - 1));
            data.remove(data.size() - 1);
            percolateDown(0);
        }
        return minVal;
    }

    public E peek()
    {
        return data.isEmpty() ? null : data.get(0);
    }

    public boolean isEmpty()
    {
        return data.isEmpty();
    }

    protected void percolateUp(int index)
    {
        while (index > 0)
        {
            int parent = (index - 1) / 2;
            E value = data.get(index);
            if (value.compareTo(data.get(parent)) < 0)
            {
                data.set(index, data.get(parent));
                data.set(parent, value);
                index = parent;
            }
            else
            {
                break;
            }
        }
    }

    protected void percolateDown(int index)
    {
        int child;
        E value = data.get(index);
        while (index * 2 + 1 < data.size())
        {
            child = index * 2 + 1;
            if (child + 1 < data.size() && data.get(child + 1).compareTo(data.get(child)) < 0)
            {
                child++;
            }
            if (data.get(child).compareTo(value) < 0)
            {
                data.set(index, data.get(child));
                index = child;
            }
            else
            {
                break;
            }
        }
        data.set(index, value);
    }
}