package senla.controller;

import lombok.extern.slf4j.Slf4j;
import senla.controller.helper.Constants;
import senla.controller.helper.Functions;
import senla.repository.*;
import senla.service.*;
import java.util.Scanner;

import static senla.controller.GlobalController.user;

@Slf4j
public class MenuManager {
    private static Scanner scanner = new Scanner(System.in);

    public static void MenuManager() {
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketServiceImpl(ticketRepository);
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieServiceImpl(movieRepository);

        while (true) {
            System.out.println(Constants.MANAGER_MENU);
            System.out.print(Constants.CHOISE);
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println(Constants.VIEW_EVENTS);
                eventService.getAllEventsFull().forEach(System.out::println);
            } else if (step.equals("2")) {
                System.out.println(Constants.BUY_TICKET);
                Functions.BuyTicket(eventService, ticketService);
            } else if (step.equals("3")) {
                System.out.println(Constants.RETURN_TICKET);
                Functions.ReturnTicket(eventService, ticketService);
            } else if (step.equals("4")) {
                System.out.println(Constants.VIEW_TICKETS);
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
            } else if (step.equals("5")) {
                System.out.println(Constants.EDIT_EVENT);
                Functions.UpdateEvent(eventService, movieService);
            } else if (step.equals("6")) {
                System.out.println(Constants.EDIT_MOVIE_NAME);
                Functions.UpdateMovie(movieService);
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
            } else {
                System.out.println("Нет такого пункта меню. Попробуйте еще раз.");
            }

        }
    }


}
