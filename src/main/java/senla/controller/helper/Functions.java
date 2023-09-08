package senla.controller.helper;

import lombok.extern.slf4j.Slf4j;
import senla.model.*;
import senla.service.EventService;
import senla.service.MovieService;
import senla.service.TicketService;
import senla.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static senla.controller.GlobalController.user;

@Slf4j
public class Functions {
    private static Scanner scanner = new Scanner(System.in);

    public static void addEvent(MovieService movieService, EventService eventService) {
        LocalDateTime MovieDateTime = getLocalDateTime();

        System.out.println(Constants.INPUT_IDMOVIE);
        Integer movieId = Integer.valueOf(scanner.nextLine());
        Movie movie = movieService.getMovieById(movieId)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", movieId)));

        Event event = new Event(movieId, MovieDateTime);
        boolean IsCreateEvent = false;
        if (movieId != 0) {
            IsCreateEvent = eventService.createEvent(event);
        }
        try {
            if (IsCreateEvent) {
                log.info("Событие успешно добавлено");
            } else {
                log.error("Не удалось добавить событие");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }

    }

    private static LocalDateTime getLocalDateTime() {
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
        return MovieDateTime;
    }

    public static void deleteUser(UserService userService) {
        System.out.println(Constants.INPUT_IDUSER);
        Integer id = scanner.nextInt();
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                        "с id= %s не найдено ", id)));
        boolean IsUserDelete = false;
        IsUserDelete = userService.deleteByAdmin(user.getId());
        try {
            if (IsUserDelete) {
                log.info("Пользователь с логином {} успешно удален", user.getUsername());
            } else {
                log.error("Не удалось удалить пользователя с логином {} ", user.getUsername());
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void updateUser(UserService userService) {
        System.out.println(Constants.INPUT_IDUSER);
        Integer id = Integer.valueOf(scanner.nextLine());
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Пользователя " +
                        "с id= %s не найдено ", id)));

        System.out.println(Constants.INPUT_LOGIN);
        String username = scanner.nextLine();
        System.out.println(Constants.INPUT_PASSWORD);
        String password = scanner.nextLine();
        System.out.println(Constants.INPUT_EMAIL);
        String email = scanner.nextLine();
        System.out.println(Constants.INPUT_ROLE);
        String role = scanner.nextLine();
        boolean IsUpdateUser = false;
        if (username != null && password != null && email != null && role != null) {
            IsUpdateUser = userService.updateByAdmin(user.getId(), username, password, email, UserRole.valueOf(role));
        }

        try {
            if (IsUpdateUser) {
                log.info("Пользователь с логином {} успешно изменен", username);
            } else {
                log.error("Не удалось изменить пользователя с логином {}", username);
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void addUser(UserService userService) {
        System.out.println(Constants.INPUT_LOGIN);
        String username = scanner.nextLine();
        System.out.println(Constants.INPUT_PASSWORD);
        String password = scanner.nextLine();
        System.out.println(Constants.INPUT_EMAIL);
        String email = scanner.nextLine();
        System.out.println(Constants.INPUT_ROLE);
        String role = scanner.nextLine();
        User user = new User(username, password, email, role);
        boolean IsCreateUser = false;
        if (!username.isEmpty() && !password.isEmpty() && !email.isEmpty() && !role.isEmpty()) {
            IsCreateUser = userService.createByAdmin(user);
        }
        try {
            if (IsCreateUser) {
                log.info("Пользователь с логином {} успешно создан", username);
            } else {
                log.error("Не удалось создать пользователя с логином {}", username);
            }
        } catch (RuntimeException e) {
            log.error("Регистрация не работает. Обратитесь к администратору системы");
        }
    }

    public static void EditEvent(MovieService movieService, EventService eventService) {
        System.out.println(Constants.INPUT_EVENT_FOR_EDIT);
        Integer id = Integer.valueOf(scanner.nextLine());

        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                        "с id= %s не найдено ", id)));

        LocalDateTime MovieDateTime = getLocalDateTime();

        System.out.println(Constants.INPUT_IDMOVIE);
        Integer movieId = Integer.valueOf(scanner.nextLine());
        Movie movie = movieService.getMovieById(movieId)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", movieId)));
        boolean IsUpdateEvent = false;
        if (movieId != null && MovieDateTime != null) {
            IsUpdateEvent = eventService.updateEvent(event.getEventId(), movieId, MovieDateTime);
        }
        try {
            if (IsUpdateEvent) {
                log.info("Событие успешно отредактировано.");
            } else {
                log.error("Не удалось отредактировать событие");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void AddMovie(MovieService movieService) {
        System.out.println(Constants.INPUT_MOVIE_NAME);
        String title = scanner.nextLine();
        boolean IsAddMovie = false;
        if (!title.isEmpty()) {
            Movie movie = new Movie(title);
            IsAddMovie = movieService.createMovie(movie);
        }
        try {
            if (IsAddMovie) {
                log.info("Фильм успешно добавлен");
            } else {
                log.error("Не удалось добавить фильм");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void EditMovie(MovieService movieService) {
        System.out.println(Constants.INPUT_IDMOVIE);
        Integer id = Integer.valueOf(scanner.nextLine());

        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", id)));

        System.out.println(Constants.INPUT_MOVIE_NAME);
        String title = scanner.nextLine();
        boolean IsUpdateMovie = false;
        if (title != null) {
            IsUpdateMovie = movieService.updateMovie(movie.getMovieId(), title);
        }
        try {
            if (IsUpdateMovie) {
                log.info("Фильм успешно обновлен");
            } else {
                log.error("Не удалось обновить фильм");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void DeleteMovie(MovieService movieService) {
        System.out.println(Constants.INPUT_IDMOVIE);
        Integer id = Integer.valueOf(scanner.nextLine());

        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", id)));

        boolean IsDeleteMovie = false;
        IsDeleteMovie = movieService.deleteMovie(movie.getMovieId());
        try {
            if (IsDeleteMovie) {
                log.info("Фильм успешно удален");
            } else {
                log.error("Не удалось удалить фильм");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void DeleteEvent(EventService eventService) {
        System.out.println(Constants.INPUT_IDEVENT);
        Integer id = Integer.valueOf(scanner.nextLine());

        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                        "с id= %s не найдено ", id)));
        boolean IsDeleteEvent = false;
        IsDeleteEvent = eventService.deleteEvent(event.getEventId());
        try {
            if (IsDeleteEvent) {
                log.info("Событие успешно удалено");
            } else {
                log.error("Не удалось удалить событие");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void DeleteTicket(TicketService ticketService) {
        System.out.println(Constants.INPUT_IDTICKET);
        Integer id = Integer.valueOf(scanner.nextLine());

        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Билета " +
                        "с id= %s не найдено ", id)));
        boolean IsDeleteTicket = false;
        IsDeleteTicket = ticketService.deleteTicket(ticket.getTicketId());
        try {
            if (IsDeleteTicket) {
                log.info("Билет успешно удален");
            } else {
                log.error("Не удалось удалить билет");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void EditTicket(TicketService ticketService) {
        System.out.println(Constants.INPUT_IDTICKET);
        Integer id = Integer.valueOf(scanner.nextLine());

        Ticket ticket = ticketService.getTicketById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Билета " +
                        "с id= %s не найдено ", id)));

        System.out.println(Constants.INPUT_IDEVENT);
        Integer eventId = Integer.valueOf(scanner.nextLine());
        System.out.println(Constants.INPUT_PLACE);
        String place = scanner.nextLine();
        System.out.println(Constants.INPUT_PRICE);
        Double price = Double.valueOf(scanner.nextLine());
        boolean IsUpdateTicket = false;
        if (eventId != null && place != null && price != null) {
            IsUpdateTicket = ticketService.updateTicket(ticket.getTicketId(), eventId, place, price);
        }
        try {
            if (IsUpdateTicket) {
                log.info("Билет успешно изменен");
            } else {
                log.error("Не удалось изменить билет");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void AddTicket(TicketService ticketService) {
        System.out.println(Constants.INPUT_IDEVENT);
        Integer eventId = Integer.valueOf(scanner.nextLine());
        System.out.println(Constants.INPUT_PLACE);
        String place = scanner.nextLine();
        System.out.println(Constants.INPUT_PRICE);
        Double price = Double.valueOf(scanner.nextLine());

        Ticket ticket = new Ticket(0, eventId, price, place, 0);
        boolean IsAddTicket = false;
        IsAddTicket = ticketService.createTicket(ticket);

        try {
            if (IsAddTicket) {
                log.info("Билет успешно добавлен");
            } else {
                log.error("Не удалось добавить билет");
            }
        } catch (RuntimeException e) {
            log.error("Операция не работает. Обратитесь к администратору системы");
        }
    }

    public static void UpdateMovie(MovieService movieService) {
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
            IsEditMovie = movieService.updateMovie(movie.getMovieId(), title);
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
    }

    public static void UpdateEvent(EventService eventService, MovieService movieService) {
        eventService.getAllEventsFull().forEach(System.out::println);
        System.out.println(Constants.INPUT_EVENT_FOR_EDIT);
        Integer id = Integer.valueOf(scanner.nextLine());

        Event event = eventService.getEventById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Сеанса " +
                        "с id= %s не найдено ", id)));

        LocalDateTime MovieDateTime = getLocalDateTime();
        movieService.getAllMovies().forEach(System.out::println);
        System.out.println(Constants.INPUT_IDMOVIE);
        Integer movieId = Integer.valueOf(scanner.nextLine());
        Movie movie = movieService.getMovieById(movieId)
                .orElseThrow(() -> new RuntimeException(String.format("Фильма " +
                        "с id= %s не найдено ", movieId)));

        boolean IsEditEvent = false;
        if (movieId != null && MovieDateTime != null) {
            IsEditEvent = eventService.updateEvent(event.getEventId(), movieId, MovieDateTime);
            ;
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
    }

    public static void ReturnTicket(EventService eventService, TicketService ticketService) {
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
    }

    public static void BuyTicket(EventService eventService, TicketService ticketService) {
        System.out.println(Constants.CHOISE_EVENT);
        eventService.getAllEventsFull().forEach(System.out::println);
        Integer eventId = Integer.valueOf(scanner.nextLine());
        ticketService.getAllTickets().forEach(System.out::println);
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
    }

    public static void BuyTicketUser(EventService eventService, TicketService ticketService) {
        System.out.println(Constants.CHOISE_EVENT);
        eventService.getAllEventsFull().forEach(System.out::println);
        Integer eventId = Integer.valueOf(scanner.nextLine());
        System.out.println(Constants.CHOISE_PLACE);
        ticketService.getTicketsBySold(eventId).forEach(System.out::println);
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
    }

    public static void ReturnTicketUser(EventService eventService, TicketService ticketService) {
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
    }

    public static void Registration(UserService userService) {
        System.out.println(Constants.REGISTRATION_MENU);
        System.out.println(Constants.INPUT_LOGIN);
        String username = scanner.nextLine();
        System.out.println(Constants.INPUT_PASSWORD);
        String password = scanner.nextLine();
        System.out.println(Constants.INPUT_EMAIL);
        String email = scanner.nextLine();
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        Matcher matcher = pattern.matcher(email);

        if (matcher.find()) {
            User user = new User(username, password, email);
            try {
                if (userService.create(user)) {
                    log.info("Пользователь с логином {} успешно создан", username);
                } else {
                    log.error("Не удалось создать пользователя с логином {}", username);
                }
            } catch (RuntimeException e) {
                log.error("Регистрация не работает. Обратитесь к администратору системы");
            }
        }
        else {
            System.out.println("Неверно введен email");
        }
    }
}
