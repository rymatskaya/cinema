package senla.controller;

import java.util.Scanner;

public class MenuUser {
    private static Scanner scanner = new Scanner(System.in);

    public static void menuUser() {
        while (true) {
            String str = """
                     1 - Просмотр мероприятий
                     2 - Купить билет
                     3 - Вернуть билет
                     4 - Просмотреть купленные билеты
                     0 - Вернуться в главное меню                   
                                        
                    """;
            System.out.println(str);
            System.out.print("Выберите вариант меню: ");
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println("Просмотр мероприятий");
                //Вызов операции вывода списка мероприятий
            } else if (step.equals("2")) {
                System.out.println("Покупка билета");
                //Вызов операции покупки билета
            } else if (step.equals("3")) {
                System.out.println("Возврата билета");
                //Вызов операции удаления товара
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
                //Вызов операции удаления товара
            }

        }
    }
}

