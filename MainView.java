
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;

import java.util.Random;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends VerticalLayout
{
    public int min = 100;
    public int max = 200;
    public int n = 2;
    public int number_of_equations = 2;
    public long base_time = 10;

    public int index = 0;


    Random r = new Random();

    Equation[] equations = new Equation[number_of_equations];

    Button new_game = new Button("NEW GAME", event -> new_game());
    Label equation = new Label();
    Label time_label = new Label();
    NumberField answer = new NumberField();
    Button submit = new Button("SUBMIT ANSWER", event -> submit());
    HorizontalLayout solver = new HorizontalLayout();

    VerticalLayout results = new VerticalLayout();

    public MainView()
    {
        solver.add(equation);
        solver.add(answer);
        solver.add(submit);
        add(new_game);
        add(solver);
        add(results);
        new_game.setSizeFull();
        time_label.setSizeFull();
        equation.setMinWidth("400px");
        submit.setEnabled(false);
    }

    public void new_game()
    {
        index = 0;
        results.removeAll();
        for(int i = 0; i < number_of_equations; i++)
        {
            equations[i] = new Equation(2 + r.nextInt(n), min, max);
        }
        equation.setText(equations[index].get_equation());
        equations[index].start = System.nanoTime();
        submit.setEnabled(true);

    }

    public void submit()
    {
        if(index < number_of_equations-1)
        {
            equation.setText(equations[index + 1].get_equation());
            equations[index].end = System.nanoTime();
            equations[index + 1].start = System.nanoTime();
            equations[index].player_answer = answer.getValue().intValue();
        }

        if(index == number_of_equations - 1)
        {
            equations[index].end = System.nanoTime();
            equations[index].player_answer = answer.getValue().intValue();
            submit.setEnabled(false);
            report();
        }
        index++;
        answer.clear();
    }

    public void report()
    {
        long result = 0;
        for(int i = 0; i < number_of_equations; i++)
        {
            results.add(new Label("QUESTION " + (i+1) +": " + equations[i].get_equation() + " " + equations[i].player_answer + " this is: " + (equations[i].answer == equations[i].player_answer) + " completed in: " + (equations[i].end - equations[i].start)/1000000000 + " seconds."));
            if(equations[i].answer == equations[i].player_answer)
            {
                double temp = (equations[i].end - equations[i].start + 1000000000)/1000000000;
                int temp2 = (int) (100 * base_time / temp);
                result = result + temp2;
            }
        }
        result = result / number_of_equations;
        results.add(new Label("FINAL RESULT: " + result));
    }
}
