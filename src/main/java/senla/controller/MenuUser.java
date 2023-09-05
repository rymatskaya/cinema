package senla.controller;

import lombok.extern.slf4j.Slf4j;
import senla.controller.helper.Constants;
import senla.repository.*;
import senla.service.*;

import java.util.Scanner;

import static senla.controller.GlobalController.user;

@Slf4j

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
            System.out.println(Constants.USER_MENU);
            System.out.print(Constants.CHOISE);
            String step = scanner.nextLine();

            if (step.equals("1")) {
                System.out.println(Constants.VIEW_EVENTS);
                eventService.getAllEventsFull().forEach(System.out::println);
            } else if (step.equals("2")) {
                System.out.println(Constants.BUY_TICKET);
                System.out.println(Constants.CHOISE_EVENT);
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine());
                System.out.println(Constants.CHOISE_PLACE);
                String place = scanner.nextLine();
                boolean IsBuyTicket = false;
                if (eventId != null && place != null) {
                    IsBuyTicket = ticketService.buyTicket(eventId, place, user.getId());
                }
                try {
                    if (IsBuyTicket) {
                        log.info("Пользователь с логином {} купил билет с местом {}", user.getUsername(), place);
                    } else {
                        log.error("Ошибка при покупке билета");
                    }
                } catch (RuntimeException e) {
                    log.error("Что-то пошло не так. Обратитесь к администратору системы");
                }
            } else if (step.equals("3")) {
                System.out.println(Constants.RETURN_TICKET);
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
                System.out.println(Constants.CHOISE_EVENT);
                eventService.getAllEventsFull().forEach(System.out::println);
                Integer eventId = Integer.valueOf(scanner.nextLine());
                System.out.println(Constants.CHOISE_PLACE);
                String place = scanner.nextLine();
                boolean IsReturnTicket = false;
                if (eventId != null && place != null) {
                    IsReturnTicket = ticketService.returnTicket(eventId, place, user.getId());
                }
                try {
                    if (IsReturnTicket) {
                        log.info("Пользователь с логином {} вернул билет с местом {}", user.getUsername(), place);
                    } else {
                        log.error("Ошибка при возврате билета");
                    }
                } catch (RuntimeException e) {
                    log.error("Что-то пошло не так. Обратитесь к администратору системы");
                }
            } else if (step.equals("4")) {
                System.out.println(Constants.VIEW_TICKETS);
                ticketService.getUserTickets(user.getId()).forEach(System.out::println);
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
            }
        }
    }
}

