import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        console.useLocale(Locale.US); // дробные числа через точку: 0.5

        Line line1 = readLine(console, 1);
        Line line2 = readLine(console, 2);

        System.out.println("Прямая №1: " + line1);
        System.out.println("Прямая №2: " + line2);
        System.out.println("Параллельны: " + (line1.isParallel(line2) ? "да" : "нет"));
        System.out.println("Перпендикулярны: " + (line1.isPerpendicular(line2) ? "да" : "нет"));

        Point point = readPoint(console);
        System.out.println("Точка на прямой №1: " + (line1.contains(point) ? "да" : "нет"));
        System.out.println("Точка на прямой №2: " + (line2.contains(point) ? "да" : "нет"));

        // Доп задание
        try {
            System.out.println("Параллельная прямой №1 через точку: " + line1.parallelThrough(point).getEquation());
        } catch (IllegalArgumentException e) {
            System.out.println("Параллельную прямой №1 построить нельзя: " + e.getMessage());
        }
        System.out.println("Перпендикуляр к прямой №1 через точку: " + line1.perpendicularThrough(point).getEquation());

        Point cross = line1.intersection(line2);
        if (cross != null) {
            System.out.println("Точка пересечения: " + cross);
        } else if (line1.contains(line2.getP1())) {
            System.out.println("Прямые совпадают, общих точек бесконечно много");
        } else {
            System.out.println("Точки пересечения нет,так как прямые параллельны");
        }
    }

    private static Line readLine(Scanner console, int number) {
        while (true) {
            System.out.print("Введите прямую №" + number + " в формате (x1 y1 x2 y2): ");
            try {
                Point p1 = new Point(console.nextDouble(), console.nextDouble());
                Point p2 = new Point(console.nextDouble(), console.nextDouble());
                return new Line(p1, p2);
            } catch (InputMismatchException e) {
                System.out.println("Ошибка, нужно вводить числа. Попробуйте ещё раз.");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка, " + e.getMessage() + ". Попробуйте ещё раз.");
            }
            console.nextLine(); // выбросить остаток неправильной строки
        }
    }

    private static Point readPoint(Scanner console) {
        while (true) {
            System.out.print("Введите координаты точки (x y): ");
            try {
                return new Point(console.nextDouble(), console.nextDouble());
            } catch (InputMismatchException e) {
                System.out.println("Ошибкa, нужно ввести два числа. Попробуйте ещё раз");
            }
            console.nextLine(); // выбросить остаток неправильной строки
        }
    }
}
