package data;

public class PlayerCounter {
    private int playerId, value;

    public int currentValue()
    {
        return value;
    }
    public void increment()
    {
        value++;
    }
    public void reset()
    {
        value=0;
    }
}
