package senla.controller;

import lombok.extern.slf4j.Slf4j;
import senla.controller.helper.Constants;
import senla.model.Event;
import senla.model.Movie;
import senla.repository.*;
import senla.service.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
            } else if (step.equals("5")) {
                System.out.println(Constants.EDIT_EVENT);
                eventService.getAllEventsFull().forEach(System.out::println);
                System.out.println(Constants.INPUT_EVENT_FOR_EDIT);
                Integer id = Integer.valueOf(scanner.nextLine());

                Event event = eventService.getEventById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                                "с id= %s не найдено ", id)));

                System.out.println(Constants.INPUT_TIME);
                Integer DD;
                do {
                    System.out.println(Constants.DD);
                    DD = Integer.valueOf(scanner.nextLine());
                } while (DD < 1 || DD > 31);
                Integer MM;
                do {
                    System.out.println(Constants.MM);
                    MM = Integer.valueOf(scanner.nextLine());
                } while (MM < 1 || MM > 12);
                Integer YYYY;
                do {
                    System.out.println(Constants.YYYY);
                    YYYY = Integer.valueOf(scanner.nextLine());
                } while (YYYY < 2023 || YYYY > 3000);
                Integer HH24;
                do {
                    System.out.println(Constants.HH24);
                    HH24 = Integer.valueOf(scanner.nextLine());
                } while (HH24 < 0 || HH24 > 23);
                Integer MI;
                do {
                    System.out.println(Constants.MI);
                    MI = Integer.valueOf(scanner.nextLine());
                } while (MI < 0 || MI > 59);
                LocalDateTime MovieDateTime = null;
                try {
                    MovieDateTime = LocalDateTime.of(YYYY, MM, DD, HH24, MI);
                } catch (DateTimeParseException e) {
                    System.out.println(Constants.WRONG_DATE);
                }
                movieService.getAllMovies().forEach(System.out::println);
                System.out.println(Constants.INPUT_IDMOVIE);
                Integer movieId = Integer.valueOf(scanner.nextLine());
                Movie movie = movieService.getMovieById(movieId)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", movieId)));

                boolean IsEditEvent = false;
                if (movieId != null && MovieDateTime != null)  {
                    IsEditEvent = eventService.updateEvent(event.getEventId(), movieId, MovieDateTime);;
                }
                try {
                    if (IsEditEvent) {
                        log.info("Событие с id {} отредактировано пользователем {}", id, user.getUsername());
                    } else {
                        log.error("Ошибка при редактировании события");
                    }
                } catch (RuntimeException e) {
                    log.error("Что-то пошло не так. Обратитесь к администратору системы");
                }
             } else if (step.equals("6")) {
                System.out.println(Constants.EDIT_MOVIE_NAME);

                System.out.println(Constants.VIEW_MOVIES);
                movieService.getAllMovies().forEach(System.out::println);
                System.out.println(Constants.INPUT_IDMOVIE);
                Integer id = Integer.valueOf(scanner.nextLine());

                Movie movie = movieService.getMovieById(id)
                        .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                                "с id= %s не найдено ", id)));

                System.out.println(Constants.INPUT_MOVIE_NAME);
                String title = scanner.nextLine();

                boolean IsEditMovie = false;
                if (title != null) {
                    IsEditMovie=movieService.updateMovie(movie.getMovieId(), title);
                }
                try {
                    if (IsEditMovie) {
                        log.info("Фильм с id {} отредактировано пользователем {}", id, user.getUsername());
                    } else {
                        log.error("Ошибка при редактировании фильма");
                    }
                } catch (RuntimeException e) {
                    log.error("Что-то пошло не так. Обратитесь к администратору системы");
                }
            } else if (step.equals("0")) {
                GlobalController.start();
                break;
                //Вызов операции удаления товара
            }

        }
    }
}
