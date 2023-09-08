package senla.controller;

import lombok.extern.slf4j.Slf4j;
import senla.controller.helper.Constants;
import senla.controller.helper.Functions;
import senla.repository.*;
import senla.service.*;
import java.util.Scanner;

@Slf4j

public class MenuAdmin {
    private static Scanner scanner = new Scanner(System.in);

    public static void menuAdmin() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        MovieRepository movieRepository = new MovieRepositoryImpl();
        MovieService movieService = new MovieServiceImpl(movieRepository);
        EventRepository eventRepository = new EventRepositoryImpl();
        EventService eventService = new EventServiceImpl(eventRepository);
        TicketRepository ticketRepository = new TicketRepositoryImpl();
        TicketService ticketService = new TicketServiceImpl(ticketRepository);

        while (true) {

            System.out.println(Constants.ADMIN_MENU);
            System.out.print(Constants.CHOISE);
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println(Constants.ADD_USER);
                Functions.addUser(userService);

            } else if (step.equals("2")) {
                System.out.println(Constants.EDIT_USER);
                Functions.updateUser(userService);

            } else if (step.equals("3")) {
                System.out.println(Constants.DELETE_USER);
                Functions.deleteUser(userService);

            } else if (step.equals("4")) {
                System.out.println(Constants.VIEW_USERS);
                userService.getAll().forEach(System.out::println);

            } else if (step.equals("5")) {
                System.out.println(Constants.ADD_MOVIE);
                Functions.AddMovie(movieService);

            } else if (step.equals("6")) {
                System.out.println(Constants.EDIT_MOVIE);
                Functions.EditMovie(movieService);
            } else if (step.equals("7")) {
                System.out.println(Constants.DELETE_MOVIE);
                Functions.DeleteMovie(movieService);
            } else if (step.equals("8")) {
                System.out.println(Constants.VIEW_MOVIES);
                movieService.getAllMovies().forEach(System.out::println);
            } else if (step.equals("9")) {
                System.out.println(Constants.ADD_EVENT);
                Functions.addEvent(movieService, eventService);
            } else if (step.equals("10")) {
                System.out.println(Constants.EDIT_EVENT_ADMIN);
                Functions.EditEvent(movieService, eventService);
            } else if (step.equals("11")) {
                System.out.println(Constants.DELETE_EVENT);
                Functions.DeleteEvent(eventService);
            } else if (step.equals("12")) {
                System.out.println(Constants.VIEW_EVENTS);
                eventService.getAllEvents().forEach(System.out::println);
            } else if (step.equals("13")) {
                System.out.println(Constants.ADD_TICKET);
                Functions.AddTicket(ticketService);
            } else if (step.equals("14")) {
                System.out.println(Constants.EDIT_TICKET);
                Functions.EditTicket(ticketService);
            } else if (step.equals("15")) {
                System.out.println(Constants.DELETE_TICKET);
                Functions.DeleteTicket(ticketService);
            } else if (step.equals("16")) {
                System.out.println(Constants.VIEW_TICKETS);
                ticketService.getAllTickets().forEach(System.out::println);
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
            } else {
                System.out.println("Нет такого пункта меню. Попробуйте еще раз.");
            }
        }
    }


}
