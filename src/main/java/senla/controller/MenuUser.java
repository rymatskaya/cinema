package senla.controller;

import senla.model.User;
import senla.repository.*;
import senla.service.*;
import senla.controller.GlobalController;

import java.util.Scanner;

import static senla.controller.GlobalController.user;

public class MenuUser {
    private static Scanner scanner = new Scanner(System.in);

    public static void menuUser() {
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketServiceImpl(ticketRepository);
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);

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
                eventService.getAllEventsFull().forEach(System.out::println);
            } else if (step.equals("2")) {
                System.out.println("Покупка билета");
                System.out.println("Выберите мероприятие и введите его номер");
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine()) ;
                System.out.println("Введите место ");
                String place = scanner.nextLine();
                if (eventId != null && place != null  ) {
                    ticketService.buyTicket(eventId, place, user.getId());
                }
            } else if (step.equals("3")) {
                System.out.println("Возврат билета");
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
                System.out.println("Выберите мероприятие и введите его номер");
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine()) ;
                System.out.println("Введите место ");
                String place = scanner.nextLine();
                if (eventId != null && place != null  ) {
                    ticketService.returnTicket(eventId, place, user.getId());
                }
            } else if (step.equals("4")) {
                System.out.println("Просмотр купленных билетов: ");
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
                //Вызов операции удаления товара
            }

        }
    }
}

