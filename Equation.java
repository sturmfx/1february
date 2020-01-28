import java.util.Random;

public class Equation
{
    public long start = 0;
    public long end = 0;
    public int answer = 0;
    public int player_answer = 0;
    public Random rand = new Random();
    int[] numbers;
    String[] actions;
    public Equation(int n, int min, int max)
    {
        numbers = new int[n];
        actions = new String[n -1];
        for(int i = 0; i < n; i++)
        {
            numbers[i] = min + rand.nextInt(max - min);
        }
        answer = numbers[0];
        for(int i = 0; i < n - 1; i++)
        {
            if(rand.nextBoolean())
            {
                actions[i] = "+";
                answer = answer + numbers[i + 1];
            }
            else
            {
                actions[i] = "-";
                answer = answer - numbers[i + 1];
            }
        }
    }

    public String get_equation()
    {
        String s = "";
        for(int i = 0; i < actions.length; i++)
        {
            s = s +  numbers[i] + " " + actions[i] + " ";
        }
        s = s + numbers[actions.length] + " = ";
        return s;
    }

    public void print()
    {
        System.out.println(get_equation());
    }

}
